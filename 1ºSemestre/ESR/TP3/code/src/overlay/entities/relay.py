import logging
import time
from typing import Dict, List

from p2p.node import Node
from p2p.conn import Connection
from utils.common import *


class Relay(Node):
    """
    A relay is a node that can request a stream from the network.
    Any node of the p2p network must implement at least some of the event methods provided by Node.
    """

    def __init__(self, ip: str, port: int, cfg_file: Dict, bootstrap=False):
        super(Relay, self).__init__(ip, port)
        self.cfg_file: Dict = cfg_file
        # distance to a stream source
        # this stream source is the closest relay with
        # a stream proxy set up the distance is increased
        # by one for each hop.
        self.distance: int = 2147483647  # 2^31 - 1
        # list of connections that we'll proxy to
        self.proxy_clients: List[Connection] = []
        # is this a bootstrap node?
        self.bootstrap: bool = bootstrap
        # list of connections that are in recovery mode
        self.reco_conn: List[Connection] = []
        self.next_hello: float = time.time() + 5  # 5 seconds between hello messages

    def proxy(self, data):
        """
        RTP Proxy.
        Receives messages from another relay/streamer.
        Example messages:
        S + StreamerID + RTPPacket # this totals to 17 bytes of overhead + RTPPacket
        """
        # sends the stream data to all clients
        for conn in self.proxy_clients:
            self.unicast(conn, data)

    def connect_to_peers(self):
        """
        Connects to all peers in the config file.
        If a peer is already connected, it will not be connected again.
        If a connection fails, the peer is added to the reco_conn list, unless it's already in the list.
        """

        for peer in self.cfg_file['peers']:
            try:
                self.connect(peer['ip'], peer['port'])
            except Exception as e:
                if not peer in self.reco_conn:
                    self.reco_conn.append(peer)

    def reconnect_to_peers(self):
        """
        When a peer connection fails the peer is added to the reco_conn list.
        When this method is called, the relay will try to reconnect to the peer.
        If the connection is successful, the peer is removed from the reco_conn list.
        """
        for peer in self.reco_conn:
            try:
                self.connect(peer['ip'], peer['port'])
                self.reco_conn.remove(peer)
            except Exception as e:
                continue

    def request_stream(self):
        """
        Request a stream from the closest peer.
        """
        # get the closest peer
        closest_peer = min(self.nodes(), key=lambda x: x.distance)
        # send the request to the closest peer
        self.unicast(closest_peer, packet_factory(
            self.id, '', HeaderTypes.REQUEST))

    def main_loop(self):
        """
        The main loop of the relay.
        The relay will first attempt to connect to other peers then start the 'Hello' message exchange.
        """

        # bootstrap nodes don't connect to other nodes.
        # these serve as the initial nodes of the p2p overlay network.
        if not self.bootstrap:
            # Try to connect to our network peer
            self.connect_to_peers()

        # infinite loop
        while True:
            cur_time = time.time()

            # check if we need to send a hello message
            if cur_time > self.next_hello:
                logging.info("Sending hello message")
                self.multicast("Hello World!")
                self.next_hello = cur_time + 5  # 5 seconds between hello messages

            # check if we need to reconnect to peers
            if len(self.reco_conn) > 0:
                self.reconnect_to_peers()

            time.sleep(0.01)

    ###################################################################################
    #                                                                                 #
    #                                  Event methods                                  #
    #                                                                                 #
    #   These override the methods in Node to provide the functionality               #
    #   of the relay.                                                                 #
    #                                                                                 #
    ###################################################################################

    def conn_msg(self, conn, data):
        """
        Override this method if necessary.
        @param conn: The connection that has received data.
        @param data: The data that has been received.
        """
        # if isinstance(data,bytes):
        #    tmp = data.decode()
        #    if tmp[0] == 'S':
        #        logging.debug("Received stream data from %s:%d" %(conn.ip, conn.port))
        #    # proxy the stream data to all clients
        #        self.proxy(tmp)
        # handle the packets by checking the first character
        if len(data):
            if data[0] == 'H':
                logging.debug("Received hello from %s:%d" % (conn.ip, conn.port))
                pass
            elif data[0] == 'D':
                logging.debug("Received hearbeat from %s:%d" %
                              (conn.ip, conn.port))

                # parse the distance
                distance = unpack_heartbeat(data)
                # call the heartbeat event handler
                self.on_heartbeat(conn, distance)

            elif data[0] == 83:
                logging.debug("Received stream data from %s:%d" %
                              (conn.ip, conn.port))
                # proxy the stream data to all clients
                self.proxy(data)

            elif data[0] == 'R':
                logging.debug("Received stream request from %s:%d" %
                              (conn.ip, conn.port))

                self.on_request(conn)
            else:
                logging.warning("Unknown packet received: %s" % data)

    ###################################################################################
    #                                                                                 #
    #                                Event handlers                                   #
    #                                                                                 #
    #   These override the methods in Node to provide the functionality               #
    #   of the relay.                                                                 #
    #                                                                                 #
    ###################################################################################

    def on_heartbeat(self, conn, distance):
        """
        Called when a heartbeat is received. The distance is used to determine
        the distance from this relay to the streamer node. The distance is increased by
        one for each hop and a new heartbeat is multicasted to all connected nodes.

        @param conn: The connection that received the heartbeat.
        @param distance: The distance to the closest stream source.
        """
        # update the connection's distance
        conn.distance = distance

        # check if distance is smaller than current distance
        if distance <= self.distance:
            # update distance
            self.distance = distance
            # multicast the hearbeat with distance+1  to all connected nodes
            self.multicast(packet_factory(
                self.id, self.distance + 1, HeaderTypes.HEARTBEAT), [conn])

    def on_request(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that received the hello.
        @param distance: The distance to the closest stream source.
        """
        # add the client to the list of clients, if it's not already in the list
        if not conn in self.proxy_clients:
            self.proxy_clients.append(conn)
            logging.info("Client %s:%d added to proxy list" %
                         (conn.ip, conn.port))

        # if we don't have a streamer, request it from our peers
        if self.distance > 0:
            # request the stream from the closest peer
            self.request_stream()

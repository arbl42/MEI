import hashlib
import socket
import time
import threading
import logging

from p2p.conn import Connection


class Node(threading.Thread):
    def __init__(self, ip, port, debug_level=0):
        """
        Initializes the node.
        @param ip: The IP address of the node.
        @param port: The port of the node.
        @param debug_level: The debug level of the logger.
        """
        super(Node, self).__init__()

        self.ip = ip       # IP address of the node
        self.port = port   # Port to listen on

        self.out_conn = []  # list of connections to other nodes
        self.in__conn = []  # list of connections from other nodes\\

        self.id = hashlib.sha256(str(self.ip).encode(
        ) + str(self.port).encode()).hexdigest()[:16]

        # create and setup the local socket
        self.local_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.local_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        self.local_socket.bind((self.ip, self.port))
        self.local_socket.settimeout(5)
        self.local_socket.listen(1)

        # When set the thread will terminate.
        self.terminate_flag = threading.Event()
        logging.info("Node %s:%d with id %s created" %
                     (self.ip, self.port, self.id))

    def nodes(self):
        """
        Returns a list of all nodes connected to this node.
        @return: A list of connections.
        """
        return self.out_conn + self.in__conn

    def nodes_total(self):
        """
        Returns the total number of nodes connected to this node.
        @return: The total number of nodes connected to this node.
        """
        return len(self.nodes())

    def multicast(self, data, blacklist=[]):
        """
        Sends a message to all nodes connected to this node.
        Excludes all connections in the blacklist. This blacklist may prove useful in message flooding.
        @param data: The data to send.
        @param blacklist: A list of connections to exclude from the multicast.
        """
        logging.debug("Multicasting message to %d nodes, excluding %d nodes" % (
            len(self.nodes()), len(blacklist)))
        for conn in self.nodes():
            if conn not in blacklist:
                self.unicast(conn, data)

    def unicast(self, conn, data):
        """
        Sends a message to a single connection.
        @param conn: The connection to send the message to.
        @param data: The data to send.
        """
        conn.send(data)
        logging.debug("Unicasting message to %s:%d" % (conn.ip, conn.port))

    def connect(self, ip, port) -> bool:
        """
        Connects to another node. Returns a boolean indicating success.

        @param ip: The IP address of the node to connect to.
        @param port: The port of the node to connect to.
        @return: A boolean indicating success. True if the connection was successful or the connection already exists. False if the connection failed.
        """

        # we can't connect to a node more than once
        if [conn for conn in self.out_conn if conn.ip == ip and conn.port == port]:
            logging.debug("Connection to %s:%d already exists" % (ip, port))
            return True

        try:
            sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            sock.connect((ip, port))
            sock.send(self.id.encode())  # send the node's id to the other node

            peer_id = sock.recv(16).decode()  # receive the other node's id

            # no two nodes can have the same id
            if peer_id in [conn.id for conn in self.in__conn]:
                logging.debug("Connection to %s:%d already exists" %
                              (ip, port))
                return True

            conn = self.new_connection(ip, port, peer_id, sock)
            conn.start()  # start the connection thread
            # add the connection to the list of out connections
            self.out_conn.append(conn)
            # Notify that a new connection has been established.
            self.conn_out(conn)
            return True

        except Exception as e:
            logging.error("Connection failed to %s:%d" % (ip, port))
            return False

    def disconnect(self, conn):
        """
        Terminates the out connection to a given node.
        @param conn: The connection to terminate. Must be a valid out connection.
        @return: A boolean indicating success. True if the connection was terminated. False if the connection was not found.
        """
        if conn in self.out_conn:
            conn.terminate()
            self.out_conn.remove(conn)
            logging.info("Connection to %s:%d terminated" %
                         (conn.ip, conn.port))
            return True

        logging.error("Connection to %s:%d not found" % (conn.ip, conn.port))
        return False

    def terminate(self):
        """
        Terminates the node.
        """
        self.terminate_flag.set()
        self._terminate()

    def new_connection(self, ip, port, peer_id, socket):
        """
        Override this method if necessary.
        @param ip: The IP address of the node.
        @param port: The port of the node.
        @param peer_id: The id of the node.
        @param socket: The socket of the node.
        @return: A Connection object.
        """
        return Connection(ip, port, peer_id, socket, self)

    # Necessary for the threading.Thread class

    def run(self):
        """

        """
        while not self.terminate_flag.is_set():
            try:
                # accept a new connection
                conn, addr = self.local_socket.accept()
                logging.debug("New connection from %s:%d" % (addr[0], addr[1]))

                peer_id = conn.recv(16).decode()  # receive the other node's id
                logging.debug("Received id %s from %s:%d" %
                              (peer_id, addr[0], addr[1]))

                # send the node's id to the other node
                conn.send(self.id.encode())

                # create a new connection, append it to the list of connections
                conn = Connection(addr[0], addr[1], peer_id, conn, self)
                conn.start()

                self.in__conn.append(conn)
                # Notify that a new in connection has been established.
                self.conn_in(conn)

            except socket.timeout:
                # this is bound to happen if the node is not connected to any other nodes
                logging.debug("Socket timeout occurred")

            except Exception as e:
                logging.error("Exception occurred: %s" % e)
                raise e

            time.sleep(0.01)  # sleep for 10ms to prevent high CPU usage

        # when a node reaches this point, it is bound for termination. Try to do as much cleanup as possible before terminating.
        logging.info("Node %s:%d terminating" % (self.ip, self.port))
        for conn in self.nodes():
            conn.terminate()

        # try to wait for all connections to terminate
        time.sleep(2)

        # join all threads
        for conn in self.nodes():
            conn.join()

        # close the socket
        self.local_socket.close()
        logging.info("Node %s:%d terminated" % (self.ip, self.port))

    def node_disconnected(self, conn):
        """
        Occurs when a node disconnects. Make sure to remove the node from the respective list.
        @param conn: The connection that disconnected.
        """
        if conn in self.in__conn:
            self.in__conn.remove(conn)
            # Notify that a connection (in) has been terminated.
            self.dconn_in(conn)
        if conn in self.out_conn:
            self.out_conn.remove(conn)
            # Notify that a connection (out) has been terminated.
            self.dconn_out(conn)

        conn.terminate()

    ###################################################################################
    #                                                                                 #
    #                                  Event methods                                  #
    #                                                                                 #
    #        Override these the methods to provide the necessary functionality        #
    #                                                                                 #
    ###################################################################################

    def conn_out(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that has been established.
        """
        logging.info("Connection to %s:%d" % (conn.ip, conn.port))

    def conn_in(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that has been established.
        """
        logging.info("Connection from %s:%d" % (conn.ip, conn.port))

    def dconn_in(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that has been terminated.
        """
        logging.info("Connection terminated from %s:%d" % (conn.ip, conn.port))

    def dconn_out(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that has been terminated.
        """
        logging.info("Connection terminated to %s:%d" % (conn.ip, conn.port))

    def conn_msg(self, conn, data):
        """
        Override this method if necessary.
        @param conn: The connection that has received data.
        @param data: The data that has been received.
        """
        logging.info("Data received from %s:%d" % (conn.ip, conn.port))

    def _terminate(self):
        """
        Override this method if necessary.
        """
        logging.info("Node %s:%d terminated" % (self.ip, self.port))

    ###################################################################################
    #                                                                                 #
    #                                Event handlers                                   #
    #                                                                                 #
    #        Override these the methods to provide the necessary functionality        #
    #                                                                                 #
    ###################################################################################

    def on_heartbeat(self, conn, distance):
        """
        Override this method if necessary.
        @param conn: The connection that received the heartbeat.
        @param distance: The distance to the closest stream source.
        """
        pass

    def on_request(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that received the hello.
        @param distance: The distance to the closest stream source.
        """
        pass

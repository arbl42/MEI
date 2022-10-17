import logging
import time

from utils.common import *
from p2p.node import Node
from utils.videostream import VideoStream
from utils.rtppacket import RtpPacket


class Streamer(Node):
    """
    A streamer is a node that can request a stream from the network.
    Any node of the p2p network must implement at least some of the event methods provided by Node.
    """
    def __init__(self, ip, port, cfg_file, stream):
        super(Streamer, self).__init__(ip, port)
        self.cfg_file = cfg_file
        self.next_hello = time.time() + 5  # 5 seconds
        self.heartbeat_timer = time.time() + 2  # 2 seconds heartbeat
        self.stream = VideoStream(stream)

    def heartbeat(self):
        """
        Heartbeats are responsible for updating the distance to the streamer node.
        """
        # unicast to the first peer in self.out_conns
        if len(self.out_conn) > 0:
            self.unicast(
                self.out_conn[0], packet_factory(self.id, 0, HeaderTypes.HEARTBEAT))

    def send_frame(self, data):
        if len(self.out_conn) > 0:
            self.unicast(self.out_conn[0],
                         packet_factory(self.id, data, HeaderTypes.STREAM))

    def makeRTP(self, payload, frameNumber):
        version = 2
        padding = 0
        extension = 0
        cc = 0
        marker = 0
        pt = 26  # uma vez que o formato do ficheiro é Mjpeg então é 26
        seqnum = frameNumber
        ssrc = 0
        packet = RtpPacket()
        packet.encode(version, padding, extension, cc,
                      seqnum, marker, pt, ssrc, payload)

        return packet.getPacket()

    def main_loop(self):
        """
        The main loop of the streamer.
        The streamer will first attempt to connect to other peers then start the 'Hello' message exchange.
        """
        # Try to connect to our network peer.
        # If this fails, terminate the process.
        try:
            self.connect(self.cfg_file['peer']['ip'],
                         self.cfg_file['peer']['port'])
        except Exception as e:
            self.terminate()
            time.sleep(3)
            logging.error("Error connecting to peer: %s" % e)
            exit(1)

        # infinite loop
        while True:
            # check if we need to send a heartbeat
            if time.time() > self.heartbeat_timer:
                logging.info("Sending heartbeat\n\n\n\n")
                self.heartbeat()
                self.heartbeat_timer = time.time() + 2

            data = self.stream.nextFrame()
            frameNumber = self.stream.frameNbr()
            packet = self.makeRTP(data, frameNumber)
          
            self.send_frame(packet)
            time.sleep(0.05)

import logging
import time

from tkinter import *
from tkinter import messagebox
from PIL import Image, ImageTk
from p2p.node import Node
from utils.common import *
from utils.rtppacket import RtpPacket


CACHE_FILE_NAME = "cache-"
CACHE_FILE_EXT = ".jpg"


class Client(Node):
    """
    A client is a node that can request a stream from the network.
    Any node of the p2p network must implement at least some of the event methods provided by Node.
    """

    def __init__(self, ip, port, cfg_file, tk):
        super(Client, self).__init__(ip, port)
        self.cfg_file = cfg_file
        #self.next_hello = time.time() + 5
        #self.heartbeat_timer = time.time() + 2
        self.master = tk
        self.count = 0

    def createWidgets(self):
        # Create Setup button
        self.setup = Button(self.master, width=20, padx=3, pady=3)
        self.setup["text"] = "Setup"
        #self.setup["command"] = self.setupMovie

        # START
        self.setup.grid(row=1, column=0, padx=2, pady=2)
        self.start = Button(self.master, width=20, padx=3, pady=3)
        self.start["text"] = "Play"
        #self.start["command"] = self.listenRTP
        self.start.grid(row=1, column=1, padx=2, pady=2)

        self.label = Label(self.master, height=19)
        self.label.grid(row=0, column=0, columnspan=4,
                        sticky=W+E+N+S, padx=5, pady=5)

    def writeFrame(self, data):
        cachename = "./cache/" + CACHE_FILE_NAME + \
            str(self.count) + CACHE_FILE_EXT
        file = open(cachename, "wb")
        file.write(data)
        file.close()
        self.count = self.count + 1
        return cachename

    def updateMovie(self, imageFile):
        try:
            photo = ImageTk.PhotoImage(Image.open(imageFile))
            self.label.configure(image=photo, height=288)
            self.label.image = photo
            print("Estou aqui")
        except:
            pass

    def main_loop(self):
        """
        The main loop of the client.
        """
        try:
            self.connect(self.cfg_file['peer']['ip'],
                         self.cfg_file['peer']['port'])
            self.createWidgets()
        except Exception as e:
            self.terminate()
            time.sleep(3)
            logging.error("Error connecting to peer %s" % e)
            exit(1)

        while True:
            time.sleep(0.05)

    ###################################################################################
    #                                                                                 #
    #                                  Event methods                                  #
    #                                                                                 #
    #   These override the methods in Node to provide the functionality               #
    #   of the client.                                                                #
    #                                                                                 #
    ###################################################################################

    def conn_msg(self, conn, data):
        """
        Override this method if necessary.
        @param conn: The connection that has received data.
        @param data: The data that has been received.
        """
        if len(data):
            if data[0] == 'D':
                logging.debug("Received hearbeat from %s:%d" %
                              (conn.ip, conn.port))
                self.on_heartbeat(conn, 0)
            if data[0] == 83:
                logging.info("Stream received from %s:%d" % (conn.ip, conn.port))
                rtp = unpack_stream(data)
                rtp_packet = RtpPacket()
                rtp_packet.decode(rtp)
                self.updateMovie(self.writeFrame(rtp_packet.getPayload()))
                # self.master.update()

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
        Override this method if necessary.
        @param conn: The connection that received the heartbeat.
        @param distance: The distance to the closest stream source.
        """
        # when an heartbeat is received, the client sends a request
        request = packet_factory(None, None, HeaderTypes.REQUEST)
        self.unicast(conn, request)

    def on_request(self, conn):
        """
        Override this method if necessary.
        @param conn: The connection that received the hello.
        @param distance: The distance to the closest stream source.
        """
        # client is the one that requests the stream
        pass

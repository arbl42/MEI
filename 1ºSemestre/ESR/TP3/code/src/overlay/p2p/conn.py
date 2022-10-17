import threading
import socket
import logging
import time


class Connection(threading.Thread):
    """
    A connection is a thread that contains the socket with which we can comunicate with another node.
    """

    def __init__(self, ip, port, id, socket, node):
        """
        @param ip: The IP address of the remote node.
        @param port: The port of the remote node.
        @param id: The id of the remote node.
        @param socket: The socket with which we can comunicate with the remote node.
        @param node: The node that owns this connection.
        """
        super(Connection, self).__init__()

        self.ip = ip
        self.port = port
        self.id = id
        self.socket = socket
        self.node = node
        self.terminate_flag = threading.Event()
        self.terminate_flag.clear()
        self.distance: int = 2147483647  # 2^31 - 1

        logging.info("Connection created to %s:%d" % (ip, port))
    
    def send(self, data):
        """
        Sends data to the remote node.
        @param data: The data to send.
        """
        if isinstance(data,str):
            data = data.encode() + 0x04.to_bytes(1, byteorder='big')
        elif isinstance(data,bytes):
        # transform the data into a byte array and append the EOT character
            data = data + 0x04.to_bytes(1, byteorder='big')
        # send the data
        self.socket.send(data)

    def terminate(self):
        """
        Terminates the connection.
        """
        self.terminate_flag.set()
        logging.info("Connection to %s:%d terminated" % (self.ip, self.port))

    def packet_parse(self, packet):
        """
        Parses a packet received from the remote node.
        @param packet: The packet to parse.
        """
        try:
            data = packet.decode()
            logging.debug("Received packet from %s:%d: %s" %
                          (self.ip, self.port, data))
            return data
        except Exception as e:
            logging.error("Received stream packet from %s:%d" %
                          (self.ip, self.port))
            return packet

    def run(self):
        """
        The thread's main loop.
        Continuously receives packets and stores them in a queue.
        Parse the queue using the EOT (end of transmission) character.
        """
        queue = b''

        while not self.terminate_flag.is_set():
            tmp = b''
            time.sleep(0.01)

            try:
                tmp = self.socket.recv(20480)
            except socket.timeout:
                logging.debug("Socket timeout")
                continue

            # terminate the connection if an error occurs while receiving data from the socket
            except Exception as e:
                self.terminate()
            
            if tmp != b'':
                queue += tmp
                index = get_index(queue)
                # parse the queue until no more packets are found
                while index != -1:
                    # a packet is the data until the next EOT character
                    data = queue[:index]
                    # remove the EOT character from the queue
                    queue = queue[index + 1:]
                    # find the next EOT character
                    index = get_index(queue)
                    self.node.conn_msg(self, self.packet_parse(data))


def get_index(queue):
    
    if len(queue) == 0:
        return -1
    if queue[0] == 83: 
        # overhead = 17
               # 1 - tipo
               # 16 - id do packet
               # 12 - header
        index = 4 + 1 + int.from_bytes(queue[1:5],byteorder="big")
    else: 
        index = queue.find(b'\x04')

    return index 

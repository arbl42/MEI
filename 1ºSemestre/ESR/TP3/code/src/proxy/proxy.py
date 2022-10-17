import sys
import socket
import threading
from overlay.utils.rtppacket import RtpPacket
import time


class Proxy:

    def __init__(self, server, server_port):
        self.server = server
        self.server_port = server_port
        self.clients = []
        self.count = 0
 
    def proxy_handler(self):
        while True:
            time.sleep(0.05)
            packet = self.server.recv(20480)
            if packet: 
                rtpPacket = RtpPacket()
                rtpPacket.decode(packet)
                currFrameNumber = rtpPacket.seqNum()
                print(currFrameNumber)
            for sock in self.clients:
                sock.send(packet)
 
    def server_loop(self):
        server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        try:
            print(f"Starting on {self.server} {self.server_port}")
            server.bind((self.server,self.server_port))
        except:
            print(f"Failed to listen on {self.server}:{self.server_port}")

        server.listen()

        while True:
            client_socket,address = server.accept()
            print(f"Got a connection from {client_socket}")
            # Isto aqui foi uma pequena "batota" para quando o cliente ligasse ser logo adicionado à lista dos clientes
            if self.count>0:
                self.clients.append(client_socket)
            else:
                self.server = client_socket
            self.count = 2

            proxy_thread = threading.Thread(target=self.proxy_handler, args=([]))
            proxy_thread.start()
 
# Ip do Router, Porta do Router
proxy = Proxy(sys.argv[1],int(sys.argv[2]))
proxy.server_loop()

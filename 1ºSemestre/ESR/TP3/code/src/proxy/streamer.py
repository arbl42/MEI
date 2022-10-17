import socket
from rtppacket import RtpPacket
import time
from videostream import VideoStream


class Streamer(object):
     def __init__(self):
        self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.video = VideoStream("movie.Mjpeg")

     def makeRTP(self, payload, frameNumber):
          version = 2
          padding = 0
          extension = 0
          cc = 0
          marker = 0
          pt = 26 # uma vez que o formato do ficheiro é Mjpeg então é 26 
          seqnum = frameNumber
          ssrc = 0

          packet = RtpPacket()
          packet.encode(version, padding, extension, cc, seqnum, marker, pt, ssrc, payload)
          
          return packet.getPacket() 
     
     def start(self):
          self.server.connect(('10.0.1.1',5679))
          while True:
               time.sleep(0.05)
               data = self.video.nextFrame()
               frameNumber = self.video.frameNbr()
               print(frameNumber)
               packet = self.makeRTP(data,frameNumber)
               self.server.send(packet)               

teste = Streamer()
teste.start()



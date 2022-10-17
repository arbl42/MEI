import time
from tkinter import *
from tkinter import messagebox
from PIL import Image, ImageTk
import socket, threading, sys, traceback, os
from rtppacket import RtpPacket

CACHE_FILE_NAME = "cache-"
CACHE_FILE_EXT = ".jpg"



class Client:
    INIT = 0
    READY = 1
    PLAYING = 2
    state = INIT
    
    SETUP = 0
    PLAY = 1
    PAUSE = 2
    TEARDOWN = 3
    
	
	# Initiation..
    def __init__(self, master):
        self.master = master
        #self.master.protocol("WM_DELETE_WINDOW", self.handler)
        self.startConnection()
        self.createWidgets()
        self.count = 0
        #self.listenRTP()
		
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
        self.label.grid(row=0, column=0, columnspan=4, sticky=W+E+N+S, padx=5, pady=5) 


    def startConnection(self):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.sock.connect(('10.0.1.1', 5679))
            self.sock.send(f"1".encode())

        except:
            messagebox.showwarning('Connection failed!')
    
    def listenRTP(self):
        while True:
            time.sleep(0.05)
            data = self.sock.recv(20480)
            if data:
                rtp_packet = RtpPacket()
                rtp_packet.decode(data)
                self.updateMovie(self.writeFrame(rtp_packet.getPayload()))
                self.master.update()


    def writeFrame(self, data):
        cachename = "./cache/" + CACHE_FILE_NAME + str(self.count) + CACHE_FILE_EXT
        file = open(cachename, "wb")
        file.write(data)
        file.close()
        self.count = self.count + 1
        return cachename


    def updateMovie(self, imageFile):
        try:
            photo = ImageTk.PhotoImage(Image.open(imageFile))
            self.label.configure(image = photo, height=288)
            self.label.image = photo
        except:
            print("Not working")
		

root = Tk()
client = Client(root)
client.listenRTP()
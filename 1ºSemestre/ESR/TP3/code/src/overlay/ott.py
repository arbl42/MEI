import logging
import argparse
import json

from entities.relay import Relay
from entities.streamer import Streamer
from entities.client import Client
from tkinter import *


def load_config(cfg_file):
    """
    Load the configuration file json.
    """
    with open(cfg_file) as config_file:
        cfg_file = json.load(config_file)
    return cfg_file


def main(args):
    """
    Main function of the OTT application.
    """
    cfg_file = load_config(args.config)

    if args.type == "r":
        node = Relay('', cfg_file['port'], cfg_file)
    elif args.type == "rb":
        node = Relay('', cfg_file['port'], cfg_file, bootstrap=True)
    elif args.type == "s":
        node = Streamer('', cfg_file['port'], cfg_file, args.file)
    elif args.type == "c":
        tk = Tk()
        node = Client('', cfg_file['port'], cfg_file, tk)
    else:
        print("Invalid type")
        exit(1)

    node.start()
    node.main_loop()

if __name__ == '__main__':
    # parse command line arguments
    parser = argparse.ArgumentParser(
        description='Runs a node in the overlay network.')
    parser.add_argument('-c', '--config', action='store',
                        required=True, help='The configuration file.')
    parser.add_argument('-t', '--type', action='store',
                        required=True, default='r', help='r: relay, rb: bootstrap, s: streamer, c: client')
    parser.add_argument('-d', '--debug', action='store_true',
                        help='Enable debug output.')
    parser.add_argument('-f', '--file', action='store',
                        help='Video to be streamed')
    
    args = parser.parse_args()

    if args.debug == 0:
        logging.basicConfig(level=logging.INFO,
                            format='%(asctime)s: %(message)s')
    else:
        logging.basicConfig(level=logging.DEBUG,
                            format='%(asctime)s: %(message)s')

    main(args)

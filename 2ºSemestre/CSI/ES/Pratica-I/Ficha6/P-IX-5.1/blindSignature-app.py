from eVotUM.Cripto import utils

import sys
from eVotUM.Cripto import eccblind

def printUsage():
    print("Usage: python blindSignature-app.py -key <chave privada> -bmsg <blind message>")

def parseArgs():
    if (len(sys.argv) != 5):
        printUsage()
    else:
        eccPrivateKeyPath = sys.argv[2]
        blindM = sys.argv[4]
        main(eccPrivateKeyPath, blindM)

def showResults(errorCode, blindSignature):
    if (errorCode is None):
        print("\n\nBlind signature: %s" % blindSignature)
    elif (errorCode == 1):
        print("Error: it was not possible to retrieve the private key")
    elif (errorCode == 2):
        print("Error: init components are invalid")
    elif (errorCode == 3):
        print("Error: invalid blind message format")

def main(eccPrivateKeyPath, blindM):
    pemKey = utils.readFile(eccPrivateKeyPath)
    passphrase = input("Passphrase: ")
    count = 0
    with open("signerFile") as fp:
        while count <= 2:
            count += 1
            initComponents = fp.readline()

    errorCode, blindSignature = eccblind.generateBlindSignature(pemKey, passphrase, blindM, initComponents)
    showResults(errorCode, blindSignature)

if __name__ == "__main__":
    parseArgs()
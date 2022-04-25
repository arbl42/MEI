from inspect import signature
from eVotUM.Cripto import utils

import sys
from eVotUM.Cripto import eccblind
import os
import OpenSSL.crypto 


def printUsage():
    print("Usage: python verify-app.py -cert <certificado do assinante> -msg <mensagem original a assinar> -sDash <Signature> -f <ficheiro do requerente>")


def parseArgs():
    if (len(sys.argv) != 9):
        printUsage()
    else:
        eccKeyCertificatePath = sys.argv[2]
        data = sys.argv[4]
        signature = sys.argv[6]
        reqFilePath = sys.argv[8] 
        main(eccKeyCertificatePath, data, signature, reqFilePath)


def showResults(errorCode, validSignature):
    if (errorCode is None):
        if (validSignature):
            print("Valid signature")
        else:
            print("Invalid signature")
    elif (errorCode == 1):
        print("Error: it was not possible to retrieve the public key")
    elif (errorCode == 2):
        print("Error: pR components are invalid")
    elif (errorCode == 3):
        print("Error: blind components are invalid")
    elif (errorCode == 4):
        print("Error: invalid signature format")


def main(eccKeyCertificatePath, data, signature, reqFilePath):

    with open(reqFilePath) as fp:
        lines = fp.readlines()
        blindComponents = lines[4]
        pRComponents = lines[9]
    
    os.system("openssl x509 -pubkey -noout -in "+eccKeyCertificatePath+" -out pubkey.pem")

    pemPublicKey = utils.readFile("pubkey.pem")

    errorCode, validSignature = eccblind.verifySignature(
        pemPublicKey, signature, blindComponents, pRComponents, data)
    showResults(errorCode, validSignature)


if __name__ == "__main__":
    parseArgs()
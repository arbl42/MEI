import sys
from eVotUM.Cripto import eccblind

def printUsage():
    print("Usage: python ofusca-app.py -msg <mensagem a assinar> -RDash <pRDashComponents>")

def parseArgs():
    if (len(sys.argv) > 5):
        printUsage()
    else:
        pRDashComponents = sys.argv[4]
        data = sys.argv[2] 
        main(pRDashComponents, data)

def showResults(errorCode, result):
    if (errorCode is None):
        blindComponents, pRComponents, blindM = result
        print("\nBlind Message: "+ blindM)
        with open("BlindDataFile","w") as file:
            file.write("\n\n----------------Blind Components-----------------------\n\n")
            file.write(blindComponents + "\n")
            file.write("\n\n----------------pRComponents-----------------------\n\n")
            file.write(pRComponents + "\n")
        print("\nOther components saved in \"BlindDataFile\"\n")
    elif (errorCode == 1):
        print("Error: pRDash components are invalid")

def main(pRDashComponents, data):
    errorCode, result = eccblind.blindData(pRDashComponents, data)
    showResults(errorCode, result)

if __name__ == "__main__":
    parseArgs()
import sys
from eVotUM.Cripto import eccblind

def printUsage():
    print("Usage: 'python init-app.py' -> R'\n\t 'python init-app.py -init' -> Initialize components")

def parseArgs():
    if (len(sys.argv) > 2):
        printUsage()
    else:
        main()

def main():
    initComponents, pRDashComponents = eccblind.initSigner()
    if (len(sys.argv) == 2):
        print("pRDashComponents: %s" % pRDashComponents)
    else:  
        with open("signerFile","w") as file:
            file.write("---------Initialized Components--------\n\n")
            file.write(initComponents)
            file.write("\n\n----------------R'-----------------------\n\n")
            file.write(pRDashComponents + "\n")
        print("\nComponents initialized in \"signerFile\"\n")    
            
    

if __name__ == "__main__":
    parseArgs()
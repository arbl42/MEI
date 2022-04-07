import random

class DHKE:
    def __init__(self,G,P):
        self.G_param = G
        self.P_param = P

    def generate_privatekey(self):
        self.pk = random.randrange(start = 1,stop = 10,step = 1)

    def generate_publickey(self):
        self.pub_key = pow(self.G_param,self.pk) % self.P_param

    def exchange_key(self,other_public):
        self.share_key = pow(other_public,self.pk) % self.P_param


#Simulating the Key Exchange b/w two entities. Let Alice and Bob be the two entities.

Alice = DHKE(5,22)
Bob = DHKE(5,22)

Alice.generate_privatekey()
Bob.generate_privatekey()

print("------------Private Keys------------------\n")
print("Alice Private Key Generated is ",Alice.pk,"\n")

print("Bob Private Key Generated is ",Bob.pk,"\n")
print("------------End of Private Keys------------\n\n")


Alice.generate_publickey()
Bob.generate_publickey()

print("------------Public Keys------------------\n")
print("Alice Public Key Generated is ",Alice.pub_key,'\n')

print("Bob Public Key Generated is ",Bob.pub_key,'\n')
print("------------End of Public Keys-----------\n\n")

#Alice & Bob Exchange each others key now.

Alice.exchange_key(Bob.pub_key)
Bob.exchange_key(Alice.pub_key)

print("------------Shared Key Derieved------------------\n")
print("Shared Key Generated now by Alice : ",Alice.share_key,'\n')

print("Shared Key Generated now by Bob : ",Bob.share_key,'\n')
print("------------End of Shared Key Derieved-----------\n")
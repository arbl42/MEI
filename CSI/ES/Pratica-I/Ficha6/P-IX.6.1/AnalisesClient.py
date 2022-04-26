import phe
from phe import paillier


pub_key,priv_key = paillier.generate_paillier_keypair()

def GerarKeys():
	pub_key,priv_key = paillier.generate_paillier_keypair() ## Gera a chave publica e privada

def CifrarValor(valor, pub_key): #Cifra um valor com a chave publica
	return pub_key.encrypt(valor)

def StringCifrada(cifra):  #Converte para string uma cifra
	return cifra.ciphertext(True)

def DecifrarString(cifra, pub_key, priv_key): #Decifra uma cifra que foi convertida em string
	enc = paillier.EncryptedNumber(pub_key, cifra)
	return priv_key.decrypt(enc)

def InserirAnalises(NIC, Analises): #Insere analises de um paciente no ficheiro
	#Exemplo
	#"123456789, (A23, 12,2), (B4, 32,1), (A2, 102), (CAA2, 34,5)"
	Linha = str(NIC) + ', '
	for analise in Analises:
		Linha = Linha + str(analise)
		if(type(analise) != str):
			Linha = Linha + '),'
		else:
			Linha = Linha +', '

	l = len(Linha) 
	Linha = Linha[:l-1]
	Linha = Linha + '\n'

	fp = open('analises.txt', 'a')
	fp.write(Linha)
	fp.close()


def CalcularMedia(NIC, Analise): #Calcula a média de determinado valor de analise
	file = open('analises.txt', 'r')
	Lines = file.readlines()

	valoresCifrados = []

	for line in Lines:
		currentNIC = line.split(",")
		if(currentNIC[0] == str(NIC)):
			valoresCifrados.append(CifrarValor(float(GetValue(line, Analise)), pub_key))

	media = CloudCalcularMedia(valoresCifrados) #Envia para a cloud os valores já cifrados

	return(priv_key.decrypt(media))


def GetValue(line, Analise):
	splitted = line.split(',')
	trigger=False
	valor = ''
	for x in splitted:
		if(trigger):
			valor = "".join(_ for _ in x if _ in ".1234567890")
			break
		if(Analise in x):
			trigger=True

	if(valor==''):
		return 0
	return valor


def CloudCalcularMedia(valores): #Parte calculada na cloud com valores cifrados
	
	media = sum(valores) / len(valores) 
	return media


GerarKeys()

Analises = ["(A23", 12.3, "(B4", 32.5, "CAA2", 34,5]
InserirAnalises(987654321, Analises)

Analises = ["(C23", 12, "(Z2", 132.7]
InserirAnalises(123456789, Analises)

Analises = ["(Y13", 12.1, "(B4", 16.6, "(K76", 78.13]
InserirAnalises(987654321, Analises)



print("Media das analises B4 do paciente com NIC 987654321 é: " + str(CalcularMedia(987654321, 'B4')))




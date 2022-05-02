import requests
import OpenSSL
from OpenSSL import SSL, crypto
import ssl
import sys

def SplitString(text):
	split = text.split()
	return split

def GetCRLURL(splittedCert):
	for split in splittedCert:
		if("crl" in split):
			url=split
	url = url.replace('\'', '')
	url = url.replace(',', '')
	url = url.replace('}', '')
	url = url.replace(')', '')
	url = url.replace('(', '')
	return url

def GetCRLFile(url):
	resp = requests.get(url)
	crl = OpenSSL.crypto.load_crl(OpenSSL.crypto.FILETYPE_ASN1, resp.content)
	return crl


def VerificarCRL(crl):
	# Exportar CRL como um cryptography CRL.
	crl_crypto = crl.to_cryptography()
	# Ler o certificado
	with open(sys.argv[1], "r") as f:
		cert_buf = f.read()

	ca = OpenSSL.crypto.load_certificate(OpenSSL.crypto.FILETYPE_PEM, cert_buf)
	# Obter chave publica
	ca_pub_key = ca.get_pubkey().to_cryptography_key()

	# Validar CRL sobre Certificado
	valid_signature = crl_crypto.is_signature_valid(ca_pub_key)

	if(valid_signature):
		print('A assinatura é valida')
	else:
		print('A assinatura não é valida')

def VerificarCertificado(certificado):
	cert= certificado.read()
	
	hasExpired = crypto.load_certificate(crypto.FILETYPE_PEM, cert).has_expired()
	ExpireDate = str(crypto.load_certificate(crypto.FILETYPE_PEM, cert).get_notAfter())
	full = crypto.load_certificate(crypto.FILETYPE_PEM, cert)

	if(hasExpired):
		print('Segundo a chavemovel.cer o certificado expirou')
	else:
		print('Segundo a chavemovel.cer o certificado não expirou')

	print('O certificado expira no ano: ' + ExpireDate[2:6] + ' mes: ' + ExpireDate[7:8] +
	 ' dia: ' + ExpireDate[9:10])

	cert_dict = ssl._ssl._test_decode_cert('certificado_chave_movel_digital.cer')
	splittedCert = SplitString(str(cert_dict))
	crlURL = GetCRLURL(splittedCert)

	print('O URL do ficheiro crl extraido do certificado é: \n' + crlURL)

	crl = GetCRLFile(crlURL)

	VerificarCRL(crl)


chaveDigitalMovel = open(sys.argv[1], 'r')
VerificarCertificado(chaveDigitalMovel)
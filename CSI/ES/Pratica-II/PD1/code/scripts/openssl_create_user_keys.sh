#!/bin/sh
openssl genrsa -out ./certificados/$1.key 3072
openssl pkcs8 -topk8 -inform PEM -outform DER -in ./certificados/$1.key -out ./certificados/$3.der -nocrypt
openssl rsa -in ./certificados/$1.key -pubout -outform DER -out ./certificados/$2.der
openssl req -new -sha256 -key ./certificados/$1.key -subj "/C=PT/O=Grupo3/CN=pessoaB" -out ./certificados/$1.csr
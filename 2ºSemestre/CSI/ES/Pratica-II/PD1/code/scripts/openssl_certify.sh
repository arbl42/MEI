#!/bin/sh
openssl x509 -req -in ./certificados/$1.csr -CA ./certificados/rootCA.crt -CAkey ./certificados/rootCA.key -CAcreateserial -out ./certificados/$1.crt -days 180 -sha256
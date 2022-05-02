#!/bin/sh
openssl genrsa -out ./certificados/rootCA.key 4096
openssl req -x509 -new -nodes -key ./certificados/rootCA.key -sha256 -days 365 -out ./certificados/rootCA.crt -subj "/C=PT/O=Grupo 3/OU=Departamento de Informática/CN=Entidade de Certificação Grupo 3"
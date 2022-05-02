#!/bin/sh
openssl verify -CAfile ./certificados/rootCA.crt ./certificados/$1.crt
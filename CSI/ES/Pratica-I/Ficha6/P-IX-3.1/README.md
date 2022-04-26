# Como executar

Para executar o programa P_IX_3_1.py apenas é preciso executar

python3 P_IX_3_1.py

na linha de comandos.

# Explicação

Este programa cifra, valida e decifra através da cifra ChaCha20_Poly1305 o ficheiro <plaintext.in>. Produz o ficheiro cifrado <ciphertext.out> e o ficheiro original de input (<plaintext.in>) após decifra, denominado de <original_plaintext.out>.

Como indica no aviso do website https://pycryptodome.readthedocs.io/en/latest/src/cipher/chacha20.html, esta cifra já implementa o EtM (Encrypt-then-MAC), pelo que foi utilizada ao invés de implementar o ChaCha20 e depois um mecanismo de HMAC.
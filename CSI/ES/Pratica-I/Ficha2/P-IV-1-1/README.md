Para cifrar ou decifrar o texto é preciso executar o programa P_IV_1_1.py na linha de comandos.
A sintaxe é: python3 P_IV_1_1.py <chave> <modo_cifra> <nome_ficheiro_input> <nome_ficheiro_output>
Em ambos os casos é possível fornecer uma chave ou não. Caso esta não seja fornecida, irá ser pedida ao executar o programa.
Nota: A chave precisa de ter 32 carateres para ser aceite.

# Cifrar
## Exemplo com chave
python3 P_IV_1_1.py uLyZcpnw2cVS2Ph4RtTKXQ9hNTDxBqKj 0 plaintext.in ciphered_text.out
## Exemplo sem chave
python3 P_IV_1_1.py 0 plaintext.in ciphered_text.out

# Decifrar
## Exemplo com chave
python3 P_IV_1_1.py uLyZcpnw2cVS2Ph4RtTKXQ9hNTDxBqKj 1 ciphered_text.out original_plaintext.out
## Exemplo sem chave
python3 P_IV_1_1.py 1 ciphered_text.out original_plaintext.out
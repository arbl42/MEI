E preciso executar o programa P_VII_1_1.py na linha de comandos.

A sintaxe base é: python3 P_VII_1_1.py <tipo_operacao> <nome_ficheiro_input>

O tipo de operação é 0 para cifrar e 1 para decifrar.

O nome do ficheiro de input refere-se ao plaintext a cifrar ou ao ciphertext a decifrar, consoante a operação pretendida.

Devido ao seu tamanho, as chaves públicas e privadas não são inseridas diretamente, mas são referenciadas através de ficheiros.

# Cifrar
Para cifrar é possível:
1) Gravar as chaves privadas e públicas em ficheiros.
2) Ser passada a chave pública

Se o ficheiro com a chave pública não for fornecida, esta será criada.

## Sintaxe
python3 P_VII_1_1.py 0 <nome_ficheiro_input> <opcao_gravar_chaves> [<ficheiro_chave_publica>]

## Exemplo onde a chave pública é fornecida:
python3 P_VII_1_1.py 0 plaintext.in 1 pub.pem

## Exemplo onde as chaves não são gravadas em ficheiros:
python3 P_VII_1_1.py 0 plaintext.in 0


# Decifrar
Para decifrar é obrigatório passar o ficheiro com a chave privada como parâmetro.

## Sintaxe
python3 P_VII_1_1.py 1 <ficheiro_chave_privada>

## Exemplo
python3 P_VII_1_1.py 1 priv.pem
from Crypto.Random import get_random_bytes
from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP
from Crypto.Cipher import ChaCha20
from Crypto.Protocol.KDF import scrypt
from sys import argv
from os.path import exists
from base64 import b64encode, b64decode
import json

public_key_filename_ext = "pub.pem"
private_key_filename_ext = "priv.pem"
ciphered_msg_filename_ext = "ciphered_msg.txt"
ciphered_session_key_filename_ext = "ciphered_session_key.txt"
salt_filename_ext = "used_salt.txt"
original_plaintext_filename_ext = "original_plaintext.txt"
chacha_key_len = 32

def read_from_file(filename):
    content = ''
    with open(filename) as f:
        line = f.read()
        content += line
    return content

def write_to_file(filename, content):
    f = open(filename, "w")
    f.write(content)
    f.close()

def write_binary_to_file(filename, content):
    f = open(filename, "wb")
    f.write(content)
    f.close()

def parse_ciphered_msg_file(file_content):
    b64 = json.loads(file_content)
    nonce = b64decode(b64['nonce'])
    ciphertext = b64decode(b64['ciphertext'])
    return nonce, ciphertext
def parse_cmd_args():
    error_ret = 0, "", 0, 0, ""

    if len(argv) < 2 or len(argv) > 5:
        print("Numero de argumentos errado")
        print("Consultar forma correta de executar no README.md")
        return error_ret
    
    op = int(argv[1])
    if(op < 0 or op > 1):
        print("Operacao errada... 0 para cifrar, 1 para decifrar")
        return error_ret

    if op == 0:
        # ver se ficheiro de input existe
        filename = argv[2]
        if(exists(filename) == False):
            print("Ficheiro de input nao existe...")
            return error_ret

        # 1) ler o conteúdo do ficheiro
        content = read_from_file(filename)
        try:
            content_b = bytes(content, 'utf-8')
        except:
            print("Conversao da mensagem no ficheiro para bytes falhou")
            return error_ret
    
    is_key_given = False
    if op == 0: # cifra
        # Ver se o utilizador quer gravar chaves em ficheiro
        save_keys_to_file = int(argv[3])

        # Se a chave pública for fornecida, guardá-la
        if len(argv) == 5:
            # ver se ficheiro existe primeiro
            if(exists(argv[4]) == False):
                print("Ficheiro que contém a chave pública nao existe...")
                print("Irá ser criada uma nova")
            else:
                is_key_given = True
                given_key_str = read_from_file(argv[4]) # argv[4]
                given_key = RSA.import_key(given_key_str)
    else:
        if len(argv) == 3: # A chave privada é fornecida
            # ver se ficheiro que contém a chave privada existe
            if(exists(argv[2]) == False):
                print("Erro fatal: ficheiro que contém a chave privada nao existe...")
                print("A sair...")
                exit(0)
            # ver se ficheiro com o salt existe
            if(exists(salt_filename_ext) == False):
                print(''' Erro fatal: ficheiro que contém o salt usado para 
aumentar a entropia da password fornecida para cifrar a chave 
privada não existe...")''')
                print("A sair...")
                exit(0)

            is_key_given = True
            given_key_str = read_from_file(argv[2]) # argv[2]
            read_salt_str = read_from_file(salt_filename_ext)
            read_salt = b64decode(read_salt_str)
            while True:
                password = input("Insira password para ler a chave privada: ")
                try:
                    password = kdf_scrypt(password, read_salt)[0]
                    given_key = RSA.import_key(given_key_str, passphrase=password)
                    print("Chave privada obtida a partir de " + private_key_filename_ext)
                    break
                except (ValueError, IndexError, TypeError):
                    print("Password fornecida errada...")
        else:
            print("Erro fatal: Ficheiro com chave privada não fornecida")
            print("A sair...")
            exit(0)

    # Para fazer return
    if op == 0: # Cifra
        if is_key_given:
            return 1, op, content_b, save_keys_to_file, given_key
        return 1, op, content_b, save_keys_to_file
    # Decifra
    return 1, op, given_key

def encrypt_ChaCha20(plaintext, key):
    nonce_rfc7539 = get_random_bytes(12)
    cipher = ChaCha20.new(key=key, nonce=nonce_rfc7539)
    ciphertext = cipher.encrypt(plaintext)

    return nonce_rfc7539, ciphertext

def decrypt_ChaCha20(nonce, ciphertext, key):
    try:
        cipher = ChaCha20.new(key=key, nonce=nonce)
        plaintext = cipher.decrypt(ciphertext)
        return 1,str(plaintext, 'utf-8')
    except (ValueError, KeyError):
        print("Incorrect decryption")
        return 0,""

def kdf_scrypt(key, salt):
    if salt is None:
        salt = get_random_bytes(16)
    return scrypt(key, salt, chacha_key_len, N=2**14, r=8, p=1),salt

def main_encryption(public_key):
    # 1) gerar aleatoriamente chave de sessão
    #    esta chave tem 32 bytes devido ao
    #    requisito do tamanho da chave
    #    no ChaCha20
    session_key = get_random_bytes(chacha_key_len)

    private_key = None
    if public_key is None:
        # 2) i. gerar chaves públicas e privadas do Bob
        private_key = RSA.generate(2048)
        public_key = private_key.publickey()

    # ****** ALICE ******
    # 2) ii. cifrar K com chave pública do Bob
    encryptor = PKCS1_OAEP.new(public_key)
    enc_session_key = encryptor.encrypt(session_key)

    # 2) iii. cifrar mensagem com cifra simétrica (ChaCha20)
    #         utilizando a chave de sessão
    nonce, ciphered_msg = encrypt_ChaCha20(msg, session_key)

    # Se a chave pública foi dada, não é possível escrever a
    # chave privada no ficheiro...
    if save_keys_to_file == 1:
        write_binary_to_file(public_key_filename_ext, public_key.export_key('PEM'))
        if private_key is not None:
            password = input("Insira password para cifrar a chave privada: ")
            password,salt = kdf_scrypt(password, None)
            salt_str = b64encode(salt).decode('utf-8')
            private_key_str = private_key.export_key(passphrase=password)
            write_binary_to_file(private_key_filename_ext, private_key_str)
            # escrever salt para um ficheiro
            write_to_file(salt_filename_ext, salt_str)

    ciphered_msg_str = b64encode(ciphered_msg).decode('utf-8')
    nonce_str = b64encode(nonce).decode('utf-8')
    content = {'nonce':nonce_str, 'ciphertext':ciphered_msg_str}
    write_to_file(ciphered_msg_filename_ext, json.dumps(content))

    enc_session_key_str = b64encode(enc_session_key).decode('utf-8')
    write_to_file(ciphered_session_key_filename_ext, enc_session_key_str)

def main_decryption(private_key, input_cipher, session_key_cipher):
    if private_key is None:
        print("Erro: chave privada não fornecida")
        return

    enc_session_key = b64decode(session_key_cipher)
    nonce, ciphered_msg = parse_ciphered_msg_file(input_cipher)

    # ****** BOB ******
    # 3) i. decifrar chave de sessão cifrada através
    #       da sua chave privada
    decryptor = PKCS1_OAEP.new(private_key)
    decrypted_session_key = decryptor.decrypt(enc_session_key)
    
    #if decrypted_session_key != session_key:
    #    print("Erro a decifrar chave de sessão...")

    # 3) ii. decifrar mensagem com cifra simétrica (ChaCha20)
    #         utilizando a chave de sessão decifrada
    succ, deciphered_msg = decrypt_ChaCha20(nonce, ciphered_msg, decrypted_session_key)
    if succ:
        print("The message was " + deciphered_msg)
        write_to_file(original_plaintext_filename_ext, deciphered_msg)


placeholder = parse_cmd_args()
succ = placeholder[0]
if succ == 0:
    print("Exiting...")
    exit(0)

given_key = None
op = placeholder[1]
if op == 0:
    msg = placeholder[2]
    save_keys_to_file = placeholder[3]
    if len(placeholder) == 5:
        given_key = placeholder[4]
else:
    if len(placeholder) == 3:
        given_key = placeholder[2]

if op == 0:
    main_encryption(given_key)
else:
    input_cipher = read_from_file(ciphered_msg_filename_ext)
    session_key_cipher = read_from_file(ciphered_session_key_filename_ext)
    main_decryption(given_key, input_cipher, session_key_cipher)
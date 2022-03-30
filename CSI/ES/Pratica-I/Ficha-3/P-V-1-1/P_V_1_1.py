import json
from base64 import b64encode, b64decode
from Crypto.Cipher import ChaCha20
from Crypto.Random import get_random_bytes
from Crypto.Protocol.KDF import scrypt

import sys
from os.path import exists, dirname, join

def kdf_scrypt(key, salt):
    if salt is None:
        salt = get_random_bytes(16)
    return scrypt(key, salt, chacha20_key_len, N=2**14, r=8, p=1),salt

def parse_ciphertext_file(file_content):
    b64 = json.loads(file_content)
    nonce = b64decode(b64['nonce'])
    ciphertext = b64decode(b64['ciphertext'])
    salt = b64decode(b64['salt'])
    return nonce, ciphertext, salt

def write_to_file(filename, content):
    f = open(filename, "w")
    f.write(content)
    f.close()

def encrypt_ChaCha20(plaintext, key):
    nonce_rfc7539 = get_random_bytes(12)
    cipher = ChaCha20.new(key=key, nonce=nonce_rfc7539)
    ciphertext = cipher.encrypt(plaintext)

    ct = b64encode(ciphertext).decode('utf-8')
    nonce_str = b64encode(nonce_rfc7539).decode('utf-8')

    return nonce_str, ct

def decrypt_ChaCha20(nonce, ciphertext, key):
    try:
        cipher = ChaCha20.new(key=key, nonce=nonce)
        plaintext = cipher.decrypt(ciphertext)
        print("The message was " + str(plaintext, 'utf-8'))
        return 1,str(plaintext, 'utf-8')
    except (ValueError, KeyError):
        print("Incorrect decryption")
        return 0,""

def str_to_bytes(str):
    return bytes(str, 'utf-8')

def check_valid_key(key_str): # key em string
    if(len(key_str) != chacha20_key_len):
        print("A chave fornecida foi rejeitada, pois tem de ter " + chacha20_key_len + " bytes")
        return 0
    
    try:
        str_to_bytes(key_str)
    except:
        print("Chave dada invalida. Conversao para bytes falhou")
        return 0
    
    return 1

def request_new_key():
    key_str = "" # chave invalida
    while True:
        key_str = input("Insira uma nova chave: ")
        if(check_valid_key(key_str) == 1):
            break
    print("Chave aceite!")
    return str_to_bytes(key_str)

def parse_cmd_args():
    # Recebe chave (opcional), operacao (cifra/decifra), ficheiro input e output
    # 3 ou 4 argumentos

    error_ret = 0, bytes(), 0, "", "" 

    # Checks preliminares
    if len(sys.argv) < 4 or len(sys.argv) > 5:
        print("Numero de argumentos errado...")
        return error_ret

    if len(sys.argv) == 5:
        op_index = 2
        in_index = 3
        out_index = 4
    else:
        op_index = 1
        in_index = 2
        out_index = 3

    op = int(sys.argv[op_index])
    if(op < 0 or op > 1):
        print("Operacao errada... 0 para cifrar, 1 para decifrar")
        return error_ret

    in_str = sys.argv[in_index]
    dirname_comp = dirname(__file__)
    path_to_file = join(dirname_comp, in_str)
    if(exists(path_to_file) == False):
        print("Ficheiro de input nao existe...")
        return error_ret

    out_filename = sys.argv[out_index]

    if len(sys.argv) == 5: # chave dada
        is_key_valid = check_valid_key(sys.argv[1])
        if(is_key_valid == 1):
            key = str_to_bytes(sys.argv[1])
        else:
            key = request_new_key()
    else: # chave pedida ao utilizador
        key = request_new_key()
    
    used_salt = None
    if op == 0: # só se for para cifrar
        key, used_salt = kdf_scrypt(key, None)

    if len(sys.argv) >= 4:
        # Parse do ficheiro de input
        input_str = ''
        with open(in_str) as f:
            line = f.read()
            input_str += line
        try:
            input_b = bytes(input_str, 'utf-8')
        except:
            print("Conversao do texto de input para bytes falhou")
            return error_ret

    return 1, key, used_salt, op, input_b, out_filename

chacha20_key_len = 32
success, key, used_salt, op, read_text, out_filename = parse_cmd_args()
if success == 1:
    if op == 0: # Cifrar
        plaintext = read_text
        nonce_str, ct = encrypt_ChaCha20(plaintext, key)

        content = {'nonce':nonce_str, 'ciphertext':ct}
        salt_dict = {'salt': b64encode(used_salt).decode('utf-8')}
        content.update(salt_dict)

        write_to_file(out_filename, json.dumps(content))
    else: # Decifrar
        nonce, ciphertext, salt = parse_ciphertext_file(read_text)
        key = kdf_scrypt(key, salt)[0] # calcular chave a partir do salt obtido
        succ,content = decrypt_ChaCha20(nonce, ciphertext, key)
        if succ == 1:
            write_to_file(out_filename, content)
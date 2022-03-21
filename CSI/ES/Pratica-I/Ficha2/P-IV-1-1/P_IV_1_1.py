import json
from base64 import b64encode, b64decode
from Crypto.Cipher import ChaCha20
from Crypto.Random import get_random_bytes

import sys
from os.path import exists, dirname, join

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
    result = json.dumps({'nonce':nonce_str, 'ciphertext':ct})
    print(result)

    return result

def decrypt_ChaCha20(json_input, key):
    try:
        b64 = json.loads(json_input)
        nonce = b64decode(b64['nonce'])
        ciphertext = b64decode(b64['ciphertext'])
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
    if(len(key_str) != 32):
        print("A chave fornecida foi rejeitada, pois tem de ter 32 bytes")
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
    return str_to_bytes(key_str) # get_random_bytes(32)

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

    if len(sys.argv) == 5:
        is_key_valid = check_valid_key(sys.argv[1])
        if(is_key_valid == 1):
            key = str_to_bytes(sys.argv[1])
        else:
            key = request_new_key()
    else:
        key = request_new_key()
    
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

    return 1, key, op, input_b, out_filename

success, key, op, read_text, out_filename = parse_cmd_args()
if success == 1:
    if op == 0:
        plaintext = read_text
        content = encrypt_ChaCha20(plaintext, key)
        write_to_file(out_filename, content)
    else:
        result = read_text
        succ,content = decrypt_ChaCha20(result, key)
        if succ == 1:
            write_to_file(out_filename, content)
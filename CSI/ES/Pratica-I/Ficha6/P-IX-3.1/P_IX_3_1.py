from Crypto.Random import get_random_bytes
from Crypto.Cipher import ChaCha20_Poly1305
import json
from base64 import b64encode, b64decode
from datetime import datetime

input_filename_ext = "plaintext.in"
ciphertext_filename_ext = "ciphertext.out"
original_input_filename_ext = "original_plaintext.out"

def read_from_file(filename):
    content = ''
    with open(filename) as f:
        line = f.read()
        content += line
    return content

def write_to_file(content, filename):
    f = open(filename, "w")
    f.write(content)
    f.close()

def encrypt_ChaCha20_Poly1305(plaintext, key, header=b""):
    cipher = ChaCha20_Poly1305.new(key=key)
    cipher.update(header)
    ciphertext, tag = cipher.encrypt_and_digest(plaintext)
    
    jk = [ 'nonce', 'header', 'ciphertext', 'tag' ]
    jv = [ b64encode(x).decode('utf-8') for x in (cipher.nonce, header, ciphertext, tag) ]
    result = json.dumps(dict(zip(jk, jv)))
    write_to_file(result, ciphertext_filename_ext)

def decrypt_and_verify_ChaCha20_Poly1305(json_input, key):
    try:
        b64 = json.loads(json_input)
        jk = [ 'nonce', 'header', 'ciphertext', 'tag' ]
        jv = {k:b64decode(b64[k]) for k in jk}

        cipher = ChaCha20_Poly1305.new(key=key, nonce=jv['nonce'])
        cipher.update(jv['header'])
        plaintext = cipher.decrypt_and_verify(jv['ciphertext'], jv['tag']).decode()
        print("The message was: " + plaintext)
        write_to_file(plaintext, original_input_filename_ext)
    except ValueError:
        print("Incorrect decryption - chave ou nonce errado...")

def get_date_time_str() -> str:
    now = datetime.now()
    return now.strftime("%Y/%m/%d %H:%M")

def main():
    msg_to_cipher_b = read_from_file(input_filename_ext).encode()
    used_key = get_random_bytes(32)
    
    encrypt_ChaCha20_Poly1305(
        plaintext=msg_to_cipher_b, 
        key=used_key,
        header=("Criado em " + get_date_time_str()).encode())

    json_input = read_from_file(ciphertext_filename_ext)
    decrypt_and_verify_ChaCha20_Poly1305(
        json_input=json_input, 
        key=used_key)

if __name__ == "__main__":
    main()
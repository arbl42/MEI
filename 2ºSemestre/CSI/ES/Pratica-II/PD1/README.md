# Trabalho Prático I - Cofre Digital

### Classificação
 
 * 12.5/20

### Autores
 * Ariana Lousada([@AITK42](https://github.com/AITK42))
 * Luís Carneiro([@lmrcarneiro](https://github.com/lmrcarneiro))
 * Rui Cardoso([@Obsessi0n](https://github.com/Obsessi0n))



## 2. Projetos do tipo 2 - Cofre digital

O objetivo deste projeto é disponibilizar um serviço de cofre digital, garantindo que os documentos lá depositados só são acedidos pelos seus titulares.

O cofre digital tem as seguintes operações para o exterior:

+ depositar documento (qualquer tipo de documento), sendo que o depositante tem que identificar quem lhe pode aceder. Como resultado é devolvido ao depositante o hash do documento depositado e a chave de decifra do mesmo, cifrada com a chave pública do depositante. Caso o depositante identifique que o documento só pode ser acedido por várias pessoas em conjunto (n pessoas de um conjunto de m pessoas, com n <= m), essa chave de decifra é partida pelas m pessoas (utilize o esquema de Shamir para partilha de segredo que iremos ver numa das próximas aulas teóricas) e fornecida a cada uma, cifrada com a chave pública de cada uma.
+ fornecer documento, desde que seja pedido pelo(s) depositante(s) que lhe podem aceder, e fornecida a chave correta de decifra (já decifrada pela chave privada do depositante).

Internamente, e como atua como fiel depositário do que lhe é confiado, o cofre digital gera a hash do documento e cifra-o, guardando apenas o par (hash, documento cifrado). A chave de cifra é única para cada documento e não é guardada, mas é devolvida ao(s) depositante(s), conforme descrito na operação de depositar.
Para fornecer o documento, o cofre digital tem que receber a chave de decifra (ou as várias componentes da mesma, caso só seja possível aceder por vários depositantes em conjunto), e só devolve o documento original caso o consiga decifrar. No caso de receber várias componentes da mesma, deve existir um mecanismo que permita que os vários depositantes forneçam a sua componente da chave sem necessitarem de a mostrar aos restantes depositantes.

### 2.1 Grupos (e linguagens a utilizar)

Este projeto deve ser realizado pelos seguintes grupos:

+ Grupo 3, em Java, utilizando o _provider_ da Sun, por omissão;

No relatório espera-se que explique as várias decisões tomadas em termos de estruturas de dados, operações existentes e bibliotecas utilizadas. Indique também de que modo é possível instalar e utilizar o código que desenvolveu.

# P-IV-2-1

In order to execute the program please use the Visual Studio Code IDE.

## Key generation, file sign generation and verification of signature:

For an example file the team used the Saramago.txt, which contains one of Saramago's most known quotes.

Firstly, the pair (publicKey, privateKey) is generated using the Bouncy Castle Provider and RSA with a size of 4096 bits.

For the signature generation the team used RSA-PSS, which was implemented in the generatePssSignature method. The signature is created using SHA3-256 as the hash algorithm, with RSA and MGF1 and the BC (BouncyCastle) provider.

For the verification of the signature we use the same algorithm, implemented in the verifyPssSignature method.


## Code execution example:

File example: Saramago.txt with the following content

```
A viagem não acaba nunca. Só os viajantes acabam. E mesmo estes podem prolongar-se em memória, em lembrança, em narrativa.
Quando o visitante sentou na areia da praia e disse: “Não há mais o que ver”, saiba que não era assim. 
O fim de uma viagem é apenas o começo de outra. 
É preciso ver o que não foi visto, ver outra vez o que se viu já, ver na primavera o que se vira no verão, ver de dia o que se viu de noite,
 com o sol onde primeiramente a chuva caía, ver a seara verde, o fruto maduro, a pedra que mudou de lugar, a sombra que aqui não estava. 
É preciso voltar aos passos que foram dados, para repetir e para traçar caminhos novos ao lado deles. 
É preciso recomeçar a viagem. Sempre.
José Saramago.
```

Execution example:

![ExampleCodeExecution](https://github.com/uminho-mei-engseg-21-22/Grupo3/blob/main/AP1/Ficha-4/P_VII_1_2/exampleCodeExecution.PNG)


# Bouncy Castle Provider Dynamic Insertion with VSCode

First off, we need to install the *Extention Pack for Java* extention in VS Code.

![InstallExtension](https://github.com/uminho-mei-engseg-21-22/Grupo3/blob/main/AP1/Ficha-4/P_VII_1_2/extentionInstall.png)

Then we need to create a project in the bottom left corner and select its folder. After this, select the *No build tools* option.

![CreateProject](https://github.com/uminho-mei-engseg-21-22/Grupo3/blob/main/AP1/Ficha-4/P_VII_1_2/createProject.png)

![NobuildToolsOption](https://github.com/uminho-mei-engseg-21-22/Grupo3/blob/main/AP1/Ficha-4/P_VII_1_2/noBuildToolsOption.png)

Insert the *jar* file downloaded from the [BouncyCastle](https://www.bouncycastle.org/latest_releases.html) website in the lib folder.

![JarFile](https://github.com/uminho-mei-engseg-21-22/Grupo3/blob/main/AP1/Ficha-4/P_VII_1_2/insertJarFile.PNG)

Finally, add the following lines of code to the project:

```java
import org.bouncycastle.jce.provider.BouncyCastleProvider;

Security.addProvider(new BouncyCastleProvider());
```


Descrição do Projecto
===============================================
Este é um analisador de estruturas internas de um programa fonte escrito na linguagem Java.
É um programa escrito em Java para realizar análise léxica, sintática e semântica de classes estáticas com apenas dados públicos de tipos primitivos.
Valida também as propriedades de um objeto.

Executar o programa
==============================================
Clone repositório
  git clone https://github.com/AnalisadorJavaClass.git

Para poder testar o analisador com outros programas fontes que contenham classes estáticas, deverá criar um ficheiro com extensão `.txt`.
E no ficheiro `/Main.java` na váriavel "mainFileName", altere o nome "Main.txt" para o ficheiro .txt por si criado.

Instruções do Command-line
---------------------------------
* **Pré-requisitos:**
  * Instalar [Java](https://java.com), de preferência a última versão do Java

```bash
cd AnalisadorJavaClass/src/
# Compilar e correr
javac Main.java
java Main.java
```

Instruções no Eclipse
------------------------------
* **Pré-requisitos:**
  * Instalar [Eclipse](http://www.eclipse.org/downloads/).
  * Opcionalmente, pode instalar o plugin [GitHub](http://eclipse.github.com/).

* Criar um novo projecto usando `/src`
  * Criar um novo Java Project.
  * Selecionar o **Directório** do projecto que será a localização do `src`

* Executar
  * Clicar o botão verde, com desenho de play, no topo.

package Grupo6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

class LexemeTableRow {
    private String token;
    private String classification;
    private int line;

    public LexemeTableRow(String token, String classification, int line) {
        this.token = token;
        this.classification = classification;
        this.line = line;
    }

    public String getToken() {
        return token;
    }

    public String getClassification() {
        return classification;
    }

    public int getLine() {
        return line;
    }
}

public class TabelaDeLexemas {
	
	public TabelaDeLexemas(String filename) {
		lexemas(filename);
	}
	
	public void lexemas(String filename) {
		 //String filename = "Declaracoes.txt"; // Replace with the path to your program file
	        Vector<LexemeTableRow> lexemeTable = new Vector<>(); // Using Vector to represent the table
	        //Vector<Identifier>
	        
	        // Arrays containing primitive types and reserved words
	        String[] primitiveTypes = {"int", "float", "double", "boolean", "char", "byte", "long", "short"};
	        String[] reservedWords = {"abstract", "assert", "break", "case", "catch", "class", "const", 
	    		    "continue", "default", "do", "else", "enum", "extends", "final", "finally",
	    		    "for", "goto", "if", "implements", "import", "instanceof", "interface", "native", 
	    		    "new", "package", "private", "protected", "public", "return", "static", "strictfp", 
	    		    "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", 
	    		    "while", "String"};
	    	String[] separador = {" " , ".", ";", "(", ")", "{", "}", "[", "]", "=", "+", "-", "<", ">", "/", "=>", "<="};

	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	            String line;
	            int lineNumber = 1;

	            while ((line = br.readLine()) != null) {
	                StringTokenizer tokenizer = new StringTokenizer(line, " \t\n\r\f;(){}[]", true);

	                while (tokenizer.hasMoreTokens()) {
	                    String token = tokenizer.nextToken().trim();

	                    if (!token.isEmpty()) {
	                        String classification;
	                        // Check if the token is a primitive type
	                        if (isPrimitiveType(token, primitiveTypes)) {
	                            classification = "Tipo Primitivo";
	                        } else if (isReservedWord(token, reservedWords)) { // Check if the token is a reserved word
	                            classification = "Palavra Reservada";
	                        } else if(isSeparator(token, separador)) {
	                        	classification = "Separador";
	                        } else {
	                            classification = "Identificador";
	                        	
	                            //if(token.equals())
	                        }
	                        // Add the token, its classification, and line number to the lexeme table
	                        lexemeTable.add(new LexemeTableRow(token, classification, lineNumber));
	                    }
	                }

	                lineNumber++;
	            }
	        } catch (IOException e) {
	            System.err.println("Error reading the file: " + e.getMessage());
	        }

	        // Display the lexeme table with column titles
	        System.out.println("||=======================================================||" +"\n"+
	        				   "||                    Tabela de Lexemas                  ||" +"\n"+
	        				   "||=======================================================||" +"\n"+
	        				   "||     Lexema        |     Classificacao     |   Linha   ||" +"\n"+
	        				   "||-------------------------------------------------------||");
	        for (LexemeTableRow row : lexemeTable) {
	         System.out.printf("||%19s|%20s   |%8d   ||",
	            		row.getToken(), row.getClassification(), row.getLine());
	        	//System.out.println(row.getToken() + "\t\t|\t\t" + row.getClassification() + "\t\t|\t\t" + row.getLine());
	         System.out.println("");
	        System.out.println("||-------------------|-----------------------|-----------||");
	        }
	}
	
	/*
    public static void main(String[] args) {
       new TabelaDeLexemas();
    }
    */

    // Method to check if a token is a primitive type
    private static boolean isPrimitiveType(String token, String[] primitiveTypes) {
        for (String primitive : primitiveTypes) {
            if (primitive.equalsIgnoreCase(token)) {
                return true;
            }
        }
        return false;
    }

    // Method to check if a token is a reserved word
    private static boolean isReservedWord(String token, String[] reservedWords) {
        for (String reserved : reservedWords) {
            if (reserved.equalsIgnoreCase(token)) {
                return true;
            }
        }
        return false;
    }
    
    //Method to check if a token is a separator
    private static boolean isSeparator(String token, String[] separadorArray) {
    	for(String separador : separadorArray) {
    		if(separador.equalsIgnoreCase(token)) {
    			return true;
    		}
    	}
    	return false;
    }
}
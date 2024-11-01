package Grupo6;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        try {
            // Diretório contendo todos os arquivos de código
            String directoryPath = "C:\\Users\\Compiladores";
            String mainFileName = directoryPath + "\\Main.txt";		//altere consoante o nome do ficheiro a qual pretende analisar
            
            TabelaDeSimbolos symbolTable = new TabelaDeSimbolos();

            // Primeira passagem: identificar arquivos mencionados nas atribuições do Main.txt
            Set<String> includedFiles = new HashSet<>();
            String mainContent = readFile(mainFileName);
            System.out.println("***********************");
            System.out.println("    Codigo Main: ");
            System.out.println("***********************");
            System.out.println(mainContent);
            findIncludedFiles(mainContent, includedFiles);

            // Segunda passagem: ler e analisar os arquivos identificados
            for (String fileName : includedFiles) {
                String content = readFile(directoryPath + "/" + fileName + ".txt");
                System.out.println("***********************");
                System.out.println("Codigo a ser analisado:");
                System.out.println("***********************");
                System.out.println(content);
                AnaliseLexica lexico = new AnaliseLexica(content);
                AnaliseSintatica sintatico = new AnaliseSintatica(lexico, symbolTable, false);

                try {
                    sintatico.parse();
                    System.out.println("Análise de " + fileName + " concluída com sucesso");
                    System.out.println(" ");
                } catch (AnalysisError e) {
                    System.out.println("Erro durante a análise da classe " + fileName + ": " + e.getMessage());
                }
            }

            // Terceira passagem: verificar declarações e atribuições em Main.txt
            AnaliseLexica mainLexico = new AnaliseLexica(mainContent);
            AnaliseSintatica mainSintatico = new AnaliseSintatica(mainLexico, symbolTable, true);

            try {
                mainSintatico.parse();
                System.out.println(" ");
                System.out.println("Análise de Main.txt concluída com sucesso");
                System.out.println(" ");
            } catch (AnalysisError e) {
                System.out.println("Erro durante a análise de Main.txt: " + e.getMessage());
            }

            short op;
        	
        	do {
        		System.out.println(" ");
        		System.out.println("------------------------");
        		System.out.println("    MENU DE TABELAS     ");
        		System.out.println("------------------------");
        		System.out.println("1. Tabela de Lexemas.");
        		System.out.println("2. Tabela de Simbolos.");
        		System.out.println("3. Terminar programa.");
        		op = validarShort(1, 3, "Introdua a opcao: ");
        		
        		switch(op) {
        		case 1: mainLexico.imprimirLexema();
        			//TabelaDeLexemas tl = new TabelaDeLexemas(); 
        			System.out.println(""); break;
        		case 2: symbolTable.imprimirTabela();
        			//System.out.println("Ooops, fora de servico por enquanto!");
        			System.out.println(""); break;
        		case 3: System.out.println("---------------------------");
        				System.out.println("|   PROGRAMA TERMINADO!   |");
        				System.out.println("---------------------------");
        		}
        	}while(op != 3);
        	
        } catch (IOException e) {
            System.out.println("Erro ao ler os arquivos: " + e.getMessage());
        }
        
        //menu();
        
    }

    private static void findIncludedFiles(String content, Set<String> includedFiles) {
        Pattern pattern = Pattern.compile("\\b(\\w+)\\.(\\w+)\\b");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            includedFiles.add(matcher.group(1));
        }
    }

    private static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    // metodo do menu para visualizacao das tabelas
    public static void menu() {
    	short op;
    	
    	do {
    		System.out.println("**********MENU DE TABELAS***************");
    		System.out.println("1. Tabela de Lexemas.");
    		System.out.println("2. Tabela de Simbolos.");
    		System.out.println("3. Terminar programa.");
    		op = validarShort(1, 3, "Introdua a opcao: ");
    		
    		switch(op) {
    		case 1: //TabelaDeLexemas tl = new TabelaDeLexemas(); 
    			System.out.println(""); break;
    		case 2: 
    			//System.out.println("Ooops, fora de servico por enquanto!");
    			System.out.println(""); break;
    		case 3: System.out.println("---------------------------");
    				System.out.println("|   PROGRAMA TERMINADO!   |");
    				System.out.println("---------------------------");
    		}
    	}while(op != 3);
    }
    
    public static short validarShort(int a, int b, String msg) {
    	short op = 0;
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
    	do {
    		System.out.println(msg);
    		
    		try {
    			op = Short.parseShort(br.readLine());
    		}catch(NumberFormatException n) {
    			System.out.println("Erro na formatacao do numero de opcao: "+n.getMessage());
    		} catch(IOException i) {
    			System.out.println("Erro na leitura da entrada da opcao: "+i.getMessage());
    		}
    		
    		if(op < a || op > b) {
    			System.out.println("Opcao invalida! Deve ser entre "+a+" e "+b);
    		}
    	}while(op < a || op > b);
    	return op;
    }
}

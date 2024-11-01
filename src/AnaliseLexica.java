package Grupo6;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;

class PreencherLexemas{
	private String lexema;
	private String classificacao;
	private int linha;
	
	public PreencherLexemas(String lexema, String classificacao, int linha) {
		this.lexema = lexema;
		this.classificacao = classificacao;
		this.linha = linha;
	}
	
	public String getLexema() {
		return lexema;
	}
	
	public String getClassificacao() {
		return classificacao;
	}
	
	public int getLinha() {
		return linha;
	}
}

public class AnaliseLexica {
	private String input;
	private int posicao;
	Vector<PreencherLexemas> tabelaLexema = new Vector<>();
	
	private static final Pattern TOKEN_PATTERN = Pattern.compile(
		"\\s+|" + 	// Espacos em branco
		"public|static|class|" + 	// Palavras Reservadas 
		"byte|short|int|long|float|double|boolean|char|" + 	// Tipos de dados
		"[A-Za-z]([A-Za-z0-9_])*|" +		// Identificadores
		"[;{}.=]"		// Separadores
	);
	
	public AnaliseLexica(String input) {
		this.input = input;
		this.posicao = 0;
	}
	
	public Token nextToken() throws LexicalError {
		if(posicao >= input.length()) {
			return null;
		}
		
		Matcher matcher = TOKEN_PATTERN.matcher(input);
		if(!matcher.find(posicao) || matcher.start() != posicao) {
			throw new LexicalError("Caracter invalido na posicao "+posicao);
		}
		
		String lexeme = matcher.group();
		int start = posicao;
		posicao = matcher.end();
		String classificacao;
		
		if(lexeme.matches("\\s+")) {
			return nextToken();
		} else if(lexeme.matches("public|static|class")) {
			classificacao = "Palavra Reservada";
			tabelaLexema.add(new PreencherLexemas(lexeme, classificacao, start));
			return new Token(Token.TK_RESERVEDW, lexeme, start);
		} else if(lexeme.matches("byte|short|int|long|float|double|boolean|char")) {
			classificacao = "Tipo Primitivo";
			tabelaLexema.add(new PreencherLexemas(lexeme, classificacao, start));
			return new Token(Token.TK_TYPE, lexeme, start);
		} else if(lexeme.matches("[A-Za-z0-9]([A-Za-z0-9_])*")) {
			classificacao = "Identificador";
			tabelaLexema.add(new PreencherLexemas(lexeme, classificacao, start));
			return new Token(Token.TK_ID, lexeme, start);
		} else if(lexeme.matches("[;{}.=]")) {
			classificacao = "Separador";
			tabelaLexema.add(new PreencherLexemas(lexeme, classificacao, start));
			return new Token(Token.TK_SEPARATOR, lexeme, start);
		} else {
			throw new LexicalError("Lexema invalido" + start);
		}
		
	}
	
	public void imprimirLexema() {
		System.out.println("||=======================================================||" +"\n"+
						   "||                    Tabela de Lexemas                  ||" +"\n"+
						   "||=======================================================||" +"\n"+
						   "||     Lexema        |     Classificacao     |   Linha   ||" +"\n"+		//posicao
						   "||-------------------------------------------------------||");
		for(PreencherLexemas row : tabelaLexema) {
			System.out.printf("||%19s|%20s   |%8d   ||", row.getLexema(), row.getClassificacao(), row.getLinha());
			System.out.println("");
			System.out.println("||-------------------------------------------------------||");
		}
	}
	
}

package Grupo6;

public class AnaliseSintatica {
    private AnaliseLexica lexico;
    private Token currentToken;
    private TabelaDeSimbolos symbolTable;
    private boolean checkAssignments;

    public AnaliseSintatica(AnaliseLexica lexico, TabelaDeSimbolos symbolTable, boolean checkAssignments) {
        this.lexico = lexico;
        this.symbolTable = symbolTable;
        this.checkAssignments = checkAssignments;
    }

    public void parse() throws LexicalError, SyntaticError, SemanticError {
        currentToken = lexico.nextToken();
        
        while(currentToken != null && (currentToken.getType() != Token.TK_SEPARATOR || !currentToken.getLexeme().equals("$"))) {
        	declaracaoClass();
        }
        
        
        /* metodo para analisar com o primeiro metodo declaracao()
        while (currentToken != null && (currentToken.getType() != Token.TK_SEPARATOR || !currentToken.getLexeme().equals("$"))) {
            if (checkAssignments) {
            	declaracaoClass();
            } else {
            	atribuicao();
            }
        }
        */
        
    }

    private void match(String expected) throws LexicalError, SyntaticError, SemanticError {
        if (currentToken.getLexeme().equals(expected)) {
            currentToken = lexico.nextToken();
        } else {
            throw new SyntaticError("esperado "+ expected+" na posicao "+currentToken.getPosition());
        }
    }

    private void declaracaoClass() throws LexicalError, SyntaticError, SemanticError {
        match("public");
        match("class");
        String className = currentToken.getLexeme();
        symbolTable.addClass(className);
        var();
        match("{");
        lDeclaracao(className);
        match("}");
    }

    /*
    private void lDeclaracao(String className) throws LexicalError, SyntaticError {
        while (currentToken.getLexeme().equals("public")) {
            declaracao(className);
        }
    }
    */
    
    public void lDeclaracao(String className) throws LexicalError, SyntaticError, SemanticError{
    	while(currentToken != null && !currentToken.getLexeme().equals("}")) {
    		declaracao(className);
    	}
    }

    /*
    private void declaracao(String className) throws LexicalError, SyntaticError {
        match("public");
        match("static");
        String type = currentToken.getLexeme();
        tipo();
        String varName = currentToken.getLexeme();
        symbolTable.addVariable(className, varName, type);
        var();
        match(";");
    }
   */
    
    //metodo declaracao com a parte expressao nele
    public void declaracao(String className) throws LexicalError, SyntaticError, SemanticError{
    	String varName = "";
    	if(currentToken.getLexeme().equals("public")) {
    		match("public");
    		match("static");
    		String type = currentToken.getLexeme();
    		tipo();
    		varName = currentToken.getLexeme();
    		
    		if(symbolTable.variableExists(className, varName)) {
    			throw new SemanticError("variavel '"+varName+"' ja foi declarada");
    		}
    		
    		symbolTable.addVariable(className, varName, type);
    		var();
    		match(";");
    	} else {
    		//atribuicao();
    		
    		String var = currentToken.getLexeme();
    		var();
    		//varName = currentToken.getLexeme();
    		verificarVar(className, var);
    		String verificarTipo = symbolTable.getVarType(className, var);
    		match("=");
    		String tipo = expressao();
    		match(";");
    		
    		if(!verificarTipo.equals(tipo)) {
    			throw new SemanticError("tipo incompativel, nao pode atribuir variavel de tipo "+tipo+" a variavel de tipo "+verificarTipo+" na posicao "+currentToken.getType());
    		}
    		
    	}
    	
    }
    

    private void tipo() throws LexicalError, SyntaticError {
        if (currentToken.getType() == Token.TK_TYPE) {
            //System.out.println(currentToken);
            currentToken = lexico.nextToken();
        } else {
            throw new SyntaticError("esperado um tipo de dado na posicao " + currentToken.getPosition());
        }
    }

    private void var() throws LexicalError, SyntaticError {
        if (currentToken.getType() == Token.TK_ID) {
            //System.out.println(currentToken);
            currentToken = lexico.nextToken();
        } else {
            throw new SyntaticError("esperado um identificador na posicao " + currentToken.getPosition());
        }
    }

    public void verificarVar(String className, String varName) throws LexicalError, SyntaticError, SemanticError{
    	if(!symbolTable.variableExists(className, varName)) {
    		throw new SemanticError("variavel '"+varName+"' nao declarada na classe "+className+" na posicao "+currentToken.getPosition());
    	}
    }
    
    /*
    public void verificarVar(String className, String var) throws LexicalError, SyntaticError{
    	if(currentToken.getType() == Token.TK_ID && currentToken.getLexeme().equals(var)) {
    		currentToken = lexico.nextToken();
    	} else {
    		throw new LexicalError("variavel "+var+" nao declarada na classe "+className+" na posicao: "+currentToken.getPosition());
    	}
    }
    */
    

    /*
    private void expressao() throws LexicalError, SyntaticError {
        if (currentToken.getType() == Token.TK_ID) {
            String[] parts = currentToken.getLexeme().split("\\.");
            if (parts.length == 2) {
                String className = parts[0];
                String varName = parts[1];
                if (!symbolTable.classExists(className)) {
                    throw new SyntaticError("Classe " + className + " não encontrada na posição " + currentToken.getPosition());
                }
                if (!symbolTable.variableExists(className, varName)) {
                    throw new SyntaticError("Variável " + varName + " não encontrada na classe " + className + " na posição " + currentToken.getPosition());
                }
            } else {
                throw new SyntaticError("Expressão inválida na posição " + currentToken.getPosition());
            }
            currentToken = lexico.nextToken();
        } else {
            throw new SyntaticError("Esperado um identificador na posição " + currentToken.getPosition());
        }
    }
    */
    
    /*
    private void expressao() throws LexicalError, SyntaticError, SemanticError {
        if (currentToken.getType() == Token.TK_ID) {
            String className = currentToken.getLexeme();
            currentToken = lexico.nextToken();
            if (currentToken != null && currentToken.getLexeme().equals(".")) {
                match(".");
                String varName = currentToken.getLexeme();
                
                if (!symbolTable.classExists(className)) {
                    throw new SemanticError("Classe " + className + " não definida na posição " + currentToken.getPosition());
                }
                if (!symbolTable.variableExists(className, varName)) {
                    throw new SemanticError("Variável " + varName + " não definida na classe " + className + " na posição " + currentToken.getPosition());
                }
       
                var();
            } else {
                throw new SyntaticError("Esperado '.' na posição " + currentToken.getPosition());
            }
        } else {
            throw new SyntaticError("Esperado um identificador ou literal na posição " + currentToken.getPosition());
        }
    }
    */
    
    private String expressao() throws LexicalError, SyntaticError, SemanticError {
        if (currentToken.getType() == Token.TK_ID) {
            String className = currentToken.getLexeme();
            currentToken = lexico.nextToken();
            if (currentToken != null && currentToken.getLexeme().equals(".")) {
                match(".");
                String varName = currentToken.getLexeme();
                
                if (!symbolTable.classExists(className)) {
                    throw new SemanticError("Classe " + className + " não definida na posição " + currentToken.getPosition());
                }
                if (!symbolTable.variableExists(className, varName)) {
                    throw new SemanticError("Variável " + varName + " não definida na classe " + className + " na posição " + currentToken.getPosition());
                }
                
                String tipo = symbolTable.getVarType(className, varName);
                var();
                
                return tipo;
            } else {
                throw new SyntaticError("Esperado '.' na posição " + currentToken.getPosition());
            }
        } else {
            throw new SyntaticError("Esperado um identificador ou literal na posição " + currentToken.getPosition());
        }
    }

    private void atribuicao() throws LexicalError, SyntaticError, SemanticError {
    	/*if (currentToken.getType() == Token.TK_ID) {
            String varName = currentToken.getLexeme();
            currentToken = lexico.nextToken();
            match("=");
            expressao();
            match(";");
        } else {
            throw new SyntaticError("Esperado um identificador na posição " + currentToken.getPosition());
        }
        */
    	
    	if(currentToken.getType() == Token.TK_ID) {
    		String className = currentToken.getLexeme();
    	}
    }
}

package Grupo6;

import java.util.HashMap;
import java.util.Map;

public class TabelaDeSimbolos {
    //Usamo o mapa encadeado onde a chave e o nome da classe e o valor e outro mapa
	// a chave do mapa de valor e o nome da variavel e o valor é o tipo de variável
	private Map<String, Map<String, String>> classes;

	//o construtor inicializa o mapa de classes
    public TabelaDeSimbolos() {
        this.classes = new HashMap<>();
    }

    //adicionar nova classe na tabela de símbolos, se a classes já existe no mapa, o método não faz nada
    public void addClass(String className) {
        if (!classes.containsKey(className)) {
            classes.put(className, new HashMap<>());
        }
    }

    //adiciona variáveis nas classes correspondentes que já existem na tabela
    /*public void addVariable(String className, String varName, String type) {
        if (classes.containsKey(className)) {
            classes.get(className).put(varName, type);
        }
    }*/
    
    public void addVariable(String className, String varName, String type) {
        if (classes.containsKey(className)) {
            classes.get(className).put(varName, type);
        }
    }

    public boolean classExists(String className) {
        return classes.containsKey(className);
    }

    public boolean variableExists(String className, String varName) {
        return classes.containsKey(className) && classes.get(className).containsKey(varName);
    }
    
    // verificar se o identificador ja foi declarado ou nao
    public boolean variavelExiste(String className, String varName) {
    	if(classes.containsKey(className)) {
    		return classes.get(classes).containsKey(varName);
    	}
    	return false;
    }
    
    public String getVarType(String className, String varName) {
    	if(classes.containsKey(className)) {
    		return classes.get(className).get(varName);
    	}
    	return null;
    }
    
    //método para imprimir
    public void imprimirTabela() {
    	System.out.println("||==========================================================================================================================================||" +"\n"+
				   		   "||                                                         Tabela de Simbolos                                                               ||" +"\n"+
				   		   "||==========================================================================================================================================||" +"\n"+
				   		   "|| Identificador |   Categoria   |    Tipo   | Estrutura da Memoria | Numeros de parametros | Sequencia de Parametros | Forma de Parametros ||" +"\n"+
    					   "||------------------------------------------------------------------------------------------------------------------------------------------||");
    	for(Map.Entry<String, Map<String, String>> entradaClasse : classes.entrySet()) {
    		String className = entradaClasse.getKey();
    		Map<String, String> variaveis = entradaClasse.getValue();
    		System.out.printf("||%10s     |%11s    |%9s  |%16s      |%16s       |%18s      |%16s      ||", className, "Classe", "-----", "-----","-----","-----","-----");
    		System.out.println("");
    		System.out.println("||------------------------------------------------------------------------------------------------------------------------------------------||");
    		//System.out.println("||     "+className+"      |    Classe       |   -----  ||");
    		for(Map.Entry<String, String> entradaVar : variaveis.entrySet()) {
    			String nomeVar = entradaVar.getKey();
    			String tipoVar = entradaVar.getValue();
    			String estruturaM = estruturaMemoria(tipoVar);
    			//System.out.println("||       "+nomeVar+"       |    Variavel    |   "+tipoVar+"  ||");
    			//System.out.println("Variavel: "+nomeVar+" Type: "+tipoVar);
    			System.out.printf("||%10s     |%10s     |%9s  |%16s      |%16s      |%16s      |%16s      ||", nomeVar, "Variavel", tipoVar, estruturaM,"-----","-----","-----","-----");
    			System.out.println("");
    			System.out.println("||----------------------------------------------------------------------------------------------------------------------------------------||");
    		}
    		//System.out.println(" ");
    	}
    }
    
    private String estruturaMemoria(String tipo) {
    	switch(tipo) {
    	case "byte":
    	case "short":
    	case "int":
    	case "long":
    	case "float":
    	case "double":
    	case "boolean":
    	case "char":
    		return "Primitivo";
    	default:
    		//if((Character.isUpperCase(0)))
    		return "Objeto";
    	}
    }
}

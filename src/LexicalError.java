package Grupo6;

public class LexicalError extends AnalysisError {
	private int posicao;
	
    public LexicalError(String msg) {
        super(msg);
    }
    
    public LexicalError(String msg, int posicao) {
        super(msg);
        this.posicao = posicao;
    }
    
    public int getPosition() {
    	return posicao;
    }
}
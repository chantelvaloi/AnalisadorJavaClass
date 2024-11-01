package Grupo6;

public class SyntaticError extends AnalysisError {
    public SyntaticError(String msg) {
        super(msg);
    }
    
    public SyntaticError(String msg, int position) {
    	super(msg, position);
    }
}
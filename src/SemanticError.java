package Grupo6;

//import ClassesEstaticas.AnalysisError;

public class SemanticError extends AnalysisError {
    public SemanticError(String msg) {
        super(msg);
    }

    public SemanticError(String msg, int position) {
        super(msg, position);
    }
}
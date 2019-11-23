package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public abstract class Node {
    public abstract void print(ASTPrinter printer) throws Exception;

	public void resolveNames(Scope scope){
        String msg = "Not implemented resolveNames in " + this.getClass().getSimpleName();
        System.err.println(msg);
        // TODO: change to abstract method
        System.exit(-1);
	}
}
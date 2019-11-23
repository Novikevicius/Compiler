package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public abstract class Node {
    public abstract void print(ASTPrinter printer) throws Exception;

	public void resolveNames(Scope scope) {
        System.err.println("Not implemented resolveNames in " + this.getClass().getSimpleName());
        // TODO: change to abstract method
        throw null;
	}
}
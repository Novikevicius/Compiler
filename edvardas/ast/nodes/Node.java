package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public abstract class Node {
    public abstract void print(ASTPrinter printer) throws Exception;

    protected Node parent;
    public void addChildren(ArrayList<Node> children)
    {
        children.forEach((child) -> {
            if(child != null)
            {
                ((Node)child).parent = this;
            }
        });
    }
    public abstract void resolveNames(Scope scope);
}
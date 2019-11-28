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
                addChildren(child);
            }
        });
    }
    public void addChildren(Node child)
    {
        if(child != null)
        {
            child.parent = this;
        }
    }
    public Node findAncestor(Class<?> cls)
    {
        Node curNode = parent;
        while(curNode != null)
        {
            if(curNode.getClass() == cls)
            {
                return curNode;
            }
            curNode = curNode.parent;
        }
        return null;
    }

    public abstract void resolveNames(Scope scope);
}
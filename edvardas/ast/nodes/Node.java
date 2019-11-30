package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Main;
import edvardas.Token;
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
    public static void semanticError(Token t, String msg)
    {
        Main.error(Main.filename + ":"+ t.getLine() + ":" + msg);
    }
    public static void unifyTypes(Node t1, Node t2) throws Exception {
        unifyTypes(t1, t2, null);
    }
    public static void unifyTypes(Node t1, Node t2, Token t) throws Exception
    {
        if(t1 == null || t2 == null) {
            // do nothing
        } else if(t1.getClass() != t2.getClass()){
            semanticError(t, "Type mismatch: " + t1.getClass().getSimpleName() + " and " + t2.getClass().getSimpleName());
        } else if(t1.getClass() == TypePrim.class) {
            TypePrim p1 = (TypePrim)t1;
            TypePrim p2 = (TypePrim)t2;
            if(p1.getKind() != p2.getKind())
            {
                semanticError(t, "Type mismatch: " + p1.getKind() + " and " + p2.getKind());
            }
        } else {
            throw new Exception("Should not happen. Args: " + t1.getClass().getSimpleName() + ", " + t2.getClass().getSimpleName());
        }
    }
}
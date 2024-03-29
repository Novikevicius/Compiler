package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Main;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.parser.Scope;

public abstract class Node {
    public abstract void print(ASTPrinter printer) throws Exception;

    protected Node parent;

    public void addChildren(ArrayList<Node> children) {
        children.forEach((child) -> {
            if (child != null) {
                addChildren(child);
            }
        });
    }

    public void addChildren(Node child) {
        if (child != null) {
            child.parent = this;
        }
    }

    public Node findAncestor(Class<?> cls) {
        Node curNode = parent;
        while (curNode != null) {
            if (curNode.getClass() == cls) {
                return curNode;
            }
            curNode = curNode.parent;
        }
        return null;
    }

    public boolean checkReturn() {
        try {
            Main.error("checkReturn not implemented for " + this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getLine() {
        try {
            Main.error("getLine not implemented for " + this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public abstract void resolveNames(Scope scope) throws Exception;
    public abstract Node checkTypes() throws Exception;
    public void genCode(CodeWriter writer) {
        try { 
            Main.error("genCode not implemented for " + this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void semanticError(int line, String msg) throws Exception
    {
        Main.error(Main.filename + ":" + line + ":semantic error: " + msg);
    }
    public static boolean unifyTypes(Node t1, Node t2, int line) throws Exception
    {
        return unifyTypes(t1, t2, line, true);
    }
    public static boolean unifyTypes(Node t1, Node t2, int line, boolean printError) throws Exception
    {
        if(t1 == null || t2 == null) {
            return true;
        }  
        if(t1 instanceof DeclFn ||  t2 instanceof DeclFn)
        {
            if(printError)
                semanticError(line, "Function name cannot be used as a variable");
            return false;
        }
        if(t1.getClass() != t2.getClass()){
            if(printError){
                Object type1 = null;
                Object type2 = null;
                if(t1 instanceof TypePrim)
                {
                    type1 = ((TypePrim)t1).getKind().getName();
                } else if (t1 instanceof ArrayDeclaration){
                    type1 = "Array";
                } else {
                    type1 = t1.getClass().getSimpleName();
                }
                if(t2 instanceof TypePrim)
                {
                    type2 = ((TypePrim)t2).getKind().getName();
                } else if (t2 instanceof ArrayDeclaration){
                    type2 = "Array";
                } else {
                    type2 = t2.getClass().getSimpleName();
                }
                semanticError(line, "Type mismatch: got: '" + type1 + "', expected: '" + type2 + "'");
            }
            return false;
        } else if(t1 instanceof TypePrim && t2 instanceof TypePrim) {
            TypePrim p1 = (TypePrim)t1;
            TypePrim p2 = (TypePrim)t2;
            if(p1.getKind() != p2.getKind())
            {
                if(printError)
                    semanticError(line, "Type mismatch: got: '" + p1.getKind().getName() + "', expected: '" + p2.getKind().getName() + "'");
                return false;
            }
            return true;
        } else if(t1 instanceof ArrayDeclaration && t2 instanceof ArrayDeclaration) {
            ArrayDeclaration a1 = (ArrayDeclaration)t1;
            ArrayDeclaration a2 = (ArrayDeclaration)t2;
            TypePrim p1 = (TypePrim)a1.getType();
            TypePrim p2 = (TypePrim)a2.getType();
            if(p1.getKind() != p2.getKind())
            {
                if(printError)
                    semanticError(line, "Array type mismatch: got: '" + p1.getKind().getName() + "', expected: '" + p2.getKind().getName() + "'");
                return false;
            }
            return true;
        }
        else {
            throw new Exception("Should not happen. Args: " + t1.getClass().getSimpleName() + ", " + t2.getClass().getSimpleName());
        }
    }
}
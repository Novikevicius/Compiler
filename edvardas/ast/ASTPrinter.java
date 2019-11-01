package edvardas.ast;

import java.util.ArrayList;
import java.lang.Object;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.nodes.Node;

public class ASTPrinter {
    private int indentLevel = 0;
    public void print(String title, Object obj) throws Exception
    {
        if(obj instanceof ArrayList) {
            printArray(title, obj);
        } else if(obj instanceof Node){
            printNode(title, obj);
        } else if(obj instanceof State){
            printText(title, obj);
        } else if(obj instanceof Token){
            printToken(title, "NULL");
        } else if(obj == null){
            printText(title, "NULL");
        }
        else {
            throw new Exception("Invalid print argument: " + obj.getClass().getName());
        }
    }
    private void printArray(String title, Object obj) throws Exception
    {
        Object[] objects = (Object[]) obj;
        if(objects.length < 1)
        {
            printText(title, "[]");
        }
        for (int i = 0; i < objects.length; i++)
        {
            print(String.format("%s[%i]", title, i), objects[i]);
        }
    }
    private void printNode(String title, Object obj) throws Exception
    {
        printText(title, obj.getClass().getName() + ":");
        indentLevel += 1;
        ((Node)obj).print();
        indentLevel += 1;
    }
    private void printText(String title, Object obj) throws Exception 
    {
        String indentation = new String(new char[indentLevel]).replace("\0", " ");
        System.out.println(indentation + title + ":" + obj);
    }
    private void printToken(String title, Object obj) throws Exception 
    {
        Token t = (Token)obj;
        printText(title, t.getValue() + " (" + t.getLine() + ")");
    }
}
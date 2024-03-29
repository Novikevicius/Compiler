package edvardas.ast;

import java.util.ArrayList;

import java.lang.Object;

import edvardas.State;
import edvardas.Token;
import edvardas.TokenType;
import edvardas.*;
import edvardas.ast.nodes.Node;

public class ASTPrinter {
    private StringBuilder indent;
    private int indentLevel = 0;
    public ASTPrinter() {
        indent = new StringBuilder();
    }
    public void print(String title, Object obj) throws Exception
    {
        if(obj instanceof ArrayList) {
            printArray(title, obj);
        } else if(obj instanceof Node){
            printNode(title, obj);
        } else if(obj instanceof State || obj instanceof BinaryOperator || obj instanceof UnaryOperator){
            printText(title, obj);
        } else if(obj instanceof Token){
            printToken(title, obj);
        } else if(obj == null){
            printText(title, "NULL");
        } else if(obj instanceof Boolean){
            printText(title, obj);
        }
        else {
            throw new Exception("Invalid print argument: " + obj.getClass().getName());
        }
    }
    private void printArray(String title, Object obj) throws Exception
    {
        ArrayList<?> objects = (ArrayList<?>) obj;
        if(objects.size() < 1)
        {
            printText(title, "[]");
        }
        for (int i = 0; i < objects.size(); i++)
        {
            String t = title + "[" + i + "]";
            print(t, objects.get(i));
        }
    }
    private void printNode(String title, Object obj) throws Exception
    {
        printText(title, obj.getClass().getSimpleName() + ":");
        indentLevel += 3;
        indent.append("|  ");
        ((Node)obj).print(this);
        indent.delete(indentLevel-3, indentLevel);
        indentLevel -= 3;
    }
    private void printText(String title, Object obj) throws Exception 
    {
        String indentation = new String(new char[indentLevel]).replace("\0", " ");
        System.out.println(indent + title + (title.isEmpty() ? "" : ": ") + obj);
    }
    private void printToken(String title, Object obj) throws Exception 
    {
        Token t = (Token)obj;
        String value = (t.getType() == State.IDENTIFIER || t.getTokenType() == TokenType.KEYWORD) ? t.getIdentifier() : t.getValue().toString();
        printText(title, value + " (" + t.getLine() + ")");
    }
}
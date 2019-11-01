package edvardas;

import java.util.*;

public class Parser {

    private ArrayList<Token> tokens;
    private Token curToken;
    private int offset;
    private String fileName;

    public Parser(String fileName, ArrayList<Token> tokens)
    {
        this.tokens = tokens;
        this.fileName = fileName;
        offset = 0;
    }
    private void error() {error("Unexpected token.", null);}
    private void error(State type) {error("Unexpected token.", type);}
    private void error(String msg, State expectedType)
    {
        Token t = tokens.get(offset);
        int line = t.getLine();
        int column = t.getColumn();
        String[] array = fileName.split("/"); // get file name
        String expected = expectedType == null ? " Got: " : " Expected:" + String.format("%s", expectedType) + " got: ";
        throw new Error("ParserError:" + array[array.length-1] + ":" + line + ":" + column + ": " + msg + expected + String.format("%s",curToken.getType()));
    }
    private Token expect(State tokenType)
    {
        if(curToken.getType() == tokenType)
        {
            offset++;
            return curToken = tokens.get(offset);
        }
        else
        {
            error("Unexpected token error. ", tokenType);
        }
        return null;
    }
    private Token accept(State tokenType)
    {
        curToken = tokens.get(offset);
        if(curToken.getType() == tokenType)
        {
            offset++;
            return curToken;
        }
        return null;
    }
    // <start> ::= <func_declarations> EOF
    public void parse()
    {
        parse_def_fns();
    }
    // <func_declarations> ::= { <func_declaration> } EOF
    private void parse_def_fns()
    {
        while (accept(State.EOF) == null) {
            parse_def_fn();
        }
    }
    // <func_declaration>  ::= <return_type> <identifier> "(" <fnc_params> ")" <block> 
    private void parse_def_fn()
    {
        parse_return_type();
        expect(State.IDENTIFIER);
        expect(State.L_PARENT);
        parse_params();
        expect(State.R_PARENT);
        parse_block();
    }
    // <return_type>  ::= "void" | <type>
    private void parse_return_type()
    {
        if( accept(State.TYPE_VOID) == null )  {
            parse_type();
        }
    }
    // <type> ::= "int" | "char" | "float" | "string" | "bool" 
    private void parse_type()
    {
        switch (curToken.getType()) {
            case TYPE_INT:
                expect(State.TYPE_INT);
                break;
            case TYPE_CHAR:
                expect(State.TYPE_CHAR);
                break;
            case TYPE_FLOAT:
                expect(State.TYPE_FLOAT);
                break;
            case TYPE_STRING:
                expect(State.TYPE_STRING);
                break;
            case TYPE_BOOL:
                expect(State.TYPE_BOOL);
                break;
            default:
                error();
                break;
        }
    }
    private void parse_params()
    {
        // do nothing, yet TODO: parse params
    }
    private void parse_block()
    {
        expect(State.L_CURLY);
        // do nothing, yet TODO: parse stmt
        expect(State.R_CURLY);
    }
}
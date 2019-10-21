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
    private void error() {error("Unexpected token", null);}
    private void error(String msg, State expectedType)
    {
        Token t = tokens.get(offset);
        int line = t.getLine();
        int column = t.getColumn();
        String[] array = fileName.split("/"); // get file name
        String expected = expectedType == null ? "" : " Expected:" + String.format("%s", expectedType) + " got: " + String.format("%s",curToken.getType());
        throw new Error("ParserError:" + array[array.length-1] + ":" + line + ":" + column + ": " + msg + expected);
    }
    private void expect(State tokenType)
    {
        if(curToken.getType() == tokenType)
        {
            offset++;
            curToken = tokens.get(offset);
        }
        else
        {
            error("Unexpected token error. ", tokenType);
        }
    }
    private State accept(State tokenType)
    {
        curToken = tokens.get(offset);
        if(curToken.getType() == tokenType)
        {
            offset++;
            return curToken.getType();
        }
        return null;
    }
    // <start> ::= <func_declarations> EOF
    public void parse()
    {
        while (accept(State.EOF) == null) {
            parse_def_fns();          
        }

    }
    // <func_declarations> ::= { <func_declaration> }
    private void parse_def_fns()
    {
        parse_def_fn();
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
    private void parse_return_type()
    {
        switch (curToken.getType()) {
            case TYPE_VOID:
                expect(State.TYPE_VOID);
                break;
            case TYPE_INT:
                expect(State.TYPE_INT);
                break;
            default:
                error();
                break;
        }
    }
    private void parse_params()
    {
        // do nothing, yet TODO:
    }
    private void parse_block()
    {
        expect(State.L_CURLY);
        expect(State.R_CURLY);
    }
}
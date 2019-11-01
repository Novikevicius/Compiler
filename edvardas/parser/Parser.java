package edvardas.parser;

import edvardas.*;
import edvardas.ast.nodes.Decl;
import edvardas.ast.nodes.DeclFn;
import edvardas.ast.nodes.Param;
import edvardas.ast.nodes.Program;
import edvardas.ast.nodes.Stmt;
import edvardas.ast.nodes.StmtBody;
import edvardas.ast.nodes.Type;
import edvardas.ast.nodes.TypePrim;

import java.util.*;
import java.util.ArrayList;

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
        curToken = tokens.get(offset);
        if(curToken.getType() == tokenType)
        {
            return curToken = tokens.get(offset++);
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
    public Program parse() throws Exception
    {
        return new Program(parse_def_fns());
    }
    // <func_declarations> ::= { <func_declaration> } EOF
    private ArrayList<Decl> parse_def_fns()
    {
        ArrayList<Decl> decls = new ArrayList<Decl>();
        while (accept(State.EOF) == null) {
            decls.add(parse_def_fn());
        }
        return decls;
    }
    // <func_declaration>  ::= <return_type> <identifier> "(" <fnc_params> ")" <block> 
    private DeclFn parse_def_fn()
    {
        Type retType = parse_return_type();
        Token name = expect(State.IDENTIFIER);
        expect(State.L_PARENT);
        ArrayList<Param> params = parse_params();
        expect(State.R_PARENT);
        StmtBody body = parse_block();
        return new DeclFn(retType, name, params, body);
    }
    // <return_type>  ::= "void" | <type>
    private Type parse_return_type()
    {
        if(accept(State.TYPE_VOID) == null )  {
            return parse_type();
        }
        return new TypePrim(State.TYPE_VOID);
    }
    // <type> ::= "int" | "char" | "float" | "string" | "bool" 
    private TypePrim parse_type()
    {
        switch (curToken.getType()) {
            case TYPE_INT:
                expect(State.TYPE_INT);
                return new TypePrim(State.TYPE_INT);
            case TYPE_CHAR:
                expect(State.TYPE_CHAR);
                return new TypePrim(State.TYPE_CHAR);
            case TYPE_FLOAT:
                expect(State.TYPE_FLOAT);
                return new TypePrim(State.TYPE_FLOAT);
            case TYPE_STRING:
                expect(State.TYPE_STRING);
                return new TypePrim(State.TYPE_STRING);
            case TYPE_BOOL:
                expect(State.TYPE_BOOL);
                return new TypePrim(State.TYPE_BOOL);
            default:
                error();
                return null;
        }
    }
    private ArrayList<Param> parse_params()
    {
        // do nothing, yet TODO: parse params
        return new ArrayList<Param>();
    }
    private StmtBody parse_block()
    {
        ArrayList<Stmt> stmts = new ArrayList<Stmt>();
        expect(State.L_CURLY);
        // do nothing, yet TODO: parse stmt
        expect(State.R_CURLY);
        return new StmtBody(stmts);
    }
}
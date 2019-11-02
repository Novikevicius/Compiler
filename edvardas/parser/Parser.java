package edvardas.parser;

import edvardas.*;
import edvardas.ast.nodes.ArrayDeclaration;
import edvardas.ast.nodes.ArrayElement;
import edvardas.ast.nodes.Decl;
import edvardas.ast.nodes.DeclFn;
import edvardas.ast.nodes.ExprBinary;
import edvardas.ast.nodes.ExprLiteral;
import edvardas.ast.nodes.ExprNegation;
import edvardas.ast.nodes.ExprPostfix;
import edvardas.ast.nodes.ExprPrefix;
import edvardas.ast.nodes.ExprVar;
import edvardas.ast.nodes.Expression;
import edvardas.ast.nodes.FunctionCall;
import edvardas.ast.nodes.Program;
import edvardas.ast.nodes.Statement;
import edvardas.ast.nodes.StatementAssignment;
import edvardas.ast.nodes.StatementBreak;
import edvardas.ast.nodes.StatementContinue;
import edvardas.ast.nodes.StatementElse;
import edvardas.ast.nodes.StatementElseIf;
import edvardas.ast.nodes.StatementFor;
import edvardas.ast.nodes.StatementIf;
import edvardas.ast.nodes.StatementReturn;
import edvardas.ast.nodes.StatementWhile;
import edvardas.ast.nodes.StmtBody;
import edvardas.ast.nodes.Type;
import edvardas.ast.nodes.TypePrim;
import edvardas.ast.nodes.VarAssignDeclaration;
import edvardas.ast.nodes.VarDeclaration;
import edvardas.ast.nodes.VarPrimaryDeclaration;

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
            Token result = curToken;
            curToken = tokens.get(++offset);
            return result;
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
            Token result = curToken;
            if(offset < tokens.size()-1) {
                curToken = tokens.get(++offset);
            }
            return result;
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
        ArrayList<VarDeclaration> params = parse_params();
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
    // <fnc_params> ::= <var_decl> { "," <var_decl> }
    private ArrayList<VarDeclaration> parse_params()
    {
        ArrayList<VarDeclaration> params = new ArrayList<VarDeclaration>();
        if(curToken.getType() == State.R_PARENT)
        {
            return params;
        }
        params.add(parse_var_decl());
        while(accept(State.COMMA) != null)
        {
            params.add(parse_var_decl());
        }
        return params;
    }
    // <var_decl> ::= <type> <identifier> | <type> <identifier> "=" <expression> | <type> "[" <int_literal> "]" <identifier>
    private VarDeclaration parse_var_decl()
    {
        Type type = parse_type();
        if(accept(State.L_BRACKET) != null)
        {
            Token size = expect(State.INT_LITERAL);
            expect(State.R_BRACKET);
            Token name = expect(State.IDENTIFIER);
            return new ArrayDeclaration(type, name, size);
        }
        Token name = expect(State.IDENTIFIER);
        if(accept(State.ASSIGN_OP) != null)
        {
            Expression expr = parse_expression();
            return new VarAssignDeclaration(name, type, expr);
        }
        return new VarPrimaryDeclaration(name, type);
    }
    private StmtBody parse_block()
    {
        ArrayList<Statement> stmts = new ArrayList<Statement>();
        expect(State.L_CURLY);
        while(accept(State.R_CURLY) == null){
            if(accept(State.SEMI_CLN) != null) {continue;}
            stmts.add(parse_stmt());
        }
        return new StmtBody(stmts);
    }
    private Statement parse_stmt()
    {
        switch (curToken.getType()) {
            case KW_IF:
                return parse_stmt_if();
            case KW_WHILE:
                return parse_stmt_while();
            case KW_FOR:
                return parse_stmt_for();
            case KW_BREAK:
                Token br = expect(State.KW_BREAK);
                expect(State.SEMI_CLN);
                return new StatementBreak(br);
            case KW_CONTINUE:
                Token cont = expect(State.KW_CONTINUE);
                expect(State.SEMI_CLN);
                return new StatementContinue(cont);
            case KW_RETURN:
                Token ret = expect(State.KW_RETURN);
                if(accept(State.SEMI_CLN) != null){
                    return new StatementReturn(ret, null);
                }
                Expression returnValue = parse_expression();
                expect(State.SEMI_CLN);
                return new StatementReturn(ret, returnValue);
            default:
                error();
                return null;
        }
    }
    private Statement parse_stmt_if()
    {
        ArrayList<StatementElseIf> elseif = new ArrayList<StatementElseIf>();
        StatementElse stmtElse = null;
        expect(State.KW_IF);
        expect(State.L_PARENT);
        Expression cond = parse_expression();
        expect(State.R_PARENT);
        StmtBody body = parse_block();
        while(accept(State.KW_ELSE) != null) {
            if(accept(State.KW_IF) != null) {
                expect(State.L_PARENT);
                Expression elseIfCond = parse_expression();
                expect(State.R_PARENT);
                StmtBody elseIfbody = parse_block();
                elseif.add(new StatementElseIf(elseIfCond, elseIfbody));
                continue;
            }
            StmtBody elsebody = parse_block();
            stmtElse = new StatementElse(elsebody);
            break;
        }
        return new StatementIf(cond, body, elseif, stmtElse);  
    }
    // <while_loop> ::= "while" "(" <expression> ")" <block>
    private Statement parse_stmt_while()
    {
        expect(State.KW_WHILE);
        expect(State.L_PARENT);
        Expression condition = parse_expression();
        expect(State.R_PARENT);
        StmtBody body = parse_block();
        return new StatementWhile(condition, body);
    }
    // <for_loop>   ::= "for" "(" <for_loop_conditions> ")" <block>
    /* 
    <for_loop_conditions> ::= <var_decl> ";" <expression> ";" <statement>
                        |                 ";" <expression> ";" <statement>
                        |     <var_decl> ";" <expression> ";"
                        |                 ";" <expression> ";"
    */
    private Statement parse_stmt_for()
    {
        expect(State.KW_FOR);
        expect(State.L_PARENT);
        VarDeclaration init = null;
        if(accept(State.SEMI_CLN) == null)
        {
            init = parse_var_decl();
            expect(State.SEMI_CLN);
        }
        Expression condition = parse_expression();
        expect(State.SEMI_CLN);
        StatementAssignment assign = null;
        if(accept(State.R_PARENT) == null)
        {
            assign = parse_stmt_assignment();
            expect(State.R_PARENT);
        }
        StmtBody body = parse_block();
        return new StatementFor(init, condition, assign, body);
    }
    // <assignement> ::= <identifier> <assignement_ops> <expression> | <array_elem> <assignement_ops> <expression>
    private StatementAssignment parse_stmt_assignment()
    {
        Token name = expect(State.IDENTIFIER);
        State operator = null;
        if(accept(State.ASSIGN_OP) != null) {
            operator = State.ASSIGN_OP;
        } else if(accept(State.ASSIGN_OP_DIV) != null) {
            operator = State.ASSIGN_OP_DIV;
        } else if(accept(State.ASSIGN_OP_MULT) != null) {
            operator = State.ASSIGN_OP_MULT;
        } else if(accept(State.ASSIGN_OP_MINUS) != null) {
            operator = State.ASSIGN_OP_MINUS;
        } else if(accept(State.ASSIGN_OP_PLUS) != null) {
            operator = State.ASSIGN_OP_PLUS;
        } else {
            error();
        } for(int i = 0; i < 10; i++) {}
        Expression expr = parse_expression();
        return new StatementAssignment(name, operator, expr);
    }
    //<expression> ::=  <expression> { ("++" | "--")}
    private Expression parse_expression()
    {
        Expression result = parse_expr_or();
        return result;
    }
    private Expression parse_expr_or()
    {
        Expression result = parse_expr_and();
        while(accept(State.LOGIC_OP_OR) != null)
        {
            result = new ExprBinary(State.LOGIC_OP_OR, result, parse_expr_and());
        }
        return result;
    }
    //<expr-and> ::= <expr-comp_equal> { "&&" <expr-comp_equal>  }
    private Expression parse_expr_and()
    {
        Expression result = parse_expr_comp_equal();
        while(accept(State.LOGIC_OP_OR) != null)
        {
            result = new ExprBinary(State.LOGIC_OP_AND, result, parse_expr_comp_equal());
        }
        return result;
    }
    //<expr-comp_equal> ::= <expr-compare_op>  { ("==" | "!=") <expr-compare_op> }
    private Expression parse_expr_comp_equal()
    {
        Expression result = parse_expr_comp_op();
        while(true)
        {
            if(accept(State.COMP_OP_EQ) != null) {
                result = new ExprBinary(State.COMP_OP_EQ, result, parse_expr_comp_op());
            } else if(accept(State.COMP_OP_NOT_EQ) != null) {
                result = new ExprBinary(State.COMP_OP_NOT_EQ, result, parse_expr_comp_op());
            } else {
                break;
            }
        }
        return result;
    }
    // <expr-comp_op> ::= <expr-sum> { <compare_op> <expr_sum> }
    private Expression parse_expr_comp_op()
    {
        Expression result = parse_expr_sum();
        while(true)
        {
            if(accept(State.COMP_OP_LESS) != null) {
                result = new ExprBinary(State.COMP_OP_LESS, result, parse_expr_sum());
            } else if(accept(State.COMP_OP_LESS_EQ) != null) {
                result = new ExprBinary(State.COMP_OP_LESS_EQ, result, parse_expr_sum());
            } else if(accept(State.COMP_OP_MORE) != null) {
                result = new ExprBinary(State.COMP_OP_MORE, result, parse_expr_sum());
            } else if(accept(State.COMP_OP_MORE_EQ) != null) {
                result = new ExprBinary(State.COMP_OP_MORE_EQ, result, parse_expr_sum());
            } else {
                break;
            }
        }
        return result;
    }
    // <expr-sum> ::= <expr-mult> { ("+" | "-" ) <expr-mult>}
    private Expression parse_expr_sum()
    {
        Expression result = parse_expr_mult();
        while(true)
        {
            if(accept(State.OP_PLUS) != null) {
                result = new ExprBinary(State.OP_PLUS, result, parse_expr_mult());
            } else if(accept(State.OP_MINUS) != null) {
                result = new ExprBinary(State.OP_MINUS, result, parse_expr_mult());
            } else {
                break;
            }
        }
        return result;
    }
    // <expr-mult> ::= <expr-negation> { ("*" | "/" ) <expr-negation>}
    private Expression parse_expr_mult()
    {
        Expression result = parse_exponent();
        while(true)
        {
            if(accept(State.OP_MULT) != null) {
                result = new ExprBinary(State.OP_MULT, result, parse_exponent());
            } else if(accept(State.OP_DIV) != null) {
                result = new ExprBinary(State.OP_DIV, result, parse_exponent());
            } else {
                break;
            }
        }
        return result;
    }
    // <expr-negation> ::= "-" <expr-negation> | <expr-exponent>
    private Expression parse_expr_negation()
    {
        if(accept(State.OP_MINUS) != null)
        {
            return new ExprNegation(parse_expr_negation());
        }
        return parse_expr_parent();
    }
    // <expr-exponen> ::= <expr-parent> { "^" <expr-parent> }
    private Expression parse_exponent()
    {
        Expression result = parse_expr_negation();
        while(accept(State.OP_EXP) != null)
        {
            result = new ExprBinary(State.OP_EXP, result, parse_expr_negation());
        }
        return result;
    }
    // <expr-parent> ::= <expr-affix> | "(" <expression> ")"
    // <expr-8>       ::= <affix> | "(" <expr-0> ")" | <func_call> | <identifier> | <literal> | <array_elem>
    private Expression parse_expr_parent()
    {
        Expression result = parse_expr_primary();
        while (true) {
            if(accept(State.OP_AFFIX_PLUS) != null)
            {
                return new ExprPostfix(State.OP_AFFIX_PLUS, result);
            } else if(accept(State.OP_AFFIX_MINUS) != null)
            {
                return new ExprPostfix(State.OP_AFFIX_MINUS, result);
            } else {
                break;
            }
        }
        return result;
    }
    private Expression parse_expr_primary()
    {
        if(accept(State.L_PARENT) != null)
        {
            Expression result = parse_expression();
            expect(State.R_PARENT);
            return result;
        }
        Token identName;
        if((identName = accept(State.IDENTIFIER)) != null)
        {
            if(accept(State.L_PARENT) != null)
            {
                return parse_expr_fn_call(identName);
            } else if(accept(State.L_BRACKET) != null)
            {
                return parse_expr_array_elem(identName);
            }
            return new ExprVar(identName);
        }
        if(accept(State.OP_AFFIX_PLUS) != null)
        {
            return new ExprPrefix(State.OP_AFFIX_PLUS, parse_expression());
        }
        if(accept(State.OP_AFFIX_MINUS) != null)
        {
            return new ExprPrefix(State.OP_AFFIX_MINUS, parse_expression());
        }
        return new ExprLiteral(expect(State.INT_LITERAL));
    }
    private Expression parse_expr_fn_call(Token name)
    {
        ArrayList<Expression> result = new ArrayList<Expression>();
        if(accept(State.R_PARENT) != null)
        {
            return new FunctionCall(name, result);
        }
        result.add(parse_expression());
        while(accept(State.COMMA) != null)
        {
            result.add(parse_expression());
        }
        expect(State.R_PARENT);
        return new FunctionCall(name, result);
    }
    private Expression parse_expr_array_elem(Token name)
    {
        Expression index = parse_expression();
        expect(State.R_BRACKET);
        return new ArrayElement(name, index);
    }
}
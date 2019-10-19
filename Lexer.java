import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class Lexer
{
    private String fileName;
    private State curState;

    private int lineNumber = 1;
    private int columnNumber = 0;

    private ArrayList<Token> tokens;

    private String buffer = "";
    private char curChar;
    private int tokenStart = 0;
    private int tokenLine = 1;
    private boolean readNext = true;
    private Map<Character, Character> special_symbols;
    private Map<String, String> keywords;

    public Lexer(String file)
    {
        this.fileName = file;
        tokens = new ArrayList<Token>();
        keywords = new HashMap<>();
        keywords.put("while",  "KW_WHILE");
        keywords.put("for",    "KW_FOR");
        keywords.put("if",     "KW_IF");
        keywords.put("else",   "KW_ELSE");
        keywords.put("void",   "TYPE_VOID");
        keywords.put("int",    "TYPE_INT");
        keywords.put("char",   "TYPE_CHAR");
        keywords.put("float",  "TYPE_FLOAT");
        keywords.put("bool",   "TYPE_BOOL");
        keywords.put("true",   "LIT_TRUE");
        keywords.put("false",  "LIT_FALSE");
        keywords.put("string", "TYPE_STRING");
        keywords.put("read",   "KW_READ");
        keywords.put("write",  "KW_WRITE");
        keywords.put("return", "KW_RETURN");
        keywords.put("break",  "TYPE_BREAK");
        keywords.put("continue",  "TYPE_CONTINUE");
        special_symbols = new HashMap<>();
        special_symbols.put('n', '\n');
        special_symbols.put('t', '\t');
        special_symbols.put('0', '\0');
        special_symbols.put('\'', '\'');
        special_symbols.put('\\', '\\');
        special_symbols.put('\"', '\"');
        special_symbols.put('\'', '\'');
    }

    public void start() throws IOException
    {
        curState = State.START;
        Scanner input = new Scanner(new FileReader(fileName));
        String line = "";
        while(input.hasNext())
        {
            line = input.nextLine() + "\n";
            for(int i = 0; i < line.length(); i++)
            {
                curChar = line.charAt(i);
                columnNumber++;
                parse();
                if(!readNext) 
                {
                    readNext = true; columnNumber--; i--;
                }
            }
        }
        input.close();
        if (curState != State.START)
        {
            if(curState == State.MULTI_COMMENT || curState == State.M_COMMENT_START)
                error("Multi line comment not closed");
            else
                error("Undetermined token");
        }
        else
        {
            beginToken(State.EOF);
            endToken();
        }
        for(int i = 0; i < tokens.size(); i++)
        {
            tokens.get(i).print();
        }

    }
    private void beginToken(State state)
    {
        buffer = "";
        curState = state;
        tokenStart = columnNumber;
        tokenLine = lineNumber;
    }

    private void endToken()
    {
        endToken(String.format("%s", curState), false);
    }
    private void endToken(String type)
    {
        endToken(type, false);
    }
    private void endToken(String type, boolean isKwd)
    {
        //Token l = new Token( isKwd ? "" : buffer, type, tokenLine, tokenStart);
        Token l = new Token( buffer, type, tokenLine, tokenStart);
        tokenStart = 0;
        tokens.add(l);
        curState = State.START;
        buffer = "";
        tokenLine = 1;
    }
    private void oneSymbolToken(State state)
    {
        beginToken(state);
        buffer += curChar;
        endToken();
    }

    private void parse()
    {
        switch (curState) {
            case START:
                st_start();
                break;
            case IDENTIFIER:
                st_ident();
                break;
            case INT_LITERAL:
                st_int_literal();
                break;
            case FLOAT_LITERAL:
                st_float_literal();
                break;
            case FLOAT_LITERAL_EXP:
                st_float_literal_exp();
                break;
            case FLOAT_LITERAL_EXP_NEGATIVE:
                st_float_literal_exp_negative();
                break;
            case FLOAT_LITERAL_EXP_END:
                st_float_literal_exp_end();
                break;
            case CHAR_LITERAL:
                st_char_literal();
                break;
            case CHAR_LITERAL_SPEC:
                st_char_spec_literal();
                break;
            case CHAR_LITERAL_END:
                st_char_literal_end();
                break;
            case STRING_LITERAL:
                st_string_literal();
                break;
            case STRING_LITERAL_SPEC:
                st_string_literal_spec();
                break;
            case L_COMMENT:
                st_l_comment();
                break;
            case M_COMMENT_START:
                st_m_comment_start();
                break;
            case M_COMMENT_END:
                st_m_comment_end();
                break;
            case MULTI_COMMENT:
                st_multi_comment();
                break;
            case OP_MINUS:
                st_op_minus();
                break;
            case OP_PLUS:
                st_op_plus();
                break;
            case OP_MULT:
                st_op_mult();
                break;
            case OP_DIV:
                st_op_div();
                break;
            case COMP_OP_LESS:
                st_comp_op_less();
                break;
            case COMP_OP_MORE:
                st_comp_op_more();
                break;
            case COMP_OP_NOT_EQ:
                st_comp_op_not_eq();
                break;
            case ASSIGN_OP:
                st_assignment_op();
                break;
            case LOGIC_OP_AND:
                st_logic_and();
                break;
            case LOGIC_OP_OR:
                st_logic_or();
                break;
            default:
                error("Unrecognized symbol");
                break;
        }
    }
    // States
    private void st_start()
    {
        if(isLetter(curChar))
        {
            beginToken(State.IDENTIFIER);
            buffer += curChar;
            return;
        }
        if(isDigit(curChar))
        {
            beginToken(State.INT_LITERAL);
            buffer += curChar;
            return;
        }
        switch (curChar) {
            case '_':
                beginToken(State.IDENTIFIER);
                buffer += curChar;
                break;
            case '.':
                beginToken(State.FLOAT_LITERAL);
                buffer += curChar;
                break;
            case '^':
                oneSymbolToken(State.OP_EXP);  
                break;
            case '\'':
                beginToken(State.CHAR_LITERAL);
                break;
            case '\"':
                beginToken(State.STRING_LITERAL);
                break;
            case ',':
                oneSymbolToken(State.COMMA);  
                break;  
            case '(':
                oneSymbolToken(State.L_PARENT);  
                break;  
            case ')':
                oneSymbolToken(State.R_PARENT);
                break;  
            case '{':
                oneSymbolToken(State.L_CURLY);       
                break;  
            case '}':
                oneSymbolToken(State.R_CURLY);    
                break;
            case '[':
                oneSymbolToken(State.L_BRACKET);       
                break;  
            case ']':
                oneSymbolToken(State.R_BRACKET);    
                break;
            case ';':
                oneSymbolToken(State.SEMI_CLN);           
                break;    
            case '#':
                curState = State.L_COMMENT;
                break;
            case '`':
                curState = State.M_COMMENT_START;
                break;
            case '-':
                beginToken(State.OP_MINUS);
                buffer += curChar;
                break;
            case '+':
                beginToken(State.OP_PLUS);
                buffer += curChar;
                break;
            case '*':
                beginToken(State.OP_MULT);
                buffer += curChar;
                break;
            case '/':
                beginToken(State.OP_DIV);
                buffer += curChar;
                break;
            case '<':
                beginToken(State.COMP_OP_LESS);
                buffer += curChar;
                break;
            case '>':
                beginToken(State.COMP_OP_MORE);
                buffer += curChar;
                break;
            case '!':
                beginToken(State.COMP_OP_NOT_EQ);
                buffer += curChar;
                break;
            case '=':
                beginToken(State.ASSIGN_OP);
                buffer += curChar;
                break;
            case '&':
                beginToken(State.LOGIC_OP_AND);
                buffer += curChar;
                break;
            case '|':
                beginToken(State.LOGIC_OP_OR);
                buffer += curChar;
                break;
            case ' ':
                columnNumber--;
                break;    
            case '\n':
                lineNumber++;
                columnNumber = 0;
                break;  
            default:
                error("Unrecognized symbol");
                break;
        }
    }
    private void st_ident()
    {
        if(isLetter(curChar) || isDigit(curChar) || curChar == '_')
        {
            buffer += curChar;
            return;
        }
        if(!buffer.isEmpty())
        {
            String kw = keywords.get(buffer);
            if(kw != null)
            {
                endToken(kw, true);
                readNext = false;
                return;
            }
            readNext = false;
            endToken();
        }
        else
        {
            error("Unrecognized symbol");
        }
    }
    private void st_int_literal()
    {
        if(isDigit(curChar))
        {
            buffer += curChar;
            return;
        }
        switch(curChar)
        {
            case '.':
                buffer += curChar;
                curState = State.FLOAT_LITERAL;
                break;
            case 'e':
                buffer += curChar;
                curState = State.FLOAT_LITERAL_EXP;
                break;
            case ' ':
            case '\n':
            case '\t':
                endToken();
                break;
            default:
                readNext = false;
                error("Unrecognized int literal value");
                break;
        }
    }
    private void st_float_literal_exp()
    {
        if(isDigit(curChar))
        {
            buffer += curChar;
            curState = State.FLOAT_LITERAL_EXP_END;
        }
        else if(curChar == '-')
        {            
            buffer += curChar;
            curState = State.FLOAT_LITERAL_EXP_NEGATIVE;
        }
        else
        {
            readNext = false;
            error("Unrecognized float literal");
        }
    }
    private void st_float_literal_exp_negative()
    {
        if(isDigit(curChar))
        {
            buffer += curChar;
            curState = State.FLOAT_LITERAL_EXP_END;
        }
        else
        {
            readNext = false;
            error("Unexpected character in float literal after '-'");
        }
    }
    private void st_float_literal_exp_end()
    {
        if(isDigit(curChar))
        {
            buffer += curChar;
            curState = State.FLOAT_LITERAL_EXP_END;
        }
        else if(curChar == ' ' || curChar == '\t' || curChar == '\n')
        {
            endToken();
        }
        else
        {
            readNext = false;
            error("Unexpected symbol after float literal");
        }
    }

    private void st_float_literal()
    {
        if(isDigit(curChar))
        {
            buffer += curChar;
        }
        else
        {
            readNext = false;
            endToken();
        }
    }
    private void st_char_literal()
    {
        switch(curChar)
        {
            case '\\':
                curState = State.CHAR_LITERAL_SPEC;
                break;
            case '\'':
                error("Empty char literal");
                break;
            default:
                buffer += curChar;
                curState = State.CHAR_LITERAL_END;
                break;
        }
    }
    private void st_char_spec_literal()
    {
        Character special_char = special_symbols.get(curChar);
        if(special_char != null)
        {
            buffer += special_char.charValue();
            curState = State.CHAR_LITERAL_END;
        }
        else
        {
            error("Unrecognized special character");
        }
    }
    private void st_char_literal_end()
    {
        if(curChar == '\'')
        {
            endToken(String.format("%s", State.CHAR_LITERAL));
        }
        else
        {
            error("Char literal has multiple symbols");
        }
    }
    private void st_string_literal()
    {
        switch(curChar)
        {
            case '\\':
                curState = State.STRING_LITERAL_SPEC;
                break;
            case '\"':
                endToken();
                break;
            case '\n':
                lineNumber++;
                break;
            default:
                buffer += curChar;
                break;
        }
    }
    private void st_string_literal_spec()
    {
        Character special_char = special_symbols.get(curChar);
        if(special_char != null)
        {
            curState = State.STRING_LITERAL;
            buffer += special_char;
        }
        else
        {
            error("Unrecognized special character");
        }
    }
    private void st_l_comment()
    {
        if(curChar == '\n')
        {
             curState = State.START;
             readNext = false;
        }
    }
    private void st_m_comment_start()
    {
        if(curChar == '`')
            curState = State.MULTI_COMMENT;
        else
        {
            error("Unrecognized symbol");
        }
    }
    private void st_multi_comment()
    {
        if(curChar == '`')
            curState = State.M_COMMENT_END;
        else if(curChar == '\n')
            lineNumber++;
        // skip other symbols
    }
    private void st_m_comment_end()
    {
        if(curChar == '`')
            curState = State.START;
        else
        {
            curState = State.MULTI_COMMENT;
            if(curChar == '\n') lineNumber++;
        }
    }
    private void st_op_minus()
    {
        switch(curChar)
        {
            case '-':
                buffer += curChar;
                curState  = State.OP_AFFIX_MINUS;
                endToken();
                break;
            case '=':
                buffer += curChar;
                curState  = State.ASSIGN_OP_MINUS;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    private void st_op_plus()
    {
        switch(curChar)
        {
            case '+':
                buffer += curChar;
                curState  = State.OP_AFFIX_PLUS;
                endToken();
                break;
            case '=':
                buffer += curChar;
                curState  = State.ASSIGN_OP_PLUS;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    private void st_op_mult()
    {
        switch(curChar)
        {
            case '=':
                buffer += curChar;
                curState  = State.ASSIGN_OP_MULT;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    private void st_op_div()
    {
        switch(curChar)
        {
            case '=':
                buffer += curChar;
                curState  = State.ASSIGN_OP_DIV;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    private void st_comp_op_less()
    {
        switch(curChar)
        {
            case '=':
                buffer += curChar;
                curState  = State.COMP_OP_LESS_EQ;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    private void st_comp_op_more()
    {
        switch(curChar)
        {
            case '=':
                buffer += curChar;
                curState  = State.COMP_OP_MORE_EQ;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    
    private void st_comp_op_not_eq()
    {
        switch(curChar)
        {
            case '=':
                buffer += curChar;
                curState  = State.COMP_OP_NOT_EQ;
                endToken();
                break;
            case ' ':
                columnNumber--;
                break;
            case '\n':
                columnNumber = 0;
                lineNumber++;
                break;
            default:            
                readNext = false;
                error("Unrecognized symbol"); // TODO: FIX ERROR MESSAGE
                break;
        }
    }
    private void st_assignment_op()
    {
        switch(curChar)
        {
            case '=':
                buffer += curChar;
                curState  = State.COMP_OP_EQ;
                endToken();
                break;
            default:            
                endToken();
                readNext = false;
                break;
        }
    }
    private void st_logic_and()
    {
        if(curChar == '&')
        {
            buffer += curChar;
            endToken();
        }
        else
        {
            readNext = false;
            error("Unrecognized symbol");
        }
    }
    private void st_logic_or()
    {
        if(curChar == '|')
        {
            buffer += curChar;
            endToken();
        }
        else
        {
            readNext = false;
            error("Unrecognized symbol");
        }
    }
    private void error(String msg)
    {
        buffer = "";
        curState = State.START;
        String[] array = fileName.split("/"); // get file name
        System.out.println("LexerError: " + array[array.length - 1] + ":" + lineNumber + ":" + columnNumber + ": " + msg + " '" +curChar +"'");
    }
}
public class Token
{
    private String token;
    private State state;
    private int line;
    private int column;

    private TokenType type;
    private int intValue;
    private float floatValue;
    private boolean boolValue;
    private char charValue;
    private String stringValue;

    public Token()
    {
        this("", "", 0, 0);
    }
    public Token(String lex, State state, TokenType type, int l, int c)
    {
        this.type = type;
        setToken(lex);
        setState(state);
        setLine(l);
        setColumn(c);
    }
    public void setValue(int v)     { intValue = v;}
    public void setValue(float v)   { floatValue = v;}
    public void setValue(boolean v) { boolValue = v;}
    public void setValue(char v)    { charValue = v;}
    public void setValue(String v)  { stringValue = v;}
    public int getInt() {return intValue;}
    public float getFloat() {return floatValue;}
    public boolean getBool() {return boolValue;}
    public char getChar() {return charValue;}
    public String getString() {return stringValue;}
    public String getIdentifier() {return token;}
    public String getKeyword() {return token;}

    public String getToken()
    {
        return token;
    }
    public State getState()
    {
        return state;
    }
    public int getLine()
    {
        return line;
    }
    public int getColumn()
    {
        return column;
    }
    public void setToken(String l)
    {
        token = l;
    }
    public void setState(State t)
    {
        state = t;
    }
    public void setLine(int l)
    {
        line = l;
    }
    public void setColumn(int c)
    {
        column = c;
    }
    public void print()
    {        
        String value = "";
        switch(type)
        {
            case INT:
                value = Integer.toString(intValue);
                break;
            case FLOAT:
            case FLOAT_EXP:
                value = Float.toString(floatValue);
                break;
            case IDENTIFIER:
                value = token;
                break;
            case BOOL:
                value = Boolean.toString(boolValue);
                break;
            case STRING:
                value = stringValue;
                break;
            case CHAR:
                value = Character.toString(charValue);
                break;
            case KEYWORD:
                value = "";
                break;
            case NONE:
                value = "";
                break;
            default:
                value = token;
                break;
        }
        System.out.println(String.format("%-3s", line) + " | " + String.format("%-3s",column) + " | "+ String.format("%-25s", type == TokenType.KEYWORD  || type == TokenType.BOOL ? token : state) + " | " + value);
    }
    public void addSymbol(char s)
    {
        token += s;
    }
}
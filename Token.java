public class Token
{
    private String token;
    private State state;
    private int line;
    private int column;

    private TokenType type;
    private Object value;

    public Token()
    {
        this("", State.START, TokenType.NONE, 0, 0);
    }
    public Token(String lex, State state, TokenType type, int l, int c)
    {
        this.type = type;
        setToken(lex);
        setState(state);
        setLine(l);
        setColumn(c);
    }
    public void setValue(Object v) { value = v;}
    public Object getValue() {return value;}
    public int getInt() {return (int)value;}
    public float getFloat() {return (float)value;}
    public boolean getBool() {return (boolean)value;}
    public char getChar() {return (char)value;}
    public String getString() {return (String)value;}
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
        System.out.println(String.format("%-3s", line) + " | " + String.format("%-3s",column) + " | "+ String.format("%-25s", state) + " | " + (value == null ? "" : value));
    }
    public void addSymbol(char s)
    {
        token += s;
    }
}
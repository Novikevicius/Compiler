public class Token
{
    private String token;
    private String type;
    private int line;
    private int column;

    public Token()
    {
        this("", "", 0, 0);
    }
    public Token(String lex, String type, int l, int c)
    {
        setToken(lex);
        setType(type);
        setLine(l);
        setColumn(c);
    }
    public String getToken()
    {
        return token;
    }
    public String getType()
    {
        return type;
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
    public void setType(String t)
    {
        type = t;
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
        System.out.println(String.format("%-3s", line) + " | " + String.format("%-3s",column) + " | "+ String.format("%-20s",type) + " | " + token);
    }
    public void addSymbol(char s)
    {
        token += s;
    }
}
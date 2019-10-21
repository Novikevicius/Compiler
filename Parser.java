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
    public void parse()
    {
        while (accept(State.EOF) == null) {
            expect(State.EOF);            
        }

    }
    private void error() {error("Unexpected token");}
    private void error(String msg)
    {
        Token t = tokens.get(offset);
        int line = t.getLine();
        int column = t.getColumn();
        String[] array = fileName.split("/"); // get file name
        throw new Error("ParserError:" + array[array.length-1] + ":" + line + ":" + column + ": " + msg);
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
            error();
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
}
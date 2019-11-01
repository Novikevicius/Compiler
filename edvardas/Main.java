package edvardas;

import java.util.*;
import edvardas.parser.*;

public class Main 
{
    public static void main(String[] args) 
    {
        String path;
        if(args.length < 1)
        {
            System.out.println(args.length);
            System.out.println("Error: program usage: java Main filename.txt");
        }
        path = args[0];
        if(args.length > 1)
        {
            for(int i = 1; i < args.length; i++)
            {
                path += " " + args[i];
            }
        }
        try
        {
            Lexer lexer = new Lexer(path);
            lexer.start();
            ArrayList<Token> tokens = lexer.getTokens();
            
            for (Token token : tokens) {
                String line   = String.format("%-3s",token.getLine());
                String column = String.format("%-3s",token.getColumn());
                String type   = String.format("%-25s",token.getState());
                String value  = token.getValue() == null ? "" : String.format("%-5s",token.getValue());
                System.out.println(line + " | " + column + " | " + type + " | " + value);
            }
            Parser parser = new Parser(path, tokens);
            parser.parse();
            System.out.println("YAY");
        }
        catch(Error e)
        {
            e.printStackTrace();       
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
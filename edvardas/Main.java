package edvardas;

import java.util.*;

import edvardas.ast.ASTPrinter;
import edvardas.ast.nodes.Node;
import edvardas.parser.*;

public class Main 
{
    public static String filename;
    public static boolean debug = false;
    private static ArrayList<String> errors;
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
            String[] array = path.split("/"); // get file name
            Main.filename = array[array.length-1];
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
                String value  = token.getValue() == null ? (token.getType() == State.IDENTIFIER ? token.getIdentifier() : "") : String.format("%-5s",token.getValue());
                System.out.println(line + " | " + column + " | " + type + " | " + value);
            }
            Parser parser = new Parser(path, tokens);
            Node root = parser.parse();
            Scope scope = new Scope(null);
            root.resolveNames(scope);
            root.checkTypes();
            ASTPrinter astPrinter = new ASTPrinter();
            //astPrinter.print("", root);
            if(errors != null)
            {
                for (String err : errors) {
                    System.err.println(err);
                }
            }
        }
        catch(Error e)
        {
            if(debug)
                e.printStackTrace();   
            else
                System.out.println(e.getMessage());    
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void error(String err) throws Exception
    {
        if(errors == null)
            errors = new ArrayList<String>();
        errors.add(err);
        throw new Exception(err);
    }
}
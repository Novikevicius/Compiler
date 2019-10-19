public class Main 
{
    /*
    
    */
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
        Lexer lexer = new Lexer(path);
        try
        {
            lexer.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
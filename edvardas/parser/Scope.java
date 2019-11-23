package edvardas.parser;

import java.util.HashMap;

import edvardas.Token;
import edvardas.ast.nodes.Node;

public class Scope {
    private Scope parentScope;
    private HashMap<Token, Node> members;
    
    public Scope(Scope scope)
    {
        parentScope = scope;
    }
    public void add(Token name_token, Node node)
    {
        if(members.containsKey(name_token))
        {
            System.err.println(String.format("file:%i:%s: %s", name_token.getLine(), "Duplicate variable", name_token.getIdentifier()));
        }
        else
        {
            members.put(name_token, node);
        }
    }

}

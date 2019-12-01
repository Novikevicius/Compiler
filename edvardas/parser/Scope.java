package edvardas.parser;

import java.util.HashMap;

import edvardas.Main;
import edvardas.Token;
import edvardas.ast.nodes.Node;

public class Scope {
    private Scope parentScope;
    private HashMap<String, Node> members;

    public Scope(Scope scope) {
        parentScope = scope;
        members = new HashMap<String, Node>();
    }

    public Scope getParent() {
        return parentScope;
    }
    public Node getMember(String name)
    {
        return members.get(name);
    }
    public void add(Token name_token, Node node) {
        String name = name_token.getIdentifier();
        if (members.containsKey(name)) {
            error(name_token, "Duplicate variable");
        } else {
            members.put(name, node);
        }
    }

    public Node resolveName(Token nameToken) {
        Node node = members.get(nameToken.getIdentifier());
        if (node != null) {
            return node;
        }
        if (parentScope != null) {
            return parentScope.resolveName(nameToken);
        }
        error(nameToken, "Undeclared variable");
        return null;
    }

    private void error(Token token, String message) {
        try {
            Main.error(String.format("file:%d:%s: %s", token.getLine(), message, token.getIdentifier()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

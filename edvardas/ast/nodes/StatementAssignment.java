package edvardas.ast.nodes;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;

public class StatementAssignment extends Statement {
    private Token name;
    private State operator;
    private Expression value;
    
    public StatementAssignment(Token name, State operator, Expression value) {
        this.name = name;
        this.operator = operator;
        this.value = value;
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("operator", operator);
        printer.print("value", value);
    }
}
package edvardas.ast.nodes;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class ArrayElement extends Expression {
    private Node type;
    private Token name;
    private Expression index;
    public int stack_slot;

    public ArrayElement(Token name, Expression index) {
        this.name = name;
        this.index = index;
        addChildren(index);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("index", index);
    }
    public Expression getIndex()
    {
        return index;
    }
    @Override
    public void resolveNames(Scope scope) throws Exception
    {
        Node n = scope.resolveName(name);
        if(!(n instanceof ArrayDeclaration))
        {
            semanticError(name.getLine(), name.getIdentifier() + " is not an array");
            return;
        }
        ArrayDeclaration decl = (ArrayDeclaration) n;
        type = decl.getType();
        stack_slot = decl.stack_slot;
        index.resolveNames(scope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        index.checkTypes();
        return type != null ? type.checkTypes() : null;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        index.genCode(writer);
        State t = ((TypePrim)type).getKind();
        writer.write(Instruction.PUSH, stack_slot, State.TYPE_INT);
        writer.write(Instruction.ADD, State.TYPE_INT);
        writer.write(Instruction.GET_A_L, t);
    }
}
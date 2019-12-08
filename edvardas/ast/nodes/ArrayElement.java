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
    private int stack_slot;

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
    @Override
    public void resolveNames(Scope scope) throws Exception
    {
        ArrayDeclaration decl = (ArrayDeclaration) scope.resolveName(name);
        type = decl.getType();
        stack_slot = decl.stack_slot;
        index.resolveNames(scope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        index.checkTypes();
        return type.checkTypes();
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
        writer.write(Instruction.PUSH, stack_slot, t);
        writer.write(Instruction.ADD, t);
        writer.write(Instruction.GET_A_L, t);
    }
}
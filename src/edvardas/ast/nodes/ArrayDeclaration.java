package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class ArrayDeclaration extends VarDeclaration {
    private Token name;
    private Token size;

    public ArrayDeclaration(Type type, Token name, Token size) {
        this.type = type;
        this.name = name;
        this.size = size;
        addChildren(type);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("type", type);
        printer.print("name", name);
        printer.print("size", size);
    }
    @Override
    public void resolveNames(Scope scope)
    {
        stack_slot = stack_slot_index;
        stack_slot_index += (int)size.getValue();
        scope.add(name, this);
    }
    @Override
    public Node checkTypes()
    {
        // do nothing
        return this;
    }
    
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        ArrayList<Object> ops = new ArrayList<Object>(2);
        ops.add(stack_slot);
        ops.add(size.getValue());
        writer.write(Instruction.DEC_A_L, ops, type.getKind());
    }
}
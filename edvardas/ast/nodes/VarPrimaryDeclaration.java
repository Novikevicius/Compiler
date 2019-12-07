package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class VarPrimaryDeclaration extends VarDeclaration {
    public VarPrimaryDeclaration(Token name, Type type) {
        this.name = name;
        this.type = type;
        addChildren(type);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("name", name);
        printer.print("type", type);
    }
    @Override
    public void resolveNames(Scope parentScope)
    {
        stack_slot = stack_slot_index;
        stack_slot_index += 1;
        parentScope.add(name, type);
    }
    @Override
    public Node checkTypes()
    {
        // do nothing
        return type;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        writer.write(Instruction.SET_L, stack_slot, type.getKind());
    }
}
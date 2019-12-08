package edvardas.ast.nodes;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class VarAssignDeclaration extends VarDeclaration {
    private Expression expr;

    public VarAssignDeclaration(Token name, Type type, Expression expr) {
        this.name = name;
        this.type = type;
        this.expr = expr;
        addChildren(type);
        addChildren(expr);
    }
    public void print(ASTPrinter printer) throws Exception
    {
        printer.print("type", type);
        printer.print("name", name);
        printer.print("expr", expr);
    }
    @Override
    public void resolveNames(Scope parentScope) throws Exception
    {
        parentScope.add(name, type);
        stack_slot = stack_slot_index;
        stack_slot_index += 1;
        expr.resolveNames(parentScope);
    }
    @Override
    public Node checkTypes() throws Exception
    {
        Node tAssign = expr.checkTypes();
        unifyTypes(tAssign, type, this.getLine());
        return tAssign;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    @Override
    public void genCode(CodeWriter writer)
    {
        expr.genCode(writer);
        writer.write(Instruction.SET_L, stack_slot, type.getKind());
    }
}
package edvardas.ast.nodes;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class ArrayReadArgument extends ReadArgument {
    private Token name;
    private Expression index;
    private int stack_slot;
    private Node type;

    public ArrayReadArgument(Token name, Expression index) {
        this.name = name;
        this.index = index;
        addChildren(index);
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("name", name);
        printer.print("index", index);
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        stack_slot = ((ArrayDeclaration) scope.resolveName(name)).stack_slot;

        type = ((TypePrim) ((ArrayDeclaration) scope.resolveName(name)).getType());
        index.resolveNames(scope);
    }

    @Override
    public Node checkTypes() throws Exception {
        index.checkTypes();
        return type;
    }

    @Override
    public int getLine() {
        return name.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        State t = null;
        try {
            t = ((TypePrim) checkTypes()).getKind();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        writer.write(Instruction.READ, t);
        index.genCode(writer);
        writer.write(Instruction.PUSH, stack_slot, State.TYPE_INT);
        writer.write(Instruction.ADD, State.TYPE_INT);
        writer.write(Instruction.SET_A_L, t);
    }
}
package edvardas.ast.nodes;

import edvardas.State;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class ArrayTarget extends AssignmentTarget {
    private ArrayElement arrayElement;

    public ArrayTarget(ArrayElement elem) {
        this.arrayElement = elem;
        addChildren(elem);
    }

    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("arrayElem", arrayElement);
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        arrayElement.resolveNames(scope);
    }

    @Override
    public Node checkTypes() throws Exception {
        return arrayElement.checkTypes();
    }

    @Override
    public int getLine() {
        return arrayElement.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        arrayElement.getIndex().genCode(writer);
        State t = null;
        try {
            t = ((TypePrim) arrayElement.checkTypes()).getKind();
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.write(Instruction.PUSH, arrayElement.stack_slot, t);
        writer.write(Instruction.ADD, t);
        writer.write(Instruction.SET_A_L, t);
    }
    public void genCodeGet(CodeWriter writer)
    {
        arrayElement.getIndex().genCode(writer);
        State t = null;
        try {
            t = ((TypePrim) arrayElement.checkTypes()).getKind();
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.write(Instruction.PUSH, arrayElement.stack_slot, t);
        writer.write(Instruction.ADD, t);
        writer.write(Instruction.GET_A_L, t);
    }
}
package edvardas.ast.nodes;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.parser.Scope;

public class StatementReturn extends Statement {
    private Token keyword;
    private Expression value;

    public StatementReturn(Token keyword, Expression value) {
        this.keyword = keyword;
        this.value = value;
        addChildren(value);
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("keyword", keyword);
        printer.print("value", value);
    }

    @Override
    public void resolveNames(Scope s) throws Exception {
        value.resolveNames(s);
    }

    @Override
    public Node checkTypes() throws Exception {
        Type func = ((DeclFn) findAncestor(DeclFn.class)).getType();
        if (value == null && func.getKind() != State.TYPE_VOID) {
            semanticError(this.getLine(), "function's return type should be " + func.getKind().getName());
        }
        if (unifyTypes(func, value.checkTypes(), this.getLine())) {
            // semanticError(this.getLine(), "Return type does not match functions type:
            // got" + );
        }

        return func;
    }

    @Override
    public int getLine() {
        return keyword.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        try {
            if(value != null)
            {
                value.genCode(writer);
                writer.write(Instruction.RET_V, ((TypePrim) checkTypes()).getKind());    
            }
            else
                writer.write(Instruction.RET, ((TypePrim) checkTypes()).getKind());            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
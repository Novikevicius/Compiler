package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.State;
import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class DeclFn extends Decl {
    private ArrayList<VarDeclaration> params;
    private StmtBody body;
    private Label startLabel;
    private int numLocal = 0;
    public DeclFn(Type retType, Token name, ArrayList<VarDeclaration> params, StmtBody body) {
        this.type = retType;
        this.name = name;
        this.params = params;
        this.body = body;
        startLabel = new Label();
        addChildren(type);
        for (VarDeclaration param : params) {
            addChildren(param);
        }
        addChildren(body);
    }
    public ArrayList<VarDeclaration> getParams()
    {
        return params;
    }
    public Label getStartLabel(){
        return startLabel;
    }
    @Override
    public void print(ASTPrinter printer) throws Exception {
        printer.print("name", name);
        printer.print("params", params);
        printer.print("returnType", type);
        printer.print("body", body);
    }

    @Override
    public void resolveNames(Scope parentScope) {
        Decl.stack_slot_index = 0;
        Scope scope = new Scope(parentScope);
        params.forEach((param) -> {
            try {
                param.resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        body.resolveNames(scope);
        numLocal += stack_slot_index;
    }

    @Override
    public Node checkTypes() throws Exception {
        params.forEach((param) -> {
            try {
                param.checkTypes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        body.checkTypes();
        if(getType().getKind() != State.TYPE_VOID){
            if( !body.checkReturn() ){
                semanticError(name.getLine(), "Function '" + name.getIdentifier() + "': not all code branches have a return statement");
            }
        }
        return null;
    }
    @Override
    public int getLine()
    {
        return name.getLine();
    }
    public void genCode(CodeWriter writer) {
        writer.placeLabel(startLabel);
        if(numLocal > 0)
            writer.write(Instruction.ALLOC, numLocal, State.TYPE_INT);
        body.genCode(writer);
        if(type.getKind() == State.TYPE_VOID && !body.checkReturn())
        {
            writer.write(Instruction.RET, type.getKind());
        }
    }
}
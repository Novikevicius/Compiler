package edvardas.ast.nodes;

import java.util.ArrayList;

import edvardas.Token;
import edvardas.ast.ASTPrinter;
import edvardas.codeGeneration.CodeWriter;
import edvardas.codeGeneration.Instruction;
import edvardas.codeGeneration.Label;
import edvardas.parser.Scope;

public class FunctionCall extends Expression {
    private Token name;
    private ArrayList<Expression> args;
    private Node target;
    private Label callBegin;

    public FunctionCall(Token name, ArrayList<Expression> args) {
        this.name = name;
        this.args = args;
        for (Expression arg : args) {
            addChildren(arg);
        }
        callBegin = new Label();
    }
    public Label getCallBegin()
    {
        return callBegin;
    }
    public Node getTarget() {
        return target;
    }

    public void print(ASTPrinter printer) throws Exception {
        printer.print("name", name);
        printer.print("args", args);
    }

    @Override
    public void resolveNames(Scope scope) throws Exception {
        target = scope.resolveName(name);
        if (!(target instanceof DeclFn)) {
            semanticError(name.getLine(), name.getIdentifier() + " is not a function");
            target = null;
        }
        args.forEach((arg) -> {
            try {
                ((Expression) arg).resolveNames(scope);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Node checkTypes() throws Exception {
        if (target == null)
            return null;
        DeclFn func = (DeclFn) target;
        ArrayList<VarDeclaration> params = func.getParams();
        if (args.size() != params.size()) {
            semanticError(this.getLine(), "Number of arguments does not match number of function's parameters");
            return null;
        }
        for (int i = 0; i < args.size(); i++) {
            Node tArg = ((Expression) args.get(i)).checkTypes();
            Node tParam = params.get(i).checkTypes();
            unifyTypes(tArg, tParam, this.getLine());
        }
        return func.getType();
    }

    @Override
    public int getLine() {
        return name.getLine();
    }

    @Override
    public void genCode(CodeWriter writer) {
        try {
            writer.write(Instruction.CALL_BEGIN, ((TypePrim) checkTypes()).getKind());
            for (Expression arg : args) {
                arg.genCode(writer);
            }
            ArrayList<Object> ops = new ArrayList<Object>(2);
            ops.add(((DeclFn)target).getStartLabel());
            ops.add(args.size());
            writer.write(Instruction.CALL, ops, ((TypePrim) checkTypes()).getKind());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
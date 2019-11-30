package edvardas.ast.nodes;

import edvardas.ast.ASTPrinter;
import edvardas.parser.Scope;

public class ArrayTarget extends AssignmentTarget {
    private ArrayElement arrayElement;

    public ArrayTarget(ArrayElement elem)
    {
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
    
}
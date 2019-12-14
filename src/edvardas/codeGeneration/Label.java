package edvardas.codeGeneration;

import java.util.ArrayList;

public class Label {
    private int value;
    private ArrayList<Integer> offsets;

    public Label(int value) {
        this.setValue(value);
    }
    public Label() {
        this(-1);
        setOffsets(new ArrayList<Integer>());
    }

    public ArrayList<Integer> getOffsets() {
        return offsets;
    }
    public void setOffsets(ArrayList<Integer> offsets) {
        this.offsets = offsets;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }


}
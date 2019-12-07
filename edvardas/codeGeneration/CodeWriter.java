package edvardas.codeGeneration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class CodeWriter
{
    private ArrayList<Integer> code;
    private ArrayList<Label> labels;

    public CodeWriter()
    {
        code = new ArrayList<Integer>();
        labels = new ArrayList<Label>();
        Instruction.init();
    }
    public Label newLabel()
    {
        Label l = new Label();
        labels.add(l);
        return l;
    }
    public void completeLabel(Label l, int value)
    {
        l.setValue(value);
    }
    public void placeLabel(Label l)
    {
        completeLabel(l, code.size());
    }
	public void write(Instruction instr) {
        write(instr, null);
    }
	public void write(Instruction instr, ArrayList<Object> ops) {
        if(instr.getOpCount() > 0)
        {
            if(ops == null)
                throw new Error("OpCount is " + instr.getOpCount() + " but ops is null");
            if(ops.size() != instr.getOpCount())
                throw new Error("OpCount is " + instr.getOpCount() + " but got " + ops.size() + " operands");
        }
        code.add(instr.getOpcode());
        for(Object op: ops)
        {
            if(!(op instanceof Label))
            {
                code.add(objToInt(op));
            }
            else
            {
                Label l = (Label) op;
                if(l.getValue() == -1)
                {
                    l.getOffsets().add(code.size());
                    code.add(0);
                }
                else
                {
                    code.add(l.getValue());
                }
            }
        }
    }
    public void showCode()
    {
        int offset = 0;
        while(offset < code.size())
        {
            int opcode = code.get(offset++);
            Instruction instr = Instruction.instrByOpcode.get(opcode);
            System.out.printf("%2i: %-16s " );
        }
    }
    private static int objToInt(Object o)
    {
        byte[] arr = objToBytes(o);
        ArrayList<Byte> bytes = new ArrayList<Byte>(arr.length);
        for(Byte b: arr)
        {
            bytes.add(b);
        }
        return bytesToInt(bytes);
    }
    private static int bytesToInt(ArrayList<Byte> bytes)
    {
        int result = 0;
        for(byte b: bytes)
        {
            result = result * 256 + (int)b;
        }
        return result;
    }
    private static byte[] intToBytes(int v)
    {
        return ByteBuffer.allocate(4).putInt(v).array();
    }
    private static byte[] objToBytes(Object o)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
          out = new ObjectOutputStream(bos);   
          out.writeObject(o);
          out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
          try {
            bos.close();
          } catch (IOException ex) {
            // ignore close exception
          }
        }
        return bos.toByteArray();
    }
}
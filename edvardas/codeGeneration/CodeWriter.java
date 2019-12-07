package edvardas.codeGeneration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import edvardas.State;

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
	public void write(Instruction instr, State type) {
        write(instr, null, type);
    }
	public void write(Instruction instr, Object o, State type) {
        ArrayList<Object> ops = new ArrayList<Object>(1);
        ops.add(o);
        write(instr, ops, type);
    }
	public void write(Instruction instr, ArrayList<Object> ops, State type) {
        if(instr.getOpCount() > 0)
        {
            if(ops == null)
                throw new Error("OpCount is " + instr.getOpCount() + " but ops is null");
            if(ops.size() != instr.getOpCount())
                throw new Error("OpCount is " + instr.getOpCount() + " but got " + ops.size() + " operands");
        }
        code.add(instr.getOpcode());
        if(type != null)
        {
            switch (type) {
                case TYPE_INT:
                    code.add(0);
                    break;
                case TYPE_FLOAT:
                    code.add(1);
                    break;
                case TYPE_CHAR:
                    code.add(2);
                    break;
                case TYPE_BOOL:
                    code.add(3);
                    break;
                case TYPE_STRING:
                    code.add(4);
                    break;                    
                default:
                    System.out.println("Not implemented: " + type.toString());
                    break;
            }
        }
        if(ops != null)
        {
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
    }
    public void showCode()
    {
        int offset = 0;
        while(offset < code.size())
        {
            int opcode = code.get(offset++);
            Instruction instr = Instruction.instrByOpcode.get(opcode);
            System.out.printf("%2d: %-16s ", offset-1, instr.toString());
            int type = -1;
            type = code.get(offset++);
            for(int i = 0; i < instr.getOpCount(); i++)
            {
                switch (type) {
                    case 0:
                        System.out.printf("%d ", code.get(offset++));
                        break;
                    case 1:
                        System.out.printf("%f ", Float.intBitsToFloat(code.get(offset++)));
                        break;
                    case 2:
                        System.out.printf("%c ", code.get(offset++).intValue());
                        break;
                    case 3:
                        System.out.print(code.get(offset++) > 0);
                        break;
                
                    default:
                        break;
                }
            }
            System.out.print("\n");
        }
        System.out.println("Raw code:");
        for (int i = 0; i < code.size(); i++) {
            System.out.printf("%d ", code.get(i));
        }
        System.out.print("\n");
    }
    private static int objToInt(Object o)
    {
        if(o instanceof Float)
            return Float.floatToRawIntBits(((Float)o));
        if(o instanceof Character)
            return ((Character)o);
        if(o instanceof Integer)
            return ((Integer)o);
        if(o instanceof Boolean)
            return ((Boolean)o) ? 1 : 0;
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
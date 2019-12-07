package edvardas.codeGeneration;

import java.util.HashMap;

public enum Instruction {
    // Arithmetic instructions
    INT_ADD(0x0000, 0),
    INT_SUB(0x0001, 0),
    INT_MUL(0x0002, 0),
    INT_DIV(0x0003, 0),
    FLOAT_ADD(0x1000, 0),
    FLOAT_SUB(0x1001, 0),
    FLOAT_MUL(0x1002, 0),
    FLOAT_DIV(0x1003, 0),
    // Comparison instructions
    INT_LESS(0x0010, 0),
    INT_MORE(0x0011, 0),
    INT_EQ  (0x0012, 0),
    INT_LEQ (0x0013, 0),
    INT_MEQ (0x0014, 0),
    INT_NEQ (0x0015, 0),
    FLOAT_LESS(0x1010, 0),
    FLOAT_MORE(0x1011, 0),
    FLOAT_EQ  (0x1012, 0),
    FLOAT_LEQ (0x1013, 0),
    FLOAT_MEQ (0x1014, 0),
    FLOAT_NEQ (0x1015, 0),
    CHAR_EQ  (0x2012, 0),
    CHAR_NEQ (0x2015, 0),
    STRING_EQ  (0x3012, 0),
    STRING_NEQ (0x3015, 0),
    BOOL_EQ  (0x4012, 0),
    BOOL_NEQ (0x4015, 0),
    // Stack instructions
    INT_PUSH (0x0020, 1),
    FLOAT_PUSH (0x1020, 1),
    CHAR_PUSH (0x2020, 1),
    STRING_PUSH (0x3020, 1),
    BOOL_PUSH (0x4020, 1),
    GET_L (0x0021, 0),
    // Control instructions
    RET (0xF030, 0),
    INT_RET (0x0030, 0),
    FLOAT_RET (0x1030, 0),
    CHAR_RET (0x2030, 0),
    STRING_RET (0x3030, 0),
    BOOL_RET (0x4030, 0),
    CALL_BEGIN (0x0031, 0),
    CALL (0x0032, 0);

    private int opcode;
    private int opCount;

    public static HashMap<Integer, Instruction> instrByOpcode;

    private Instruction(int opcode, int opCount) {
        this.opcode = opcode;
        this.opCount = opCount;
    }
    public int getOpCount() {
        return opCount;
    }
    public int getOpcode() {
        return opcode;
    }
    public static void init()
    {
        if(instrByOpcode == null)
            instrByOpcode = new HashMap<Integer, Instruction>();
        for(Instruction instr : Instruction.values())
        {
            instrByOpcode.put(instr.getOpcode(), instr);
        }
    }
}
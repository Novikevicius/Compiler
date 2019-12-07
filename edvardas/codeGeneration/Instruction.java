package edvardas.codeGeneration;

import java.util.HashMap;

public enum Instruction {
    // Arithmetic instructions
    ADD(0x0000, 0),
    SUB(0x0001, 0),
    MUL(0x0002, 0),
    DIV(0x0003, 0),
    EXP(0x0004, 0),
    // Comparison instructions
    LESS(0x0010, 0),
    MORE(0x0011, 0),
    EQ  (0x0012, 0),
    LEQ (0x0013, 0),
    MEQ (0x0014, 0),
    NEQ (0x0015, 0),
    // Stack instructions
    PUSH (0x0020, 1),
    // Control instructions
    RET (0xF030, 0),
    RET_V (0x0030, 0),
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
package edvardas;

import java.util.ArrayList;

import edvardas.codeGeneration.Instruction;

class Interpreter {
    private final int STACK_START = 1024;
    private int[] memory;
    private boolean running = false;
    private int ip = 0;
    private int fp = 0;
    private int sp = 0;
    private int type = -1;

    public Interpreter(ArrayList<Integer> code) {
        memory = new int[4096];
        for (int i = 0; i < code.size(); i++) {
            memory[i] = code.get(i);
        }
        running = true;
        fp = STACK_START;
        sp = STACK_START;
    }

    public void execute() {
        while (running) {
            executeOne();
        }
        System.out.println("Exit code: " + pop());
    }

    public void executeOne() {
        Instruction opcode = Instruction.instrByOpcode.get(readImm());
        switch (opcode) {
        case ADD: {
            int a = pop();
            int b = pop();
            push(a + b);
            break;
        }
        case SUB: {
            int a = pop();
            int b = pop();
            push(b - a);
            break;
        }
        case MUL: {
            int b = pop();
            int a = pop();
            push(a * b);
            break;
        }
        case DIV: {
            int b = pop();
            int a = pop();
            push(a / b);
            break;
        }
        case EXP: {
            int a = pop();
            int b = pop();
            push((int) Math.pow(b, a));
            break;
        }
        case NEG: {
            int a = pop();
            push(-a);
            break;
        }
        case INC: {
            int a = pop();
            push(++a);
            break;
        }
        case DEC: {
            int a = pop();
            push(--a);
            break;
        }
        case LESS: {
            int a = pop();
            int b = pop();
            push(b < a ? 1 : 0);
            break;
        }
        case MORE: {
            int a = pop();
            int b = pop();
            push(b > a ? 1 : 0);
            break;
        }
        case EQ: {
            int a = pop();
            int b = pop();
            push(a == b ? 1 : 0);
            break;
        }
        case LEQ: {
            int a = pop();
            int b = pop();
            push(b <= a ? 1 : 0);
            break;
        }
        case MEQ: {
            int a = pop();
            int b = pop();
            push(b >= a ? 1 : 0);
            break;
        }
        case NEQ: {
            int a = pop();
            int b = pop();
            push(a != b ? 1 : 0);
            break;
        }
        case AND: {
            int a = pop();
            int b = pop();
            push(a == b && a == 1 ? 1 : 0);
            break;
        }
        case OR: {
            int a = pop();
            int b = pop();
            push(a == 1 || b == 1 ? 1 : 0);
            break;
        }
        case PUSH: {
            int a = readArg();
            if(type == 4){
                for (int i = 0; i < a; i++) {
                    push(readArg());
                }
            }
            push(a);
            break;
        }
        case POP: {
            pop();
            break;
        }
        case GET_L: {
            int i = readArg();
            push(memory[fp + i]);
            break;
        }
        case SET_L: {
            int i = readArg();
            memory[fp + i] = pop();
            break;
        }
        case ALLOC: {
            sp += readArg();
            break;
        }
        case RET: {
            exec_ret(0);
            break;
        }
        case RET_V: {
            exec_ret(pop());
            break;
        }
        case CALL_BEGIN: {
            readArg();
            push(0);
            push(0);
            push(0);
            break;
        }
        case CALL: {
            exec_call(readArg(), readArg());
            break;
        }
        case JMP: {
            ip = readArg();
            break;
        }
        case JZ: {
            int i = readArg();
            if (pop() == 0) {
                ip = i;
            }
            break;
        }
        case EXIT: {
            running = false;
            break;
        }
        case WRITE: {
            int args = readArg();
            ArrayList<Object> arg = new ArrayList<Object>(args);
            for(int i = 0; i < args; i++)
            {
                int v = pop();
                Object o = null;
                switch(type)
                {
                    case 0:
                        o = v;
                        break;
                    case 1:
                        o = (float)v;
                        break;
                    case 2:
                        o = (char)v;
                        break;
                    case 3:
                        o = (v == 0 ? "false" : "true");
                        break;
                    case 4:
                        StringBuilder str = new StringBuilder(v);
                        for (int j = 0; j < v; j++) {
                            str.insert(0, (char)pop());
                        }
                        o = str.toString();
                        break;
                }
                arg.add(0, o);
            }
            for(int i = 0; i < args; i++)
            {
                System.out.print(arg.get(i));
            }
            break;
        }
        default: {
            try {
                Main.error("No such instruction: " + opcode.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        }

    }
    private void exec_ret(int value)
    {
        int oldIP = memory[fp-3];
        int oldFP = memory[fp-2];
        int oldSP = memory[fp-1];
        ip = oldIP;
        fp = oldFP;
        sp = oldSP;
        push(value);
    }
    private void exec_call(int target, int arg_num)
    {
        int newIP = target;
        int newFP = sp - arg_num;
        int newSP = newFP;
        memory[newFP - 3] = ip;
        memory[newFP - 2] = fp;
        memory[newFP - 1] = newFP - 3;
        ip = newIP;
        fp = newFP;
        sp = newSP;
    }
    private void push(int v)
    {
        memory[sp++] = v;
        memory[sp++] = type;
    }
    private int pop()
    {
        type = memory[--sp];
        return memory[--sp];
    }
    private int readImm()
    {
        int value = memory[ip++];
        type = memory[ip++];
        return value;
    }
    private int readArg()
    {
        int value = memory[ip++];
        return value;
    }
}
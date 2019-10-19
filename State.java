public enum State {
    START("start"),
    IDENTIFIER("identifier"),
    INT_LITERAL("number"),
    FLOAT_LITERAL("float number"),
    FLOAT_LITERAL_EXP("float number exponential form"),
    FLOAT_LITERAL_EXP_NEGATIVE("float number negative exponential form"),
    FLOAT_LITERAL_EXP_END("float number or e or -e"),
    CHAR_LITERAL("'"),
    CHAR_LITERAL_SPEC("'"),
    CHAR_LITERAL_END("'"),
    STRING_LITERAL("\""),
    STRING_LITERAL_SPEC("\""),
    ESCAPE_SYMBOL("\\"),
    L_PARENT("("),
    R_PARENT(")"),
    L_CURLY("{"),
    R_CURLY("}"),
    L_BRACKET("["),
    R_BRACKET("]"),
    COMMA(","),
    KEYWORD("keyword"),
    
    SEMI_CLN(";"),
    OP_EXP("^"),
    OP_PLUS("+"),
    OP_AFFIX_PLUS("++"),
    OP_MINUS("-"),
    OP_AFFIX_MINUS("--"),
    OP_MULT("*"),
    OP_DIV("/"),
    ASSIGN_OP("="),
    ASSIGN_OP_PLUS("+="),
    ASSIGN_OP_MINUS("-="),
    ASSIGN_OP_MULT("*="),
    ASSIGN_OP_DIV("/="),
  
    L_COMMENT("#"),
    M_COMMENT_START("``"),
    MULTI_COMMENT("``"),
    M_COMMENT_END("``"),
    
    COMP_OP_MORE(">"),
    COMP_OP_LESS("<"),
    COMP_OP_MORE_EQ(">="),
    COMP_OP_LESS_EQ("<="),
    COMP_OP_EQ("=="),
    COMP_OP_NOT_EQ("!="),
    LOGIC_OP_OR("||"),
    LOGIC_OP_AND("&&"),
    EOF("EOF"),
    ERROR_UNKNOWN_SYMB("error"),
    ERROR_NOT_CLOSED_COMMENT("error"),
    ERROR_NOT_CLOSED_STRING("error");
  
    private String name;
  
    private State(String name) {
      this.name = name;
    }
  
    public String getName() {
      return this.name;
    }
  }
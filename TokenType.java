public enum TokenType {
    STRING(0),
    IDENTIFIER(1),
    INT(2),
    FLOAT(3),
    KEYWORD(4),
    CHAR(5),
    BOOL(6),
    NONE(7),
    FLOAT_EXP(8);
    
    private int id;
  
    private TokenType(int id) {
      this.id = id;
    }
  
    public int getId() {
      return this.id;
    }
  }
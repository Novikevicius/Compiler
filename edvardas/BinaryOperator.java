package edvardas;

public enum BinaryOperator {
    ADD("+"),
    MINUS("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    EXPONENTIAL("^"),
    AND("&&"),
    OR("||"),
    LESS("<"),
    LESS_EQUAL("<="),
    MORE(">"),
    MORE_EQUAL(">="),
    EQUAL("=="),
    NOT_EQUAL("!=");

    private String name;
  
    private BinaryOperator(String name) {
      this.name = name;
    }
  
    public String getName() {
      return this.name;
    }
}
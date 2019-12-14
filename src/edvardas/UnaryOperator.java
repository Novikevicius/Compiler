package edvardas;

public enum UnaryOperator {
    NEGATION("-"),
    INCREMENT("++"),
    DECREMENT("--");

    private String name;
  
    private UnaryOperator(String name) {
      this.name = name;
    }
  
    public String getName() {
      return this.name;
    }
}
package absyn;

public class OpExp extends Exp {
  public final static int PLUS  = 0;
  public final static int MINUS = 1;
  public final static int TIMES = 2;
  public final static int OVER  = 3;
  public final static int EQ    = 4;
  public final static int LT    = 5;
  public final static int GT    = 6;
  public final static int LTEQ    = 7;
  public final static int NOTEQ    = 8;
  public final static int GTEQ    = 9;
  public final static int ERROR    = 10;

  public int operation;

// left and right represent the left and right operands int he operation(+,-,*)
  public OpExp(int left, int right, int operation) {
    this.left = left;
    this.right = right;
    this.operation = operation;
  }
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
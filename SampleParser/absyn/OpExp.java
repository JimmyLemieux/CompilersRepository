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
  public final static int MUL     = 11;
  public final static int ERROR    = 12;

  public int operation;
  public int row;
  public int col;
  public Exp left;
  public Exp right;
// left and right represent the left and right operands int he operation(+,-,*)
  public OpExp(int row, int col, Exp left, int operation, Exp right) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.right = right;
    this.operation = operation;
  }
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
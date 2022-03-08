package absyn;

public class CallingExp extends Exp {
    public ExpList args;
    public String funName;
    public int row;
    public int col;

  public CallingExp( int row, int col, String funName, ExpList args) {
      this.row = row;
      this.col = col;
      this.funName = funName;
      this.args = args;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
package absyn;

public class VarExp extends Exp {
  public VarDec name;

  public VarExp( int row, int col, VarDec name ) {
    this.row = row;
    this.col = col;
    this.name = name;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

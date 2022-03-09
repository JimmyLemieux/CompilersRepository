package absyn;

public class ArrayAssignExp extends Exp {
  public VarExp lhs;
  public Exp rhs;
  public IntExp index;

  public ArrayAssignExp( int row, int col, VarExp lhs, IntExp index, Exp rhs ) {
    this.row = row;
    this.col = col;
    this.lhs = lhs;
    this.index = index;
    this.rhs = rhs;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

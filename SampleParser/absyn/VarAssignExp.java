package absyn;

public class VarAssignExp extends Exp {
  public Var lhs;
  public Exp rhs;

  public VarAssignExp( int row, int col, Var lhs, Exp rhs ) {
    this.row = row;
    this.col = col;
    this.lhs = lhs;
    this.rhs = rhs;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

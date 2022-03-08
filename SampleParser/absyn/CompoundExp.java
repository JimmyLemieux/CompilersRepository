package absyn;

public class CompoundExp extends Exp {
    public ExpList expl;
    public VarDecList decl;
    public int row;
    public int col;

  public CompoundExp( int row, int col, VarDecList decl, ExpList expl) {
      this.row = row;
      this.col = col;
      this.decl = decl;
      this.expl = expl;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
package absyn;

public class CompoundExp extends Exp {
    public int position;
    public ExpList expl;
    public VarDecList decl;

  public CompoundExp( int position, VarDecList decl, ExpList expl) {
      this.position = position;
      this.decl = decl;
      this.expl = expl;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
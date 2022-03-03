package absyn;

public class IfExp extends Exp {
  public Exp test;
  public ExpList thenpart;
  public ExpList elsepart;

  public IfExp( int position, Exp test, ExpList thenpart, ExpList elsepart ) {
    this.position = position;
    this.test = test;
    this.thenpart = thenpart;
    this.elsepart = elsepart;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}


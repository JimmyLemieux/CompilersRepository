package absyn;

public class IntExp extends Exp {
  public int val;

  public IntExp( int position, int val ) {
    this.position = position;
    this.val = val;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
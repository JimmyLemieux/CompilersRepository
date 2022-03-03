package absyn;

public class IntExp extends Exp {
  public String value;

  public IntExp( int position, String val ) {
    this.position = position;
    this.value = val;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
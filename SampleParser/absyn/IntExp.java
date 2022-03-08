package absyn;

public class IntExp extends Exp {
  public String value;
  public int row;
  public int col;

  public IntExp( int row, int col, String val ) {
    this.row = row;
    this.col = col;
    this.value = val;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
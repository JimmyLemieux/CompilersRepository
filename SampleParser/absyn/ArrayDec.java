package absyn;

public class ArrayDec extends VarDec {
  public String arrayName;
  public TypeName type;
  public IntExp arraySize;
  public int row;
  public int col;

  public ArrayDec( int row, int col, TypeName type, String arrayName, IntExp arraySize) {
    this.row = row;
    this.col = col;
    this.type = type;
    this.arrayName = arrayName;
    this.arraySize = arraySize;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
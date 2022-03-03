package absyn;

public class ArrayDec extends VarDec {
  public String arrayName;
  public TypeName type;
  public IntExp arraySize;

  public ArrayDec( int position, TypeName type, String arrayName, IntExp arraySize) {
    this.position = position;
    this.type = type;
    this.arrayName = arrayName;
    this.arraySize = arraySize;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
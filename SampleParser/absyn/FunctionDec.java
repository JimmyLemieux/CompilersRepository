package absyn;

public class FunctionDec extends Declaration {
    public TypeName type;
    public VarDecList param;
    public CompoundExp funBody;
    public String funName;
    public int row;
    public int col;

  public FunctionDec(int row, int col, TypeName type, String funName, VarDecList param, CompoundExp funBody){
    this.row = row;
    this.col = col;
    this.type = type;
    this.funName = funName;
    this.param = param;
    this.funBody = funBody;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
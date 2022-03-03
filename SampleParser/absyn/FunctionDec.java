package absyn;

public class FunctionDec extends Exp {
    public TypeName type;
    public VarDecList param;
    public CompoundExp funBody;
    public String funName;

  public FunctionDec(int position, TypeName type, String funName, VarDecList param, CompoundExp funBody){
    this.position = position;
    this.type = type;
    this.funName = funName;
    this.param = param;
    this.funBody = funBody;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
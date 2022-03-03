package absyn;

public class CallingExp extends Exp {
    public ExpList args;
    public String funName;

  public CallingExp( int position, String funName, ExpList args) {
      this.position = position;
      this.funName = funName;
      this.args = args;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public Exp exps;

  public WhileExp(int position, Exp test, Exp exps ) {
    this.position = position;
    this.test = test;
    this.exps = exps;
  }
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public ExpList exps;

  public WhileExp(int position, Exp test, ExpList exps ) {
    this.position = position;
    this.test = test;
    this.exps = exps;
  }
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

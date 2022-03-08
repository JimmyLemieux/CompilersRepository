package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public Exp exps;
  public int row;
  public int col;

  public WhileExp(int row, int col, Exp test, Exp exps ) {
    this.row = row;
    this.col = col;
    this.test = test;
    this.exps = exps;
  }
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

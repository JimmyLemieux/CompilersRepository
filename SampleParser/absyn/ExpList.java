package absyn;

public class ExpList {
  public Exp head;
  public ExpList tail;
  public int row;
  public int col;

  public ExpList( int row, int col, Exp head, ExpList tail ) {
    this.row = row;
    this.col = col;
    this.head = head;
    this.tail = tail;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}

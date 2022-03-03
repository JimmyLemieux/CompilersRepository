package absyn;

abstract public class Absyn {
  public int row, col, position;

  abstract public void accept( AbsynVisitor visitor, int level );
}

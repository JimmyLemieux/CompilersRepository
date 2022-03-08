package absyn;

abstract public class Absyn {
  public int row, col, position;
  public TypeName type;

  abstract public void accept( AbsynVisitor visitor, int level );
}

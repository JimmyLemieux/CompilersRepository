package absyn;

public class ReturnExp extends Exp {
    public Exp expr;
    public int row;
    public int col;

    public ReturnExp(int row, int col, Exp expr) {
        this.expr = expr;
        this.row = row;
        this.col = col;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
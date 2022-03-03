package absyn;

public class ReturnExp extends Exp{
    public Exp expr;

    public ReturnExp(int position, Exp expr) {
        this.position = pos;
        this.expr = expr;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
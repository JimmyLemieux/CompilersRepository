package absyn;

public class SimpleVarExp extends Exp {
    public Var var;

    public SimpleVarExp(int row, int col, Var var) {
        this.row = row;
        this.col = col;
        this.var = var;
    }

    public void accept( AbsynVisitor visitor, int level) {
        visitor.visit(this, level);
    }
}
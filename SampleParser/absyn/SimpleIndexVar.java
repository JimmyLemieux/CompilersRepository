package absyn;

public class SimpleIndexVar extends Var {
    public String varName;
    public Exp exp;
    public SimpleIndexVar(int row, int col, String varName, Exp exp) {
        this.row = row;
        this.col = col;
        this.varName = varName;
        this.exp = exp;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
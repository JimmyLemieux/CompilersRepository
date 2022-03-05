package absyn;

public class SimpleVar extends Var {
    public String varName;

    public SimpleVar(int row, int col, String varName) {
        this.row = row;
        this.col = col;
        this.varName = varName;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
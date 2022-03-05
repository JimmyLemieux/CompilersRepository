package absyn;

public class VarDecExp extends VarDec {

    public TypeName name;
    public String varName;
    public Exp exp;

    public VarDecExp(int row, int col, TypeName name, String varName, Exp exp) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.varName = varName;
        this.exp = exp;
    }
    public void accept( AbsynVisitor visitor, int level) {
        visitor.visit(this, level);
    }
}
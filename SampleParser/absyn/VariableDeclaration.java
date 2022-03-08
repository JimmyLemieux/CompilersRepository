package absyn;

public class VariableDeclaration extends VarDec {
    public TypeName type;
    public String sname;
    public int row;
    public int col;

    public VariableDeclaration(int row, int col, TypeName type, String sname)
    {
        this.row = row;
        this.col = col;
        this.type = type;
        this.sname = sname;
        
    }
    public void accept( AbsynVisitor visitor, int level ) 
    {
        visitor.visit( this, level );
    }
}
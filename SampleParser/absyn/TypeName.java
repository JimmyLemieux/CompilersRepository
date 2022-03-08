package absyn;

public class TypeName {
    public final static int VOID = 0;
    public final static int INT = 1;
    public final static int ERROR = 2;
    public int type;
    public int row;
    public int col;

    public TypeName(int row, int col, int type)
    {
        this.row = row;
        this.col = col;
        this.type = type;
    }
    public void accept( AbsynVisitor visitor, int level ) 
    {
        visitor.visit( this, level );
    }
}
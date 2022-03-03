package absyn;

public class TypeName extends Absyn{
    public final static int VOID = 0;
    public final static int INT = 1;
    public int type;

    public TypeName(int typePos, int type)
    {
        this.typePos = typePos;
        this.type = type;
    }
    public void accept( AbsynVisitor visitor, int level ) 
    {
        visitor.visit( this, level );
    }
}
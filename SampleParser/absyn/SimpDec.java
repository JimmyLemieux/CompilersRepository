package absyn;

public class SimpDec extends VarDec{
    public TypeName type;
    public String sname;

    public SimpleDec(int position, TypeName type, String sname)
    {
        this.position = position;
        this.type = type;
        this.sname = sname;
        
    }
    public void accept( AbsynVisitor visitor, int level ) 
    {
        visitor.visit( this, level );
    }
}
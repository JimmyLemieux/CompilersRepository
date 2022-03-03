package absyn;

public class varDecList extends Absyn{
    public VarDec head;
    public VarDecList tail;

    public varDecList(varDec head, varDecList tail)
    {
        this.head = head;
        this.tail = tail;
    }
    public void accept( AbsynVisitor visitor, int level ) 
    {
        visitor.visit( this, level );
    }
}
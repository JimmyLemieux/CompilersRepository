package absyn;

public class ErrorDec extends VarDec{
    public int position;

    public ErrorDec(int position){
        this.position = position;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
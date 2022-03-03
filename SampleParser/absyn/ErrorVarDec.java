package absyn;

public class ErrorVarDec extends VarDec{
    public int position;

    public ErrorVarDec(int position){
        this.position = position;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
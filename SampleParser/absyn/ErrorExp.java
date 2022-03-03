package absyn;

public class ErrorExp extends Exp{
    public int position;

    public ErrorExp(int position){
        this.position = position;
    }
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
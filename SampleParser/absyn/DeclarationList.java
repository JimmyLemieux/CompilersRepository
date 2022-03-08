package absyn;

public class DeclarationList extends Absyn {
    public Declaration head;
    public DeclarationList tail;
    public int row;
    public int col;

    public DeclarationList(int row, int col, Declaration head, DeclarationList tail) {
        this.row = row;
        this.col = col;
        this.head = head;
        this.tail = tail;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
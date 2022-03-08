package absyn;

public class DeclarationList extends Absyn {
    public Declaration head;
    public DeclarationList tail;

    public DeclarationList(Declaration head, DeclarationList tail) {
        this.head = head;
        this.tail = tail;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
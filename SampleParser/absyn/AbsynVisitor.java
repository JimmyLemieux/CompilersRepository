package absyn;

public interface AbsynVisitor {

  public void visit( ExpList exp, int level );

  public void visit( AssignExp exp, int level );

  public void visit( IfExp exp, int level );

  public void visit( IntExp exp, int level );

  public void visit( OpExp exp, int level );

  public void visit( VarExp exp, int level );

  public void visit( WhileExp exp, int level );

  public void visit( VarDecList exp, int level );

  public void visit( TypeName exp, int level );

  public void visit( SimpDec exp, int level );

  public void visit( ReturnExp exp, int level );

  public void visit( FunctionDec exp, int level );

  public void visit( CompoundExp exp, int level );

  public void visit( CallingExp exp, int level );

  public void visit( ArrayDec exp, int level );

  public void visit( ErrorExp exp, int level );

  public void visit( ErrorVarDec exp, int level );

  public void visit( ErrorDec exp, int level );

  public void visit (ReadExp exp, int level);

  public void visit (WriteExp exp, int level);

  public void visit (RepeatExp exp, int level);
}

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

  public void visit( VariableDeclaration exp, int level );

  public void visit( ReturnExp exp, int level );

  public void visit( FunctionDec exp, int level );

  public void visit( CompoundExp exp, int level );

  public void visit( CallingExp exp, int level );

  public void visit( ArrayDec exp, int level );

  public void visit (WriteExp exp, int level);

  public void visit (DeclarationList exp, int level);

  public void visit (SimpleVar var, int level);

  public void visit (VarAssignExp varAssign, int level);

  public void visit (SimpleVarExp var, int level);

  public void visit (SimpleIndexVar var, int level);

  public void visit (VarDecExp vExp, int level);

  public void visit (ArrayAssignExp exp, int level);
}

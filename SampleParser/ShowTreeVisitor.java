import absyn.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ExpList expList, int level ) {
    while( expList != null ) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }

  public void visit( IfExp exp, int level ) {
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    if (exp.test != null) {
      exp.test.accept( this, level );
    } else {
      System.out.println("No expression given for if condition!");
    }
    exp.thenpart.accept( this, level );
    if (exp.elsepart != null )
       exp.elsepart.accept( this, level );
  }

  public void visit( IntExp exp, int level ) {
    indent( level );
    System.out.println( "IntExp: " + exp.value ); 
  }

  public void visit( OpExp exp, int level ) {
    indent( level );
    System.out.print( "OpExp:" ); 
    switch( exp.operation ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
      case OpExp.MUL:
        System.out.println(" * ");
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
    }
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( ReadExp exp, int level ) {
    indent( level );
    System.out.println( "ReadExp:" );
    exp.input.accept( this, ++level );
  }

  public void visit( RepeatExp exp, int level ) {
    indent( level );
    System.out.println( "RepeatExp:" );
    level++;
    exp.exps.accept( this, level );
    exp.test.accept( this, level ); 
  }

  public void visit( VarExp exp, int level ) {
    indent( level );
    System.out.println( "VarExp: " + exp.name );
  }

  public void visit( WriteExp exp, int level ) {
    indent( level );
    System.out.println( "WriteExp:" );
    exp.output.accept( this, ++level );
  }
  
  public void visit( DecList decList, int level ) {
    while( decList != null ) {
      if (decList.head != null) {
        decList.head.accept( this, level );
      } else {
        System.out.println("Error in the decList");
      }
      decList = decList.tail;
    }
  }

  public void visit( SimpleVar var, int level ) {
    indent( level );
    System.out.println(" Simple Variable: " + var.varName);
  }

  public void visit( VarAssignExp varAssign, int level) {
    indent( level );
    System.out.println( "VarAssignExp:" );
    level++;
    varAssign.lhs.accept( this, level );
    varAssign.rhs.accept( this, level );
  }

  public void visit(SimpleIndexVar var, int level) {
    indent( level );
    System.out.println("Simple Index Var:" + var.varName);
    level++;
    var.exp.accept(this, level);
  }

  // TODO: Fill these out here!
  public void visit(ErrorDec exp, int level) {

  }

  public void visit(ErrorVarDec exp, int level) {

  }

  public void visit(ErrorExp exp, int level) {

  }

  public void visit(ArrayDec exp, int level) {
    indent (level);
    System.out.print( "ArrayDec: " + exp.arrayName + "[");
    if (exp.arraySize != null)
        System.out.print("" + exp.arraySize.value);
    System.out.print("] - ");
    if (exp.type != null) {
      if (exp.type.type == 0)
          System.out.println("VOID");
      else if (exp.type.type == 1)
          System.out.println("INT");
    } else {
      System.out.println("Symbol not found!");
    }
  }

  public void visit(CallingExp exp, int level) {
    indent( level );
    System.out.println( "CallExp: " + exp.funName);
    level++;
    ExpList args = exp.args;
    while (args != null) {
        args.head.accept(this, level);
        args = args.tail;
    }
  }
  
  public void visit(CompoundExp exp, int level) {
    indent (level);
    System.out.println( "CompoundExp: ");
    level++;
    VarDecList dds = exp.decl;
    while (dds != null) {
        dds.head.accept(this, level);
        dds = dds.tail;
    }
    ExpList exps = exp.expl;
    while (exps != null) {
        exps.head.accept(this, level);
        exps = exps.tail;
    }
  }

  public void visit(FunctionDec exp, int level) {
    indent (level);
    System.out.print( "FunctionDec: " + exp.funName + " - ");
    if (exp.type.type == 0)
        System.out.println("VOID");
    else if (exp.type.type == 1)
        System.out.println("INT");
    level++;
    VarDecList parms = exp.param;
    while (parms != null) {
        parms.head.accept(this, level);
        parms = parms.tail;
    }
    exp.funBody.accept(this, level);
  }

  public void visit(ReturnExp exp, int level) {
    indent (level);
    System.out.println( "ReturnExp: ");
    level++;
    if (exp.expr != null)
        exp.expr.accept(this, level);
  }

  public void visit(SimpDec exp, int level) {
    indent (level);
    System.out.print( "SimpDec: " + exp.sname + " - ");
    if (exp.type.type == 0)
        System.out.println("VOID");
    else if (exp.type.type == 1)
        System.out.println("INT");
  }

  public void visit(TypeName exp, int level) {
    indent (level);
    System.out.print( "TypeName: ");
    if (exp.type == 0)
        System.out.println("VOID");
    else if (exp.type == 1)
        System.out.println("INT");
  }

  public void visit(VarDecList expList, int level) {
    while( expList != null ) {
        expList.head.accept( this, level );
        expList = expList.tail;
    }
  }

  public void visit(WhileExp exp, int level) {
    indent (level);
    System.out.println( "WhileExp: ");
    level++;
    if (exp.test != null)
      exp.test.accept(this, level);
    else {
      System.out.println("No expression given for while condition!");
    }
    exp.exps.accept(this, level);
  }

  public void visit(SimpleVarExp exp, int level) {
    indent(level);
    System.out.println(" SimpleVarExp: ");
    level++;
    exp.var.accept(this, level);
  }
}

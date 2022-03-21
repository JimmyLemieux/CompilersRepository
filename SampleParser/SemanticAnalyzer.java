/*
  Created by: James Lemieux and Mustafa Al-Obaidi
  File Name: showTreeVisitor.java
*/
import absyn.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class SemanticAnalyzer implements AbsynVisitor {

  HashMap<String, ArrayList<NodeType>> table;
  int globalLevel = 0;

  public SemanticAnalyzer() {
    table = new HashMap<String, ArrayList<NodeType>>();
  }

  public int findType(String name) {
    ArrayList<NodeType> nodeList = table.get(name);
    for (int i =0; i<nodeList.size(); i++) {
      if (nodeList.get(i).name.equals(name)) {
        return isInteger(nodeList.get(i).def) ? 1 : 0;
      }
    }
    return -1;
  }

  public boolean isInteger(Declaration d) {
    if (d instanceof VariableDeclaration) {
      VariableDeclaration v = (VariableDeclaration) d;
      return v.type.type == 1;
    } else if (d instanceof ArrayDec) {
      ArrayDec ad = (ArrayDec) d;
      return ad.type.type == 1;
    } else if (d instanceof FunctionDec) {
      FunctionDec fd = (FunctionDec) d;
      if (fd.funName == "output") {
        return false;
      } else if (fd.funName == "input") {
        return true;
      }
      return fd.type.type == 1;
    }
    return false;
  }

  public void insert(NodeType node) {
    if (!table.containsKey(node.name)) {
      ArrayList<NodeType> list = new ArrayList<NodeType>();
      list.add(0, node);
      table.put(node.name, list);
    } else {
      ArrayList<NodeType> c = table.get(node.name);
      c.add(0, node);
      table.put(node.name, c);
    }
  }

  public NodeType lookup(NodeType node) {
    if (!table.containsKey(node.name)) {
      return null;
    }
    ArrayList<NodeType> c = table.get(node.name);
    return c.get(c.size() - 1);
  }

  public boolean inTable(String key) {
    return table.containsKey(key);
  }

  public void delete(int level) {
    Set<String> keys = table.keySet();
    ArrayList<String> keysToDelete = new ArrayList<String>();
    
    for (String key : keys) {
      ArrayList<NodeType> c = table.get(key);
      NodeType current = c.get(0);
      if (current.level == level) {
        // remove the key
        c.remove(current);
        if (c.isEmpty()) {
          // remove this from the table
          keysToDelete.add(key);
        }
      }
    }
    table.keySet().removeAll(keysToDelete);
  }

  // do later
  public void printLevel(int level) {
    Set<String> keys = table.keySet();
    for (String key: keys) {
      ArrayList<NodeType> current = table.get(key);
      NodeType c = current.get(0);
      if (c.level == level) {
        indent(level);
        System.out.print(c.name + ":");
        if (c.def instanceof FunctionDec) {
          System.out.print("(");
          FunctionDec f = (FunctionDec) c.def;
          if (f.funName.equals("output")) {
            System.out.println("INT) -> VOID");
          } else {
            VarDecList vdl = f.param;
            boolean isEmptyParam = (vdl == null);
            while (vdl != null) {
              VarDec vd = vdl.head;
              if (vd != null) {
                VariableDeclaration vardec = (VariableDeclaration) vd;
                if (isInteger(vardec)) {
                  System.out.print("INT");
                } else {
                  System.out.print("VOID");
                }
                if (vdl.tail != null) {
                  System.out.print(",");
                }
              }
              vdl = vdl.tail;
            }
            if (isEmptyParam) System.out.print("VOID");
            System.out.print(") -> ");
            if (isInteger(f)) {
              System.out.println("INT");
            } else {
              System.out.println("VOID");
            }
          }
        } else if (c.def instanceof VariableDeclaration) {
          VariableDeclaration vd = (VariableDeclaration) c.def;
          if (isInteger(vd)) {
            System.out.println(" INT");
          } else System.out.println(" VOID");
        } else if (c.def instanceof ArrayDec) {
          ArrayDec ad = (ArrayDec) c.def;
          if (isInteger(ad)) {
            System.out.println(" INT");
          } else System.out.println(" VOID");
        }
      }
    }
  }

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ExpList expList, int level ) {
    while( expList != null ) {
      if (expList.head != null) {
        expList.head.accept( this, level );
      } else {
        System.out.println("Error: Invalid Expression List. row: " + expList.row + " col: " + expList.col);
      }
      expList = expList.tail;
    } 
  }

  public void visit( AssignExp exp, int level ) {
    // indent( level );
    // System.out.println( "AssignExp:" );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }

  public void visit( IfExp exp, int level ) {
    level++;
    if (exp.test != null) {
      exp.test.accept( this, level );
    } else {
      indent(level);
      System.out.println("Error: No expression given for if condition! row: " + exp.row + " col: " + exp.col);
    }
    System.out.println("Entering a new block");
    if (exp.thenpart != null) {
      exp.thenpart.accept( this, level );
    }
    printLevel(level);
    delete(level);
    System.out.println("Leaving a new block");
    if (exp.elsepart != null ) {
      indent(level);
      System.out.println("Entering a new block");
      exp.elsepart.accept( this, level );
      printLevel(level);
      System.out.println("Leaving a new block");
      delete(level);
    }
  }

  public void visit( IntExp exp, int level ) {
    // indent( level );
    //System.out.println( "IntExp: " + exp.value ); 
  }

  public void visit( OpExp exp, int level ) {
    // indent( level );
    //System.out.print( "OpExp:" ); 
    switch( exp.operation ) {
      case OpExp.PLUS:
        //System.out.println( " + " );
        break;
      case OpExp.MINUS:
        //System.out.println( " - " );
        break;
      case OpExp.TIMES:
        //System.out.println( " * " );
        break;
      case OpExp.OVER:
        //System.out.println( " / " );
        break;
      case OpExp.EQ:
        // System.out.println( " = " );
        break;
      case OpExp.LT:
        // System.out.println( " < " );
        break;
      case OpExp.GT:
        // System.out.println( " > " );
        break;
      case OpExp.MUL:
        // System.out.println(" * ");
        break;
      case OpExp.LTEQ:
        // System.out.println(" <= ");
        break;
      case OpExp.NOTEQ:
        // System.out.println(" != ");
        break;
      case OpExp.GTEQ:
        // System.out.println(" >= ");
        break;
      default:
        //indent(level);
        // System.out.println( "Error: Unrecognized operator at row: " + exp.row + " and col: " + exp.col);
    }
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( VarExp exp, int level ) {
    // indent( level );
    // System.out.println( "VarExp: " + exp.name );
  }

  public void visit( WriteExp exp, int level ) {
   //  indent( level );
    // System.out.println( "WriteExp:" );
    exp.output.accept( this, ++level );
  }
  
  public void visit( DeclarationList decList, int level ) {
    while( decList != null ) {
      if (decList.head != null) {
        decList.head.accept( this, level );
      } else {
        //indent(level);
        //System.out.println("Error: Invalid Declaration List. row: " + decList.row + " col: " + decList.col);
      }
      decList = decList.tail;
    }
  }

  public void visit( SimpleVar var, int level ) {
    // System.out.println(" Simple Variable: " + var.varName);
  }

  public void visit( VarAssignExp varAssign, int level) {
    indent( level );
    int lhsType = -1;
    int rhsType = -1;
    String lhsName = "";
    int row = 0;
    int col = 0;
    if (varAssign.lhs instanceof SimpleVar) {
      SimpleVar templhs = (SimpleVar) varAssign.lhs;
      if (!inTable(templhs.varName)) {
        System.err.println("Error: Unknown Variable with name: " + templhs.varName + " at row: " + templhs.row + " at col: " + templhs.col);
      }
      lhsType = findType(templhs.varName);
      lhsName = templhs.varName;
      row = templhs.row;
      col = templhs.col;
      if (varAssign.rhs instanceof CallingExp) {
        CallingExp tempRhs = (CallingExp) varAssign.rhs;
        if (tempRhs.funName.equals("input")) {
          rhsType = 1;
        } else if (tempRhs.funName.equals("output")) {
          rhsType = 0;
        } else rhsType = findType(tempRhs.funName);
      } else {
        rhsType = 1;
      }
    }

    if (lhsType != rhsType) {
      System.err.println("Error: Type Mismatch for variable with name: " + lhsName + " at row: " + (row + 1) + " at col: " + (col + 1));
    }

    varAssign.lhs.accept( this, level );
    varAssign.rhs.accept( this, level );
  }

  public void visit(SimpleIndexVar var, int level) {
    indent( level );
    // System.out.println("Simple Index Var:" + var.varName);
    level++;
    var.exp.accept(this, level);
  }

  public void visit(ArrayDec exp, int level) {
    ArrayList<NodeType> check = table.get(exp.arrayName);
    if (check != null && check.get(0).level == level) {
      System.err.println("Error: Redefined variable " + exp.arrayName + " at the same level, at line: " + exp.row + " column: " + exp.col);
    } else {
      if (exp.type != null && exp.type.type == 0) {
        System.err.println("Error: variables cannot be defined as VOID type, at line: " + exp.row + " column: " + exp.col);
        exp.type.type = 1;
      }
    }
    NodeType newNode = new NodeType(exp.arrayName, exp, level);
    insert(newNode);
  }

  public void visit(CallingExp exp, int level) {
    indent( level );
    // System.out.println( "CallExp: " + exp.funName);
    level++;
    ExpList args = exp.args;
    while (args != null) {
        args.head.accept(this, level);
        args = args.tail;
    }
  }
  
  public void visit(CompoundExp exp, int level) {
    // indent (level);
    // System.out.println( "CompoundExp: ");
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
    ArrayList<NodeType> check = table.get(exp.funName);

    if (check != null && check.get(0).level == level) {
      System.err.println("Error: Redefined Function " + exp.funName + " at the same level, at line: " + exp.row + " column: " + exp.col);
    } else {
      NodeType newNode = new NodeType(exp.funName, exp, level);
      insert(newNode);
    }
    level++;
    indent(level);
    System.out.println("Entering the scope for function: " + exp.funName + ":");

    VarDecList parms = exp.param;
    while (parms != null) {
        if (parms.head == null) {
          // System.err.println("Invalid Declaration list! row : " + exp.row + " col: " + exp.col);
        }
        else{
          parms.head.accept(this, level);
        }
        parms = parms.tail;
    }
    exp.funBody.accept(this, level);
    printLevel(level);
    indent(level);
    delete(level);
    System.out.println("Leaving the scope for function: " + exp.funName + ":");
  }

  public void visit(ReturnExp exp, int level) {
    // indent (level);
    // System.out.println( "ReturnExp: ");
    level++;
    if (exp.expr != null)
        exp.expr.accept(this, level);
  }

  public void visit(VariableDeclaration exp, int level) {
    // Add the declaration to the table. Check if the table already contains this symbol!
    ArrayList<NodeType> check = table.get(exp.sname);

    if (check != null && check.get(0).level == level) {
      // redefinition!
      System.err.println("Error: Redefined variable " + exp.sname + " at the same level, at line: " + exp.row + " column: " + exp.col);
    } else {
      if (exp.type.type == 0) {
        System.err.println("Error: variables cannot be defined as VOID type, at line: " + exp.row + " column: " + exp.col);
        exp.type.type = 1;
      }
      NodeType newNode = new NodeType(exp.sname, exp, level);
      insert(newNode);
    }
  }

  public void visit(TypeName exp, int level) {
  }

  public void visit(VarDecList expList, int level) {
    while( expList != null ) {
        if (expList.head == null){
          System.out.println("Invalid Declaration list! row : " + expList.row + " col: " + expList.col);
        }
        expList.head.accept( this, level );
        expList = expList.tail;
    }
  }

  public void visit(WhileExp exp, int level) {
    //indent (level);
    //System.out.println( "WhileExp: ");
    level++;
    if (exp.test != null)
      exp.test.accept(this, level);
    else {
      //indent(level);
      //System.out.println("Error: No expression given for while condition! row: " + exp.row + " col: " + exp.col);
    }
    indent(level);
    System.out.println("Entering a new block");
    exp.exps.accept(this, level);
    indent(level);
    indent(level);
    printLevel(level);
    delete(level);
    System.out.println("Leaving a new block");
  }

  public void visit(SimpleVarExp exp, int level) {
    // indent(level);
    // System.out.println(" SimpleVarExp: ");
    level++;
    exp.var.accept(this, level);
  }

  public void visit(VarDecExp vExp, int level) {
    // indent(level);
    level++;
    // System.out.print( "VariableDeclaratonExp: " + vExp.varName + " - ");
    vExp.name.accept(this, level);
    vExp.exp.accept(this, level);

  }

  public void visit(ArrayAssignExp exp, int level) {
    // indent(level);
    level++;
    // System.out.println( "ArrayAssignExp:" );
    if (isInteger(exp.lhs.dtype) != isInteger(exp.rhs.dtype)) {
      System.err.println("Error: Type mismatch for Array. At line: " + (exp.row + 1) + " col: " + (exp.col + 1));
    }
    exp.lhs.accept(this, level);
    exp.index.accept(this, level);
    exp.rhs.accept(this, level);
  }
}

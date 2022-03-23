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
  // boolean isArrayIndex = false;
  String currentFunctionScope;

  public SemanticAnalyzer() {
    table = new HashMap<String, ArrayList<NodeType>>();
  }

  public int findType(String name) {
    ArrayList<NodeType> nodeList = table.get(name);
    if (nodeList == null || nodeList.isEmpty()) {
      return -2;
    }

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

  public NodeType findFunction(String functionName) {
    ArrayList<NodeType> current = table.get(functionName);
    if (current == null || current.isEmpty()) {
      return null;
    }
    for (int i = 0;i<current.size();i++) {
      if (current.get(i).name.equals(functionName) && current.get(i).def instanceof FunctionDec) {
        return current.get(i);
      }
    }
    return null;
  }

  public NodeType findVariable(String varName) {
    ArrayList<NodeType> current = table.get(varName);
    if (current == null || current.isEmpty()) {
      return null;
    }
    for (int i = 0;i<current.size();i++) {
      if (current.get(i).name.equals(varName) && current.get(i).def instanceof VarDec) {
        return current.get(i);
      }
    }
    return null;
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
    return c.get(0);
  }

  public boolean inTable(String key) {
    return table.containsKey(key);
  }

  public void delete(int level) {
    Set<String> keys = table.keySet();
    ArrayList<String> keysToDelete = new ArrayList<String>();
    
    for (String key : keys) {
     // indent(level);
      //System.out.print(key + ": ");
      ArrayList<NodeType> c = table.get(key);
      NodeType current = c.get(0);
      // System.out.println(current.level);
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
        indent(globalLevel);
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
            System.out.println(" INT[" + ad.arraySize.value + "]");
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
    globalLevel++;
    level++;
    if (exp.test != null) {
      exp.test.accept( this, level );
    } else {
      System.out.println("Error: No expression given for if condition! row: " + exp.row + " col: " + exp.col);
    }
    System.out.println("Entering a new block");
    if (exp.thenpart != null) {
      exp.thenpart.accept( this, level );
    }
    printLevel(globalLevel);
    delete(level);
    indent(globalLevel);
    System.out.println("Leaving a new block");
    if (exp.elsepart != null ) {
      indent(globalLevel);
      System.out.println("Entering a new block");
      exp.elsepart.accept( this, level );
      printLevel(globalLevel);
      System.out.println("Leaving a new block");
      delete(level);
    }
    globalLevel--;
  }

  public void visit( IntExp exp, int level ) {
    // indent( level );
    //System.out.println( "IntExp: " + exp.value ); 
    
  }

  public void visit( OpExp exp, int level ) {
    // indent( level );
    //System.out.print( "OpExp:" ); 

    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
    String varName = "";
    if (exp.left instanceof SimpleVarExp) {
      SimpleVarExp tempSimp = (SimpleVarExp) exp.left;
      if (tempSimp.var instanceof SimpleVar) {
        SimpleVar tempSVar = (SimpleVar) tempSimp.var;
        varName = tempSVar.varName;
      } else if (tempSimp.var instanceof SimpleIndexVar) {
        SimpleIndexVar tempSIVar = (SimpleIndexVar) tempSimp.var;
        varName = tempSIVar.varName;
      }
      if (findVariable(varName) != null) {
        if (findType(varName) != 1) {
          System.err.println("Error: Left side of operation is not an integer at row: " + (exp.left.row + 1) + " and col: " + (exp.left.col + 1));
        }
      } else {
        System.err.println("Error: Undeclared operation at row: " + (exp.left.row + 1) + " col: " + (exp.left.col + 1));
      }
    } else if (exp.left instanceof CallingExp) {
      CallingExp tempCall = (CallingExp) exp.left;
      varName = tempCall.funName;
      if (findFunction(varName) != null) {
        if (findType(varName) != 1) {
          System.err.println("Error: Left side of operation is not an integer at row: " + (exp.left.row + 1) + " and col: " + (exp.left.col + 1));
        } 
      } else {
          System.err.println("Error: Undeclared operation at row: " + (exp.left.row + 1) + " col: " + (exp.left.col + 1));
      }
    }

    if (exp.right instanceof SimpleVarExp) {
      SimpleVarExp tempSimp = (SimpleVarExp) exp.right;
      if (tempSimp.var instanceof SimpleVar) {
        SimpleVar tempSVar = (SimpleVar) tempSimp.var;
        varName = tempSVar.varName;
      } else if (tempSimp.var instanceof SimpleIndexVar) {
        SimpleIndexVar tempSIVar = (SimpleIndexVar) tempSimp.var;
        varName = tempSIVar.varName;
      }
      if (findVariable(varName) != null) {
        if (findType(varName) != 1) {
          System.err.println("Error: Right side of operation is not an integer. Or has a VOID variable in expression at row: " + (exp.right.row + 1) + " and col: " + (exp.right.col + 1));
        }
      } else {
        System.err.println("Error: Undeclared operation at row: " + (exp.right.row + 1) + " col: " + (exp.right.col + 1));
      }
    } else if (exp.right instanceof CallingExp) {
      CallingExp tempCall = (CallingExp) exp.right;
      varName = tempCall.funName;
      if (findFunction(varName) != null) {
        if (findType(varName) != 1) {
          System.err.println("Error: Right side of operation is not an integer or contains a VOID expression at row: " + (tempCall.row + 1) + " and col: " + (tempCall.col + 1));
        } 
      } else {
          System.err.println("Error: Undeclared operation at row: " + (exp.right.row + 1) + " col: " + (exp.right.col + 1));
      }
    }

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
    // lookup the symbol and check the type
    ArrayList<NodeType> currentList = table.get(var.varName);
    NodeType current = currentList.get(0);
    if (current == null) {
      System.err.println("Error: Unknown variable named " + var.varName + " at row: " + (var.row + 1) + " and col: " + (var.col + 1)); 
    }
    
    if (current != null && !isInteger(current.def)) {
      System.err.println("Error: Variable cannot be VOID. Variable named " + var.varName + " at row: " + (var.row + 1) + " and col: " + (var.col + 1)); 
    }

    if (current != null) {
    var.dtype = current.def;
    }
  }

  public void visit( VarAssignExp varAssign, int level) {
    // indent( globalLevel );
    int lhsType = -1;
    int rhsType = -1;
    String lhsName = "";
    int row = 0;
    int col = 0;
    if (varAssign.lhs instanceof SimpleVar) {
      SimpleVar templhs = (SimpleVar) varAssign.lhs;
      // if (!inTable(templhs.varName)) {
      //   System.err.println("Error: Unknown Variable with name: " + templhs.varName + " at row: " + templhs.row + " at col: " + templhs.col);
      // }
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

    if (lhsType != rhsType && lhsType != -2) {
      System.err.println("Error: Type Mismatch for variable with name: " + lhsName + " at row: " + (row + 1) + " at col: " + (col + 1));
    }

    varAssign.lhs.accept( this, level );
    varAssign.rhs.accept( this, level );
  }

  public void visit(SimpleIndexVar var, int level) {
    // indent( globalLevel );
    System.out.println("Simple Index Var:" + var.varName);

    // TODO: Check an error here for the index of the variable!
     level++;

    var.exp.accept(this, level);

    if (var.exp instanceof CallingExp) {
      CallingExp tempCall = (CallingExp) var.exp;
      if (!isInteger(tempCall.dtype)) {
        System.err.println("Error: Invalid array index. Array index cannot be VOID. Function name: " + tempCall.funName + " at row: " + (tempCall.row+1) + " and col: " + (tempCall.col + 1));
      }
    }
  }

  public void visit(ArrayDec exp, int level) {
    ArrayList<NodeType> check = table.get(exp.arrayName);
    if (check != null && check.get(0).level == level) {
      System.err.println("Error: Redefined variable " + exp.arrayName + " at the same level, at line: " + (exp.row + 1) + " column: " + (exp.col + 1));
    } else {
      if (exp.type != null && exp.type.type == 0) {
        System.err.println("Error: variables cannot be defined as VOID type, at line: " + (exp.row + 1) + " column: " + (exp.col + 1));
        exp.type.type = 0;
      }
    }
    NodeType newNode = new NodeType(exp.arrayName, exp, level);
    insert(newNode);
  }

  public void visit(CallingExp exp, int level) {
    // indent( level );
    // System.out.println( "CallExp: " + exp.funName);
    // lookup the function
    String functionName = exp.funName;
    NodeType functionNode = findFunction(functionName);
    VarDecList funargs = null;
    if (functionNode == null) {
      System.err.println("Error: Invalid function with name: " + functionName + " at row: " + (exp.row+1) + " and col: " + (exp.col + 1));
    } 
    if (functionNode != null) {
      exp.dtype = functionNode.def;
      FunctionDec temp = (FunctionDec) functionNode.def;
      funargs = temp.param;
    }

    
    ExpList args = exp.args;

    ExpList tempArgs = args;
    ExpList tempArgs2 = args;
    VarDecList tempFunArgs = funargs;

    while (args != null) {
        args.head.accept(this, level);
        args = args.tail;
    }

    int callCount = 0;
    int funCount = 0;

    if (args != null && funargs == null && functionNode != null && !functionNode.name.equals("output")) {
      System.err.println("Error: Calling function with mismatched type params. With name " + functionNode.name + " at row: " + (functionNode.def.row + 1) + " at col: " + (functionNode.def.col + 1));
    }

    // For the typemismatch check in function call!
    while (tempArgs2 != null && tempFunArgs != null) {
      VariableDeclaration tempVd = (VariableDeclaration) tempFunArgs.head;
      boolean isintHack = tempArgs2.head instanceof IntExp;
      boolean canSkip = isintHack && isInteger(tempVd);

      if ( !canSkip && isInteger(tempArgs2.head.dtype) != isInteger(tempVd)) {
        System.err.println("Error: Type mistmatch for function argument at row: " + (tempArgs2.row + 1) + " and col: " + (tempArgs2.col + 1));
      }
      tempArgs2 = tempArgs2.tail;
      tempFunArgs = tempFunArgs.tail;
    }


    // For the number of param check!
    while (tempArgs != null) {
      callCount++;
      tempArgs = tempArgs.tail;
    }
    while (funargs != null) {
      funCount++;
      funargs = funargs.tail;
    }

    // System.err.println(callCount + " " + funCount);
    if (callCount != funCount && !functionNode.name.equals("output")) {
      System.err.println("Error: Calling function with incorrect number of paramaters for function name: " + exp.funName + " at row: " + (exp.row + 1) + " col: " + (exp.col + 1));
    }
  }
  
  public void visit(CompoundExp exp, int level) {
    // indent (level);
    // System.out.println( "CompoundExp: ");
    VarDecList dds = exp.decl;
    while (dds != null) {
        dds.head.accept(this, level);
        dds = dds.tail;
    }
    ExpList exps = exp.expl;
    while (exps != null) {
        exps.head.accept(this, level);
        if (exps.head instanceof ReturnExp) {
          ReturnExp ret = (ReturnExp) exps.head;
          exp.dtype = ret.dtype;
        }
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
    globalLevel++;
    indent(globalLevel);
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
    
    currentFunctionScope = exp.funName;
    exp.funBody.accept(this, level);
    currentFunctionScope = "";
    // Check the return ty

    indent(globalLevel);
    printLevel(globalLevel);
    delete(level);
    indent(globalLevel);
    System.out.println("Leaving the scope for function: " + exp.funName + ":");
    globalLevel--;
  }

  public void visit(ReturnExp exp, int level) {
    // indent (level);
    // System.out.println( "ReturnExp: ");
    NodeType c = null;
    if (!currentFunctionScope.equals("")) {
      c = findFunction(currentFunctionScope);
    }
    level++;
    if (exp.expr == null && c != null && isInteger(c.def)) {
      System.err.println("Error: Invalid return type. Expecting INT for function: " + c.name + " at row: " + (exp.row + 1) + " at col: " + (exp.col + 1));
    }
    if (exp.expr != null) {
      exp.expr.accept(this, level);
      if (exp.expr instanceof CallingExp) {
        CallingExp call = (CallingExp) exp.expr;
        exp.dtype = call.dtype;
        if (c != null && isInteger(c.def) != isInteger(call.dtype)) {
          System.err.println("Error: Invalid return type for function at row: " + (exp.row + 1) + " and col: " + (exp.col + 1));
        }
      }
    }
  }

  public void visit(VariableDeclaration exp, int level) {
    // Add the declaration to the table. Check if the table already contains this symbol!
    ArrayList<NodeType> check = table.get(exp.sname);

    if (check != null && check.get(0).level == level) {
      // redefinition!
      System.err.println("Error: Redefined variable " + exp.sname + " at the same level, at line: " + (exp.row + 1) + " column: " + (exp.col + 1));
    } else {
      if (exp.type.type == 0) {
        System.err.println("Error: variables cannot be defined as VOID type, at line: " + (exp.row + 1) + " column: " + (exp.col + 1));
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
          System.out.println("Invalid Declaration list! row : " + (expList.row + 1) + " col: " + (expList.col + 1));
        }
        expList.head.accept( this, level );
        expList = expList.tail;
    }
  }

  public void visit(WhileExp exp, int level) {
    //indent (level);
    //System.out.println( "WhileExp: ");
    level++;
    globalLevel++;
    if (exp.test != null)
      exp.test.accept(this, level);
    else {
      //indent(level);
      //System.out.println("Error: No expression given for while condition! row: " + exp.row + " col: " + exp.col);
    }
    indent(globalLevel);
    System.out.println("Entering a new block");
    exp.exps.accept(this, level);
    printLevel(globalLevel);
    delete(level);
    indent(globalLevel);
    System.out.println("Leaving a new block");
    globalLevel--;
  }

  public void visit(SimpleVarExp exp, int level) {
    // indent(level);
    // System.out.println(" SimpleVarExp: ");
    level++;
    exp.var.accept(this, level);
    // TODO: MAYBE
    exp.dtype = exp.var.dtype;
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

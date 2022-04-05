/*
  Created by: James Lemieux and Mustafa Al-Obaidi
  File Name: CM.java
*/
   
import java.io.*;
import absyn.*;
   
class CM {
  public static boolean SHOW_TREE = false;
  public static boolean SHOW_SEMATIC = false;
  public static boolean SHOW_GENERATE = false;
  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      if (argv[1].equals("-a")) {
        SHOW_TREE = true;
      }
      else if (argv[1].equals("-s")) {
        SHOW_SEMATIC = true;
      } else if (argv[1].equals("-c")) {
        SHOW_GENERATE = true;
      } else{
        System.out.println("Error: Please add -a flag for the abstract syntax tree or -s for the symantic analyzer tree!");
        System.out.println("Error: Or add a -c flag to start the code generation process");
      }
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);      
      if (SHOW_TREE && result != null) {
        System.out.println("The abstract syntax tree is:");
        ShowTreeVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0);
      }
      else if(SHOW_SEMATIC && result != null){
        System.out.flush();
        PrintStream out = new PrintStream(new FileOutputStream(argv[0].replace(".cm", ".sym")));
        System.setOut(out);
        System.out.println("The Semantic analyzer tree is:");
        System.out.println("Entering the global scope:");
        SemanticAnalyzer visitor = new SemanticAnalyzer();
        NodeType node = new NodeType("input", new FunctionDec(0, 0, new TypeName(0,0, TypeName.INT), "input", null, null), 0, 0);
        visitor.insert(node);
        NodeType node2 = new NodeType("output", new FunctionDec(0, 0, new TypeName(0,0, TypeName.VOID), "output", null, null), 0, 0);
        visitor.insert(node2);
        result.accept(visitor,0);
        visitor.printLevel(0);
        System.out.println("Leaving the global scope");
      } else if (SHOW_GENERATE && result != null) {
        PrintStream out = new PrintStream(new FileOutputStream(argv[0].replace(".cm", "") + ".tm"));
        System.setOut(out);
        codeGenerator visitor2 = new codeGenerator();
        visitor2.emitComment("C-Minus Compilation to TM Code");
        visitor2.emitComment("File: " + argv[0].replace(".cm", "") + ".tm");
        visitor2.visit(result, visitor2);
      }
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}



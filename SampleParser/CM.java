/*
  Created by: James Lemieux
  File Name: CM.java
*/
   
import java.io.*;
import absyn.*;
   
class CM {
  public static boolean SHOW_TREE = false;
  public static boolean SHOW_SEMATIC = false;
  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      if (argv[1].equals("-a")) {
        SHOW_TREE = true;
      }
      else if (argv[1].equals("-s")) {
        SHOW_SEMATIC = true;
      }
      else{
        System.out.println("Error: Please add -a flag for the abstract syntax tree or -s for the symantic analyzer tree!");
      }
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);      
      if (SHOW_TREE) {
         System.out.println("The abstract syntax tree is:");
         ShowTreeVisitor visitor = new ShowTreeVisitor();
         if (result != null)
          result.accept(visitor, 0);
      }
      else if(SHOW_SEMATIC){
        System.out.println("The Semantic analyzer tree is:");
        SemanticAnalyzer visitor = new SemanticAnalyzer();
        System.out.println("Entering the global scope:");
        if (result != null)
          result.accept(visitor,0);
        System.out.println("Leaving the global scope");
      }
    
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}



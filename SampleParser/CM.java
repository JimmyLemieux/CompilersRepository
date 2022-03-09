/*
  Created by: James Lemieux
  File Name: CM.java
*/
   
import java.io.*;
import absyn.*;
   
class CM {
  public final static boolean SHOW_TREE = true;
  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);      
      if (SHOW_TREE) {
         System.out.println("The abstract syntax tree is:");
         ShowTreeVisitor visitor = new ShowTreeVisitor();
         if (result != null)
          result.accept(visitor, 0); 
      }
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}



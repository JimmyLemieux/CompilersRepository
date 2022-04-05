/*
  Created by: James Lemieux and Mustafa Al-Obaidi
  File Name: NodeType.java
*/
import absyn.*;

public class NodeType {
  public String name;
  public Declaration def;
  public int level;

  public NodeType(String name, Declaration def, int level, int offset) {
    this.name = name;
    this.def = def;
    this.level = level;
  }
}
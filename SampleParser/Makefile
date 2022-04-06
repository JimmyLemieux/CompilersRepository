JAVA=java
JAVAC=javac
JFLEX=jflex
CLASSPATH=-cp /usr/share/java/cup.jar:.
CUP=cup
#JFLEX=~/Projects/jflex/bin/jflex
#CLASSPATH=-cp ~/Projects/java-cup-11b.jar:.
#CUP=$(JAVA) $(CLASSPATH) java_cup.CM

all: clean CM.class

CM.class: absyn/*.java parser.java sym.java Lexer.java ShowTreeVisitor.java codeGenerator.java Scanner.java CM.java

%.class: %.java
	$(JAVAC) $(CLASSPATH) $^

Lexer.java: cm.flex
	$(JFLEX) cm.flex

parser.java: cm.cup
	#$(CUP) -dump -expect 3 cm.cup
	$(CUP) -expect 3 < cm.cup

clean:
	rm -f parser.java Lexer.java sym.java *.class absyn/*.class *~

testSyn:
	java -classpath /usr/share/java/cup.jar:. CM $n.cm -a

testSym:
	java -classpath /usr/share/java/cup.jar:. CM $n.cm -s

testCode:
	java -classpath /usr/share/java/cup.jar:. CM $n.cm -c

run:
	java -classpath /usr/share/java/cup.jar:. CM 5.cm -s
all: rmk35/partIIProject/frontend/SchemeParser.class

rmk35/partIIProject/frontend/SchemeParser.class: rmk35/partIIProject/frontend/SchemeParser.java \
														 rmk35/partIIProject/frontend/SchemeFileParser.java \
														 rmk35/partIIProject/frontend/SchemeFileLexer.java
	javac $?

%BaseListener.java %Lexer.java %Listener.java %Parser.java: %.g4
	java -jar antlr-4.5.1-complete.jar $<

%GrammarTest: %BaseListener.java %Lexer.java %Listener.java %Parser.java
	javac $?
	java org.antlr.v4.gui.TestRig $* datum -gui

rmk35/partIIProject/frontend/SchemeFile.g4: rmk35/partIIProject/frontend/SchemeExternalRepresentation.g4 \
	rmk35/partIIProject/frontend/AST/SchemeList.java \
	rmk35/partIIProject/frontend/AST/SchemeIdentifier.java \
	rmk35/partIIProject/frontend/AST/SchemeSimpleNumber.java \
	rmk35/partIIProject/frontend/AST/SchemeEquality.java \
	rmk35/partIIProject/frontend/AST/SchemeCharacter.java \
	rmk35/partIIProject/frontend/AST/SchemeVector.java \
	rmk35/partIIProject/frontend/AST/SchemeString.java \
	rmk35/partIIProject/frontend/AST/SchemeObject.java \
	rmk35/partIIProject/frontend/AST/SchemeNumber.java \
	rmk35/partIIProject/frontend/AST/SchemeBoolean.java \
	rmk35/partIIProject/frontend/AST/SchemeBytevector.java \
	rmk35/partIIProject/frontend/SchemeParserException.java

rmk35/partIIProject/frontend/SchemeExternalRepresentation.g4: rmk35/partIIProject/frontend/SchemeLexicalStructure.g4

.PHONY: clean

clean:
	-rm rmk35/partIIProject/frontend/SchemeFileLexer.java \
	rmk35/partIIProject/frontend/SchemeFileLexer.class \
	rmk35/partIIProject/frontend/SchemeFileLexer.tokens \
	rmk35/partIIProject/frontend/SchemeFileParser.java \
	rmk35/partIIProject/frontend/SchemeFileParser.class \
	rmk35/partIIProject/frontend/SchemeFileListener.java \
	rmk35/partIIProject/frontend/SchemeFileListener.class \
	rmk35/partIIProject/frontend/SchemeFileBaseListener.java \
	rmk35/partIIProject/frontend/SchemeFileBaseListener.class \
	rmk35/partIIProject/frontend/SchemeFile.tokens \
	rmk35/partIIProject/frontend/AST/SchemeList.class \
	rmk35/partIIProject/frontend/AST/SchemeIdentifier.class \
	rmk35/partIIProject/frontend/AST/SchemeSimpleNumber.class \
	rmk35/partIIProject/frontend/AST/SchemeEquality.class \
	rmk35/partIIProject/frontend/AST/SchemeCharacter.class \
	rmk35/partIIProject/frontend/AST/SchemeVector.class \
	rmk35/partIIProject/frontend/AST/SchemeString.class \
	rmk35/partIIProject/frontend/AST/SchemeObject.class \
	rmk35/partIIProject/frontend/AST/SchemeNumber.class \
	rmk35/partIIProject/frontend/AST/SchemeBoolean.class \
	rmk35/partIIProject/frontend/AST/SchemeBytevector.class \
	rmk35/partIIProject/frontend/Parser.class


.PHONY: runtest

runtest: all
	java rmk35.partIIProject.frontend.SchemeParser test.txt

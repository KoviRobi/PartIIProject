all: rmk35/partIIProject/frontend/SchemeParser.class

rmk35/partIIProject/frontend/SchemeParser.class: rmk35/partIIProject/frontend/SchemeParser.java \
														 rmk35/partIIProject/frontend/SchemeFileParser.java \
														 rmk35/partIIProject/frontend/SchemeFileLexer.java \

	javac $?

%BaseListener.java %Lexer.java %Listener.java %Parser.java: %.g4
	java -jar antlr-4.5.1-complete.jar $<

%GrammarTest: %BaseListener.java %Lexer.java %Listener.java %Parser.java
	javac $?
	java org.antlr.v4.gui.TestRig $* datum -gui

rmk35/partIIProject/frontend/SchemeFile.g4: rmk35/partIIProject/frontend/SchemeExternalRepresentation.g4
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
	rmk35/partIIProject/frontend/Parser.class \


.PHONY: runtest

runtest:
	java rmk35.partIIProject.frontend.SchemeParser test.txt

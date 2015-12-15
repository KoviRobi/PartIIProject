all: rmk35/partIIProject/frontend/SchemeParser.class

rmk35/partIIProject/frontend/SchemeParser.class: rmk35/partIIProject/frontend/SchemeParser.java \
														 rmk35/partIIProject/frontend/SchemeFileParser.java \
														 rmk35/partIIProject/frontend/SchemeFileLexer.java \
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
														 rmk35/partIIProject/frontend/AST/SchemeAbbreviation.java \
														 rmk35/partIIProject/frontend/AST/SchemeLabelledData.java \
														 rmk35/partIIProject/frontend/SchemeParserException.java \
														 rmk35/partIIProject/backend/SetStatement.java \
														 rmk35/partIIProject/backend/RuntimeValue.java \
														 rmk35/partIIProject/backend/LambdaValue.java \
														 rmk35/partIIProject/backend/Identifier.java \
														 rmk35/partIIProject/backend/JavaBytecodeGenerator.java \
														 rmk35/partIIProject/backend/OutputClass.java \
														 rmk35/partIIProject/backend/Macro.java \
														 rmk35/partIIProject/backend/MainClass.java \
														 rmk35/partIIProject/backend/IfStatement.java \
														 rmk35/partIIProject/backend/ApplicationStatement.java \
														 rmk35/partIIProject/backend/Definition.java \
														 rmk35/partIIProject/backend/Statement.java \
														 rmk35/partIIProject/backend/InnerClass.java \
														 rmk35/partIIProject/backend/LambdaStatement.java
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
	-rm -f rmk35/partIIProject/frontend/SchemeFileLexer.java \
	rmk35/partIIProject/frontend/SchemeFileLexer.class \
	rmk35/partIIProject/frontend/SchemeFileLexer.tokens \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$AbbrevPrefixContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$AbbreviationContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$BoolContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$BytevectorContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$CharacterContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$CompoundDatumContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$DatumContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$LabelContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$ListContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$NumberContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$SimpleDatumContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$StringContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$SymbolContext.class \
	rmk35/partIIProject/frontend/SchemeExternalRepresentationParser\$$VectorContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$AbbrevPrefixContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$AbbreviationContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$BoolContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$BytevectorContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$CharacterContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$CompoundDatumContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$DatumContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$FileContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$LabelContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$ListContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$NumberContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$SimpleDatumContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$StringContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$SymbolContext.class \
	rmk35/partIIProject/frontend/SchemeFileParser\$$VectorContext.class \
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
	rmk35/partIIProject/frontend/AST/SchemeAbbreviation.class \
	rmk35/partIIProject/frontend/AST/SchemeLabelledData.class \
	rmk35/partIIProject/frontend/SchemeParser.class \
	rmk35/partIIProject/frontend/SchemeParserException.class \
	rmk35/partIIProject/backend/SetStatement.class \
	rmk35/partIIProject/backend/RuntimeValue.class \
	rmk35/partIIProject/backend/LambdaValue.class \
	rmk35/partIIProject/backend/Identifier.class \
	rmk35/partIIProject/backend/JavaBytecodeGenerator.class \
	rmk35/partIIProject/backend/OutputClass.class \
	rmk35/partIIProject/backend/Macro.class \
	rmk35/partIIProject/backend/MainClass.class \
	rmk35/partIIProject/backend/IfStatement.class \
	rmk35/partIIProject/backend/ApplicationStatement.class \
	rmk35/partIIProject/backend/Definition.class \
	rmk35/partIIProject/backend/Statement.class \
	rmk35/partIIProject/backend/InnerClass.class \
	rmk35/partIIProject/backend/LambdaStatement.class


.PHONY: runtest

runtest: all
	java rmk35.partIIProject.frontend.SchemeParser test.txt

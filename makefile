.PHONY: all frontend backend middle shared delombok
all: frontend backend middle shared
frontend: delombok shared \
 out/rmk35/partIIProject/frontend/SchemeParser.class
backend: delombok shared \
 out/rmk35/partIIProject/backend/JavaBytecodeGenerator.class
middle: delombok shared \
 out/rmk35/partIIProject/middle/Interconnect.class
shared: out/rmk35/partIIProject/InternalCompilerException.class
delombok:
	java -cp .:libraries/* -jar libraries/lombok.jar delombok --quiet src --target=out --classpath=.:libraries/* 2> /dev/null

# TODO: Make check automatic
.PHONY: frontendtest backendtest middletest tests
frontendtest: frontend
	cd out; java -cp .:../libraries/* rmk35.partIIProject.frontend.SchemeParser test.txt
backendtest: backend
	cd out; java -cp .:../libraries/* rmk35.partIIProject.backend.JavaBytecodeGenerator
middletest: middle
	cd out; java -cp .:../libraries/* rmk35.partIIProject.middle.Interconnect
tests: frontendtest backendtest middletest

.PHONY: bugs
bugs:
	! find . -name '*.java' -or -name '*.md' -or -name 'makefile' | xargs egrep -i 'TODO|FIXME|UnsupportedOperationException' # Look for TODO, FIXME and UnsupportedOperationException

.PHONY: release
release: all tests bugs

out/%.class: out/%.java
	cd out; javac -implicit:class -cp .:../libraries/* -g $*.java

%BaseListener.java %Lexer.java %Listener.java %Parser.java: %.g4
	java -jar libraries/antlr-4.5.1-complete.jar $<

%GrammarTest: %BaseListener.java %Lexer.java %Listener.java %Parser.java
	javac -cp .:libraries/* $?
	java -cp .:libraries/* org.antlr.v4.gui.TestRig $* datum -gui

# Dependency graph removed as lombok updates the timestamps, so we will
# recompile all

rmk35/partIIProject/frontend/SchemeFile.g4: rmk35/partIIProject/frontend/SchemeExternalRepresentation.g4
rmk35/partIIProject/frontend/SchemeExternalRepresentation.g4: rmk35/partIIProject/frontend/SchemeLexicalStructure.g4

.PHONY: clean
clean:
	-rm -rf out

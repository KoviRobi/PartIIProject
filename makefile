.PHONY: all frontend backend middle shared parser delombok
all: frontend backend middle shared
frontend: parser delombok shared \
 out/rmk35/partIIProject/frontend/SchemeParser.class
backend: delombok shared \
 out/rmk35/partIIProject/backend/JavaBytecodeGenerator.class \
 out/rmk35/partIIProject/backend/runtimeValues/LambdaValue.class \
 out/rmk35/partIIProject/backend/runtimeValues/RuntimeValue.class \
 out/rmk35/partIIProject/backend/runtimeValues/NumberValue.class \
 out/rmk35/partIIProject/backend/runtimeValues/BooleanValue.class
middle: delombok frontend shared \
 out/rmk35/partIIProject/middle/Interconnect.class
shared: out/rmk35/partIIProject/InternalCompilerException.class
parser: out/rmk35/partIIProject/frontend/SchemeFileLexer.java out/rmk35/partIIProject/frontend/SchemeFileParser.java
delombok:
	java -cp .:libraries/* -jar libraries/lombok.jar delombok --quiet src --target=out --classpath=.:libraries/* 2> /dev/null

# TODO: Make check automatic
.PHONY: frontendtest backendtest middletest tests
frontendtest: frontend
	cd out; java -cp .:../libraries/* rmk35.partIIProject.frontend.SchemeParser ../test.txt
backendtest: backend
	cd out; java -cp .:../libraries/* rmk35.partIIProject.backend.JavaBytecodeGenerator
middletest: frontend middle
	cd out; java -cp .:../libraries/* rmk35.partIIProject.middle.Interconnect
tests: frontendtest backendtest middletest

.PHONY: bugs
bugs:
	! find . -name '*.java' -or -name '*.md' -or -name 'makefile' | xargs egrep -i 'TODO|FIXME|UnsupportedOperationException' # Look for TODO, FIXME and UnsupportedOperationException

.PHONY: release
release: all tests bugs

out/%.class: out/%.java
	cd out; javac -implicit:class -cp .:../libraries/* -g $*.java

out/%BaseListener.java out/%Lexer.java out/%Listener.java out/%Parser.java: src/%.g4
	cd src; java -jar ../libraries/antlr-4.5.1-complete.jar $*.g4 -o ../out

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

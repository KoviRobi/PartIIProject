# PartIIProject
Cambridge Part II Project
Also see glossary.md

## Front-end
The entry point to the parser is SchemeParser.java To understand the
parser, have a look at
src/main/antlr4/rmk35/partIIProject/frontend/SchemeFile.g4, it
includes the other parts of the grammar, and uses parser actions to
construct abstract syntax trees. The abstract syntax tree objects are
under src/main/java/rmk35/partIIProject/runtime as the AST is exposed
in Scheme to the runtime

## Back-end
To understand the back-end, start with JavaByteCodeGenerator, which
dispatches generateOutput on each statement of the program. Also look
at the GLOSSARY.md.

##  Middle
This connects the front and the back end by converting the data
structures, The entry point is the Interconnect.java file, which uses
a visitor pattern to walk the output of the front end. This is
actually an implementation of the hygienic macro engine, as the Scheme
keywords are not reserved so we need to know if for example 'lambda'
is keyword or an identifier (and indeed if is a keyword, is it the
inbuilt lambda one).

## Runtime
The AST/runtime objects (mainly the *Value objects)

### Trampoline
This is backwards, but what happens is application returns a TrampolineValue, which is bounce()d [applied until it returns a non-TrampolineValue] by either a fencepost of sequencing (the last TrampolineValue in a sequence gets passed up, only values that would be popped due to sequencing throwing values away are bounced()) or the top level value guard. Trampolines are also responsible for exception handling

## Experiments
This folder contains things that I found unsufficiently documented so
I had a look for myself.

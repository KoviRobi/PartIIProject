# PartIIProject
Cambridge Part II Project
Also see glossary.md

## Front-end
The entry point to the parser is SchemeParser.java To understand the
parser, have a look at
[SchemeFile.g4](src/main/antlr4/rmk35/partIIProject/frontend/SchemeFile.g4), it
includes the other parts of the grammar, and uses parser actions to
construct abstract syntax trees. The abstract syntax tree objects are
under src/main/java/rmk35/partIIProject/runtime as the AST is exposed
in Scheme to the runtime

## Back-end
This is organised into `OutputClasses` (subclasses: inner and main
class), which contain `ByteCodeMethods` which contain a list of
instructions. The ByteCodeMethod is populated by the Statements, with
contain Instructions, by sending a `generateOutput` message (method
call) to the `Statement` class (one begin statement that encapsulates
the whole programme). There are three ways of doing tail calls, not
doing them, using trampolines or maintaining our own stack. These are
each subclasses of `src/**/backend/statements/TailCallSettings`. Briefly  these work by

|               | no tail calls    | trampolines         | own stack           |
|---------------|------------------|---------------------|---------------------|
| method call:  | java method call | return a call value | return a call value |
| continuation: | do nothing       | spawn a trampoline  | create a new frame  |

##  Middle
This connects the front and the back end by converting the data
structures, The entry point is the LibraryOrProgramme.java file, which
uses both visitors and macro matching to generate the output a visitor
pattern to walk the output of the front end. Keywords are not reserved
so we need to know if for example 'lambda' is syntactic or an
identifier (and indeed if is syntactic, is it the inbuilt lambda one
or some other macro), hence why we only get such simple output from
the front-end

## Runtime
The AST/runtime objects (mainly the `*Value` objects). ValueHelper
contains code to convert Java values to Scheme values

## Experiments
This folder contains things that I found unsufficiently documented so
I had a look for myself.

## Settings
The settings to the compiler are passed in via environment variables, the ones in use are
- "tailcalls"
 - 0: No tail calls
 - 1: Trampolining
 - 2: Use custom stack for Scheme calls (for call/cc)
- "intermediate"
 - When set, generates intermediate Jasmin assembly files
- "profile"
 - When set, outputs simple profiling data
- timeStages
 - When set, outputs time spent in front, middle (macros, mainly) and back-end stages

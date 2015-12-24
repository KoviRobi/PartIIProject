# PartIIProject
Cambridge Part II Project

## Front-end
To understand the parser, have a look at
rmk35/partIIProject/frontend/SchemeFile.g4, it includes the other
parts of the grammar, and uses parser actions to construct abstract
syntax trees. The abstract syntax tree objects are under
rmk35/partIIProject/frontend/AST/

All AST items contain working versions of:
  eq
  eqv
  equal
  mutable
  toString

## Back-end
To understand the back-end, start with JavaByteCodeGenerator, which
dispatches generateOutput on each statement of the program. Also look
at the GLOSSARY.md.

## Experiments
This folder contains things that I found unsufficiently documented so
I had a look for myself.

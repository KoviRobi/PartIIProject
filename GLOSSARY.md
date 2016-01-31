* rmk35.partIIProject.frontend.AST
  - Here are the thing that may be parsed as data
  - All subclass of rmk35.partIIProject.middle.AST
  - SchemeLiteral has the following known subclasses:
    - SchemeAbbreviation: quote and quasi-quote
    - SchemeBoolean
    - Scheme

* rmk35.partIIProject.middle
  - This contain the macro processor, which is also the engine that looks at keywords as Scheme has no resevered keywords
  - Interconnect: Entry point
  - AST*Visit.java: Visitors on the abstract syntax tree, parameterized so has different return values
    - astExpectVisitor/: this contains visitors for extracting subclasses of AST
    - ASTConvertVisitor.java: this is the main visitor for compilation, converts to statements
    - ASTApplyicationVisitor: this applies an element to the rest of the list passed in as arguments
    - astExpectVisitor: this contains visitors for extracting subclasses of AST
    - ASTListFoldVisitor: this folds a binary function across a proper list or throws an exception
    - ASTMacroTranscribeVisitor: does transcription, see Macros That Work, W. Clinger, J. Rees
    - ASTSyntaxSpecificationVisitor: handles syntax spec as on r7rs 7.1.3, page 64
  - Environment: Store of bindings
    - subEnvironment (concept): An environment that is used in the lambda body, e.g. has local variables of
       the lambda use environment as closure variables (then formals are the new local variables)
  
* rmk35.partIIProject.backend
  - Statement: Input datastructure from front-end, superclass is Statement
  - Value: Runtime value, output of the back-end, superclass is RuntimeValue

* Comment keywords
  - FIXME: shortcomings that need to be fixed before release
  - TODO: things that would be nice
  - XXX Speed: speed improvements that should be fairly easy
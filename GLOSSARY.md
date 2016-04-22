Also see README.md

* rmk35.partIIProject.frontend
  - Parses the string into data, in the sense of lists, vectors, symbols and literals

* rmk35.partIIProject.middle
  - This contain the macro processor, which is also the engine that looks at keywords as Scheme has no resevered keywords
  - LibraryOrProgramme: Entry point
  - AST*Visit.java: Visitors on the abstract syntax tree, parameterized so has different return values
    - astExpectVisitor/: this contains visitors for extracting subclasses of AST
    - astMacroMatchVisitor/: Contains macro match visitors
    - ASTConvertVisitor.java: this is the main visitor for compilation, converts to statements
    - ASTApplyicationVisitor: this applies an element to the rest of the list passed in as arguments
    - ASTListFoldVisitor: this folds a binary function across a proper list or throws an exception
    - ASTMacroTranscribeVisitor: does transcription, see Macros That Work, W. Clinger, J. Rees
    - ASTMatcher: Java interface to the macro engine, very useful as it gives us pattern matching (kind of)
    - ASTSyntaxSpecificationVisitor: handles syntax spec as on r7rs 7.1.3, page 64
  - Environment: Mapping from identifiers to values in store
    - subEnvironment (concept): An environment that is used in the lambda body, e.g. has local variables of
       the lambda use environment as closure variables (then formals are the new local variables)
  - bindings: These fall into one of the three categories:
    - SyntacticBinding: An abstract class for all the syntactic bindings
    - VariableBinding: Variable (global, local)
    - MarkerBinding: Just to use for instanceof, should only be used for the macro engine, then use ASTMatcher instead

* rmk35.partIIProject.backend
  - Statement: Input data from middle

* rmk35.partIIProject.runtime
  - RuntimeValue: things that can occur at runtime
  - PrimitiveValue: things that can be parsed by (read), i.e. not lambdas or any other pecurial things

* Comment keywords
  - FIXME: shortcomings that need to be fixed before release
  - TODO: things that would be nice
  - XXX Speed: speed improvements that should be fairly easy
* rmk35.partIIProject.frontend.AST
  - Here are the thing that may be parsed as data

* rmk35.partIIProject.middle
  - This contain the macro processor, which is also the engine that looks at keywords as Scheme has no resevered keywords
  - Interconnect: Entry point
  - AST*Visit.java: Visitors on the abstract syntax tree, parameterized so has different return values
  - Environment: Store of bindings
    - SubEnvironment: An environment that is used in the lambda body, e.g. has local variables of
       the lambda use environment as closure variables (then formals are the new local variables)
  
* rmk35.partIIProject.backend
  - Statement: Input datastructure from front-end, superclass is Statement
  - Value: Runtime value, output of the back-end, superclass is RuntimeValue

* Comment keywords
  - FIXME: shortcomings that need to be fixed before release
  - TODO: things that would be nice
  - XXX Speed: speed improvements that should be fairly easy
* rmk35.partIIProject.frontend
  - SchemeObject: Something that ends up being a value, such as a vector or a quotation.

* rmk35.partIIProject.middle

* rmk35.partIIProject.backend
  - Statement: Input datastructure from front-end, superclass is Statement
  - Value: Runtime value, output of the back-end, superclass is RuntimeValue
  - Definition: Identifier value and general value binding
  - Transformer: Syntax tree transformer, for substituting one syntax tree into
    another (like C macros do string substitutions, but these are hygienic).
  - Macro: Identifier value and transformer binding
  - IdentifierFactory: Ensures identifiers with the same name are only created once

* Comment keywords
  - FIXME: shortcomings that need to be fixed before release
  - TODO: things that would be nice
  - XXX Speed: speed improvements that should be fairly easy
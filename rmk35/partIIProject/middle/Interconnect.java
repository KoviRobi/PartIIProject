public class Interconnect
{ static Environment InitialEnvironment;
  static Binding LambdaBinding;
  static Binding LetSyntaxBinding;
  static
  { // ToDo: initialize Bindings
  }

  public Statement ASTToStatement(AST syntax)
  { ASTVisitor visitor = new ASTConvertVisitor(InitialEnvironment);
    return syntax.accept(visitor);
  }
}
public class ASTConvertVisitor implements ASTVisitor
{ @Value Environment environment;
  Statement visit(SchemeList list)
  { // FIXME: what about non-identifier head? Solution: ApplicationVisitor
    applicate(listHead);
    Binding listHead = environment.lookUp(list.get(1)).accept();

    if (listHead == Interconnect.LambdaBinding)
    { // ToDo
    } else if (listHead == Interconnect.LetSyntaxBinding)
    {
    } else // Application
    { new ApplicationStatement
      // Call
      (list.parallelStream().map(ast -> ast.accept(this)));
    }
  }
}

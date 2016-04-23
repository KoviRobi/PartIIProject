package rmk35.partIIProject.runtime;

import rmk35.partIIProject.middle.ASTVisitor;

public class Trampoline
{ // Class defined at the bottom of the file
  private static final TrampolineVisitor trampolineVisitor = new TrampolineVisitor();

  public static RuntimeValue bounce(RuntimeValue object)
  {// TrampolineVisitor calls tail calls, or returns normal RuntimeValues
    // While we have tail calls to perform
    while (object != null && object instanceof CallValue)
    { object = object.accept(trampolineVisitor);
    }
    return object;
  }
}

class TrampolineVisitor extends ASTVisitor<RuntimeValue>
{ @Override
  public RuntimeValue visit(CallValue call) { return call.call(); }

  @Override
  public RuntimeValue visit(RuntimeValue value) { return value; }
}

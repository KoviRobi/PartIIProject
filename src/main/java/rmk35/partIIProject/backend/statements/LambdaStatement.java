package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;
import rmk35.partIIProject.backend.instructions.CommentPseudoInstruction;
import rmk35.partIIProject.backend.instructions.NewObjectInstruction;
import rmk35.partIIProject.backend.instructions.DupInstruction;

import java.util.Collection;
import java.util.TreeSet;
import java.util.List;

import lombok.ToString;

@ToString
public class LambdaStatement extends Statement
{ List<String> formals;
  List<IdentifierStatement> closureVariables;
  BeginStatement body;
  String comment;

  public LambdaStatement(List<String> formals, List<IdentifierStatement> closureVariables, BeginStatement body, String comment)
  { this.formals = formals;
    this.closureVariables = closureVariables;
    this.body = body;
    this.comment = comment;
  }

  public void generateOutput(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method)
  { method.addInstruction(new CommentPseudoInstruction("LambdaStatement, comment: " + comment));

    String innerClassName = mainClass.uniqueID() + "$Lambda"; // Using main class' unique ID as that way all files definitely have different names
    InnerClass innerClass = new InnerClass(innerClassName, closureVariables, formals.size(), mainClass, comment);
    // Implicit begin
    body.generateOutput(mainClass, innerClass, innerClass.getPrimaryMethod());
    mainClass.addInnerClass(innerClass);

    // Create class, need to use Deprecated API as we don't have this class compiled yet
    method.addInstruction(new NewObjectInstruction(innerClassName));
    method.addInstruction(new DupInstruction()); // For invokenonvirtual, need 'this' pointer
    innerClass.invokeConstructor(mainClass, outputClass, method);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> bodyFreeIdentifiers = body.getFreeIdentifiers();
     bodyFreeIdentifiers.removeAll(formals);
     return bodyFreeIdentifiers;
  }
}

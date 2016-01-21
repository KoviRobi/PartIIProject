package rmk35.partIIProject.backend.statements;

import rmk35.partIIProject.backend.InnerClass;
import rmk35.partIIProject.backend.OutputClass;
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
  Statement body;

  public LambdaStatement(List<String> formals, List<IdentifierStatement> closureVariables, Statement body)
  { this.formals = formals;
    this.closureVariables = closureVariables;
    this.body = body;
  }

  public void generateOutput(OutputClass output)
  { output.addToPrimaryMethod(new CommentPseudoInstruction("LambdaStatement"));

    String innerClassName = output.uniqueID() + "$Lambda";
    InnerClass innerClass = new InnerClass(innerClassName, closureVariables, output.getMainClass(), formals.size());
    body.generateOutput(innerClass);
    output.getMainClass().addInnerClass(innerClass);

    output.addToPrimaryMethod(new NewObjectInstruction(innerClassName)); // Create class
    output.addToPrimaryMethod(new DupInstruction()); // For invokenonvirtual, need 'this' pointer
    innerClass.invokeConstructor(output);
  }

  @Override
  public Collection<String> getFreeIdentifiers()
  { Collection<String> bodyFreeIdentifiers = body.getFreeIdentifiers();
     bodyFreeIdentifiers.removeAll(formals);
     return bodyFreeIdentifiers;
  }
}

package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.ApplicationStatement;

import java.util.List;
import java.util.ArrayList;

public abstract class VariableBinding implements Binding
{ public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { ASTVisitor<Statement> convertVisitor = new ASTConvertVisitor(environment, outputClass, mainClass);
    return new ApplicationStatement(this.toStatement(operator.getSourceInfo()),
      operands.accept(new ASTListMapVisitor<>(convertVisitor)));
  }
}

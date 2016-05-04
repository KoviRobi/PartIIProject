package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.ASTVisitor;
import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListMapVisitor;

import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.statements.Statement;
import rmk35.partIIProject.backend.statements.BeginStatement;

import java.util.List;
import java.util.ArrayList;

import lombok.ToString;

@ToString
public class LetSyntaxBinding extends SintacticBinding
{ @Override
  public Statement applicate(EnvironmentValue environment, OutputClass outputClass, MainClass mainClass, RuntimeValue operator, RuntimeValue operands)
  { ConsValue first = operands.accept(new ASTExpectConsVisitor());
    //  Copy environment for lexical effect
    EnvironmentValue letEnvironment = new EnvironmentValue(environment, /* mutable */ true);

    // XXX To think about: letrec-syntax in particular this and looking up a variable in the environment to see if it has changed

    ASTVisitor<Pair<String, SyntaxBinding>> syntaxSpecificationVisitor = new ASTSyntaxSpecificationVisitor(letEnvironment, mainClass);
    List<Pair<String, SyntaxBinding>> macros = first.getCar().accept(new ASTListMapVisitor<>(syntaxSpecificationVisitor));

    for (Pair<String, SyntaxBinding> macro : macros)
    { letEnvironment.addBinding(macro.getFirst(), macro.getSecond());
    }

    // Implicit begin (superset of the standard but useful)
    ASTVisitor<Statement> bodyVisitor = new ASTConvertVisitor(letEnvironment, outputClass, mainClass);
    List<Statement> body = first.getCdr().accept(new ASTListMapVisitor<>(bodyVisitor));
    if (body.isEmpty())
    { throw new SyntaxErrorException("Empty let-syntax body", operator.getSourceInfo());
    }
    return new BeginStatement(body);
  }
}

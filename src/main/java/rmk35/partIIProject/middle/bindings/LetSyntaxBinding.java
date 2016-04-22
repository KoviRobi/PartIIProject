package rmk35.partIIProject.middle.bindings;

import rmk35.partIIProject.InternalCompilerException;
import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.utility.Pair;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.EnvironmentValue;

import rmk35.partIIProject.middle.astExpectVisitor.ASTExpectConsVisitor;
import rmk35.partIIProject.middle.astExpectVisitor.ASTListFoldVisitor;
import rmk35.partIIProject.middle.ASTSyntaxSpecificationVisitor;
import rmk35.partIIProject.middle.ASTConvertVisitor;

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

    List<Pair<String, SyntaxBinding>> macros = first.getCar().accept(new ASTListFoldVisitor<>(new ArrayList<>(),
      (List<Pair<String, SyntaxBinding>> list, RuntimeValue ast) -> { list.add(ast.accept(new ASTSyntaxSpecificationVisitor(letEnvironment, mainClass))); return list; } ));

    for (Pair<String, SyntaxBinding> macro : macros)
    { letEnvironment.addBinding(macro.getFirst(), macro.getSecond());
    }

    // Implicit begin (superset of the standard but useful)
    List<Statement> body = first.getCdr().accept
      (new ASTListFoldVisitor<List<Statement>>(new ArrayList<>(),
        (list, ast) -> { list.add(ast.accept(new ASTConvertVisitor(letEnvironment, outputClass, mainClass))); return list; } ));
    if (body.isEmpty())
    { throw new SyntaxErrorException("Empty lambda body", operator.getSourceInfo());
    }
    return new BeginStatement(body);
  }
}

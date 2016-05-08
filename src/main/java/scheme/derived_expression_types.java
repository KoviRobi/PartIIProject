package scheme;

import rmk35.partIIProject.Compiler;

import rmk35.partIIProject.runtime.libraries.ReflectiveEnvironment;
import rmk35.partIIProject.runtime.libraries.SyntaxBindingCreator;

import rmk35.partIIProject.middle.bindings.Binding;

import java.util.Arrays;

public class derived_expression_types extends ReflectiveEnvironment
{ public derived_expression_types() { bind(); }

  // R7RS, Derived expression types, section 7.3
  public static Binding cond = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , Arrays.asList("else", "=>")
  , "(cond (else result1 result2 ...))"
  , "(begin result1 result2 ...)"
  , "(cond (test => result))"
  , "(let ((temp test))\n" +
    "   (if temp (result temp)))"
  , "(cond (test => result) clause1 clause2 ...)"
  , "(let ((temp test))\n" +
    "   (if temp\n" +
    "       (result temp)\n" +
    "       (cond clause1 clause2 ...)))"
  , "(cond (test))"
  , "test"
  , "(cond (test) clause1 clause2 ...)"
  , "(let ((temp test))\n" +
    "   (if temp\n" +
    "       temp\n" +
    "       (cond clause1 clause2 ...)))"
  , "(cond (test result1 result2 ...))"
  , "(if test (begin result1 result2 ...))"
  , "(cond (test result1 result2 ...)\n" +
    "       clause1 clause2 ...)"
  , "(if test\n" +
    "     (begin result1 result2 ...)\n" +
    "     (cond clause1 clause2 ...))");

  public static Binding cas$000065 = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , Arrays.asList("else", "=>")
  , "(case (key ...)\n" +
    "  clauses ...)"
  , "(let ((atom-key (key ...)))\n" +
    "  (case atom-key clauses ...))"
  , "(case key\n" +
    "  (else => result))"
  , "(result key)"
  , "(case key\n" +
    "  (else result1 result2 ...))"
  , "(begin result1 result2 ...)"
  , "(case key\n" +
    "  ((atoms ...) result1 result2 ...))"
  , "(if (memv key '(atoms ...))\n" +
    "    (begin result1 result2 ...))"
  , "(case key\n" +
    "  ((atoms ...) => result))"
  , "(if (memv key '(atoms ...))\n" +
    "    (result key))"
  , "(case key\n" +
    "  ((atoms ...) => result)\n" +
    "  clause clauses ...)"
  , "(if (memv key '(atoms ...))\n" +
    "    (result key)\n" +
    "    (case key clause clauses ...))"
  , "(case key\n" +
    "  ((atoms ...) result1 result2 ...)\n" +
    "  clause clauses ...)"
  , "(if (memv key '(atoms ...))\n" +
    "    (begin result1 result2 ...)\n" +
    "    (case key clause clauses ...))");

  public static Binding d$00006F /* do */ = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , "(do ((var init step ...) ...)\n" +
    "     (test expr ...)\n" +
    "     command ...)"
  , "(letrec\n" +
    "   ((loop\n" +
    "     (lambda (var ...)\n" +
    "       (if test\n" +
    "           (begin\n" +
    "             (if #f #f)\n" +
    "             expr ...)\n" +
    "           (begin\n" +
    "             command\n" +
    "             ...\n" +
    "             (loop (do \"step\" var step ...)\n" +
    "                   ...))))))\n" +
    "   (loop init ...))"
  , "(do \"step\" x)"
  , "x"
  , "(do \"step\" x y)"
  , "y");

  public static Binding and = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , "(and)", "#true"
  , "(and test)", "test"
  , "(and test1 test2 ...)"
  , "(if test1 (and test2 ...) #true)");

  public static Binding or = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , "(or)", "#false"
  , "(or test)", "test"
  , "(or test1 test2 ...)"
  , "(let ((x test1))\n" +
    "  (if x x (or test2 ...)))");

  public static Binding when = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , "(when test result1 result2 ...)"
  , " (if test\n" +
    "     (begin result1 result2 ...))");

  public static Binding unless = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , "(unless test result1 result2 ...)"
  , "(if (not test)\n" +
    "    (begin result1 result2 ...))");

  public static Binding let$00002A /* let* */ = SyntaxBindingCreator.create
  (Compiler.simpleBaseEnvironment
  , "(let* () body1 body2 ...)"
  , " (let () body1 body2 ...)"
  , "(let* ((name1 val1) (name2 val2) ...)\n" +
    "   body1 body2 ...)"
  , " (let ((name1 val1))\n" +
    "   (let* ((name2 val2) ...)\n" +
    "     body1 body2 ...))");

/* TODO: letrec, letrec*, (these might be better as non-macros?), cond-expand */
}

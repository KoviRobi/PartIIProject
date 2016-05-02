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
}
/* Do more:
(define-syntax \ide{case}
  (syntax-rules (else =>)
    ((case (key ...)
       clauses ...)
     (let ((atom-key (key ...)))
       (case atom-key clauses ...)))
    ((case key
       (else => result))
     (result key))
    ((case key
       (else result1 result2 ...))
     (begin result1 result2 ...))
    ((case key
       ((atoms ...) result1 result2 ...))
     (if (memv key '(atoms ...))
         (begin result1 result2 ...)))
    ((case key
       ((atoms ...) => result))
     (if (memv key '(atoms ...))
         (result key)))
    ((case key
       ((atoms ...) => result)
       clause clauses ...)
     (if (memv key '(atoms ...))
         (result key)
         (case key clause clauses ...)))
    ((case key
       ((atoms ...) result1 result2 ...)
       clause clauses ...)
     (if (memv key '(atoms ...))
         (begin result1 result2 ...)
         (case key clause clauses ...)))))
(define-syntax \ide{and}
  (syntax-rules ()
    ((and) \sharpfoo{t})
    ((and test) test)
    ((and test1 test2 ...)
     (if test1 (and test2 ...) \sharpfoo{f}))))
(define-syntax \ide{or}
  (syntax-rules ()
    ((or) \sharpfoo{f})
    ((or test) test)
    ((or test1 test2 ...)
     (let ((x test1))
       (if x x (or test2 ...))))))
(define-syntax \ide{when}
  (syntax-rules ()
    ((when test result1 result2 ...)
     (if test
         (begin result1 result2 ...)))))
(define-syntax \ide{unless}
  (syntax-rules ()
    ((unless test result1 result2 ...)
     (if (not test)
         (begin result1 result2 ...)))))
(define-syntax \ide{let*}
  (syntax-rules ()
    ((let* () body1 body2 ...)
     (let () body1 body2 ...))
    ((let* ((name1 val1) (name2 val2) ...)
       body1 body2 ...)
     (let ((name1 val1))
       (let* ((name2 val2) ...)
         body1 body2 ...)))))
(define-syntax \ide{do}
  (syntax-rules ()
    ((do ((var init step ...) ...)
         (test expr ...)
         command ...)
     (letrec
       ((loop
         (lambda (var ...)
           (if test
               (begin
                 (if \#f \#f)
                 expr ...)
               (begin
                 command
                 ...
                 (loop (do "step" var step ...)
                       ...))))))
       (loop init ...)))
    ((do "step" x)
     x)
    ((do "step" x y)
     y)))
* This is not exhaustive, do the rest too
*/

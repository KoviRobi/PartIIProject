package rmk35.partIIProject.runtime;

import rmk35.partIIProject.middle.AST;

import rmk35.partIIProject.backend.MainClass;
import rmk35.partIIProject.backend.OutputClass;
import rmk35.partIIProject.backend.ByteCodeMethod;

public interface RuntimeValue extends AST
{ boolean equal(RuntimeValue other);
  boolean eqv(RuntimeValue other);
  boolean eq(RuntimeValue other);
  void generateByteCode(MainClass mainClass, OutputClass outputClass, ByteCodeMethod method);
}
grammar SchemeFile;

@header {
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;

import rmk35.partIIProject.SyntaxErrorException;

import rmk35.partIIProject.runtime.RuntimeValue;
import rmk35.partIIProject.runtime.PrimitiveValue;
import rmk35.partIIProject.runtime.BooleanValue;
import rmk35.partIIProject.runtime.NumberValue;
import rmk35.partIIProject.runtime.CharacterValue;
import rmk35.partIIProject.runtime.StringValue;
import rmk35.partIIProject.runtime.IdentifierValue;
import rmk35.partIIProject.runtime.BytevectorValue;
import rmk35.partIIProject.runtime.ConsValue;
import rmk35.partIIProject.runtime.NullValue;
import rmk35.partIIProject.runtime.VectorValue;

import rmk35.partIIProject.frontend.SourceInfo;
}

import SchemeExternalRepresentation;

file[String filename] returns [List<RuntimeValue> data]
  @init {
   $data = new LinkedList<>();
  }
  :
  (datum[$filename] { if ($datum.expr != null) $data.add($datum.expr); })* EOF
  ;

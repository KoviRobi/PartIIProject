grammar SchemeFile;

@header {
package rmk35.partIIProject.frontend;

import java.util.List;
import java.util.LinkedList;

import rmk35.partIIProject.frontend.AST.SchemeBoolean;
import rmk35.partIIProject.frontend.AST.SchemeNumber;
import rmk35.partIIProject.frontend.AST.SchemeSimpleNumber;
import rmk35.partIIProject.frontend.AST.SchemeCharacter;
import rmk35.partIIProject.frontend.AST.SchemeString;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeBytevector;

import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeVector;
}

import SchemeExternalRepresentation;

file[String filename] returns [List<Object> x]
  @init {
   $x = new LinkedList();
  }
  :
  (datum[$filename] {$x.add($datum.expr);})* EOF
  ;

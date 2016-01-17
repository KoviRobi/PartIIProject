grammar SchemeFile;

@header {
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;

import rmk35.partIIProject.frontend.AST.SchemeBoolean;
import rmk35.partIIProject.frontend.AST.SchemeNumber;
import rmk35.partIIProject.frontend.AST.SchemeCharacter;
import rmk35.partIIProject.frontend.AST.SchemeString;
import rmk35.partIIProject.frontend.AST.SchemeIdentifier;
import rmk35.partIIProject.frontend.AST.SchemeBytevector;
import rmk35.partIIProject.frontend.AST.SchemeList;
import rmk35.partIIProject.frontend.AST.SchemeVector;
import rmk35.partIIProject.frontend.AST.SchemeAbbreviation;
import rmk35.partIIProject.frontend.AST.SchemeLabelledData;
import rmk35.partIIProject.frontend.AST.SchemeLabelReference;

import rmk35.partIIProject.middle.AST;
}

import SchemeExternalRepresentation;

file[String filename] returns [List<AST> data]
  @init {
   $data = new LinkedList<>();
  }
  :
  (datum[$filename] {$data.add($datum.expr);})* EOF
  ;

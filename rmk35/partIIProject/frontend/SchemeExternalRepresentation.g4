// See r7rs 7.1.2

// See SchemeFile.g4 for @header declarations

grammar SchemeExternalRepresentation;

import SchemeLexicalStructure;

datum[String filename] returns [Object expr]
  : simpleDatum[$filename] { $expr = $simpleDatum.expr; }
  | compoundDatum[$filename] { $expr = $compoundDatum.expr; }
	| label '=' datum[$filename]
  | label '#'
	;

simpleDatum[String filename] returns [Object expr]
  : bool[$filename] { $expr = $bool.expr; }
  | number[$filename] { $expr = $number.expr; }
	| character[$filename] { $expr = $character.expr; }
  | string[$filename] { $expr = $string.expr; }
  | symbol[$filename] { $expr = $symbol.expr; }
  | bytevector[$filename] { $expr = $bytevector.expr; }
	;

bool[String filename] returns [SchemeBoolean expr] : Boolean { $expr = new SchemeBoolean($Boolean.text, $filename, $Boolean.line, $Boolean.pos); } ;
number[String filename] returns [SchemeNumber expr]: Number { $expr = new SchemeSimpleNumber($Number.text, $filename, $Number.line, $Number.pos); } ;
character[String filename] returns [SchemeCharacter expr] : Character { $expr = new SchemeCharacter($Character.text, $filename, $Character.line, $Character.pos); } ;
string[String filename] returns [SchemeString expr]: String { $expr = new SchemeString($String.text, "Filename unknown", $String.line, $String.pos); } ;
symbol[String filename] returns [SchemeIdentifier expr] : Identifier { $expr = new SchemeIdentifier($Identifier.text, $filename, $Identifier.line, $Identifier.pos); } ;
bytevector[String filename] returns [SchemeBytevector expr] : Bytevector { $expr = new SchemeBytevector($Bytevector.text, $filename, $Bytevector.line, $Bytevector.pos); } ;

compoundDatum[String filename] returns [Object expr]
  : list[$filename] { $expr = $list.expr; }
  | vector[$filename] { $expr = $vector.expr; }
  | abbreviation[$filename] { $expr = $abbreviation.expr; }
  ;

list[String filename]
  returns [SchemeList expr]
  locals [List acc]
  @init {
   $acc = new LinkedList();
  }
  @after {
   $expr = new SchemeList($acc, $filename, -1, -1);
  }
  : '(' (datum[$filename] { $acc.add($datum.expr); } )* ')'
  | '(' (datum[$filename] { $acc.add($datum.expr); } )+ '.' datum[$filename] { $acc.add($datum.expr); } ')'
  ;

vector[String filename]
  returns [SchemeVector expr]
  locals [List acc]
  @init {
   $acc = new ArrayList();
  }
  @after {
   $expr = new SchemeVector($acc.toArray(), $filename, -1, -1);
  }
  : '#(' (datum[$filename] { $acc.add($datum.expr); } )* ')'
  ;

abbreviation[String filename]
  returns [Object expr]
  : abbrevPrefix datum[$filename] ;

abbrevPrefix : '\'' | '`' | ',' | ',@' | '#;' ;

label : '#' UInteger ;

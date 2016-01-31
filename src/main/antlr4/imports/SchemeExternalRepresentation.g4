// See r7rs 7.1.2

// See SchemeFile.g4 for @header declarations

grammar SchemeExternalRepresentation;

import SchemeLexicalStructure;

datum[String filename] returns [AST expr]
  : simpleDatum[$filename] { $expr = $simpleDatum.expr; }
  | compoundDatum[$filename] { $expr = $compoundDatum.expr; }
  | LabelCreate child=datum[$filename] { $expr = new SchemeLabelledData($LabelCreate.text, $child.expr, $filename, $LabelCreate.line, $LabelCreate.pos); }
  | LabelReference { $expr = new SchemeLabelReference($LabelReference.text); } // FIXME: Error from here.
  | '#;' datum[$filename] { $expr = null; }
  ;

simpleDatum[String filename] returns [AST expr]
  : bool[$filename] { $expr = $bool.expr; }
  | number[$filename] { $expr = $number.expr; }
  | character[$filename] { $expr = $character.expr; }
  | string[$filename] { $expr = $string.expr; }
  | symbol[$filename] { $expr = $symbol.expr; }
  | bytevector[$filename] { $expr = $bytevector.expr; }
  ;

bool[String filename] returns [SchemeBoolean expr] : Boolean { $expr = new SchemeBoolean($Boolean.text, $filename, $Boolean.line, $Boolean.pos); } ;
number[String filename] returns [SchemeNumber expr]: Number { $expr = new SchemeNumber($Number.text, $filename, $Number.line, $Number.pos); } ;
character[String filename] returns [SchemeCharacter expr] : Character { $expr = new SchemeCharacter($Character.text, $filename, $Character.line, $Character.pos); } ;
string[String filename] returns [SchemeString expr]: String { $expr = new SchemeString($String.text, "Filename unknown", $String.line, $String.pos); } ;
symbol[String filename] returns [SchemeIdentifier expr] : Identifier { $expr = new SchemeIdentifier($Identifier.text, $filename, $Identifier.line, $Identifier.pos); } ;

bytevector[String filename]
  returns [SchemeBytevector expr]
  locals [Stack<String> acc, long line, long col]
  @init {
   $acc = new Stack<>();
  }
  @after {
   $expr = new SchemeBytevector($acc.toArray(), $filename, $line, $col);
  }
  : BytevectorOpen { $line = $BytevectorOpen.line; $col = $BytevectorOpen.pos; }
    (UInteger { $acc.push($UInteger.text); } )*
    ')'
  ;

compoundDatum[String filename] returns [AST expr]
  : list[$filename] { $expr = $list.expr; }
  | vector[$filename] { $expr = $vector.expr; }
  | abbreviation[$filename] { $expr = $abbreviation.expr; }
  ;

list[String filename]
  returns [AST expr] // For example, '( . a) is just 'a  NOTE: this is a bug in Larceny ;)
  locals [Stack<SchemeCons> acc, AST end]
  @init {
   $acc = new  Stack<>();
  }
  @after {
   while (!$acc.empty())
   { // Reverse iterate, append lists
     SchemeCons tail = $acc.pop();
     tail.setCdr($end);
     $end = tail;
   }
   $expr = $end;
  }
  : '(' (datum[$filename] { if ($datum.expr != null) $acc.push(new SchemeCons($datum.expr, null, $filename, $datum.expr.line(), $datum.expr.character())); } )*  closingParen=')' { $end = new SchemeNil($filename, $closingParen.line, $closingParen.pos); }
  | openingParen='(' (datum[$filename] { if ($datum.expr != null) $acc.push(new SchemeCons($datum.expr, null, $filename, $datum.expr.line(), $datum.expr.character())); } )+ '.' datum[$filename] { if ($datum.expr != null) $end = $datum.expr; else throw new SyntaxErrorException("Improper improper list, no end datum", $filename, $openingParen.line, $openingParen.pos); } ')'
  ;

vector[String filename]
  returns [SchemeVector expr]
  locals [List<AST> acc]
  @init {
   $acc = new ArrayList<>();
  }
  @after {
   $expr = new SchemeVector($acc.toArray(), $filename, -1, -1);
  }
  : '#(' (datum[$filename] { if ($datum.expr != null) $acc.add($datum.expr); } )* ')'
  ;

abbreviation[String filename]
  returns [AST expr]
  : AbbrevPrefix datum[$filename] 
  { $expr = new SchemeAbbreviation($AbbrevPrefix.text, $datum.expr, $filename, $AbbrevPrefix.line, $AbbrevPrefix.pos); }; //FIXME:

AbbrevPrefix : '\'' | '`' | ',' | ',@' ;

LabelCreate : '#' UInteger '=' ;
LabelReference : '#' UInteger '#' ;

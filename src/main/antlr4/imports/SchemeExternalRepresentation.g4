// See r7rs 7.1.2

// See SchemeFile.g4 for @header declarations

grammar SchemeExternalRepresentation;

import SchemeLexicalStructure;

datum[String filename] returns [RuntimeValue expr]
  : simpleDatum[$filename] { $expr = $simpleDatum.expr; }
  | compoundDatum[$filename] { $expr = $compoundDatum.expr; }
//  | LabelCreate child=datum[$filename] { throw new UnsupportedOperationException("Labels are not yet supported"); }
//  | LabelReference { throw new UnsupportedOperationException("Labels are not yet supported"); }
  | '#;' datum[$filename] { $expr = null; }
  ;

simpleDatum[String filename] returns [RuntimeValue expr]
  : bool[$filename] { $expr = $bool.expr; }
  | number[$filename] { $expr = $number.expr; }
  | character[$filename] { $expr = $character.expr; }
  | string[$filename] { $expr = $string.expr; }
  | symbol[$filename] { $expr = $symbol.expr; }
  | bytevector[$filename] { $expr = $bytevector.expr; }
  ;

bool[String filename] returns [BooleanValue expr] : Boolean { $expr = new BooleanValue($Boolean.text, new SourceInfo($filename, $Boolean.line, $Boolean.pos)); } ;
number[String filename] returns [NumberValue expr]: Number { $expr = (NumberValue) NumberValue.parse($Number.text, 10, new SourceInfo($filename, $Number.line, $Number.pos)); } ;
character[String filename] returns [CharacterValue expr] : Character { $expr = new CharacterValue($Character.text, new SourceInfo($filename, $Character.line, $Character.pos)); } ;
string[String filename] returns [StringValue expr]: String { $expr = new StringValue(StringValue.decodeParsedString($String.text), new SourceInfo($filename, $String.line, $String.pos)); } ;
symbol[String filename] returns [IdentifierValue expr] : Identifier { $expr = new IdentifierValue($Identifier.text, new SourceInfo($filename, $Identifier.line, $Identifier.pos)); } ;

bytevector[String filename]
  returns [BytevectorValue expr]
  locals [List<Byte> acc, SourceInfo sourceInfo]
  @init {
   $acc = new LinkedList<>();
  }
  @after {
   $expr = new BytevectorValue($acc, $sourceInfo);
  }
  : open=BytevectorOpen { $sourceInfo = new SourceInfo($filename, $open.line, $open.pos); }
    (UInteger { $acc.add(Byte.decode($UInteger.text)); } )*
    ')'
  ;

compoundDatum[String filename] returns [RuntimeValue expr]
  : list[$filename] { $expr = $list.expr; }
  | vector[$filename] { $expr = $vector.expr; }
  | abbreviation[$filename] { $expr = $abbreviation.expr; }
  ;

list[String filename]
  returns [RuntimeValue expr] // For example, '( . a) is just 'a  NOTE: this is a bug in Larceny ;)
  locals [Deque<ConsValue> acc, RuntimeValue end]
  @init {
   $acc = new  LinkedList<>();
  }
  @after {
   while (!$acc.isEmpty())
   { // Reverse iterate, append lists
     ConsValue tail = $acc.removeFirst();
     tail.setCdr($end);
     $end = tail;
   }
   $expr = $end;
  }
  : '(' (datum[$filename] { if ($datum.expr != null) $acc.addFirst(new ConsValue($datum.expr, null, $datum.expr.getSourceInfo())); } )*  closingParen=')' { $end = new NullValue(new SourceInfo($filename, $closingParen.line, $closingParen.pos)); }
  | openingParen='(' (datum[$filename] { if ($datum.expr != null) $acc.addFirst(new ConsValue($datum.expr, null, $datum.expr.getSourceInfo())); } )+ '.' datum[$filename] { if ($datum.expr != null) $end = $datum.expr; else throw new SyntaxErrorException("Improper improper list, no end datum", new SourceInfo($filename, $openingParen.line, $openingParen.pos)); } ')'
  ;

vector[String filename]
  returns [VectorValue expr]
  locals [List<RuntimeValue> acc, SourceInfo sourceInfo]
  @init {
   $acc = new ArrayList<>();
  }
  @after {
   $expr = new VectorValue($acc, $sourceInfo);
  }
  : open='#(' (datum[$filename] { if ($datum.expr != null) $acc.add($datum.expr); } )* ')' { $sourceInfo = new SourceInfo($filename, $open.line, $open.pos); }
  ;

abbreviation[String filename]
  returns [RuntimeValue expr]
  : AbbrevPrefix datum[$filename]
  { $expr = new ConsValue(new IdentifierValue
  (IdentifierValue.decodeAbbreviationPrefix($AbbrevPrefix.text), new SourceInfo($filename, $AbbrevPrefix.line, $AbbrevPrefix.pos)),
    new ConsValue($datum.expr, new NullValue($datum.expr.getSourceInfo()), $datum.expr.getSourceInfo()), $datum.expr.getSourceInfo()); };

AbbrevPrefix : '\'' | '`' | ',' | ',@' ;

//LabelCreate : '#' UInteger '=' ;
//LabelReference : '#' UInteger '#' ;

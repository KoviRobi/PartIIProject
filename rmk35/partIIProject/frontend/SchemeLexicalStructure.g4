// See r7rs 7.1.1
lexer grammar SchemeLexicalStructure;

//  Token : Identifier | Boolean | Number
//  	| Character | String
//  	| '(' | ')' | '#(' | '#u8' | '\'' | ',' | ',@' | '.'
//  	;
//  
//  Delimiter : Whitespace | VerticalLine
//  	| '(' | ')' | '"' | ';'
//  	;

fragment IntralineWhitespace : ' ' | '\t' ;

fragment Whitespace : IntralineWhitespace | LineEnding ;

fragment VerticalLine : '|' ;

fragment LineEnding : '\n' | '\r\n' | '\r' ;

fragment Comment : ';'  (~[\n\r])*
	// Nested comment in the spec,
	// but they cause grammar ambiguities
	| MultilineComment
	// | '#;' // FIXME: Ignore next datum
	;

fragment MultilineComment : '#|' .*? '|#' ; // Non-greedy any character.

fragment Directive : '#!fold-case' | '#!no-fold-case' ;

fragment Atmosphere : Whitespace | Comment | Directive;

IntertokenSpace : Atmosphere+ -> skip;

Identifier : Initial Subsequent*
	| VerticalLine SymbolElement* VerticalLine
	| PeculiarIdentifier
	;

fragment Initial : Letter | SpecialInitial ;

fragment Letter : [a-zA-Z] ;

fragment SpecialInitial : [!$%&*/:<=>?^_~] ;

fragment Subsequent : Initial | Digit
	| SpecialSubsequent
	;

fragment Digit : [0-9] ;

fragment HexDigit : Digit | [a-f];

fragment ExplicitSign : [+-] ;

fragment SpecialSubsequent : ExplicitSign | [.@] ;

fragment InlineHexEscape : '\\x' HexScalarValue ';' ;

fragment HexScalarValue : HexDigit+ ;

fragment MnemonicEscape : '\\' [abtnr] ;

// FIXME: +i, -i, and infnan are not pecuiliar identifiers
fragment PeculiarIdentifier : ExplicitSign
	| ExplicitSign SignSubsequent Subsequent *
	| ExplicitSign '.' DotSubsequent Subsequent*
	| '.' DotSubsequent Subsequent*
	;

fragment DotSubsequent : SignSubsequent | '.' ;

fragment SignSubsequent : Initial | ExplicitSign | '@' ;

fragment SymbolElement : ~[\\|]
	| InlineHexEscape
	| MnemonicEscape
	| '\\|'
	;

Boolean : '#' [tf] | '#true' | '#false' ;

Character : '#\\' .
	| '#\\' CharacterName
	| '#\\x' HexScalarValue
	;

fragment CharacterName : 'alarm' | 'backspace' | 'delete'
	| 'escape' | 'newline' | 'null' | 'return' | 'space' | 'tab'
	;

String : '"' StringElement* '"' ;

fragment StringElement : ~[\\"]
	| MnemonicEscape | '\\"' | '\\'
	| '\\' IntralineWhitespace* LineEnding IntralineWhitespace*
	| InlineHexEscape
	;

Bytevector : '#u8(' Byte* ')' ;

fragment Byte : Digit+ ; // FIXME: only between 0 and 255

Number : Num ; // FIXME: only accept proper basis

fragment Num : Prefix Complex ;

fragment Complex : Real | Real '@' Real
	| Real '+' UReal 'i' | Real '-' UReal 'i'
	| Real '+i' | Real '-i' | Real InfNaN 'i'
	| '+' UReal 'i' | '-' UReal 'i'
	| InfNaN 'i' | '+i' | '-i'
	;

fragment Real : Sign UReal
	| InfNaN
	;

fragment UReal : UInteger
	| UInteger '/' UInteger
	| Decimal
	;

fragment Decimal : UInteger Suffix
	| '.' HexDigit+ Suffix
	| HexDigit+ '.' HexDigit* Suffix
	;

UInteger : HexDigit+ ;

fragment Prefix : Radix Exactness ;

fragment InfNaN : '+inf.0' | '-inf.0' | '+nan.0' | '-nan.0' ;

fragment Suffix : /* \epsilon */
	| ExponentMarker Sign HexDigit+
	;

fragment ExponentMarker : 'e' ;

fragment Sign : /* \epsilon */ | '+' | '-' ;

fragment Exactness : /* \epsilon */ | '#i' | '#e' ;

fragment Radix : '#b' | '#o' | /* \epsilon */ | '#d' | '#x' ;

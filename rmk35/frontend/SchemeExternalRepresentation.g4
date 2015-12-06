// See r7rs 7.1.2
grammar SchemeExternalRepresentation;

import SchemeLexicalStructure;

datum : simpleDatum | compoundDatum
	| label '=' datum | label '#'
	| IntertokenSpace
	;

simpleDatum : bool | number
	| character | string | symbol | bytevector
	;

bool : Boolean ;
number : Number ;
character : Character ;
string : String ;
symbol : Identifier ;
bytevector : Bytevector ;

compoundDatum : list | vector | abbreviation ;

list : '(' datum* ')' | '(' datum+ '.' datum ')' ;

abbreviation : abbrevPrefix datum ;

abbrevPrefix : '\'' | '`' | ',' | ',@' | '#;' ;

vector : '#(' datum* ')' ;

label : '#' UInteger ;

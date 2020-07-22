grammar Level;

import UnicodeClasses;

@header {
    package xyz.qwewqa.trebla.grammar.generated;
}

LineComment
    : '//' ~[\u000A\u000D]*
      -> channel(HIDDEN)
    ;

WS
    : ([\u0020\u0009\u000C] | NL)
      -> skip
    ;

NL: '\u000A' | '\u000D' '\u000A' ;

Identifier
    : (Letter | '_') (Letter | '_' | DecDigit)*
    | '`' ~('`')+ '`'
    ;

FloatLiteral
    : DecDigit+ '.' DecDigit+
    ;

IntegerLiteral
    : DecDigit+
    ;

fragment DecDigit
    : UNICODE_CLASS_ND
    ;

BooleanLiteral
    : 'true'
    | 'false'
    ;

fragment Letter
    : UNICODE_CLASS_LL
    | UNICODE_CLASS_LM
    | UNICODE_CLASS_LO
    | UNICODE_CLASS_LT
    | UNICODE_CLASS_LU
    | UNICODE_CLASS_NL
    ;

levelFile: entity* EOF ;

entity: (entityName '=')? archetypeName NL* '(' NL* (parameter ( NL* ',' NL* parameter)* ','?)? NL* ')' NL* ;

entityName: Identifier ;

archetypeName: Identifier ;

parameter: Identifier '=' (BooleanLiteral | IntegerLiteral | FloatLiteral | entityName) ;
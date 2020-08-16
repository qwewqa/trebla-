/**
 * This file was adapted and modified from the respective file in the https://github.com/shadrina/kotlin-grammar-antlr4 repository.
 * This file was licensed under the Apache 2.0 license.
 * A copy of the license can be found in the Apache-2.0.txt file in this directory.
*/

parser grammar TreblaParser;

options { tokenVocab = TreblaLexer; }

@parser::header {
    package xyz.qwewqa.trebla.grammar.generated;
}

treblaFile
    : NL* packageHeader?
    NL* importList
    anysemi* (topLevelObject (semi+ topLevelObject?)*)? EOF
    ;

packageHeader
    : PACKAGE identifier semi
    ;

importList
    : importHeader*
    ;

importHeader
    : IMPORT identifier (DOT MULT)? semi
    ;

topLevelObject
    : functionDeclaration
    | propertyDeclaration
    | letDeclaration
    | structDeclaration
    | scriptDeclaration
    | archetypeDeclaration
    ;

functionDeclaration
    : modifierList FUNC
    (NL* functionReceiver NL* DOT)?
    NL* simpleIdentifier
    NL* functionValueParameters
    (NL* COLON NL* type)?
    NL* functionBody
    ;

functionReceiver
    : LPAREN simpleIdentifier COLON type RPAREN
    | type
    ;

functionValueParameters
    : LPAREN (parameter (COMMA parameter)* COMMA?)? RPAREN
    ;

functionBody
    : block
    | ASSIGNMENT NL* expression
    ;

propertyDeclaration
    : modifierList VAR
    simpleIdentifier (COLON type)?
    (NL* (ASSIGNMENT) NL* expression)?
    ;

structDeclaration
    : modifierList STRUCT NL* simpleIdentifier
    structFields
    ;

structFields
    :  (LPAREN (structField (COMMA structField)* COMMA?)? RPAREN)?
    ;

structField
    : parameter
    ;

scriptDeclaration
    : SCRIPT NL* simpleIdentifier
    NL* scriptBody
    ;

scriptBody
    : LCURL NL* scriptMemberDeclaration* NL* RCURL
    ;

scriptMemberDeclaration
    : (functionDeclaration
    | propertyDeclaration
    | letDeclaration
    | structDeclaration
    | callbackDeclaration
    | initBlock) anysemi*
    ;

archetypeDeclaration
    : NOTE? ARCHETYPE NL* archetypeName NL*
    ARROW NL* simpleIdentifier
    archetypeDefaults
    ;

archetypeName
    : simpleIdentifier
    ;

archetypeDefaults
    : LPAREN (archetypeDefault (COMMA archetypeDefault)*)? RPAREN
    ;

archetypeDefault
    : simpleIdentifier ASSIGNMENT expression
    ;

callbackDeclaration
    : CALLBACK
    (AT callbackOrder)?
    NL* simpleIdentifier
    NL* functionBody
    ;

callbackOrder
    : expression
    ;

letDeclaration
    : modifierList LET
    simpleIdentifier (COLON type)?
    NL* (ASSIGNMENT) NL* expression
    ;

initBlock
    : INIT NL* block
    ;

parameter
    : simpleIdentifier (COLON type)? (ASSIGNMENT expression)?
    ;

type
    : prefixUnaryExpression
    ;

parenthesizedType
    : LPAREN type RPAREN
    ;

simpleUserType
    : simpleIdentifier
    ;

block
    : LCURL anysemi* (statement (anysemi+ statement?)*)? RCURL
    ;

statement
    : expression
    | declaration
    ;

declaration
    : functionDeclaration
    | propertyDeclaration
    | letDeclaration
    | structDeclaration
    ;

expression
    : disjunction (assignmentOperator disjunction)*
    ;

disjunction
    : conjunction (NL* DISJ NL* conjunction)*
    ;

conjunction
    : equalityComparison (NL* CONJ NL* equalityComparison)*
    ;

equalityComparison
    : comparison (equalityOperation NL* comparison)*
    ;

comparison
    : infixFunctionCall (comparisonOperator NL* infixFunctionCall)?
    ;

infixFunctionCall
    : additiveExpression (simpleIdentifier NL* additiveExpression)*
    ;

additiveExpression
    : multiplicativeExpression (additiveOperator NL* multiplicativeExpression)*
    ;

multiplicativeExpression
    : exponentiationExpression (multiplicativeOperation NL* exponentiationExpression)*
    ;

exponentiationExpression
    : prefixUnaryExpression (POW NL* prefixUnaryExpression)*
    ;

prefixUnaryExpression
    : prefixUnaryOperation* postfixUnaryExpression
    ;

postfixUnaryExpression
    : atomicExpression postfixUnaryOperation*
    ;

atomicExpression
    : parenthesizedExpression
    | literalConstant
    | conditionalExpression
    | loopExpression
    | simpleIdentifier
    | lambda
    ;

parenthesizedExpression
    : LPAREN expression RPAREN
    ;

callSuffix
    : valueArguments
    ;

subscriptingSuffix
    : LSQUARE (valueArgument (COMMA valueArgument)* COMMA?)? RSQUARE
    ;

lambda
    : LCURL NL* (functionValueParameters NL* ARROW)? NL* anysemi* (statement (anysemi+ statement?)*)? RCURL
    ;

valueArguments
    : LPAREN (valueArgument (COMMA valueArgument)* COMMA?)? RPAREN lambda?
    | lambda
    ;

valueArgument
    : (simpleIdentifier ASSIGNMENT)? expression
    ;

conditionalExpression
    : ifExpression
    ;

ifExpression
    : CONST? IF NL* LPAREN expression RPAREN NL* controlStructureBody SEMICOLON?
    (NL* ELSE NL* controlStructureBody)?
    ;

loopExpression
    : whileExpression
    | forExpression
    ;


whileExpression
    : WHILE NL* LPAREN expression RPAREN NL* controlStructureBody
    ;

forExpression
    : FOR NL* LPAREN forInitializer? SEMICOLON forCondition? SEMICOLON forAfterthought? RPAREN NL* controlStructureBody
    ;

forInitializer
    : statement
    ;

forCondition
    : expression
    ;

forAfterthought
    : expression
    ;

controlStructureBody
    : block
    | expression
    ;

modifierList
    : modifier*
    ;

modifier
    : (PUBLIC
    | PRIVATE
    | SHARED
    | STATIC
    | OPERATOR
    | INIT
    | SPAWN
    | DATA
    | LEVEL
    | TEMP
    | RAW
    | CONST
    | INFIX) NL*
    ;

identifier
    : simpleIdentifier (NL* DOT simpleIdentifier)*
    ;

simpleIdentifier
    : Identifier
    | PUBLIC
    | PRIVATE
    | INTERNAL
    | PROTECTED
    | SHARED
    | STATIC
    | OPERATOR
    | INLINE
    | INIT
    | SPAWN
    | DATA
    | LEVEL
    | RAW
    | PACKAGE
    | TEMP
    | ARCHETYPE
    | NOTE
    | SETTER
    | GETTER
    ;

literalConstant
    : BooleanLiteral
    | IntegerLiteral
    | FloatLiteral
    ;

additiveOperator
    : ADD | SUB
    ;

multiplicativeOperation
    : MULT
    | DIV
    | MOD
    ;

prefixUnaryOperation
    : INCR
    | DECR
    | ADD
    | SUB
    | EXCL
    | MULT
    ;

postfixUnaryOperation
    : INCR | DECR | EXCL EXCL
    | callSuffix
    | subscriptingSuffix
    | NL* memberAccessOperator simpleIdentifier
    ;

memberAccessOperator
    : DOT
    ;

assignmentOperator
    : ASSIGNMENT
    | ADD_ASSIGNMENT
    | SUB_ASSIGNMENT
    | MULT_ASSIGNMENT
    | DIV_ASSIGNMENT
    | MOD_ASSIGNMENT
    ;

equalityOperation
    : EXCL_EQ
    | EQEQ
    | EQEQEQ
    ;

comparisonOperator
    : LANGLE
    | RANGLE
    | LE
    | GE
    ;

// Takes up to one semicolon, but no more.
// If more than one semicolon is allowed, anysemi* will suffice.
semi: NL+ | NL* SEMICOLON NL*;

anysemi: NL | SEMICOLON;

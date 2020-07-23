# Quick Reference

This reference details the main features of Trebla and
assumes some existing programming experience as well of knowledge of the Sonolus [docs](https://github.com/NonSpicyBurrito/sonolus-wiki/wiki).

Note that this is a new language, and features may be added, removed, or changed frequently.

Bugs may be reported at [https://github.com/qwewqa/trebla/issues](https://github.com/qwewqa/trebla/issues).

## Setup
1. Go to [https://github.com/qwewqa/trebla/releases](https://github.com/qwewqa/trebla/releases) and download an image file
for the respective operating system.
2. Extract the archive and add the bin directory to the PATH if desired.

## Project Setup

### Commands
Initializing an empty project in the provided folder.
```shell
$ trebla init [PATH]
```
Building the engine in the provided project folder.
```shell
$ trebla build [PATH]
```
Building levels in the provided project folder.
```shell
$ trebla levels [PATH]
```
Building the engine and levels in the provided project folder.
```shell
$ trebla compileAll [PATH]
```

### Project structure
Within a project directory there is a treblaproj.yaml file.
This specifies the level, source, and output directories, as well as
buckets and options as defined by Sonolus.

The source directory includes Trebla files, with the .trb extension.
The contents of these files are detailed in the following sections.

The level directory includes Level files with the .tlv extension.
These files are formatted as follows.
```
// Specifies an entity with provided archetype and data values.
Archetype(argName1 = value1, argName2 = value2, ...)

// Specifies an entity with provided archetype and data values.
// Additionally, `name` may be used to refer to the index of this entity.
name = Archetype(argName1 = value1, argName2 = value2, ...)

// Overrides the previous value of `name`
name = Archetype(argName1 = value1, argName2 = value2, ...)
```

## General Concepts

### Scope
Declarations in Trebla all belong in some scope. This might be the package,
at the top level, or within a script or function. They are accessible from
the scope that they are contained in, or within scopes that are children of that scope.

No two declarations can share the same name (identifier) in the same scope.
Different declarations may have the same name if they have different signatures.
Three types of signatures exist.
- The default signature that most declarations have.
- The receiver signature that functions with receivers (methods) have
- The archetype receiver, only used by archetypes.

Importing a package brings all the declarations in that package into
the scope of the importing file. This does not impact other files
declared with the same package nor the contents of the package when imported by another file. 

### Values
Anything that is interacted with in code has a value.
This includes functions, struct declarations, literals, variables,
scripts, and more.

## Comments
Comments start with `//` and last until the end of a line, or
are wrapped in a starting `/*` and a closing `*/`.
```
// Line comment
struct Foo(x: Number) // End of line comment.

/*
Block Comments
Something
End of Comment
*/

func bar() { /* Another Comment */ }
```

## Variables
Variables, also known as properties, are declared using the `var` keyword.
Regardless of the type of variable, a variable will always take up actual memory
in the relevant [block](https://github.com/NonSpicyBurrito/sonolus-wiki/wiki/Blocks).

!!! note
    There are cases where an unnecessary allocation may be optimized out,
    but this case happens transparently and only for temporary variables. 

### Normal Variables
Normal variables are declared directly within scripts without any additional modifiers,
where they will be members of the script and will be accessible from callbacks
within the script.
They may also be declared as temporary variables within functions,
where they are limited to the local scope.
Temporary variables require an initializer expression and optionally take a type.
Script member variables optionally have an initializer expression and optionally take a type.
```
script Script {
    var a = 1
    var b: Boolean = true
}

func Run {
    var a = 1
    var b: Boolean = true
}
```

### Top level variables
Top level variables are declared with the `level` or `temp` modifiers.
They exist in the corresponding blocks in Sonolus, and may be accessed from different scripts.
Top level variables do not take an initializer and are required to have a type.

`level`
:   Located in level memory. Accessible from all callbacks and writable in `updateSequential` and `touch`.
Does not take an initializer and requires a type.

`temp`
:   Located in temporary memory. Accessible only from the `touch` callback.
Does not take an initializer and requires a type.

```
level var a: Number
temp var b: Boolean
```

### Script member variables variables
In addition to the normal variable, script member variables may be declared with the
`spawn`, `data`, or `shared` modifiers. Like top level variables,
they require a type and do not take an initializer.

`spawn`
:   Located in entity memory. May be initialized if spawned by another entity. 
Does not take an initializer and requires a type.

`data`
:   Located in entity data. Accessible from all callbacks, but not writable from any. 
Initialized via entities in a level and archetypes.
Does not take an initializer and requires a raw type (Boolean or Number).

`shared`
:   Located in entity shared memory. Accessible from all callbacks and writable in `updateSequential` and `touch`.
Does not take an initializer and requires a type.

```
script Script {
    spawn var a: Number
    data var b: Boolean
    shared var c: Point
}
```

## Let declarations
Let declarations bind their name to the resulting value from the expression to the right.
Like normal variables, they may optionally take a type.
Let declarations never take up any memory themselves, but their expression may itself
include the use of temporary variables.
Let declarations can be located in most places including the top level, scripts, functions, and callbacks.
```
let a = 1
let b: Number = a
```

### Differences between let and var
The following examples detail some of the differences between let and var.
Assume that the examples takes place in the body of a function or callback.
```
// a copies the value of 1.
var a = 1
// b has a value of 1.
let b = 1

// changes the value of varA to 2.
a = 2
// ERROR: b has a value of 1, an integer literal, which is immutable.
b = 2
```

```
// a copies the value of 1.
var a = 1
// b has the value of a.
let b = a
// c copies the value of a.
var c = a

// a and b both evaluate to 2. c still evaluates to 1.
a = 2
// a and b both evaluate to 3. c still evaluates to 1.
b = 3
// a and b both still evaluate to 3. c now evaluates to 4.
c = 4
```

## Control Flow
### If statement
Trebla has if-else statements for control flow.
```
if (condition) {
    // true branch
} else {
    // false branch
}

if (condition) {
    // true branch
}
```

## Function declarations
Functions are declared with the `func` keyword.
They may include calls to other functions, but calls to themselves,
directly or within another function, are not supported.
Functions can be declared in most places including the top level, scripts, other functions, and callbacks.
```
// A function that takes two Number parameters and returns a Number.
func sum(a: Number, b: Number): Number {
    a + b
}

// A function with an impicit Any return type and an expression body.
func sum2(a: Number, b: Number) = a + b

// A function which has two parameters of any type and returns any type.
func sum3(a, b): Any {
    b
}

// A function that returns no meaningful value.
fun run(): Unit {
    Clip(0).play()
}

// A function that returns no meaninful value with an omitted Unit return type.
fun run2() {
    Clip(0).play()
}

// A call to the sum function.
sum(1, 2)

// A call to the sum function with named parameters.
sum(b = 2, a = 1)
```

## Receiver Functions
Receiver functions, also known as methods, are a special type of function.
```
// Declares a function named translate with
// a receiver type of Point named this, as well as two normal number parameters.
func (this: Point).translate(x: Number, y: Number) {
    this.x += x
    this.y += y
}

func run() {
    var p = Point(1, 2)
    
    // Calls the method using the dot operator on a struct value.
    p.translate(5, 6)
    
    // Refernces the raw function using the dot on a struct declaration.
    let translatePoint = Point.translate
    // Calls the method like a normal function
    translatePoint(p, 1, 5)
}
```
The name of the receiver parameter may also be omitted, and will default to `this`
```
func Point.translate(x: Number, y: Number) {
    this.x += x
    this.y += y
}
```
Multiple functions may have the same name if their receiver type is different.
Only struct receivers are allowed.

### Function modifiers
The `infix` modifier allows functions to be called as infix functions.
```
infix func Boolean.and(other: Boolean) = Boolean(builtins.And(this, other))

func run() {
    var x = true and false // false
}
```
The `operator` modifier allows certain named functions to be used as modifiers.
```
operator func Number.plus(other: Number) = Number(builtins.Add(this, other))

func run() {
    var x = 1 + 2
}
```

### Boolean Operators
The boolean `||` (or) and `&&` (and) operators are not overloadable.
These are special operators, which short circuit.
Instead of overriding these operators, the reccomendation is
to use infix functions named `or` and `and`.
```
func play() {
    Clip(0).play()
}

func run() {
    // Play is never called in either of these cases.
    false && play()
    true || play()
}
```

### Operator Names
=== "Prefix"

    operator | function 
    --- | --- 
    + | unaryPlus
    - | unaryMinus
    ! | not
    ++ | preIncrement
    -- | preDecrement

=== "Postfix"

    operator | function 
    --- | --- 
    ++ | postIncrement
    -- | postDecrement

=== "Infix"

    operator | function 
    --- | --- 
    + | plus 
    - | minus 
    * | times 
    / | div 
    % | mod 
    ** | pow 
    += | plusAssign 
    -= | minusAssign 
    *= | timesAssign 
    /= | divAssign 
    %= | modAssign 
    **= | powAssign 
    == | equals 
    != | notEquals 
    > | greater 
    < | less 
    >= | greaterOr 
    <= | lessOr

## Struct declarations
Structs are collections of values. 
Struct declarations can be used as types, and they can
be called like functions.
```
// Declares a struct named Point, which contains two fields (values),
// which are named x and y
struct Point(x: Number, y: Number)

func run() {
    // Binds Point(1, 2) as the value of p1
    let p1 = Point(1, 2)
    // Copies the value of the point as p2
    var p2 = Point(x = 1, y = 5)
    // Changes the value of x in p2
    p2.x = 5
}
```
Structs usually contain other structs, but may sometimes contain other values.
This will limit where they can be used however.
```
struct Foo(num: Number, fun: Function)

func bar1() { /* does nothing and returns Unit */ }

func bar2() { }

func run() {
    var x = Foo(1, bar1)
    
    // Allowed. Numbers in variables are mutable.
    x.num = 2
    // Allowed, but does nothing. Functions are the same.
    x.fun = bar1 
    // ERROR: Functions are not mutable.
    x.fun = bar2 
    
    // Allowed.
    x = Foo(2, bar1)
    // ERROR: Functions are not mutable.
    x = Foo(2, bar2)
}

// ERROR: Function is not allocatable.
level var x: Foo
```

### Raw Structs
Raw structs have the `raw` modifier.
They have no declared fields, and are backed by either a literal value,
a call to a builtin Sonolus function, or a location in memory.
There are three raw structs in the standard library, and it 
generally is unnecessary to declare more.
A struct is allocatable if it only has fields that are typed as raw structs or other allocatable structs.
Variables that don't take an initializer must be annotated with an allocatable struct type.
```
// Default for integer and float literals.
raw struct Number

// Default for boolean literals.
raw struct Boolean

// Default in other cases
raw struct Raw
```

## Script declarations
Scripts are declared with the `script` keyword.
```
script Note {
    /* Script Members */
}
```

### Callbacks
Scripts contain callbacks, which correspond to callbacks in Sonolus.
```
script Note {
    callback updateParallel {
        /* Callback body */
    }
}
```
The initialize callback in Sonolus is not declared explicitly.
Instead, it is described inline with `init` blocks.
```
script Note {
    data var large: Boolean
    var width: Number
    init {
        if (large) {
            width = 0.8
        } else {
            width = 0.4
        }
    }
    var height = width / 2
}
```
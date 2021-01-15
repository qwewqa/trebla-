# Project Background

Trebla is a programming language targeting the script format used by levels in Sonolus,
a rhythm game (a game where the player generally completes actions according to the beat
of background music) where all behavior in a level is governed by user defined scripts.
For more information about Sonolus, check out the [website](https://sonolus.com/) or the
[wiki](https://github.com/NonSpicyBurrito/sonolus-wiki/wiki) for more information.

## Sonolus
Many design aspects of Trebla are best understood in the context of the exeuction
model used by Sonolus.

### Nodes
A level in Sonolus is simply a JSON file defining gameplay elements and their behaviors.
The behavior of a level is defined in its script which is a single array of "nodes".
A node can be either: (1) a builtin function which references other nodes by index as arguments, 
including special control flow functions (If, While, ...), mathematical functions (Add, Subtract, ...),
memory related functions (Get, Set, ...), and gameplay related functions(Draw, Play, ...),
or (2) a literal floating-point value. Gameplay elements (entities) can reference particular
nodes as callbacks to be called at different times, such as every frame or in response to player input.

Directly editing nodes by hand would be tedious, so scripting is generally done in another language
which compiles down to nodes. The provided [SIL](https://github.com/NonSpicyBurrito/sonolus-wiki/wiki/Sonolus-Intermediate-Language)
language is one such language, providing a basic interface on top of nodes. 
Trebla aims to provide a higher level language and a more familiar programming experience than SIL.

### Memory
Scripts can store or read data from a number of [blocks](https://github.com/NonSpicyBurrito/sonolus-wiki/wiki/Blocks),
arrays of numbers defined for various purposes. Blocks can either have a fixed size or vary in size based on
the number of entities defined in a level. Some blocks are specific to the entity calling a script while
others are shared globally.

Some languages for Sonolus such as the provided SIL language require users to manually specify and manage memory
indexes. Trebla abstracts this away, automatically assigning memory to user defined variables. 
This includes both statically assigning memory locations (blocks indexes) for global or entity related variables,
and potentially assigning multiple memory locations and reusing memory locations for temporary variables to
minimize memory used (this essentially uses the same algorithms as register allocation where each memory
location can be thought of as a register).

### Functions
Sonolus does not provide a call stack so user defined functions in Trebla do not use a call stack either.
While it would be technically possible to implement one, it would be inefficient and unnecessary to do so. As such, 
all functions in Trebla are inlined. This restricts the ability to have recursive functions (unless tail call optimized),
but in practice it is rarely an issue for intended uses. It also makes it possible to emulate some features usually
only found in dynamic languages such as function parameters without specified types, and provide a larger
variety of first-class values such as direct references to types themselves.

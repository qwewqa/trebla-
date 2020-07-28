# Trebla
A programming language and compiler for creating rhythm game engines on [Sonolus](https://sonolus.com).

## Quick Start
1. Download the corresponding image for your operating system from [releases](https://github.com/qwewqa/trebla/releases).
2. Extract the contents of the archive and add the bin folder to your PATH if desired.
3. Check the documentation at https://trebla.qwewqa.xyz for more information.

## Building from Source
Trebla was built and tested with JDK 11, but JDK 8 or higher should suffice.

1. Clone via git.
    ```
    git clone 
    ```
2. Run via gradle.
    ```
    gradlew run --args="[arguments]"
    ```
    See `gradlew tasks` for other options.
    
## GraalVM Native Image
Updating Native Image Configuration (in META-INF/native-image)
```
java -agentlib:native-image-agent=config-merge-dir=path/to/folder trebla.jar build [project]
```
Generating Native Image
```
native-image trebla.jar -H:+AddAllCharsets
```
    
### Documentation
Documentation is built using [mkdocs](https://www.mkdocs.org/)
and the [material theme](https://squidfunk.github.io/mkdocs-material/).
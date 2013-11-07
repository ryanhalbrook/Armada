Project Armada README
Revision: 001
*********************

Required Environment for Running Armada:
- Java Runtime version 1.60 or higher

Required Toolchain for Compiling Armada:
- Java Compiler version 1.60 or higher
- ANT recommended

HOW TO COMPILE AND RUN ARMADA
- WARNING: These directions are provided as is. This document has been carefully
    constructed to avoid any loss in user data. However, the Armada team assumes
    no liability for lost data or damaged equipment. Always use your own judgement when
    entering system commands.
- NOTE: Each set of directions below assumes you are in the same directory as this README.

+Compiling with ANT:
ant compile

+Running with ANT:
ant run

+Compiling using UNIX-based terminal session (bash):
cp -R image src/image
cp -R sound src/sound
cd src
javac *.java

+Running using UNIX-based terminal session (bash):
cd src (unless you just did the above compilation steps)
java Game

+Compiling using Windows Command Line*:
xcopy image src\image
xcopy sound src\sound
cd src
javac *.java

+Running using Windows Command Line*:
cd src (unless you just did the above compilation steps)
java Game

*Please note: these methods have not been tested.

Project Armada README
Revision: 001
*********************

SYSTEM REQUIREMENTS
===================

Required Environment for Running Armada:
- Java Runtime version 1.60 or higher

Required Toolchain for Compiling Armada:
- Java Compiler version 1.60 or higher
- ANT recommended

HOW TO COMPILE AND RUN ARMADA
=============================
- WARNING: These directions are provided as is. This document has been carefully
    constructed to avoid any loss in user data. However, the Armada team assumes
    no liability for lost data or damaged equipment. Always use your own judgement when
    entering system commands.
- NOTE: Each set of directions below assumes you are in the same directory as this README.

+Compiling with ANT:
ant compile

+Running with ANT:
ant run

+Creating Javadoc documentation with ANT:
ant javadoc

+Viewing Javadoc: Open the file at documentation/index.html

+Cleaning up using ANT:
ant clean

+Compiling using UNIX-based terminal session (bash):
cp -R image src/image
cp -R sound src/sound
cd src
javac *.java

+Running using UNIX-based terminal session (bash):
cd src (unless you just did the above compilation steps)
java Game

EXPLANATION OF FOLDERS
======================
src: contains the source files that must be compiled for the current state of the system
src_other: contains source files that may eventually make their way into src
image: contains the image resource files
sound: contains the sound resource files
build: this folder is created by the ant compile command and contains class files and a
       copy of the image and sound resources. 

RECOMMENDED DATA FILE STORAGE LOCATION
======================================
-User preference

SYSTEM USE INSTRUCTIONS
=======================
At the start of Armada, you have a home base planet, a small armada and a small amount of 
resources. Your opponent is presented with identical objects. In order to increase the 
size and strength of your armada, you will conquer neutral planets to claim their resources. 
You will then use your acquired resources to upgrade individual ships of your armada and
fortify the planets you have conquered. Meanwhile, your opponent will be doing similar 
tasks to upgrade his or her armada. The first player to destroy the other’s home base 
planet wins the game. 

SYSTEM USE CONTROLS
===================
-Display Navigation
	+(Arrow Keys): shift display in direction of arrow key press
	+(m): maximizes mini-map to make “mini-map click/drag” more visually efficient. pressing the“m” key again toggles the maximization off. 
	+“Mini-Map Click/Drag”: navigate display by clicking/dragging on mini-map 
	+”Edge Scrolling”: when mouse is in border of display, it will shift in direction of the occupied border
-Gameplay
	+(1): when ship selected, move to specified location within range
	+(2): when ship selected, attack hull of specified enemy object within range
	+(3): when ship selected, attack engine of specified enemy ship within range
	+(4): when ship selected, dock neutral planet or planet in possession within range
	+(esc): end current turn
	+(spacebar): toggle through objects in possession 
	+(q): quit current game 

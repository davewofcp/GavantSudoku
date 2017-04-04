Sudoku Solver
by Dave Wollyung
Developed for a coding challenge by Gavant Software
===================================================

Usage
-----
SudokuSolver is the main class. The program runs with no arguments.

You will be prompted for a sudoku filename. This must be a text file containing, at minimum,
nine lines of nine characters, each character being a number 1 through 9 or an X to denote
an empty cell. No data beyond the first nine characters will be read, nor any data past the
ninth line; anything in the file outside of the sudoku data is effectively a comment.

If successful, the solution will be saved to [0].sln.txt, where [0] is the filename without
its extension. If unsuccessful, a partial solution will be saved to [0].prt.txt. In both
cases, the format is the same as the input file. A partial solution file also contains a
grid of the candidates for each cell. A partial solution file can be input to the program.

Invalid starting data will result either in a validation error or a partial solution, the
former being saved to validate.err.

Extensions
----------
The program was designed to solve the included 5 puzzle files, and no sudoku-solving steps
not required to do this were written. However, the code is easily extensible without
modifying it.

Simply write a class that extends solver.AbstractStep and place it in the package
"extensions.steps". These will be automatically loaded and run if the included steps fail
to find anything. The perform() method must return how many numbers were found.

GUI and flow control extensions can also be added. They must extend extensions.AbstractGui
and extensions.AbstractController, respectively, and be placed in the packages
"extensions.gui" and "extensions.control", respectively.

Development Notes
-----------------
My naive understanding of sudoku resulted in a simple program that could solve the first 4
puzzles. That program took about 4 hours to write, and contained no code whatsoever for
dealing with candidate values. After some research, during which time absolutely no sudoku-
solving code was consulted (to make this project illustrative of my problem-solving skills
and preserving the originality of all the code), I arrived at a final program that deals
almost exclusively with candidate values and their removal.

One interesting design decision you may note, which at first was an afterthought, is the
fact that cell values and candidates are chars instead of ints or shorts. I saw no reason
to refactor the program, since no calculations are ever performed on the numbers, and chars
use the minimum storage space anyway.

Another early decision was to have each Nonet (3x3 box) be the only place where values are
stored, and to have the Grid object handle any inter-nonet operations and calculations. I
believe this is good design. Wherever possible, in accordance with OO principles, every
object handles all the necessary operations on itself.

Testing
-------
One of the first bits of code I wrote was a validator for the grid, which I tested manually
in every way a grid could be wrong. Having ensured its accuracy, I added it to the main
loop in the Solver class to make sure no step ever placed a number incorrectly. I then
wrote each step in increasing order of complexity, as needed to solve the puzzle at hand.
This iterative process was self-validating and numbers were manually checked against a
sudoku solver I found online.

Knowing what I know now, I would have added code to read in an answer key and check the
grid against it during the debugging phase. It would have saved a lot of time for something
that initially seemed to be quick.

What I Learned
--------------
This project opened my eyes to what a wonderful puzzle sudoku is. Seeing how one part fits
into the whole and making logical steps based on this teaches transcendent wisdom about
life, and it is now one of my favorite puzzles. It is a good thing there are several
quadrillion possible configurations, and many more starting positions; I will never run out.

I consulted a web-based sudoku solver for an explanation of the steps, particularly the
Naked Triple step, for which I also consulted pseudocode posted on StackOverflow. The long
list of advanced steps I found inspired me to implement extensions.

I also noticed this is all very relevant to front-end web design, so kudos to Gavant for
choosing a good relevant challenge in my native language.

References
----------
http://www.sudokuwiki.org/sudoku.htm
http://stackoverflow.com/questions/3018919/solving-naked-triples-in-sudoku

License
-------
Copyright (c) 2017 Dave Wollyung, Clifton Park, NY, United States

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License, Version 3, as
published by the Free Software Foundation.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, see <http://www.gnu.org/licenses>.

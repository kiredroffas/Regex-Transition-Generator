# Regular Expression to Finite Automata

This program implements a regular expression transition generator using Java. The engine creates a Nondeterministic Finite
Automata (NFA) from user supplied Regular Expressions in postfixed notation. Regular Expressions are read in from
a .txt file through a file scanner in Main, the filepath of the .txt file containing the Regular Expressions is given to
the file scanner, which then reads in the Regular Expressions (line by line). A list of transitions, denoting the NFA
(including starting and final states), is then created for each Regular Expression read in from the .txt file. This is
done by preforming operations on a stack of NFA's based on the symbol of each individual character of a regex.
Regular Expression input symbols include: {a, b, c, d, e, |(union), &(concatenation), *(Kleene star), and E(epsilon).
Incorrectly formatted Regular Expressions will promt an error message. I used the 12 postfix sample expressions from the
assignment description to test my program's functionality (which are contained in input.txt).

NFA stack operations are based on these Rules:
	Rule 1: There is a FA that accepts any symbol of Σ and there is a FA that accepts ε.
  o	If x is in Σ then give a FA that accepts x
  o	Give a FA that accepts ε.
	Rule 2: Given FA1 that accepts regular expression r1 and FA2 that accepts regular expression r2 then make FA3 that
  accepts r1 | r2. Add a new start state s and make a ε-transition from this state to the start states of FA1 and FA2.
  Add a new final state f and make a ε-transition to this state from each of the final states of FA1 and FA2.
	Rule 3: Given FA1 that accepts regular expression r1 and FA2 that accepts regular expression r2 then make FA3 that
  accepts r1◦ r2. Add a ε-transition from the final state of r1 to the start state of r2. The start state of FA3 is the
  start state of FA1 and the final state of FA3 is the final state of FA2.
	Rule 4: Given FA1 that accepts regular expression r then make a FA2 that accepts r*. Add a new start state s and make
  a ε-transition from this state to the start state of FA1. Make a ε-transition from the final state of F1 to the new
  start state s. The final states of FA1 are no longer final and s is the final state of FA2.

To build:
  Import all files into IntelliJ IDEA/VSCode (as a Java Project)
  Make sure correct filepath to input.txt (or other .txt file containing regex) is given to file scanner in Main.java
  Run Main
  List of NFA Transitions should print for each regex read in from input.txt

Contained Files:
  README.txt - Provides information about input & output of the program
  input.txt - Text file containing sample Regular Expressions from the assignment (one per line, 12 total)
  Main.java - Main class containing the majority of the logic used in the program
  NFA.java - NFA object used by Main to represent an NFA
  Transition.java - Transition object used by Main and NFA to represent a transition from one state to another

# Screenshots
![Alt text](/screenshots/output1.png?raw=true "Screenshot 1")
![Alt text](/screenshots/output2.png?raw=true "Screenshot 2")
![Alt text](/screenshots/output3.png?raw=true "Screenshot 3")
![Alt text](/screenshots/output4.png?raw=true "Screenshot 4")
![Alt text](/screenshots/output5.png?raw=true "Screenshot 5")

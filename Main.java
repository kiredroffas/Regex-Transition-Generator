/* Erik Safford
   Project 1 - Regular Expression to Finite Automata
   CS 317
   October 2018
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws FileNotFoundException {

    File file = new File("/home/erik/Desktop/Regex-Transition-Generator/input.txt"); //Change this to your regex filepath
    Scanner sc = new Scanner(file);
    NFA stack[] = new NFA[20]; // global NFA stack
    NFA newNFA; // global NFA variable

    while (sc.hasNextLine()) { // While there is input in file (1 regular expression per line)
      String str = sc.nextLine(); // str is the regex read in from 1 line of the file
      System.out.println(str); //Print regex read in
      int SC = 0; // NFA stack counter

      for (int i = 0; i < str.length(); i++) { // for each character in regex
        // perform a corresponding process on the NFA stack
        char regChar = str.charAt(i);

        if (str.charAt(i) == '&') { // concatenation

          SC--; // pop() NFA 2 off stack
          NFA nfa2 = stack[SC];
          SC--; // pop() NFA 1 off stack
          NFA nfa1 = stack[SC];

          //Generate transitions for the NFA that concatenates NFA 1 & 2 (Rule 3)
          Transition uE1 = nfa1.tList;
          Transition check = uE1;
          Transition prev;
          prev = check;
          prev.startFinal = 1; //Make nfa1 startState into new start state
          check = check.next;

          while(check != null) { //cycle to the end of nfa1's tList, prev will end up being last Transition before null
            prev = check;
            prev.startFinal = 0;
            check = check.next;
          }
          Transition uE2 = new Transition(nfa1.finalState,nfa2.startState,'E');
          prev.next = uE2; //Connect nfa1 finalState to nfa2 startState by E transition
          Transition uE3 = nfa2.tList;
          uE2.next = uE3;
          uE3.startFinal = 0; //make nfa2 start state into normal state
          check = uE3;

          while(check != null) { //cycle to the end of nfa2's tList, prev will end up being last Transition before null
            prev = check;
            prev.startFinal = 0;
            check = check.next;
          }
          prev.startFinal = 2; //make nfa2 final state into new final state

          //Push new NFA to stack with generated transitions
          NFA concatNFA = new NFA(nfa1.startState,nfa2.finalState,uE1);
          stack[SC] = concatNFA;
          SC++;

        }
        else if(str.charAt(i) == '|') { // union

          SC--; // pop() NFA 2 off stack
          NFA nfa2 = stack[SC];
          int state = nfa2.finalState+1; //state is number of next state that can be used to Transition
          Transition prev = null;

          SC--; //pop() NFA 1 off stack
          NFA nfa1 = stack[SC];

          //Generate transitions for the NFA that unions NFA 1 & 2 (Rule 2)
          Transition uE1 = new Transition(state,nfa1.startState,'E',1); //make new start state
          Transition uE2 = nfa1.tList;
          uE2.startFinal = 0;
          uE1.next = uE2;
          Transition check = uE2;

          while(check != null) { //cycle to the end of nfa1's tList, prev will end up being last Transition before null
            prev = check;
            prev.startFinal = 0;
            check = check.next;
          }
          Transition uE3 = new Transition(nfa1.finalState,state+1,'E',2);
          prev.next = uE3;

          Transition uE4 = new Transition(state,nfa2.startState,'E',1);
          uE3.next = uE4;
          Transition uE5 = nfa2.tList;
          uE4.next = uE5;

          check = uE5;
          while(check != null) { //cycle to the end of nfa2's tList, prev will end up being last Transition before null
            prev = check;
            prev.startFinal = 0;
            check = check.next;
          }
          Transition uE6 = new Transition(nfa2.finalState,state+1,'E',2);
          prev.next = uE6;

          //Push new NFA to stack with generated transitions
          NFA unionNFA = new NFA(state,state+1,uE1);
          stack[SC] = unionNFA;
          SC++;

        }
        else if(str.charAt(i) == '*') { // Kleene star

          SC--; // pop() NFA off stack
          NFA nfa = stack[SC];
          int state = nfa.finalState+1; //state is number of next state that can be used to Transition

          //Generate transitions for the NFA that accepts the (popped NFA)* (Rule 4)
          Transition prev = null;
          Transition uE1 = nfa.tList;
          uE1.startFinal = 0; //change start state to normal state
          Transition check = uE1;

          while(check != null) { //cycle to the end of nfa's tList, prev will end up being last Transition before null
            prev = check;
            prev.startFinal = 0;
            check = check.next;
          }
          Transition uE2 = new Transition(nfa.finalState,state,'E');
          prev.next = uE2;
          Transition uE3 = new Transition(state,nfa.startState,'E',3);
          uE2.next = uE3;

          //Push new NFA to stack with generated transitions
          NFA kStarNFA = new NFA(state,state,uE1);
          stack[SC] = kStarNFA;
          SC++;

        }
        else if(regChar == 'a'||regChar == 'b'||regChar == 'c'||regChar == 'd'||regChar == 'e'||regChar == 'E') {

          // If normal char of language {a,b,c,d,e,E(epsilon)} push NFA that accepts a single character a,b,c,d,e,E
          if(SC == 0) { //If the NFA stack is empty
            Transition nT = new Transition(1, 2, str.charAt(i)); // construct new Transition
            newNFA = new NFA(1, 2, nT); // construct a new NFA
            stack[SC] = newNFA; //push new NFA to stack
            SC++;  //Increment stack pointer and counters
          }
          else { //If there are already NFA's on stack we need to determine which state we can use
            SC--;
            NFA nfa = stack[SC];
            int state = nfa.finalState+1; //state is number of next state that can be used
            SC++;
            Transition nT = new Transition(state, state+1, str.charAt(i)); // construct new Transition
            newNFA = new NFA(state, state+1, nT); // construct a new NFA
            stack[SC] = newNFA; //push new NFA to stack
            SC++;  //Increment stack pointer and counters
          }

        }
        else { //If improper file input from file

          System.out.println("WARNING: Regex ' "+str+" ' is not properly formatted with elements of the langauge {a,b,c,d,e,E}");
          System.out.println(" ");
          break;

        }
      }
      // Prints list of transitions for the regex read in (per line in the file)
      for(int z = 0; z < SC;z++) {
        Transition tprev = null;
        for (int i = 1; i < stack[z].finalState; i++) {
          Transition t = stack[z].tList;
          while (t != null) {
            if(t.state1 == i) {
              if(t.startFinal == 0) {
                System.out.println("(q" + t.state1 + ", " + t.symbol + ") -> q" + t.state2);
              }
              else if(t.startFinal == 1) {
                System.out.println("S(q" + t.state1 + ", " + t.symbol + ") -> q" + t.state2);
              }
              else if(t.startFinal == 2) {
                System.out.println("(q" + t.state1 + ", " + t.symbol + ") -> q"+t.state2);
              }
              else if(t.startFinal == 3) {
                System.out.println("S F(q" + t.state1 + ", " + t.symbol + ") -> q" + t.state2);
              }
            }
            else if(t.state2 == i && t.startFinal == 2) {
              System.out.println("F(q" + t.state2 + ", " + t.symbol + ") ");
            }
            tprev = t;
            t = t.next;
          }
        }
        if(tprev.startFinal == 0) { //Print out the final state
          System.out.println("(q" + tprev.state1 + ", " + tprev.symbol + ") -> q" + tprev.state2);
          System.out.println(" ");
        }
        else if(tprev.startFinal == 1) {
          System.out.println("S(q" + tprev.state1 + ", " + tprev.symbol + ") -> q" + tprev.state2);
          System.out.println(" ");
        }
        else if(tprev.startFinal == 2) {
          System.out.println("F(q" + tprev.state2 + ", E)");
          System.out.println(" ");
        }
        else if(tprev.startFinal == 3) {
          System.out.println("S F(q" + tprev.state1 + ", " + tprev.symbol + ") -> q" + tprev.state2);
          System.out.println(" ");
        }
      }
    }
    sc.close();
  }
}

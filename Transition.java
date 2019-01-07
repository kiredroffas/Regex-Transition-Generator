public class Transition {

  public int state1; // number of the first state
  public int state2; // number for the second state
  public char symbol; // the symbol that goes from 1st state to 2nd state
  public Transition next; //pointer to a list of transitions for the NFA
  public int startFinal; //0 = normal state,1 = start,2 = final,3 = start/final, for printing

  public Transition(int state1,int state2,char symbol) { // constructor
    this.state1 = state1;
    this.state2 = state2;
    this.symbol = symbol;
    next = null;
    this.startFinal = 0; //if not given startFinal value, assume is normal
  }
  public Transition(int state1,int state2,char symbol,int startFinal) { // constructor
    this.state1 = state1;
    this.state2 = state2;
    this.symbol = symbol;
    this.next = null;
    this.startFinal = startFinal;
  }
}

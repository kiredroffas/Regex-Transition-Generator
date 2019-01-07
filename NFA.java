public class NFA { //This structure represents an NFA

  public int startState; // number of the start state
  public int finalState; // number of the final state
  public Transition tList; //pointer to a list of transitions for the NFA

  public NFA(int startState,int finalState,Transition tList) { // constructor
    this.startState = startState;
    this.finalState = finalState;
    this.tList = tList;
  }
}

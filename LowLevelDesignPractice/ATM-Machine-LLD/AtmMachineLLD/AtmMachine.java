package AtmMachineLLD;
import States.State;

public class AtmMachine{
    State state;
    int atmBalance;
    int noOf2KNotes;
    int noOf500Notes;
    int noOf100Notes;

    
    public int getNoOf2KNotes() {
        return noOf2KNotes;
    }

    public void setNoOf2KNotes(int noOf2KNotes) {
        this.noOf2KNotes = noOf2KNotes;
    }

    public int getNoOf500Notes() {
        return noOf500Notes;
    }

    public void setNoOf500Notes(int noOf500Notes) {
        this.noOf500Notes = noOf500Notes;
    }

    public int getNoOf100Notes() {
        return noOf100Notes;
    }

    public void setNoOf100Notes(int noOf100Notes) {
        this.noOf100Notes = noOf100Notes;
    }

    public State getState() {
        return state;
    }

    public void setState(State states) {
        this.state = states;
    }

    public int getAtmBalance() {
        return atmBalance;
    }

    public void setAtmBalance(int atmBalance) {
        this.atmBalance = atmBalance;
    }


    
}
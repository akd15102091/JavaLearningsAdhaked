package States;

import AtmMachineLLD.AtmMachine;
import AtmMachineLLD.Card;

public class IdleState extends State{
    @Override
    public void insertCard(AtmMachine atmMachine, Card card){
        atmMachine.setState(new HasCardState());
    }
}

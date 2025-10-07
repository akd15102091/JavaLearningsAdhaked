package States;

import AtmMachineLLD.AtmMachine;
import AtmMachineLLD.Card;

public class HasCardState extends State{
    @Override 
    public void autheticatePIN(AtmMachine atmMachine,Card card,int PIN){
        if(card.getPin() ==PIN){
            atmMachine.setState(new SelectOperationState());
        }
        else{
            exit(atmMachine);
        }
    }

    @Override
    public void returnCard(AtmMachine atmMachine){
        System.out.println("card returned , PIN is incorrect");
    }

    @Override
    public void exit(AtmMachine atmMachine){
        returnCard(atmMachine);
        atmMachine.setState(new IdleState());
    }
}

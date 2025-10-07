package States;

import AtmMachineLLD.Account;
import AtmMachineLLD.AtmMachine;

public class CheckBalanceState extends State{

    @Override
     public void checkBalance(AtmMachine atmMachine, Account account){
        System.out.println("Your account balance is: "+account.getBalance());
        exit(atmMachine);
    }

    
    @Override
    public void returnCard(AtmMachine atmMachine){
        System.out.println("card returned");
    }

    @Override
    public void exit(AtmMachine atmMachine){
        returnCard(atmMachine);
        atmMachine.setState(new IdleState());
    }
}

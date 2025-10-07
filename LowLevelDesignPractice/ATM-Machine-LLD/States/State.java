package States;
import AtmMachineLLD.Account;
import AtmMachineLLD.AtmMachine;
import AtmMachineLLD.Card;

public abstract class State {

    public void insertCard(AtmMachine atmMachine, Card card){
        System.out.println("oops, something went wrong");
    }

    public void autheticatePIN(AtmMachine atmMachine, Card card, int pin){
        System.out.println("oops, something went wrong");
    }
    
    public void selectOperation(AtmMachine atmMachine, Operations op){
        System.out.println("oops, something went wrong");
    }

    public void cashWithdrawl(AtmMachine atmMachine, Card card, int withDrawlAmt){
        System.out.println("oops, something went wrong");
    }
    
    public void checkBalance(AtmMachine atmMachine, Account account){
        System.out.println("oops, something went wrong");
    }

    public void changePIN(AtmMachine atmMachine){
        System.out.println("oops, something went wrong");
    }

    public void cashDeposit(AtmMachine atmMachine){
        System.out.println("oops, something went wrong");
    }

    public void returnCard(AtmMachine atmMachine){
        System.out.println("oops, something went wrong");
    }

    public void exit(AtmMachine atmMachine){
        System.out.println("oops, something went wrong");
    }
}

package States;

import AtmMachineLLD.AtmMachine;

public class SelectOperationState extends State{

    @Override
     public void selectOperation(AtmMachine atmMachine, Operations op){
        if(op == Operations.CASHWITHDRAWL){
            atmMachine.setState(new CashWithdrawlState());
        }
        else if (op == Operations.CHECKBALANCE){
            atmMachine.setState(new CheckBalanceState());
        }
        else{
            exit(atmMachine);
        }
    }
    
   
    @Override
    public void returnCard(AtmMachine atmMachine){
        System.out.println("Operation not supported ,card returned");
    }

    @Override
    public void exit(AtmMachine atmMachine){
        returnCard(atmMachine);
        atmMachine.setState(new IdleState());
    }
}

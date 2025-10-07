package CashWithdrawl;

import AtmMachineLLD.AtmMachine;

public class CashHandler500 implements CashWithdrawProcessor{

     CashWithdrawProcessor nextHandler;
    @Override
    public boolean handleWithdrwalRequest(AtmMachine atmMachine,int amount) {
        int reqNotes = Math.min(amount/500, atmMachine.getNoOf500Notes());
        int remaningAmount = amount - reqNotes*500;

        if(remaningAmount>0){
           boolean isSuccess =  nextHandler.handleWithdrwalRequest(atmMachine, remaningAmount);
           if(isSuccess){
            atmMachine.setNoOf500Notes(atmMachine.getNoOf500Notes()-reqNotes);
            System.out.println("Request fulfilled by 500 notes "+reqNotes);

            return true;
           }
           return false;
        }
 
        atmMachine.setNoOf500Notes(atmMachine.getNoOf500Notes()-reqNotes);
        System.out.println("Request fulfilled by 500 notes "+reqNotes);
        return true;
        
    }

    @Override
    public void setHandler(CashWithdrawProcessor cashWithdrawProcessor) {
        this.nextHandler = cashWithdrawProcessor;
    }
    
}

package CashWithdrawl;

import AtmMachineLLD.AtmMachine;

public class CashHandler2K implements CashWithdrawProcessor{

    CashWithdrawProcessor nextHandler;
    @Override
    public boolean handleWithdrwalRequest(AtmMachine atmMachine,int amount) {
        int reqNotes = Math.min(amount/2000, atmMachine.getNoOf2KNotes());
        int remaningAmount = amount - reqNotes*2000;

        if(remaningAmount>0){
           boolean isSuccess =  nextHandler.handleWithdrwalRequest(atmMachine, remaningAmount);
           if(isSuccess){
            atmMachine.setNoOf2KNotes(atmMachine.getNoOf2KNotes()-reqNotes);
            System.out.println("Request fulfilled by 2K notes "+reqNotes);
            return true;
           }
           return false;
        }
       
        atmMachine.setNoOf2KNotes(atmMachine.getNoOf2KNotes()-reqNotes);
        System.out.println("Request fulfilled by 2K notes "+reqNotes);
        return true;
       
    }

    @Override
    public void setHandler(CashWithdrawProcessor cashWithdrawProcessor) {
        this.nextHandler = cashWithdrawProcessor;
    }

    
}

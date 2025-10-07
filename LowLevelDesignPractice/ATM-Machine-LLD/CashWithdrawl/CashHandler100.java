package CashWithdrawl;

import AtmMachineLLD.AtmMachine;

public class CashHandler100 implements CashWithdrawProcessor{

     CashWithdrawProcessor nextHandler;
    @Override
    public boolean handleWithdrwalRequest(AtmMachine atmMachine,int amount) {
        int reqNotes = Math.min(amount/100, atmMachine.getNoOf100Notes());
        int remaningAmount = amount - reqNotes*100;

        if(remaningAmount>0){
           return false;
        }
        
        atmMachine.setNoOf100Notes(atmMachine.getNoOf100Notes()-reqNotes);
        System.out.println("Request fulfilled by 100 notes "+reqNotes);
        return true;
        
    }

    @Override
    public void setHandler(CashWithdrawProcessor cashWithdrawProcessor) {
        this.nextHandler = cashWithdrawProcessor;
    }
    
}
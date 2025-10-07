package States;

import AtmMachineLLD.AtmMachine;
import AtmMachineLLD.Card;
import CashWithdrawl.CashHandler100;
import CashWithdrawl.CashHandler2K;
import CashWithdrawl.CashHandler500;
import CashWithdrawl.CashWithdrawProcessor;

public class CashWithdrawlState extends State{
    @Override
     public void cashWithdrawl(AtmMachine atmMachine,Card card, int withDrawlAmount){

        if(atmMachine.getAtmBalance()<withDrawlAmount){
            System.out.println("ATM doesn't have sufficient amount");
            exit(atmMachine);
            return;
        }
       
        if(card.getAccount().getBalance()>=withDrawlAmount ){
            CashWithdrawProcessor cashHandler2K = new CashHandler2K();
            CashWithdrawProcessor cashHandler500 = new CashHandler500();
            CashWithdrawProcessor cashHandler100 = new CashHandler100();
            
            cashHandler2K.setHandler(cashHandler500);
            cashHandler500.setHandler(cashHandler100);

            boolean isSuccess = cashHandler2K.handleWithdrwalRequest(atmMachine,withDrawlAmount);
            if(isSuccess){
                atmMachine.setAtmBalance(atmMachine.getAtmBalance()-withDrawlAmount);
                card.getAccount().setBalance(card.getAccount().getBalance()-withDrawlAmount);
                System.out.println("Collect your cash from cash tray");
                exit(atmMachine);
            }
            else{
                System.out.println("Insufficient fund in ATM");
                exit(atmMachine);
            }

        }
        else{
            System.out.println("Insufficient Balance in your account");
            exit(atmMachine);
            return;
        }
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

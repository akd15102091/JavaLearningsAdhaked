package CashWithdrawl;

import AtmMachineLLD.AtmMachine;

public interface CashWithdrawProcessor {
     boolean handleWithdrwalRequest(AtmMachine atmMachine,int amount);
     void setHandler(CashWithdrawProcessor cashWithdrawProcessor);
}

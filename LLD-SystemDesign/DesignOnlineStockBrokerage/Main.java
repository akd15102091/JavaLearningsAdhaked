package DesignOnlineStockBrokerage;

import DesignOnlineStockBrokerage.services.BrokerageService;

public class Main {
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        BrokerageService service = BrokerageService.getInstance();

        User user1 = service.registerUser("akd@gmail.com", "Ashwini", "9123456789");
        Account sbi = new Account(user1.getUserId(), BankName.SBI, 35000.0);
        sbi = service.addAccount(user1.getUserId(), sbi);
        Account icici = new Account(user1.getUserId(), BankName.ICICI, 50000.0);
        icici = service.addAccount(user1.getUserId(), icici);
        Account creditAccount = service.updateCreditAccount(user1.getUserId(), icici);

        service.buyShares("APPL", user1.getUserId(), 10, sbi);
        service.viewPortfolio(user1.getUserId());
        service.sellShares("APPL", user1.getUserId(), 5);
        service.viewPortfolio(user1.getUserId());
    }
}

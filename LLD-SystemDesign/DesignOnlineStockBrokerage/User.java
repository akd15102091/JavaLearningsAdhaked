package DesignOnlineStockBrokerage;

@SuppressWarnings("unused")
public class User {
    private final String userId;
    private final String email;
    private String mobile;
    private String name;
    private Account creditAccount;

    public User(String email, String name, String mobile){
        this.userId = email;
        this.email = email;
        this.name = name;
        this.mobile = mobile;
    }

    public String getUserId() {
        return this.userId;
    }

    public void updateCreditAccount(Account account){
        this.creditAccount = account;
    }

}


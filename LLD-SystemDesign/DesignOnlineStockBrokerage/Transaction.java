package DesignOnlineStockBrokerage;

import java.util.UUID;

@SuppressWarnings("unused")
public class Transaction {
    private String content;
    private final String transactionId;
    private final long addedAt;

    public Transaction(String content){
        this.content = content;
        this.transactionId = UUID.randomUUID().toString();
        this.addedAt = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Transaction [content=" + content + ", transactionId=" + transactionId + "]";
    }
}


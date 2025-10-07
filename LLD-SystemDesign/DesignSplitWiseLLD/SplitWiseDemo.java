package DesignSplitWiseLLD;

import java.util.List;
import java.util.Map;

import DesignSplitWiseLLD.splitstrategy.SplitType;

@SuppressWarnings("unused")
public class SplitWiseDemo {
    public static void main(String[] args) {
        SplitWiseService splitWiseService = SplitWiseService.getInstance();

        // create users
        User user1 = splitWiseService.createUser("Alice", "alice@example.com");
        User user2 = splitWiseService.createUser("Bob", "bob@example.com");
        User user3 = splitWiseService.createUser("Charlie", "charlie@example.com");

        // create a group
        Group group = splitWiseService.createGroup("Apartment", List.of(user1.getUserId(), user2.getUserId(), user3.getUserId()));

        // Add group expense
        Expense rentExpense = splitWiseService.addExpense("Rent", 9000.0, user1.getUserId(), group.getGroupId(), SplitType.EQUAL, 
                            Map.of(
                                user1.getUserId(),3000.0,
                                user2.getUserId(),3000.0,
                                user3.getUserId(),3000.0
                            ));
        
        // Add non-group expense
        Expense dinnerExpense = splitWiseService.addExpense("Dinner", 600.0, user2.getUserId(),
                            null, SplitType.EXACT,
                            Map.of(
                                    user1.getUserId(), 400.0,
                                    user2.getUserId(), 200.0
                            ));

        // Print user balances
        System.out.println("\nPrinting balance for each user:");
        splitWiseService.printBalances();

        // Settle balances
        System.out.println("\nSettling balances between users");
        splitWiseService.settle(user2.getUserId(), user1.getUserId(), 2000.0);
        splitWiseService.settle(user3.getUserId(), user1.getUserId(), 3000.0);

        // Print user balances
        System.out.println("\nPrinting balance for each user:");
        splitWiseService.printBalances();

        splitWiseService.deleteExpense(dinnerExpense.getExpenseId());

        // Print user balances
        System.out.println("\nPrinting balance for each user:");
        splitWiseService.printBalances();

        // Print user balance
        System.out.println("\nPrinting balance for: " + user1.getName());
        splitWiseService.printBalanceForUser(user1.getUserId());
    }
}

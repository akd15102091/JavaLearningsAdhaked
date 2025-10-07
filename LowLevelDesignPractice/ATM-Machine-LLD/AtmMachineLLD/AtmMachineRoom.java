package AtmMachineLLD;

import java.util.Arrays;

import States.IdleState;
import States.Operations;

/*
 * Chain of responsibility
 * State design pattern
 */
public class AtmMachineRoom {
    User user;
    AtmMachine atmMachine;

    private void createUser() {
        user = new User();
        Card card =new Card();
        card.setCardNumber("12345678911");
        card.setPin(1425);
        card.setAccount(new Account("14254678",200000));
        user.setCard(card);
        user.setUserName("ajju");
    }

    public void createAtm(){
         atmMachine = new AtmMachine();
         atmMachine.setAtmBalance(50000);
         atmMachine.setNoOf2KNotes(20); //40,000
         atmMachine.setNoOf500Notes(10); //5000
         atmMachine.setNoOf100Notes(50); //5000 = 100*50
    }

    public static void main(String[] args) {
        AtmMachineRoom atmMachineRoom = new AtmMachineRoom();
        atmMachineRoom.createUser();
        atmMachineRoom.createAtm();

        //initial atm is in idle state
        atmMachineRoom.atmMachine.setState(new IdleState());

        System.out.println("user comes and insert a card "+atmMachineRoom.user.getUserName()+" "+atmMachineRoom.user.getCard().getCardNumber());
        atmMachineRoom.atmMachine.getState().insertCard(atmMachineRoom.atmMachine,atmMachineRoom.user.getCard());

        System.out.println("User enter the PIN 1425");
        atmMachineRoom.atmMachine.getState().autheticatePIN(atmMachineRoom.atmMachine, atmMachineRoom.user.getCard(), 1425);

        System.out.println("List of operations : -");
        System.out.println(Arrays.asList(Operations.values()));
        System.out.println("User select cash withdrawal");
        atmMachineRoom.atmMachine.getState().selectOperation(atmMachineRoom.atmMachine, Operations.CASHWITHDRAWL);
        atmMachineRoom.atmMachine.getState().cashWithdrawl(atmMachineRoom.atmMachine, atmMachineRoom.user.getCard(), 45200);

        System.out.println("Remaining Balance user account :--------------- ");
        atmMachineRoom.atmMachine.getState().insertCard(atmMachineRoom.atmMachine,atmMachineRoom.user.getCard());
        atmMachineRoom.atmMachine.getState().autheticatePIN(atmMachineRoom.atmMachine, atmMachineRoom.user.getCard(), 1425);

        atmMachineRoom.atmMachine.getState().selectOperation(atmMachineRoom.atmMachine, Operations.CHECKBALANCE);
        atmMachineRoom.atmMachine.getState().checkBalance(atmMachineRoom.atmMachine, atmMachineRoom.user.getCard().getAccount());

     }
        
           
}




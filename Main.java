// Snack class representing a snack item
import java.util.*;
class Snack {
    private String name;
    private double price;
    private int quantity;

    public Snack(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

// StateOfVendingMachine interface representing different states of the vending machine
interface StateOfVendingMachine {
    void selectSnack(VendingMachine vendingMachine, String snackName);
    void insertMoney(VendingMachine vendingMachine,double money);
    void dispenseSnack(VendingMachine vendingMachine);
}

// Idle state of the vending machine
class IdleState implements StateOfVendingMachine {
    public void selectSnack(VendingMachine vendingMachine, String snackName) {
        // Check if the selected snack is available
        for (Snack snack : vendingMachine.getSnacks()) {
            if (snack.getName().equals(snackName)) {
                vendingMachine.setSelectedSnack(snack);
                vendingMachine.setState(new WaitingForMoneyState());
                return;
            }
        }
        System.out.println("Selected snack is not available.");
    }

    public void insertMoney(VendingMachine vendingMachine,double money) {
        System.out.println("Please select a snack first.");
    }

    public void dispenseSnack(VendingMachine vendingMachine) {
        System.out.println("Please select a snack first.");
    }
}

// WaitingForMoney state of the vending machine
class WaitingForMoneyState implements StateOfVendingMachine {

    public void selectSnack(VendingMachine vendingMachine,String snackName) {
        System.out.println("Snack already selected. Please insert money.");
    }

    public void insertMoney(VendingMachine vendingMachine, double money) {
        if (money >= vendingMachine.getSelectedSnack().getPrice()) {
            vendingMachine.setInsertedMoney(money);
            vendingMachine.setState(new DispensingSnackState());
        } else {
            System.out.println("Insufficient money inserted.");
            vendingMachine.setState(new IdleState());
        }
    }

    public void dispenseSnack(VendingMachine vendingMachine) {
        System.out.println("Please insert money first.");
    }
}

// DispensingSnack state of the vending machine
class DispensingSnackState implements StateOfVendingMachine {

    public void selectSnack(VendingMachine vendingMachine,String snackName) {
        System.out.println("Already dispensing a snack.");
    }

    public void insertMoney(VendingMachine vendingMachine,double money) {
        System.out.println("Already dispensing a snack.");
    }

    public void dispenseSnack(VendingMachine vendingMachine) {
        Snack selectedSnack = vendingMachine.getSelectedSnack();
        if (selectedSnack.getQuantity() > 0) {
            vendingMachine.getSnackDispenseHandler().dispenseSnack(selectedSnack.getName());
            selectedSnack.setQuantity(selectedSnack.getQuantity() - 1);
            System.out.println("Thank you for purchasing, Here is your return money " + (vendingMachine.getInsertedMoney() - selectedSnack.getPrice()));
            vendingMachine.setInsertedMoney(0);
            vendingMachine.setState(new IdleState());
        } else {
            System.out.println("Selected snack is out of stock. Returning money.");
            vendingMachine.setInsertedMoney(0);
            vendingMachine.setState(new IdleState());
        }
    }
}

// SnackDispenseHandler abstract class for the chain of responsibility
abstract class SnackDispenseHandler{
    private SnackDispenseHandler next;

    public SnackDispenseHandler (SnackDispenseHandler next) {

        this.next = next;

    }

    public void dispenseSnack(String snack) {

        if(next != null) {

         next.dispenseSnack(snack);

        }

    }

}
//Coke, Pepsi, Cheetos,
//Doritos, KitKat, and Snickers
class CokeDispenseHandler extends SnackDispenseHandler {

    public CokeDispenseHandler(SnackDispenseHandler next) {
        super(next);
    }

    public void dispenseSnack(String snackName) {
        if (snackName.equals("Coke")) {
            System.out.println("Here is your Coke");
        } else{
            super.dispenseSnack(snackName);
        }
    }
}
class PepsiDispenseHandler extends SnackDispenseHandler {

    public PepsiDispenseHandler(SnackDispenseHandler next) {
        super(next);
    }


    public void dispenseSnack(String snackName) {
        if (snackName.equals("Pepsi")) {
            System.out.println("Here is your Pepsi");
        } else {
            super.dispenseSnack(snackName);
        }
    }
}
class CheetosDispenseHandler extends SnackDispenseHandler {

    public CheetosDispenseHandler(SnackDispenseHandler next) {
        super(next);
    }


    public void dispenseSnack(String snackName) {
        if (snackName.equals("Cheetos")) {
            System.out.println("Here is your Cheetos");
        } else  {
            super.dispenseSnack(snackName);
        }
    }
}
class DoritosDispenseHandler extends SnackDispenseHandler {

    public DoritosDispenseHandler(SnackDispenseHandler next) {
        super(next);
    }

    public void dispenseSnack(String snackName) {
        if (snackName.equals("Doritos")) {
            System.out.println("Here is your Doritos");
        } else  {
            super.dispenseSnack(snackName);
        }
    }
}
class KitKatDispenseHandler extends SnackDispenseHandler {

    public KitKatDispenseHandler(SnackDispenseHandler next) {
        super(next);
    }

    public void dispenseSnack(String snackName) {
        if (snackName.equals("KitKat")) {
            System.out.println("Here is your KitKat");
        } else {
            super.dispenseSnack(snackName);
        }
    }
}
class SnickersDispenseHandler extends SnackDispenseHandler {

    public SnickersDispenseHandler(SnackDispenseHandler next) {
        super(next);
    }


    public void dispenseSnack(String snackName) {
        if (snackName.equals("Snickers")) {
            System.out.println("Here is your Snickers");
        } else  {
            super.dispenseSnack(snackName);
        }
    }
}

// VendingMachine class representing the vending machine
class VendingMachine {
    private List<Snack> snacks;
    private StateOfVendingMachine state = new IdleState();
    private Snack selectedSnack;

    private SnackDispenseHandler snackDispenseHandler;
    private double insertedMoney;
;

    public VendingMachine(List<Snack> snacks) {
        this.snacks = snacks;
        snackDispenseHandler = new CokeDispenseHandler(new PepsiDispenseHandler(new CheetosDispenseHandler(new DoritosDispenseHandler(new KitKatDispenseHandler(new SnickersDispenseHandler(null))))));
    }

    public List<Snack> getSnacks() {
        return snacks;
    }

    public Snack getSelectedSnack() {
        return selectedSnack;
    }

    public void setSelectedSnack(Snack selectedSnack) {
        this.selectedSnack = selectedSnack;
    }

    public double getInsertedMoney() {
        return insertedMoney;
    }

    public void setInsertedMoney(double insertedMoney) {
        this.insertedMoney = insertedMoney;
    }

    public void selectSnack(String snack)
    {
        getState().selectSnack(this,snack);
    }

    public void insertMoney(double money)
    {
        getState().insertMoney(this,money);
    }

    public void dispenseSnack()
    {
        getState().dispenseSnack(this);
    }


    public StateOfVendingMachine getState() {
        return state;
    }

    public void setState(StateOfVendingMachine state) {
        this.state = state;
    }

    public SnackDispenseHandler getSnackDispenseHandler() {
        return snackDispenseHandler;
    }

    public void setSnackDispenseHandler(SnackDispenseHandler snackDispenseHandler) {
        this.snackDispenseHandler = snackDispenseHandler;
    }
}

public class Main {
    public static void main(String[] args) {
        //Update inventory
        List<Snack> snacks = new ArrayList<>();
        snacks.add(new Snack("Coke", 1.5, 5));
        snacks.add(new Snack("Pepsi", 1.5, 5));
        snacks.add(new Snack("Cheetos", 1.0, 5));
        snacks.add(new Snack("Doritos", 1.0, 5));
        snacks.add(new Snack("KitKat", 2.0, 5));
        snacks.add(new Snack("Snickers", 2.0, 0));

        VendingMachine vendingMachine = new VendingMachine(snacks);

//Vending test
        vendingMachine.selectSnack("Coke");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();

        vendingMachine.selectSnack("Pepsi");
        vendingMachine.insertMoney(1.0);
        vendingMachine.dispenseSnack();

        vendingMachine.selectSnack("Cheetos");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();

        vendingMachine.selectSnack("Doritos");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();

        vendingMachine.selectSnack("KitKat");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();

        vendingMachine.selectSnack("Snickers");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();
    }
}
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendingMachineTest {

    private VendingMachine vendingMachine;

    @BeforeEach
    public void setUp() {
        List<Snack> snacks = new ArrayList<>();
        snacks.add(new Snack("Coke", 1.5, 5));
        snacks.add(new Snack("Pepsi", 1.5, 5));
        snacks.add(new Snack("Cheetos", 1.0, 5));
        snacks.add(new Snack("Doritos", 1.0, 5));
        snacks.add(new Snack("KitKat", 2.0, 5));
        snacks.add(new Snack("Snickers", 2.0, 0));

        vendingMachine = new VendingMachine(snacks);
    }

    @Test
    public void testVendingMachine() {
        vendingMachine.selectSnack("Coke");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();
        assertEquals(4, vendingMachine.getSelectedSnack().getQuantity());
        assertEquals(0, vendingMachine.getInsertedMoney());

        vendingMachine.selectSnack("Snickers");
        vendingMachine.insertMoney(2.0);
        vendingMachine.dispenseSnack();
        assertEquals(0, vendingMachine.getSelectedSnack().getQuantity());
        assertEquals(0.0, vendingMachine.getInsertedMoney());

        vendingMachine.selectSnack("NonExistingSnack");
        assertEquals(0, vendingMachine.getSelectedSnack().getQuantity());
        assertEquals(0.0, vendingMachine.getInsertedMoney());
    }

    @Test
    public void testVendingMachineDispenseWithoutMoney() {
        vendingMachine.selectSnack("Coke");
        vendingMachine.dispenseSnack();
        assertEquals(5, vendingMachine.getSelectedSnack().getQuantity());
        assertEquals(0.0, vendingMachine.getInsertedMoney());
    }

    @Test
    public void testVendingMachineInsufficientMoney() {
        vendingMachine.selectSnack("Coke");
        vendingMachine.insertMoney(1.0);
        vendingMachine.dispenseSnack();
        assertEquals(5, vendingMachine.getSelectedSnack().getQuantity());
        assertEquals(0, vendingMachine.getInsertedMoney());
    }
}
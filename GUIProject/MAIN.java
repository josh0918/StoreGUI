/**import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Main {
    private static void createAndShowGUI() {
        JFrame jFrame = new JFrame("Hello World Swing Example");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(500, 360);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        System.out.println("help wrld");


        JLabel label = new JLabel("Hello World Swing");
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        label.setBorder(border);
        label.setPreferredSize(new Dimension(150, 100));

        label.setText("Hello World Swing");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        jFrame.add(label);
        jFrame.setVisible(true);
    }
    
  public static void main(String[] args) {
       
    createAndShowGUI();
  }
}**/

// Don't mind the above code it is for later use to build the GUI
import java.util.Scanner;

can you see anything now?


public class MAIN{
    static Scanner input = new Scanner(System.in); // Make input accessible to all classes

    public static void main(String[] args) {
        new MyFrame();
    }
}

// BEAMLAKU CODE
/*class Item {
    String itemName;
    double itemPrice;

    // Default constructor to read item details
    Item() {
        System.out.println("Enter the item name: ");
        itemName = Main.input.nextLine(); // Use the shared input
        System.out.println("Enter the item price: ");
        itemPrice = Main.input.nextDouble();

        Cart cart1 = new Cart();
        cart1.addItem(itemName, itemPrice); // Add the item to the cart

        TaxCalculator taxCalculator1 = new TaxCalculator();
        taxCalculator1.calculatePriceWithTax(itemName, itemPrice, taxCalculator1.taxRate);
    }

    Item(String itemName, double itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}*/
abstract class Item {
    private String itemName;
    private double itemPrice;

    public Item(String name, double price) {
        this.itemName = name;
        this.itemPrice = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    // Abstract method for calculating price with tax
    public abstract double calculatePriceWithTax(double taxRate);
}

// RegularItem Class
class RegularItem extends Item {

    public RegularItem(String name, double price) {
        super(name, price);
    }

    @Override
    public double calculatePriceWithTax(double taxRate) {
        return getItemPrice() + (getItemPrice() * taxRate / 100);
    }
}

// DiscountedItem Class
class DiscountedItem extends Item {
    private double discountRate; // Discount rate in percentage

    public DiscountedItem(String name, double price, double discountRate) {
        super(name, price);
        this.discountRate = discountRate;
    }

    @Override
    public double calculatePriceWithTax(double taxRate) {
        double discountedPrice = getItemPrice() - (getItemPrice() * discountRate / 100);
        return discountedPrice + (discountedPrice * taxRate / 100);
    }
}


// JOSHUA CODE
class Cart  {

    RegularItem[] itemArr = new RegularItem[50];
    public static int arrLen = 0;

    
    public void addItem(String name, double price)
    {
    	RegularItem i1 = new RegularItem(name,price);
    	itemArr[arrLen] = i1;
    	arrLen++;
    }
    
    public int rmItem(String name)
    {
    	boolean found = false;
    	if (arrLen > 0)
    	{
    		for (int i = 0; i < arrLen; i++)
    		{
    			if (itemArr[i].getItemName().equalsIgnoreCase(name))
    			{
    				itemArr[i] = null;
    				found = true;
    				arrLen--;
    				
    			}
    			if (i != 50 && found == true)
    			{
    				itemArr[i] = itemArr[i+1];
    			}
    		}
    		
    		return 0;
    	}
		return 0;
    }

    public double calculateTotalPriceWithTax(double taxRate) 
    {
        double price = 0;

        for (int i = 0; i < arrLen; i++)
        {

            price += itemArr[i].calculatePriceWithTax(taxRate);

        }

        return price;
        
    }

    public double calculateTotalPriceWithOutTax()
    {

        return calculateTotalPriceWithTax(0);

    }

    public boolean dupCheck(String name) // check for duplicate values such as names
    {
        if (arrLen != 0)
        {
            for (int i = 0; i < arrLen; i++) 
            {
                if (itemArr[i].getItemName().equals(name))
                {
                    return true;
                }
            }

            return false;
        } 
        else
        {
            System.out.println("No items in cart");
            return false;
        }

    }
    public void displayItems()
    {
        for (int i = 0; i < arrLen; i++)
            {
                System.out.println(itemArr[i].getItemName() + " " + itemArr[i].getItemPrice());
            }
    }
}

// ENKOPAZION CODE
class TaxCalculator {
    Scanner TaxCalculatorInput = new Scanner(System.in);
    double taxRate, totalPrice;

    // Default constructor to read tax rate
    TaxCalculator() {
        System.out.println("Enter the tax rate: ");
        taxRate = TaxCalculatorInput.nextDouble();
    }

    TaxCalculator(double taxRate, String name, double price) {
        this.taxRate = taxRate;
    }

    public static double calculatePriceWithTax(String itemName, double totalPrice, double taxRate) {
        double priceWithTax = totalPrice + ((taxRate / 100) * totalPrice);
        System.out.println("Total  price with tax is " + priceWithTax);
        return priceWithTax;
    }
}

    


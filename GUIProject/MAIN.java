
package javase;

import java.util.Scanner;



public class MAIN{
    static Scanner input = new Scanner(System.in); // Make input accessible to all classes

    public static void main(String[] args) {
        new MyFrame();
    }
}


abstract class Item {
    private String itemName;
    private double itemPrice;
    private int quantity;

    public Item(String name, double price) {
        this.itemName = name;
        this.itemPrice = price;
        this.quantity = 1;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice*quantity;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity (){
    	return quantity;
    }
   
    public void setQuantity (int quantity){
    	this.quantity = quantity;
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
        return TaxCalculator.calculatePriceWithTax(getItemPrice(), taxRate);
    }
}


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

            price += (itemArr[i].getQuantity() *itemArr[i].getItemPrice());

        }

        return TaxCalculator.calculatePriceWithTax(price,taxRate);
        
    }

    public double calculateTotalPriceWithOutTax()
    {

        return calculateTotalPriceWithTax(0);

    }

   
}

// Tax Calculator Class to calculate total price wiht and with out (tax rate = 0 ) tax
class TaxCalculator {
    double taxRate, totalPrice;

    // Constructor
    TaxCalculator(double taxRate, String name, double price) {
        this.taxRate = taxRate;
    }

    public static double calculatePriceWithTax(double totalPrice, double taxRate) {
        double priceWithTax = totalPrice + ((taxRate / 100) * totalPrice);
        return priceWithTax;
    }
}

    


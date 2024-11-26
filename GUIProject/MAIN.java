
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

    RegularItem[] itemArr = new RegularItem[50]; // creates object itemArr with dataType RegularItem
    public static int arrLen = 0; // counter for the number of items in itemArr

    // adds new items
    public void addItem(String name, double price)
    {
    	RegularItem i1 = new RegularItem(name,price); // create new instance of Regular item
    	itemArr[arrLen] = i1; // adds new item into item array
    	arrLen++; 
    }
    
    // removes items
    public void rmItem(String name)
    {
    	boolean found = false; // boolean used to indicate the status of the search
    	if (arrLen > 0)  // if array is no empty
    	{
    		for (int i = 0; i < arrLen; i++)
    		{
    			if (itemArr[i].getItemName().equalsIgnoreCase(name))
    			{
    				itemArr[i] = null;  // removes item from array
    				found = true;
    				arrLen--;
    				
    			}
    			if (i != 50 && found == true) // if loop is not on the final item in the array and is item was found
    			{
    				itemArr[i] = itemArr[i+1]; // shift items one step back. Used to rearrange the array.
    			}
    		}
    	}
    }

    // calculates the total price of all the items with tax
    public double calculateTotalPriceWithTax(double taxRate) 
    {
        double price = 0;

        for (int i = 0; i < arrLen; i++)
        {

            price += (itemArr[i].getQuantity() *itemArr[i].getItemPrice()); // product of itemprice and quantity of item

        }

        return TaxCalculator.calculatePriceWithTax(price,taxRate); // calls TaxCalulator class to find the price with tax, and then return that value
        
    }
    
    // calculates the total price wihtout tax
    public double calculateTotalPriceWithOutTax()
    {

        return calculateTotalPriceWithTax(0); // call the calculateTotalPriceWithTax method in the cart class.

    }

   
}


// Tax Calculator Class to calculate total price wiht and with out (tax rate = 0 ) tax
class TaxCalculator {
    double taxRate, totalPrice;

    // Constructor
    TaxCalculator() {

    }

    public static double calculatePriceWithTax(double totalPrice, double taxRate) {
        double priceWithTax = totalPrice + ((taxRate / 100) * totalPrice);
        return priceWithTax;
    }
}

    


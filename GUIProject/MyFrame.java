package javase;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public  class MyFrame extends JFrame implements ActionListener
{
	//global variables for the main components
	private Cart c1 = new Cart();    
   	private JPanel productPanel;		
   	private JLabel totalPriceLabel;
    	private JPanel totalPricePanel;
    	private JTextField taxRate;
    
	MyFrame()
	{
		ImageIcon image = new ImageIcon("shopping-cart.png");  //object "image" which stores the Icon of the frame
		
		//creating button to add items
		JButton addButton = new JButton("+");     
		addButton.setBounds(0,0,100,100);
		addButton.setFocusable(false);
		addButton.addActionListener(e -> showAddProductDialog());
		
		//creates button to remove items
		JButton removeButton = new JButton("-");
		removeButton.setBounds(0,0,100,100);
		removeButton.addActionListener(e -> showRemoveProductDialog());
		removeButton.setFocusable(false);

		//Creates the text for the price
		JLabel prePriceLabel = new JLabel("Total Price at ");
		prePriceLabel.setFont(new Font("MV Boli" , Font.PLAIN,20)); // sets Font for text
		
		//creates textfield to enter taxrate
		taxRate = new JTextField("0",2);
		taxRate.setFont(new Font("MV Boli" , Font.PLAIN,20));
		taxRate.addActionListener(e -> {updateTotalPrice();updateProductPane();});
		
		//create JLabel with contains the total price of all items
		totalPriceLabel = new JLabel("%  AED 0.0");
		totalPriceLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		
		//creates JPanel which holds the details of all the items in the cart
		productPanel = new JPanel();
		productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS)); //BoxLayout.Y_AXIS arranges all new components vertically
		
        	//gives productPanel the ability to scroll when components inside productPanel exceed set Bounds
        	JScrollPane scrollPane = new JScrollPane(productPanel);
        	scrollPane.setBackground(Color.GRAY);
        
        	//creates JPanel to hold the price of all items in cart
        	totalPricePanel = new JPanel();
        	totalPricePanel.setPreferredSize(new Dimension(0,70)); // sets width of the panel to height 70
        
        	totalPricePanel.setLayout(new FlowLayout(FlowLayout.LEADING)); //FlowLayout.LEADING set alignment of the components the the left side
        	totalPricePanel.add(prePriceLabel);
        	totalPricePanel.add(taxRate);
        	totalPricePanel.add(totalPriceLabel);
        	
        	// create JPanel that holds the add and remove button
        	JPanel optionsPane = new JPanel();
        	optionsPane.setLayout(new FlowLayout(FlowLayout.CENTER,200,20)); //FlowLayout.CENTER set alignment of the components the the Center
       	 	optionsPane.setPreferredSize(new Dimension(70,0));
        	optionsPane.add(addButton);
        	optionsPane.add(removeButton);
        
        	//creates the JFrame that will house all components
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // sets what happens when we exit button is pressed
		this.setTitle("Simplified Shopping Cart System"); // title
		this.setIconImage(image.getImage()); 
		this.setLayout(new BorderLayout());
		this.setSize(1000,600);
		this.add(totalPricePanel,BorderLayout.SOUTH); //sets totalPricePanel as a border at the SOUTH of the frame
		this.add(optionsPane,BorderLayout.EAST);  //sets optionsPane as a border at the EAST of the frame
		this.add(scrollPane,BorderLayout.CENTER); //sets scrollPane as a border at the CENTER of the frame
		this.setVisible(true); // makes the JFrame visible
		
	}
	


	public void showAddProductDialog()
	{
		JTextField nameField = new JTextField(10); //creates textfield for user to enter name
		JTextField priceField = new JTextField(10);//creates textfield for user to enter name
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Item Name: "));
		panel.add(nameField);
		panel.add(new JLabel("Price:"));
		panel.add(priceField);
		
		int result = JOptionPane.showConfirmDialog(this,panel,"Add New Product",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); //formats the panel in the form of a dialog box
		
		if (result == JOptionPane.OK_OPTION) // if okay is pressed.
		{
			String itemName = nameField.getText(); //stores text in textfield into string variable
			double itemPrice = Double.parseDouble(priceField.getText());  // convert text in textfield priceField and stores in double variable
			
			addProd(itemName,itemPrice); // call addProd method to add new product
		}
	}
	
	public void showRemoveProductDialog()
	{
		JTextField nameField = new JTextField(10);
		
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Item Name: "));
		panel.add(nameField);
		panel.add(Box.createHorizontalStrut(15));
		
		int result = JOptionPane.showConfirmDialog(this,panel,"Remove Product",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (result == JOptionPane.OK_OPTION)
		{
			String itemName = nameField.getText();
			
			rmProd(itemName);
		}
	}
	
	public void addProd(String itemName, double itemPrice)
	{
		c1.addItem(itemName.trim(),itemPrice);
		
		updateProductPane();
		updateTotalPrice();
		
	}
	public void rmProd(String itemName)
	{
		c1.rmItem(itemName.trim());
		
		updateProductPane();
		updateTotalPrice();
		
	}
	
	
	
	public void updateProductPane()
	{
		if (c1.arrLen != 0)
		{
			productPanel.removeAll();
			for (int i = 0; i < c1.arrLen;i++)
			{
				
				JPanel panel = productLabelCreation(c1.itemArr[i]);
				productPanel.add(panel);
				productPanel.revalidate();
				productPanel.repaint();
			}
		}
		else
		{
			productPanel.removeAll();
			productPanel.revalidate();
			productPanel.repaint();

		
		}
		
		
	}
	
	public void updateTotalPrice()
	{
		totalPriceLabel.setText("%  AED " + c1.calculateTotalPriceWithTax(Double.parseDouble(taxRate.getText())));
	}

	
	// Iterates in a for loop to pass each item from our array
	// JPanel retruning type class to create panel containg products name and price 
	public JPanel productLabelCreation(RegularItem item)
	{	
		// GUI Panel instance declaration
		JPanel panel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel pricePanel = new JPanel();
		JPanel rmPanel = new JPanel();

		//GUI Lable: Item name and price labels declaration and modification
		JLabel nameLabel = new JLabel(item.getItemName());
		nameLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		JLabel priceLabel = new JLabel("" + TaxCalculator.calculatePriceWithTax(item.getItemPrice(),Double.parseDouble(taxRate.getText())));
		priceLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		
		// GUI Spinner to increase the quantity of items in the cart
		JSpinner quantityBox = new JSpinner(new SpinnerNumberModel(1,1,15,1));
		quantityBox.setFont(new Font("MV Boli" , Font.BOLD,15));
		quantityBox.setValue(item.getQuantity());
		quantityBox.addChangeListener(e -> {item.setQuantity((int)quantityBox.getValue());updateProductPane();updateTotalPrice();});
		
		// GUI Button: Remove Button to remove the item from our panel
		JButton removeButton = new JButton(" - ");
		removeButton.setFocusable(false);
		removeButton.setBounds(200,200,15,15);
		removeButton.addActionListener(e -> rmProd(item.getItemName()));
	
		// Sets the layout and dimension of our general panel
		panel.setLayout(new BorderLayout(30,0));
		panel.setPreferredSize(new Dimension(0, 100));

		// Adds the items name, price, and remove button panels to general panel
		panel.add(namePanel,BorderLayout.CENTER);
		panel.add(pricePanel,BorderLayout.EAST);
		panel.add(rmPanel,BorderLayout.WEST);

		// Sets the layout of item name, price, and remove button
		namePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		pricePanel.setLayout(new FlowLayout(FlowLayout.LEADING,20,0));
		rmPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Each panel adds its own content
		namePanel.add(nameLabel);
		pricePanel.add(quantityBox);
		pricePanel.add(priceLabel);
		rmPanel.add(removeButton);

		// This panel will be returned back to the caller JPanel object
		return panel;
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	
}
	
	



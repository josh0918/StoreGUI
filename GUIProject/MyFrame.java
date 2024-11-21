
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;



public  class MyFrame extends JFrame implements ActionListener
{
	public Cart c1 = new Cart();
    public JPanel productPanel;
    public JLabel totalPriceLabel;
    public JPanel totalPricePanel;
    public JTextField taxRate;
    
	MyFrame()
	{
		
		
		               
		JButton addButton = new JButton();
		addButton.setBounds(0,0,100,100);
		addButton.setText("+");
		addButton.setFocusable(false);
		addButton.addActionListener(e -> showAddProductDialog());
		
		
		JButton removeButton = new JButton();
		removeButton.setBounds(0,0,100,100);
		removeButton.setText("-");
		removeButton.addActionListener(e -> showRemoveProductDialog());
		removeButton.setFocusable(false);

		JLabel prePriceLabel = new JLabel("Total Price at ");
		prePriceLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		
		taxRate = new JTextField("0",2);
		taxRate.setFont(new Font("MV Boli" , Font.PLAIN,20));
		taxRate.addActionListener(e -> updateTotalPrice());
		
		totalPriceLabel = new JLabel("%  AED 0.0");
		totalPriceLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		
		
		productPanel = new JPanel();
		productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
		
        
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setBackground(Color.GRAY);
        
        totalPricePanel = new JPanel();
        totalPricePanel.setPreferredSize(new Dimension(0,70));
        totalPricePanel.add(prePriceLabel);
        totalPricePanel.add(taxRate);
        totalPricePanel.add(totalPriceLabel);
        totalPricePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        
        JPanel optionsPane = new JPanel();
        optionsPane.setLayout(new FlowLayout(FlowLayout.CENTER,200,20));
        optionsPane.setPreferredSize(new Dimension(70,0));
        optionsPane.add(addButton);
        optionsPane.add(removeButton);
        

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1000,600);
		this.add(totalPricePanel,BorderLayout.SOUTH);
		this.add(optionsPane,BorderLayout.EAST);
		this.add(scrollPane,BorderLayout.CENTER);
		this.setVisible(true);
		
	}
	

	public void showAddProductDialog()
	{
		JTextField nameField = new JTextField(10);
		JTextField priceField = new JTextField(10);
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Item Name: "));
		panel.add(nameField);
		panel.add(Box.createHorizontalStrut(15));
		panel.add(new JLabel("Price:"));
		panel.add(priceField);
		
		int result = JOptionPane.showConfirmDialog(this,panel,"Add New Product",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if (result == JOptionPane.OK_OPTION)
		{
			String itemName = nameField.getText();
			double itemPrice = Double.parseDouble(priceField.getText());
			
			addProd(itemName,itemPrice);
		}
	}
	
	public void showRemoveProductDialog()
	{
		JTextField nameField = new JTextField(10);
		
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Item Name: "));
		panel.add(nameField);
		panel.add(Box.createHorizontalStrut(15));
		
		int result = JOptionPane.showConfirmDialog(this,panel,"Add New Product",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
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
			System.out.println(c1.arrLen);
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
		totalPriceLabel.setText("%  AED " + TaxCalculator.calculatePriceWithTax(""	,c1.calculateTotalPriceWithOutTax(),Double.parseDouble(taxRate.getText())));
	}
	
	public JPanel productLabelCreation(RegularItem item)
	{
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel(item.getItemName());
		JLabel priceLabel = new JLabel("" +item.getItemPrice());
		nameLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		priceLabel.setFont(new Font("MV Boli" , Font.PLAIN,20));
		nameLabel.setVerticalAlignment(JLabel.TOP);
		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		priceLabel.setVerticalAlignment(JLabel.TOP);
		
		JButton removeButton = new JButton(" - ");
		removeButton.setFocusable(false);
		removeButton.setBounds(200,200,15,15);
		removeButton.addActionListener(e -> rmProd(item.getItemName()));
		removeButton.setHorizontalAlignment(JLabel.RIGHT);
		
		panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		panel.setPreferredSize(new Dimension(100,100));
		panel.add(nameLabel);
		panel.add(new JLabel("                                                                                                    "));
		panel.add(priceLabel);
		panel.add(new JLabel("                                                                                                    "));
		panel.add(removeButton);
		return panel;
	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	
}
	
	


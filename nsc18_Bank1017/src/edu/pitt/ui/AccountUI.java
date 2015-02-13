/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:The second window a user will encounter.  This will enable the user to view
 * their accounts, make withdrawals and deposits, and proceed to a new screen that will show them their transactions for that particular account.
 * 
 * 
 * 
 */

package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import edu.pitt.bank.Account;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountUI.
 */
public class AccountUI {

	/** The account frame. */
	private JFrame accountFrame;

	/** The amount field. */
	private JTextField amountField;

	/** The account owner. */
	private Customer accountOwner;

	/**
	 * Create the application.
	 *
	 * @param c the c
	 */
	public AccountUI(Customer c) {
		accountOwner = c;
		initialize();
		accountFrame.setVisible(true);

		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JTextArea balanceArea = new JTextArea();
		JTextArea penaltyArea = new JTextArea();
		JTextArea interestRateArea = new JTextArea();
		JTextArea accountTypeArea = new JTextArea();

		String userID =accountOwner.getCustomerID();
		String sql = "SELECT fk_accountID FROM customer_account WHERE fk_customerID='"+accountOwner.getCustomerID() +"' ;"; 

		ArrayList<Account> accts = new ArrayList<Account>();

		
		DbUtilities db = new MySqlUtilities();
		ArrayList<String> groupID = null;
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next() ){

				Account a = new Account(rs.getString("fk_accountID"));
				accts.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}




		accountFrame = new JFrame();
		accountFrame.setTitle("Bank 1017 Account Details");
		accountFrame.setBounds(100, 100, 450, 300);
		accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accountFrame.getContentPane().setLayout(null);



		JComboBox accountComboBox = new JComboBox();
		accountComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				
				
				Account selectedAccount =   (Account) accountComboBox.getSelectedItem();
				
				balanceArea.setText("Balance: "+ ((Account) accountComboBox.getSelectedItem()).getBalance());
				accountTypeArea.setText("Account Type: "+ ((Account) accountComboBox.getSelectedItem()).getType());
				penaltyArea.setText("Penalty: "+ ((Account) accountComboBox.getSelectedItem()).getPenalty());
				interestRateArea.setText("Interest Rate: "+ ((Account) accountComboBox.getSelectedItem()).getInterestRate());

			}
		});

		

		accountComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		accountComboBox.setBounds(119, 76, 305, 26);
		accountFrame.getContentPane().add(accountComboBox);

		for(int i = 0; i < accts.size(); i++)
		{
			accountComboBox.addItem(accts.get(i));
		}

		JLabel accountLabel = new JLabel("Your Accounts:");
		accountLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		accountLabel.setBounds(10, 74, 99, 28);
		accountFrame.getContentPane().add(accountLabel);

		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amountTest = amountField.getText();
				boolean test=true; 
				
				for (int i = 0; i<amountTest.length();i++){
					if (!((amountTest.charAt(i)>='0' && amountTest.charAt(i)<='9')||amountTest.charAt(i)=='.')){
						test = false;
					}
				}
				if (test){
					 Double amount = Double.parseDouble(amountTest);
					((Account) accountComboBox.getSelectedItem()).deposit(amount);
					balanceArea.setText("Balance: "+ ((Account) accountComboBox.getSelectedItem()).getBalance());
					amountField.setText("");

					
				}else{
					amountField.setText("");
					JOptionPane.showMessageDialog(null, "You have entered an incorrect value");
				}
			}
		});
		depositButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		depositButton.setBounds(236, 149, 89, 26);
		accountFrame.getContentPane().add(depositButton);

		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amountTest = amountField.getText();
				double x =((Account) accountComboBox.getSelectedItem()).getBalance();
				boolean test=true; 
				
				for (int i = 0; i<amountTest.length();i++){
					if (!((amountTest.charAt(i)>='0' && amountTest.charAt(i)<='9')||amountTest.charAt(i)=='.')){
						test = false;
					}

				}
				
				if (test){
						double y = Double.parseDouble(amountTest);
						if(y<=x){
						 Double amount = Double.parseDouble(amountTest);
						((Account) accountComboBox.getSelectedItem()).withdraw(amount);
						balanceArea.setText("Balance: "+ ((Account) accountComboBox.getSelectedItem()).getBalance());
						amountField.setText("");
						}else{
							amountField.setText("");
							JOptionPane.showMessageDialog(null, "You cant withdraw that much");
						}
				}else{
					amountField.setText("");
					JOptionPane.showMessageDialog(null, "You have entered an incorrect value");
				}
			}
		});
		withdrawButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		withdrawButton.setBounds(335, 149, 89, 26);
		accountFrame.getContentPane().add(withdrawButton);

		amountField = new JTextField();
		amountField.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		amountField.setBounds(271, 113, 153, 25);
		accountFrame.getContentPane().add(amountField);
		amountField.setColumns(10);
		
		JLabel amountLabel = new JLabel("Amount:");
		amountLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountLabel.setBounds(184, 113, 99, 25);
		accountFrame.getContentPane().add(amountLabel);

		JButton showTransButton = new JButton("Show Transactions");
		showTransButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		showTransButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TransactionUI ad = new TransactionUI(((Account) accountComboBox.getSelectedItem()));
			}
		});
		showTransButton.setBounds(172, 207, 153, 26);
		accountFrame.getContentPane().add(showTransButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Thank you, goodbye");
				MySqlUtilities db = new MySqlUtilities();
				db.connectionClose();
				System.exit(0);
			}
		});
		exitButton.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		exitButton.setBounds(335, 207, 89, 26);
		accountFrame.getContentPane().add(exitButton);

		JTextArea permissionsArea = new JTextArea();
		permissionsArea.setLineWrap(true);
		permissionsArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		Security s = new Security();
	
		
		System.out.println(userID);
		
		// takes an array of all of the User's account permissions and prints them along with the welcome
		permissionsArea.setText("Hello " + accountOwner.getFirstName()+" "+accountOwner.getLastName() +", "
				+ "welcome to 1017 bank.\n You have the following permissions in this system:"+ s.listUserGroups(userID));
		permissionsArea.setBackground(SystemColor.menu);
		permissionsArea.setEditable(false);
		permissionsArea.setBounds(10, 10, 414, 68);
		accountFrame.getContentPane().add(permissionsArea);

		accountTypeArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		accountTypeArea.setText("Account Type: "+ ((Account) accountComboBox.getSelectedItem()).getType());
		accountTypeArea.setEditable(false);
		accountTypeArea.setBackground(SystemColor.menu);
		accountTypeArea.setBounds(10, 113, 152, 22);
		accountFrame.getContentPane().add(accountTypeArea);

		balanceArea.setText("Balance: "+ ((Account) accountComboBox.getSelectedItem()).getBalance());
		balanceArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		balanceArea.setEditable(false);
		balanceArea.setBackground(SystemColor.menu);
		balanceArea.setBounds(10, 134, 152, 18);
		accountFrame.getContentPane().add(balanceArea);

		interestRateArea.setText("Interest Rate: "+ ((Account) accountComboBox.getSelectedItem()).getInterestRate());
		interestRateArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		interestRateArea.setEditable(false);
		interestRateArea.setBackground(SystemColor.menu);
		interestRateArea.setBounds(10, 153, 152, 18);
		accountFrame.getContentPane().add(interestRateArea);

		penaltyArea.setText("Penalty: "+ ((Account) accountComboBox.getSelectedItem()).getPenalty());
		penaltyArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		penaltyArea.setEditable(false);
		penaltyArea.setBackground(SystemColor.menu);
		penaltyArea.setBounds(10, 178, 152, 18);
		accountFrame.getContentPane().add(penaltyArea);
	}
}

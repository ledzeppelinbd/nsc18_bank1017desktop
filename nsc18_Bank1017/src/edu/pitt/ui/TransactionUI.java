/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description: The "final" screen a user may see, this screen simply lists basic information about the type of transactions they have done, 
 * their date, and their amount.
 * 
 * 
 * 
 */





package edu.pitt.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.bank.Account;
import edu.pitt.utilities.DbUtilities;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionUI.
 */
public class TransactionUI {

	/** The acct. */
	private Account acct;
	/** The frame. */
	private JFrame transFrame;
	
	/** The transaction pane. */
	private JScrollPane transactionPane;
	
	/** The tbl transactions. */
	private JTable tblTransactions;
	
	
	/**
	 * Create the application.
	 *
	 * @param a the a
	 */
	public TransactionUI(Account a) {
		acct = a;
		initialize();
		
		transFrame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		transFrame = new JFrame();
		transFrame.setTitle("Account Transactions");
		transFrame.setBounds(100, 100, 450, 300);
		transFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		transactionPane = new JScrollPane();
		transFrame.getContentPane().add(transactionPane);
		MySqlUtilities db = new MySqlUtilities();
		String[] cols = { "Transaction Type"+"Transaction Date"+"Amount"};
		String sql = "SELECT type,transactionDate, amount FROM transaction WHERE accountID='"+acct.getAccountID()+"';";
		
		try {
			DefaultTableModel transactionList = db.getDataTable(sql);
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.BLACK);
			transactionPane.getViewport().add(tblTransactions);
			
		} catch (SQLException e) {
			e.printStackTrace();
			ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
			ErrorLogger.log(e.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
		}
	}

}

/*
 * Author: Nick Campbell (Ledzeppelinbd)
 * 
 * Version:1.4 The final version, the one version..... TO RULE THEM ALL
 * 
 * Date:2.10.15
 * 
 * Description:The main method of the program that will bring up the initial log in screen
 * This will call the other methods from the bank package to ensure that the user's credentials
 * are valid then it will proceed to the Account UI.
 * 
 * 
 * 
 */

package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginUI.
 */
public class LoginUI {



	/** The bank frame. */
	private JFrame bankFrame;

	/** The txt login name. */
	private JTextField txtLoginName;

	/** The txt password. */
	private JTextField txtPassword;

	/** The login button. */
	private JButton loginButton;

	/** The exit button. */
	private JButton exitButton;

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.bankFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		bankFrame = new JFrame();
		bankFrame.setTitle("Bank 1017 Login");
		bankFrame.setBounds(100, 100, 450, 300);
		bankFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bankFrame.getContentPane().setLayout(null);

		txtLoginName = new JTextField();
		txtLoginName.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtLoginName.setBounds(168, 69, 169, 20);
		bankFrame.getContentPane().add(txtLoginName);
		txtLoginName.setColumns(10);


		JLabel loginLabel = new JLabel("Login Name: ");
		loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		loginLabel.setBounds(84, 68, 86, 20);
		bankFrame.getContentPane().add(loginLabel);

		JLabel passwordVariable = new JLabel("Password:");
		passwordVariable.setFont(new Font("Times New Roman", Font.BOLD, 14));
		passwordVariable.setBounds(84, 118, 86, 20);
		bankFrame.getContentPane().add(passwordVariable);

		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtPassword.setBounds(168, 119, 169, 20);
		bankFrame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);


		/*
		 * This was the example he used in class 
		 */
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String logName=txtLoginName.getText();

				boolean correctLogin;
				int valid = 0;
				String sPassword = txtPassword.getText();


				try {

					int pin = Integer.parseInt(sPassword);
					if (pin >=0);
					Security s = new Security();
					Customer c= s.validateLogin(txtLoginName.getText(),pin);


					if (c !=null){
						AccountUI ad = new AccountUI(c);
						bankFrame.setVisible(false);
					}
					else{
						JOptionPane.showMessageDialog(null,"Invalid Login");
					}	
				} catch (NumberFormatException e2) {
					// TODO: handle exception

					txtLoginName.setText("");
					txtPassword.setText("");
					e2.printStackTrace();
					ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
					ErrorLogger.log(e2.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
					JOptionPane.showMessageDialog(null,"Error, not a numeric value");

				}catch(NullPointerException e4){
					txtLoginName.setText("");
					txtPassword.setText("");
					e4.printStackTrace();
					ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
					ErrorLogger.log(e4.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
					JOptionPane.showMessageDialog(null,"Too many connections to database, please try again later");
				} catch(Exception e3){
					txtLoginName.setText("");
					txtPassword.setText("");
					e3.printStackTrace();
					ErrorLogger.log("Invalid");	 			// use this for all error handling so it logs
					ErrorLogger.log(e3.getMessage());		// use this for all error handling so it logs, remember to also log the query along with the errors
					JOptionPane.showMessageDialog(null,"Unable to connect to Database, an error has occured");
				}
			}
		});




		loginButton.setBounds(184, 188, 99, 20);
		bankFrame.getContentPane().add(loginButton);



		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Thank you, goodbye");

				MySqlUtilities db = new MySqlUtilities(); //this is how you disconnect from the database
				db.connectionClose();

				System.exit(0);
			}
		});
		exitButton.setBounds(304, 188, 99, 20);
		bankFrame.getContentPane().add(exitButton);
	}

	/**
	 * Gets the txt login name.
	 *
	 * @return the txt login name
	 */
	public String getTxtLoginName() {
		return txtLoginName.getText();
	}

}



package edu.metrostate.ics499.cstaff;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.awt.event.ActionEvent;


/*
 * Author: Morgan
 * Cook Staff window to add menu items
 */
public class CStaff_AddMenu {
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;
	private JFrame frame;
	private JTextField itemNameTextField;
	private JLabel lblMenuItemName;
	private JLabel lblMenuItemDescription;
	private JLabel successLabel;
	private static Connection con;
	private static PreparedStatement stmt;
	private JTextField itemDescTextField;
	private static String[] arguments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		arguments = args;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CStaff_AddMenu window = new CStaff_AddMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CStaff_AddMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		readSettings();
		frame = new JFrame();
		frame.setBounds(100, 100, 564, 368);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblMenuItemName = new JLabel("Menu Item Name:");
		lblMenuItemName.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemName.setBounds(23, 54, 160, 24);
		frame.getContentPane().add(lblMenuItemName);

		lblMenuItemDescription = new JLabel("Menu Item Description:");
		lblMenuItemDescription.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemDescription.setBounds(23, 154, 197, 24);
		frame.getContentPane().add(lblMenuItemDescription);

		itemNameTextField = new JTextField();
		itemNameTextField.setFont(new Font("Georgia", Font.PLAIN, 14));
		itemNameTextField.setColumns(10);
		itemNameTextField.setBounds(230, 56, 271, 24);
		frame.getContentPane().add(itemNameTextField);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CStaff_Homepage.main(arguments);
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Georgia", Font.PLAIN, 10));
		btnNewButton.setBounds(23, 287, 85, 21);
		frame.getContentPane().add(btnNewButton);

		JButton btnDone = new JButton("Submit");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkConditions()) {
					String itemName = itemNameTextField.getText();
					String itemDesc = itemDescTextField.getText();
					int itemNum = createMenuItem(itemName, itemDesc);
					if(itemNum > 0) {
						successLabel.setText("Menu Item: " + itemName + " (" + itemNum + ") has been successfully added");
					}					
				}
			}
		});
		btnDone.setFont(new Font("Georgia", Font.PLAIN, 10));
		btnDone.setBounds(414, 287, 85, 21);
		frame.getContentPane().add(btnDone);
		
		successLabel = new JLabel("");
		successLabel.setFont(new Font("Georgia", Font.PLAIN, 16));
		successLabel.setBounds(23, 243, 496, 13);
		frame.getContentPane().add(successLabel);
		
		itemDescTextField = new JTextField();
		itemDescTextField.setFont(new Font("Georgia", Font.PLAIN, 14));
		itemDescTextField.setColumns(10);
		itemDescTextField.setBounds(230, 156, 271, 24);
		frame.getContentPane().add(itemDescTextField);
	}
	/**
	 * Reads in settings from settings.conf
	 */
	private void readSettings() {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("settings.conf");
		} catch (FileNotFoundException ex) {
			System.out.println("settings.conf not found");
			System.exit(1);
		}
		try {
			prop.load(is);
		} catch (IOException e1) {
			System.out.println("An error occured");
			System.exit(1);
			
		}
		MYSQL_URL = "jdbc:mysql://" + prop.getProperty("MYSQL_IP") +
				":" + prop.getProperty("MYSQL_PORT") + "/" 
				+ prop.getProperty("MYSQL_SCHEMA") + "?useSSL=false";
		MYSQL_USERNAME = prop.getProperty("MYSQL_USER");
		MYSQL_PASSWORD = prop.getProperty("MYSQL_PASS");
	}
	/**
	 * A class to add a menu item into the database. The ID is automatically added in.
	 * @param itemName - Name of the menu item being added
	 * @param itemDescription - A description of the item being added
	 * @return 
	 */
	public int createMenuItem(String itemName, String itemDescription) {
		try {
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL,MYSQL_USERNAME,MYSQL_PASSWORD);
			stmt =  con.prepareStatement
					("insert into menuitems (ItemName, ItemDesc) values ( ?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1,itemName);
			stmt.setString(2, itemDescription);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();  
			return rs.getInt(1);
		} catch (SQLException e) {
		}
		return -1;
	}
	
	/**
	 * Checks if all requirements are met for creating a new user
	 * @return true if conditions are met and false otherwise
	 */
	private boolean checkConditions() {
		boolean conditionsOk = true;

		if(itemNameTextField.getText().isBlank()) {
			lblMenuItemName.setForeground(Color.RED);
			successLabel.setText("Item name cannot be blank");
			conditionsOk = false;
		}else {
			successLabel.setText("");
			lblMenuItemName.setForeground(Color.BLACK);
		}
		if(itemDescTextField.getText().isBlank()) {
			lblMenuItemDescription.setForeground(Color.RED);
			successLabel.setText("Item description cannot be blank");
			conditionsOk = false;
		}else {
			successLabel.setText("");
			lblMenuItemDescription.setForeground(Color.BLACK);
		}
		return conditionsOk;
	}
}

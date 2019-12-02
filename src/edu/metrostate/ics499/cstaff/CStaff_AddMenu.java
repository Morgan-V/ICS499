

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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 541, 267);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblMenuItemName = new JLabel("Menu Item Name:");
		lblMenuItemName.setBackground(new Color(255, 255, 255));
		lblMenuItemName.setForeground(new Color(169, 169, 169));
		lblMenuItemName.setFont(new Font("Arial", Font.ITALIC, 17));
		lblMenuItemName.setBounds(63, 54, 146, 24);
		frame.getContentPane().add(lblMenuItemName);

		lblMenuItemDescription = new JLabel("Menu Item Description:");
		lblMenuItemDescription.setForeground(new Color(169, 169, 169));
		lblMenuItemDescription.setFont(new Font("Arial", Font.ITALIC, 17));
		lblMenuItemDescription.setBounds(23, 98, 197, 24);
		frame.getContentPane().add(lblMenuItemDescription);

		itemNameTextField = new JTextField();
		itemNameTextField.setBackground(new Color(255, 255, 255));
		itemNameTextField.setForeground(new Color(47, 79, 79));
		itemNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		itemNameTextField.setColumns(10);
		itemNameTextField.setBounds(230, 56, 271, 24);
		frame.getContentPane().add(itemNameTextField);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBackground(new Color(143, 188, 143));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 10));
		btnNewButton.setBounds(23, 158, 100, 21);
		frame.getContentPane().add(btnNewButton);

		JButton btnDone = new JButton("Submit");
		btnDone.setBackground(new Color(143, 188, 143));
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
		btnDone.setFont(new Font("Arial", Font.BOLD, 10));
		btnDone.setBounds(401, 158, 100, 21);
		frame.getContentPane().add(btnDone);
		
		successLabel = new JLabel("");
		successLabel.setForeground(new Color(169, 169, 169));
		successLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		successLabel.setBounds(23, 199, 496, 21);
		frame.getContentPane().add(successLabel);
		
		itemDescTextField = new JTextField();
		itemDescTextField.setForeground(new Color(47, 79, 79));
		itemDescTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		itemDescTextField.setColumns(10);
		itemDescTextField.setBounds(230, 100, 271, 24);
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
			lblMenuItemDescription.setForeground(Color.RED);
			successLabel.setForeground(Color.RED);
			successLabel.setText("Item name cannot be blank");
			conditionsOk = false;
		}
		else if(itemDescTextField.getText().isBlank()) {
			lblMenuItemDescription.setForeground(Color.RED);
			successLabel.setForeground(Color.RED);
			successLabel.setText("Item description cannot be blank");
			conditionsOk = false;
		}else {
			successLabel.setText("");
			successLabel.setForeground(Color.decode("#A9A9A9"));
			lblMenuItemDescription.setForeground(Color.decode("#A9A9A9"));
			lblMenuItemName.setForeground(Color.decode("#A9A9A9"));
		}
		return conditionsOk;
	}
}

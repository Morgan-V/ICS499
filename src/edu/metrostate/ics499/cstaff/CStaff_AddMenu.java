

package edu.metrostate.ics499.cstaff;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;


/*
 * Author: Morgan
 * Cook Staff window to add menu items
 */
public class CStaff_AddMenu {

	private JFrame frame;
	private JTextField itemNumTextField;
	private JTextField itemNameTextField;
	private JTextArea itemDescTextArea;
	private JLabel menuItemLbl;
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
		frame = new JFrame();
		frame.setBounds(100, 100, 564, 368);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblMenuItemName = new JLabel("Menu Item Name:");
		lblMenuItemName.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemName.setBounds(23, 79, 160, 24);
		frame.getContentPane().add(lblMenuItemName);

		lblMenuItemDescription = new JLabel("Menu Item Description:");
		lblMenuItemDescription.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemDescription.setBounds(23, 154, 197, 24);
		frame.getContentPane().add(lblMenuItemDescription);

		 menuItemLbl = new JLabel("Menu Item Number:");
		menuItemLbl.setFont(new Font("Georgia", Font.PLAIN, 17));
		menuItemLbl.setBounds(23, 25, 160, 24);
		frame.getContentPane().add(menuItemLbl);

		itemNumTextField = new JTextField();
		itemNumTextField.setFont(new Font("Georgia", Font.PLAIN, 10));
		itemNumTextField.setBounds(228, 30, 271, 19);
		frame.getContentPane().add(itemNumTextField);
		itemNumTextField.setColumns(10);

		itemNameTextField = new JTextField();
		itemNameTextField.setFont(new Font("Georgia", Font.PLAIN, 10));
		itemNameTextField.setColumns(10);
		itemNameTextField.setBounds(228, 84, 271, 19);
		frame.getContentPane().add(itemNameTextField);

		// TO-DO: CLOSE WINDOW AND LAUNCH HOMEPAGE
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CStaff_Homepage.main(null);
			}
		});
		btnNewButton.setFont(new Font("Georgia", Font.PLAIN, 10));
		btnNewButton.setBounds(23, 287, 85, 21);
		frame.getContentPane().add(btnNewButton);

		JButton btnDone = new JButton("Submit");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkConditions()) {
					String itemNum = itemNumTextField.getText();
					String itemName = itemNameTextField.getText();
					String itemDesc = itemDescTextField.getText();
					createMenuItem(itemNum, itemName, itemDesc);
					
						successLabel.setText("Menu Item: " + itemName + " has been successfully added");
					
				}
			}
		});
		btnDone.setFont(new Font("Georgia", Font.PLAIN, 10));
		btnDone.setBounds(414, 287, 85, 21);
		frame.getContentPane().add(btnDone);
		
		successLabel = new JLabel("");
		successLabel.setFont(new Font("Georgia", Font.PLAIN, 10));
		successLabel.setBounds(39, 243, 447, 13);
		frame.getContentPane().add(successLabel);
		
		itemDescTextField = new JTextField();
		itemDescTextField.setFont(new Font("Georgia", Font.PLAIN, 10));
		itemDescTextField.setColumns(10);
		itemDescTextField.setBounds(228, 138, 271, 51);
		frame.getContentPane().add(itemDescTextField);
	}
	
	public void createMenuItem(String itemNum, String itemName, String itemDescription) {
		try {
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false","root","root");
			stmt =  con.prepareStatement
					("insert into menuitems (MenuItem, ItemName, ItemDesc) values ( ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, itemNum);
			stmt.setString(2,itemName);
			stmt.setString(3, itemDescription);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();  
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Checks if all requirements are met for creating a new user
	 * @return true if conditions are met and false otherwise
	 */
	private boolean checkConditions() {
		boolean conditionsOk = true;
		if(itemNumTextField.getText().isBlank()) {
			successLabel.setForeground(Color.RED);
			successLabel.setText("Item number cannot be blank");
			menuItemLbl.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			successLabel.setText("");
			menuItemLbl.setForeground(Color.BLACK);
		}
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

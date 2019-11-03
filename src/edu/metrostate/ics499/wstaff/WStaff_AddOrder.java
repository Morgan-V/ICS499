
package edu.metrostate.ics499.wstaff;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/*
 * Author: Morgan
 * Wait Staff window to add orders 
 */
public class WStaff_AddOrder {

	private JFrame frame;
	private JLabel lblMenuItemName;
	private JLabel lblMenuItemDescription;
	private JLabel successLabel;
	private static Connection con;
	private static PreparedStatement stmt;
	private static java.sql.Statement stmt2;
	private JTextField srLabel;
	private JComboBox<Integer> menuComboBox = new JComboBox<Integer>();
	private JComboBox<Integer> tableComboBox = new JComboBox<Integer>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WStaff_AddOrder window = new WStaff_AddOrder();
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
	public WStaff_AddOrder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 564, 368);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		updateMenuItems();
		updateTables();

		lblMenuItemName = new JLabel("Item:");
		lblMenuItemName.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemName.setBounds(23, 54, 160, 24);
		frame.getContentPane().add(lblMenuItemName);

		lblMenuItemDescription = new JLabel("Special Requests:");
		lblMenuItemDescription.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemDescription.setBounds(23, 154, 197, 24);
		frame.getContentPane().add(lblMenuItemDescription);

		// TO-DO: CLOSE WINDOW AND LAUNCH HOMEPAGE
		JButton btnNewButton = new JButton("Done");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});
		btnNewButton.setFont(new Font("Georgia", Font.PLAIN, 10));
		btnNewButton.setBounds(23, 287, 85, 21);
		frame.getContentPane().add(btnNewButton);

		JButton btnDone = new JButton("Add Order");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					int order = (int) menuComboBox.getSelectedItem();
					int table = (int) tableComboBox.getSelectedItem();
					String sr = (String) srLabel.getText(); 
					createOrder(order, table, sr);
						successLabel.setText("Order Added!");
				
				
			}
		});
		btnDone.setFont(new Font("Georgia", Font.PLAIN, 10));
		btnDone.setBounds(414, 287, 85, 21);
		frame.getContentPane().add(btnDone);

		successLabel = new JLabel("");
		successLabel.setFont(new Font("Georgia", Font.PLAIN, 16));
		successLabel.setBounds(218, 243, 301, 13);
		frame.getContentPane().add(successLabel);

		srLabel = new JTextField();
		srLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
		srLabel.setColumns(10);
		srLabel.setBounds(230, 156, 271, 24);
		frame.getContentPane().add(srLabel);

		JButton button = new JButton("Back");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

			}
		});
		button.setFont(new Font("Georgia", Font.PLAIN, 10));
		button.setBounds(218, 287, 85, 21);
		frame.getContentPane().add(button);

		JLabel lblForTable = new JLabel("For Table:");
		lblForTable.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblForTable.setBounds(23, 103, 160, 24);
		frame.getContentPane().add(lblForTable);

		menuComboBox.setBounds(230, 58, 269, 21);
		frame.getContentPane().add(menuComboBox);

		tableComboBox.setBounds(230, 107, 269, 21);
		frame.getContentPane().add(tableComboBox);


	}

	private void updateMenuItems() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false", "root", "root");
			stmt2 = con.createStatement();
			String s = "select * from menuitems;";
			ResultSet rs = stmt2.executeQuery(s);
			while (rs.next()) {
				int itemName = rs.getInt("MenuItem");
				menuComboBox.addItem(itemName);
			}
		} catch (SQLException e) {
		}
	}

	private void updateTables() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false", "root", "root");
			stmt2 = con.createStatement();
			String s = "select * from tables;";
			ResultSet rs = stmt2.executeQuery(s);
			while (rs.next()) {
				int tableID = rs.getInt("TableID");
				tableComboBox.addItem(tableID);
			}
		} catch (SQLException e) {
		}
	}

	public void createOrder(int menuItem, int table, String sr) {
		try {
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false", "root",
					"root");
			stmt = con.prepareStatement("insert into orders (MenuItem, TableID, SpecialRequest) values (?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, menuItem);
			stmt.setInt(2, table);
			stmt.setString(3, sr);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
		} catch

		(SQLException e) {
		}
	}

}

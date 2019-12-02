
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
import javax.swing.JComboBox;
import java.awt.Color;

/**
 * 
 * @author Morgan
 * Class to add an order into the system
 *
 */
public class WStaff_AddOrder {
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;
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
		readSettings();
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 564, 368);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		updateMenuItems();
		updateTables();

		lblMenuItemName = new JLabel("Item:");
		lblMenuItemName.setForeground(new Color(169, 169, 169));
		lblMenuItemName.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemName.setBounds(23, 54, 160, 24);
		frame.getContentPane().add(lblMenuItemName);

		lblMenuItemDescription = new JLabel("Special Requests:");
		lblMenuItemDescription.setForeground(new Color(169, 169, 169));
		lblMenuItemDescription.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblMenuItemDescription.setBounds(23, 154, 197, 24);
		frame.getContentPane().add(lblMenuItemDescription);

		JButton btnNewButton = new JButton("Done");
		btnNewButton.setBackground(new Color(143, 188, 143));
		btnNewButton.setForeground(new Color(47, 79, 79));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
		btnNewButton.setBounds(23, 287, 133, 27);
		frame.getContentPane().add(btnNewButton);

		JButton btnDone = new JButton("Add Order");
		btnDone.setBackground(new Color(143, 188, 143));
		btnDone.setForeground(new Color(47, 79, 79));
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

					int order = (int) menuComboBox.getSelectedItem();
					int table = (int) tableComboBox.getSelectedItem();
					String sr = (String) srLabel.getText(); 
					createOrder(order, table, sr);
						successLabel.setText("Order Added!");
				
				
			}
		});
		btnDone.setFont(new Font("Arial", Font.BOLD, 15));
		btnDone.setBounds(366, 287, 133, 27);
		frame.getContentPane().add(btnDone);

		successLabel = new JLabel("");
		successLabel.setForeground(new Color(169, 169, 169));
		successLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		successLabel.setBounds(218, 243, 301, 24);
		frame.getContentPane().add(successLabel);

		srLabel = new JTextField();
		srLabel.setBackground(new Color(169, 169, 169));
		srLabel.setForeground(new Color(47, 79, 79));
		srLabel.setFont(new Font("Arial", Font.BOLD, 14));
		srLabel.setColumns(10);
		srLabel.setBounds(230, 156, 271, 24);
		frame.getContentPane().add(srLabel);

		JLabel lblForTable = new JLabel("For Table:");
		lblForTable.setForeground(new Color(169, 169, 169));
		lblForTable.setFont(new Font("Georgia", Font.PLAIN, 17));
		lblForTable.setBounds(23, 103, 160, 24);
		frame.getContentPane().add(lblForTable);
		menuComboBox.setFont(new Font("Arial", Font.BOLD, 15));
		menuComboBox.setBackground(new Color(169, 169, 169));
		menuComboBox.setForeground(new Color(47, 79, 79));

		menuComboBox.setBounds(230, 58, 269, 21);
		frame.getContentPane().add(menuComboBox);
		tableComboBox.setFont(new Font("Arial", Font.BOLD, 15));
		tableComboBox.setBackground(new Color(169, 169, 169));
		tableComboBox.setForeground(new Color(47, 79, 79));

		tableComboBox.setBounds(230, 107, 269, 21);
		frame.getContentPane().add(tableComboBox);


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
 * method called to populate the menu items into the JComboBox
 */
	private void updateMenuItems() {
		try {
			con = DriverManager
				.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
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
/**
 * method called to populate the tables into the JComboBox
 */
	private void updateTables() {
		try {
			con = DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
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
/**
 * Method used to create orders
 * @param menuItem - The number of the menu item to add
 * @param table - The table the order is associated with
 * @param sr - any special requests for the order
 */
	public void createOrder(int menuItem, int table, String sr) {
		try {
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME,MYSQL_PASSWORD);
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

package edu.metrostate.ics499.manager;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.mysql.jdbc.Statement;

import javax.swing.JTextField;
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
import java.awt.Font;

public class Manager_AddTables {
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;
	private JFrame frame;
	private JLabel lblTableType;
	private JLabel lblTableCapacity;
	private JLabel SuccessAddLabel;
	private JTextField tableTypeTextField;
	private JTextField tableCapacityTextField;

	private static Connection con;
	private static PreparedStatement stmt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_AddTables window = new Manager_AddTables();
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
	public Manager_AddTables() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		readSettings();
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 386);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(null);
		
		lblTableType = new JLabel("Table Type");
		lblTableType.setBounds(23, 64, 79, 20);
		frame.getContentPane().add(lblTableType);
		

		lblTableCapacity = new JLabel("Table Capacity");
		lblTableCapacity.setBounds(23, 164, 115, 20);
		frame.getContentPane().add(lblTableCapacity);
		
		tableTypeTextField = new JTextField();
		tableTypeTextField.setBounds(158, 61, 146, 26);
		tableTypeTextField.setColumns(10);
		frame.getContentPane().add(tableTypeTextField);

		
		tableCapacityTextField = new JTextField();
		tableCapacityTextField.setBounds(158, 161, 146, 26);
		tableCapacityTextField.setColumns(10);
		frame.getContentPane().add(tableCapacityTextField);				
		
		//success label, display success if table created
		SuccessAddLabel = new JLabel("");
		SuccessAddLabel.setFont(new Font("Georgia", Font.BOLD, 11));
		SuccessAddLabel.setBounds(245, 218, 69, 20);
		frame.getContentPane().add(SuccessAddLabel);
				
		//submits table information and display message
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkConditions()) {
					String tableType = tableTypeTextField.getText();
					int tableCapacity = Integer
							.parseInt(tableCapacityTextField.getText());					
					if(createTable(tableType, tableCapacity)) {
						SuccessAddLabel.setText("Success:  Table Added!");
						lblTableCapacity.setForeground(Color.BLACK);
					}
															
				}
			}

			private boolean createTable(String tableType, int tableCapacity) {
				try {
				con = (Connection) DriverManager
						.getConnection(MYSQL_URL,MYSQL_USERNAME, MYSQL_PASSWORD);
				stmt = con
						.prepareStatement("insert into Tables "
								+ "(TableType, Occupied, Capacity) "
								+ "values ( ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, tableType);
				stmt.setBoolean(2, false);
				stmt.setInt(3, tableCapacity);				
				
				//execute insert statement and update DB
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next() == true) {
					return true;
				}
				} catch (SQLException e) {
				}
				return false;
			}

			private boolean checkConditions() {
				//check for empty texts
				boolean conditionsOk = true;
				if(tableTypeTextField.getText().isEmpty()) {
					lblTableType.setForeground(Color.RED);
					SuccessAddLabel.setText("Item name cannot be blank");
					conditionsOk = false;
				}else {
					SuccessAddLabel.setText("");
					lblTableType.setForeground(Color.BLACK);
				}
				if(tableCapacityTextField.getText().isEmpty()) {
					lblTableCapacity.setForeground(Color.RED);
					SuccessAddLabel.setText("Item description cannot be blank");
					conditionsOk = false;
				}	
				
				return conditionsOk;
			}
		});
		btnSubmit.setFont(new Font("Georgia", Font.BOLD, 11));
		btnSubmit.setBounds(204, 268, 115, 29);
		frame.getContentPane().add(btnSubmit);

			
		//back button that returns to managers homepage
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Georgia", Font.BOLD, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Manager_Homepage.main(null);
			}
		});
		btnBack.setBounds(28, 268, 115, 29);
		frame.getContentPane().add(btnBack);
						
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
}

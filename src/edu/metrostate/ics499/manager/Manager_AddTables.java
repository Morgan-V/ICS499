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
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 480, 296);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(null);
		
		lblTableType = new JLabel("Table Type");
		lblTableType.setForeground(new Color(169, 169, 169));
		lblTableType.setFont(new Font("Arial", Font.ITALIC, 20));
		lblTableType.setBounds(48, 64, 121, 20);
		frame.getContentPane().add(lblTableType);
		

		lblTableCapacity = new JLabel("Table Capacity");
		lblTableCapacity.setForeground(new Color(169, 169, 169));
		lblTableCapacity.setFont(new Font("Arial", Font.ITALIC, 20));
		lblTableCapacity.setBounds(48, 114, 137, 27);
		frame.getContentPane().add(lblTableCapacity);
		
		tableTypeTextField = new JTextField();
		tableTypeTextField.setForeground(new Color(47, 79, 79));
		tableTypeTextField.setFont(new Font("Arial", Font.BOLD, 20));
		tableTypeTextField.setBounds(207, 61, 209, 26);
		tableTypeTextField.setColumns(10);
		frame.getContentPane().add(tableTypeTextField);

		
		tableCapacityTextField = new JTextField();
		tableCapacityTextField.setForeground(new Color(47, 79, 79));
		tableCapacityTextField.setFont(new Font("Arial", Font.BOLD, 20));
		tableCapacityTextField.setBounds(207, 115, 209, 26);
		tableCapacityTextField.setColumns(10);
		frame.getContentPane().add(tableCapacityTextField);				
		
		//success label, display success if table created
		SuccessAddLabel = new JLabel("");
		SuccessAddLabel.setBackground(new Color(169, 169, 169));
		SuccessAddLabel.setFont(new Font("Arial", Font.BOLD, 20));
		SuccessAddLabel.setBounds(48, 163, 368, 29);
		frame.getContentPane().add(SuccessAddLabel);
				
		//submits table information and display message
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setForeground(new Color(47, 79, 79));
		btnSubmit.setBackground(new Color(143, 188, 143));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkConditions()) {
					String tableType = tableTypeTextField.getText();
					int tableCapacity = Integer
							.parseInt(tableCapacityTextField.getText());					
					if(createTable(tableType, tableCapacity)) {
						SuccessAddLabel.setText("Success:  Table Added!");
						lblTableCapacity.setForeground(Color.decode("#A9A9A9"));
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
					SuccessAddLabel.setText("Table type cannot be blank");
					conditionsOk = false;
				}else {
					SuccessAddLabel.setText("");
					lblTableType.setForeground(Color.decode("#A9A9A9"));
				}
				//make sure capacity is an int
				if(isInteger(tableCapacityTextField.getText())) {
				if(tableCapacityTextField.getText().isEmpty()) {
					lblTableCapacity.setForeground(Color.RED);
					SuccessAddLabel.setForeground(Color.RED);
					SuccessAddLabel.setText("Table capacity cannot be blank");
					conditionsOk = false;
				}
				}
				else {
					SuccessAddLabel.setForeground(Color.RED);
					lblTableCapacity.setForeground(Color.RED);

					SuccessAddLabel.setText("Table capacity must be a number!");
					conditionsOk = false;
				}
		
				return conditionsOk;
			}

			private boolean isInteger(String text) {
				boolean isValidInteger = false;
			      try
			      {
			         Integer.parseInt(text);
			         isValidInteger = true;
			      }
			      catch (NumberFormatException ex)
			      {
			      }
			      return isValidInteger;
			}
		});
		btnSubmit.setFont(new Font("Arial", Font.BOLD, 20));
		btnSubmit.setBounds(283, 219, 133, 29);
		frame.getContentPane().add(btnSubmit);

			
		//back button that returns to managers homepage
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(47, 79, 79));
		btnBack.setBackground(new Color(143, 188, 143));
		btnBack.setFont(new Font("Arial", Font.BOLD, 20));
		btnBack.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent arg0) {
		frame.dispose();
	}

	});btnBack.setBounds(48,219,133,29);frame.getContentPane().add(btnBack);

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
		MYSQL_URL = "jdbc:mysql://" + prop.getProperty("MYSQL_IP") + ":" + prop.getProperty("MYSQL_PORT") + "/"
				+ prop.getProperty("MYSQL_SCHEMA") + "?useSSL=false";
		MYSQL_USERNAME = prop.getProperty("MYSQL_USER");
		MYSQL_PASSWORD = prop.getProperty("MYSQL_PASS");
	}
}

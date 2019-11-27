package edu.metrostate.ics499.manager;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.Font;
/**
 * Manager window to add new users
 * @author Ryan
 *
 */
public class Manager_AddUsers implements ActionListener{
	
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;

	private JFrame frame;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel contactInfoLabel;
	private JLabel positionChoiceLabel;
	private JLabel passwordLabel;
	private JLabel passwordConfirmLabel;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField contactInfo;
	private JComboBox<?> positionChoice;
	private String[] positionOptions = 
		{"Manager", "Wait Staff", "Cook Staff", "Host Staff"};
	private JPasswordField passwordEntry;
	private JPasswordField passwordEntryConfirm;
	private JButton submitButton;
	private JButton backButton;
	private JLabel passwordMismach;
	private JLabel idDisplay;
	private static Connection con;
	private static PreparedStatement stmt;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_AddUsers window = new Manager_AddUsers();
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
	public Manager_AddUsers() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		readSettings();
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setForeground(Color.WHITE);
		frame.setBounds(100, 100, 450, 210);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Create New User");
		frame.setResizable(false);
		
		firstNameLabel = new JLabel("First Name");
		firstNameLabel.setForeground(new Color(47, 79, 79));
		firstNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		firstNameLabel.setBounds(10, 10, 80, 25);
		frame.getContentPane().add(firstNameLabel);
		firstName = new JTextField();
		firstName.setBackground(new Color(211, 211, 211));
		firstName.setFont(new Font("Arial", Font.PLAIN, 12));
		firstName.setForeground(new Color(47, 79, 79));
		firstName.setBounds(100, 10, 110, 25);
		frame.getContentPane().add(firstName);
		
		lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setForeground(new Color(47, 79, 79));
		lastNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		lastNameLabel.setBounds(220, 10, 80, 25);
		frame.getContentPane().add(lastNameLabel);
		lastName = new JTextField();
		lastName.setBackground(new Color(211, 211, 211));
		lastName.setFont(new Font("Arial", Font.PLAIN, 12));
		lastName.setForeground(new Color(47, 79, 79));
		lastName.setBounds(290, 10, 120, 25);
		frame.getContentPane().add(lastName);

		contactInfoLabel = new JLabel("Phone Number");
		contactInfoLabel.setForeground(new Color(47, 79, 79));
		contactInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
		contactInfoLabel.setBounds(10, 40, 100, 25);
		frame.getContentPane().add(contactInfoLabel);
		contactInfo = new JTextField();
		contactInfo.setBackground(new Color(211, 211, 211));
		contactInfo.setFont(new Font("Arial", Font.PLAIN, 12));
		contactInfo.setForeground(new Color(47, 79, 79));
		contactInfo.setBounds(100, 40, 110, 25);
		frame.getContentPane().add(contactInfo);
	
		positionChoiceLabel = new JLabel("Position");
		positionChoiceLabel.setForeground(new Color(47, 79, 79));
		positionChoiceLabel.setFont(new Font("Arial", Font.BOLD, 12));
		positionChoiceLabel.setBounds(220, 40, 80, 25);
		frame.getContentPane().add(positionChoiceLabel);
		positionChoice = new JComboBox<Object>(positionOptions);
		positionChoice.setBackground(new Color(255, 255, 255));
		positionChoice.setForeground(new Color(47, 79, 79));
		positionChoice.setBounds(290, 40, 110, 25);
		frame.getContentPane().add(positionChoice);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(new Color(47, 79, 79));
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
		passwordLabel.setBounds(10, 70, 130, 25);
		frame.getContentPane().add(passwordLabel);
		passwordEntry = new JPasswordField();
		passwordEntry.setBackground(new Color(211, 211, 211));
		passwordEntry.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordEntry.setForeground(new Color(47, 79, 79));
		passwordEntry.setBounds(140, 70, 100, 25);
		frame.getContentPane().add(passwordEntry);
		
		passwordConfirmLabel = new JLabel("Confirm Password");
		passwordConfirmLabel.setForeground(new Color(47, 79, 79));
		passwordConfirmLabel.setFont(new Font("Arial", Font.BOLD, 12));
		passwordConfirmLabel.setBounds(10, 100, 130, 25);
		frame.getContentPane().add(passwordConfirmLabel);
		passwordEntryConfirm = new JPasswordField();
		passwordEntryConfirm.setBackground(new Color(211, 211, 211));
		passwordEntryConfirm.setFont(new Font("Arial", Font.PLAIN, 12));
		passwordEntryConfirm.setForeground(new Color(47, 79, 79));
		passwordEntryConfirm.setBounds(140, 100, 100, 25);
		frame.getContentPane().add(passwordEntryConfirm);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(new Font("Arial", Font.BOLD, 12));
		submitButton.setForeground(new Color(47, 79, 79));
		submitButton.setBackground(new Color(211, 211, 211));
		submitButton.setBounds(10, 130, 100, 25);
		frame.getContentPane().add(submitButton);
		submitButton.addActionListener(this);
		
		backButton =  new JButton("Back");
		backButton.setForeground(new Color(47, 79, 79));
		backButton.setBackground(new Color(211, 211, 211));
		backButton.setFont(new Font("Arial", Font.BOLD, 12));
		backButton.setBounds(120, 130, 100, 25);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(this);
		
		passwordMismach = new JLabel();
		passwordMismach.setForeground(new Color(128, 0, 0));
		passwordMismach.setFont(new Font("Arial", Font.BOLD, 12));
		passwordMismach.setBounds(250, 70, 150, 25);
		frame.getContentPane().add(passwordMismach);
		
		idDisplay = new JLabel();
		idDisplay.setForeground(new Color(47, 79, 79));
		idDisplay.setFont(new Font("Arial", Font.BOLD, 12));
		idDisplay.setBounds(250, 100, 150, 25);
		frame.getContentPane().add(idDisplay);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(169, 169, 169));
		panel.setBounds(0, 0, 446, 182);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Back") {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}else if(e.getActionCommand() == "Submit") {
			if(checkConditions()) {
				String pos = positionChoice.getSelectedItem().toString();
				String fname = firstName.getText();
				String lname = lastName.getText();
				String password = passwordEntry.getText();
				String contact = contactInfo.getText();
				int id = createUser(pos,fname,lname,password,contact);
				if(id > 0) {
					idDisplay.setText("User created with ID: " + id);
				}
			}
		}
		
	}
	/**
	 * Checks if all requirements are met for creating a new user
	 * @return true if conditions are met and false otherwise
	 */
	private boolean checkConditions() {
		boolean conditionsOk = true;
		if(passwordEntry.getText().isBlank()||passwordEntryConfirm.getText().isBlank()) {
			passwordMismach.setForeground(Color.decode("#800000"));
			passwordMismach.setText("Passwords do not Match");
			passwordLabel.setForeground(Color.decode("#800000"));
			passwordConfirmLabel.setForeground(Color.decode("#800000"));
			conditionsOk = false;
		}else {
			passwordMismach.setText("");
			passwordLabel.setForeground(Color.BLACK);
			passwordConfirmLabel.setForeground(Color.BLACK);
			passwordConfirmLabel.setForeground(Color.BLACK);
		}
		if(firstName.getText().isBlank()) {
			firstNameLabel.setForeground(Color.decode("#800000"));
			conditionsOk = false;
		}else {
			firstNameLabel.setForeground(Color.BLACK);
		}
		if(lastName.getText().isBlank()) {
			lastNameLabel.setForeground(Color.decode("#800000"));
			conditionsOk = false;
		}else {
			lastNameLabel.setForeground(Color.BLACK);
		}
		if(contactInfo.getText().isBlank()) {
			contactInfoLabel.setForeground(Color.decode("#800000"));
			conditionsOk = false;
		}else {
			contactInfoLabel.setForeground(Color.BLACK);
		}
		if(!passwordEntry.getText().matches(passwordEntryConfirm.getText())){
			passwordMismach.setForeground(Color.RED);
			passwordMismach.setText("Passwords do not Match");
			passwordLabel.setForeground(Color.RED);
			passwordConfirmLabel.setForeground(Color.RED);
			conditionsOk = false;
		}
		return conditionsOk;
	}
	
	/**
	 * Takes all the required data to register a new user in the database.
	 * returns the Id assigned to the new user or -1 if the operation failed.
	 * @param pos
	 * @param fName
	 * @param lName
	 * @param password
	 * @param contact
	 * @return int Id of the new user or -1 if the operation failed 
	 */
	public int createUser(String pos, String fName, String lName, String password, String contact) {
		try {
			//ID,Position,FirstName,LastName,Password,Contact.
			con = (Connection) DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME,MYSQL_PASSWORD);
			stmt =  con.prepareStatement
					("insert into users (Position, FirstName, LastName, Password, "
							+ "Contact) values ( ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, pos);
			stmt.setString(2,fName);
			stmt.setString(3, lName);
			stmt.setString(4, password);
			stmt.setString(5, contact);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();  
			return rs.getInt(1);
		} catch (SQLException e) {
			//Errors fall through to return -1.
		}
		return -1;
	}
}
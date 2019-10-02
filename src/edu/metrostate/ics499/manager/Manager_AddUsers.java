package edu.metrostate.ics499.manager;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * Manager window to add new users
 * @author Ryan
 *
 */
public class Manager_AddUsers implements ActionListener{

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
	private JComboBox positionChoice;
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 210);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Create New User");
		frame.setResizable(false);
		
		firstNameLabel = new JLabel("First Name");
		firstNameLabel.setBounds(10, 10, 80, 25);
		frame.getContentPane().add(firstNameLabel);
		firstName = new JTextField();
		firstName.setBounds(100, 10, 110, 25);
		frame.getContentPane().add(firstName);
		
		lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setBounds(220, 10, 80, 25);
		frame.getContentPane().add(lastNameLabel);
		lastName = new JTextField();
		lastName.setBounds(290, 10, 120, 25);
		frame.getContentPane().add(lastName);

		contactInfoLabel = new JLabel("Phone Number");
		contactInfoLabel.setBounds(10, 40, 100, 25);
		frame.getContentPane().add(contactInfoLabel);
		contactInfo = new JTextField();
		contactInfo.setBounds(100, 40, 110, 25);
		frame.getContentPane().add(contactInfo);
	
		positionChoiceLabel = new JLabel("Position");
		positionChoiceLabel.setBounds(220, 40, 80, 25);
		frame.getContentPane().add(positionChoiceLabel);
		positionChoice = new JComboBox(positionOptions);
		positionChoice.setBounds(290, 40, 110, 25);
		frame.getContentPane().add(positionChoice);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 70, 130, 25);
		frame.getContentPane().add(passwordLabel);
		passwordEntry = new JPasswordField();
		passwordEntry.setBounds(140, 70, 100, 25);
		frame.getContentPane().add(passwordEntry);
		
		passwordConfirmLabel = new JLabel("Confirm Password");
		passwordConfirmLabel.setBounds(10, 100, 130, 25);
		frame.getContentPane().add(passwordConfirmLabel);
		passwordEntryConfirm = new JPasswordField();
		passwordEntryConfirm.setBounds(140, 100, 100, 25);
		frame.getContentPane().add(passwordEntryConfirm);
		
		submitButton = new JButton("Submit");
		submitButton.setBounds(10, 130, 100, 25);
		frame.getContentPane().add(submitButton);
		submitButton.addActionListener(this);
		
		backButton =  new JButton("Back");
		backButton.setBounds(120, 130, 100, 25);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(this);
		
		passwordMismach = new JLabel();
		passwordMismach.setBounds(250, 70, 150, 25);
		frame.getContentPane().add(passwordMismach);
		
		idDisplay = new JLabel();
		idDisplay.setBounds(250, 100, 150, 25);
		frame.getContentPane().add(idDisplay);
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
			passwordMismach.setForeground(Color.RED);
			passwordMismach.setText("Passwords do not Match");
			passwordLabel.setForeground(Color.RED);
			passwordConfirmLabel.setForeground(Color.RED);
			passwordConfirmLabel.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			passwordMismach.setText("");
			passwordLabel.setForeground(Color.BLACK);
			passwordConfirmLabel.setForeground(Color.BLACK);
			passwordConfirmLabel.setForeground(Color.BLACK);
		}
		if(firstName.getText().isBlank()) {
			firstNameLabel.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			firstNameLabel.setForeground(Color.BLACK);
		}
		if(lastName.getText().isBlank()) {
			lastNameLabel.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			lastNameLabel.setForeground(Color.BLACK);
		}
		if(contactInfo.getText().isBlank()) {
			contactInfoLabel.setForeground(Color.RED);
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
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ems?useSSL=false","root","root");
			stmt =  con.prepareStatement
					("insert into users (Position, FirstName, LastName, Password, Contact) values ( ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
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
			System.out.println("error");
		}
		return -1;
	}
}
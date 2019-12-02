package edu.metrostate.ics499.manager;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
<<<<<<< Updated upstream
		firstNameLabel.setBounds(10, 10, 80, 25);
		frame.getContentPane().add(firstNameLabel);
		firstName = new JTextField();
=======
		firstNameLabel.setForeground(new Color(169, 169, 169));
		firstNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		firstNameLabel.setBounds(10, 10, 80, 25);
		frame.getContentPane().add(firstNameLabel);
		firstName = new JTextField();
		firstName.setBackground(new Color(211, 211, 211));
		firstName.setFont(new Font("Arial", Font.BOLD, 12));
		firstName.setForeground(new Color(47, 79, 79));
>>>>>>> Stashed changes
		firstName.setBounds(100, 10, 110, 25);
		frame.getContentPane().add(firstName);
		
		lastNameLabel = new JLabel("Last Name");
<<<<<<< Updated upstream
		lastNameLabel.setBounds(220, 10, 80, 25);
		frame.getContentPane().add(lastNameLabel);
		lastName = new JTextField();
=======
		lastNameLabel.setForeground(new Color(169, 169, 169));
		lastNameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		lastNameLabel.setBounds(220, 10, 80, 25);
		frame.getContentPane().add(lastNameLabel);
		lastName = new JTextField();
		lastName.setBackground(new Color(211, 211, 211));
		lastName.setFont(new Font("Arial", Font.BOLD, 12));
		lastName.setForeground(new Color(47, 79, 79));
>>>>>>> Stashed changes
		lastName.setBounds(290, 10, 120, 25);
		frame.getContentPane().add(lastName);

		contactInfoLabel = new JLabel("Phone Number");
<<<<<<< Updated upstream
		contactInfoLabel.setBounds(10, 40, 100, 25);
		frame.getContentPane().add(contactInfoLabel);
		contactInfo = new JTextField();
=======
		contactInfoLabel.setForeground(new Color(169, 169, 169));
		contactInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
		contactInfoLabel.setBounds(10, 40, 100, 25);
		frame.getContentPane().add(contactInfoLabel);
		contactInfo = new JTextField();
		contactInfo.setBackground(new Color(211, 211, 211));
		contactInfo.setFont(new Font("Arial", Font.BOLD, 12));
		contactInfo.setForeground(new Color(47, 79, 79));
>>>>>>> Stashed changes
		contactInfo.setBounds(100, 40, 110, 25);
		frame.getContentPane().add(contactInfo);
	
		positionChoiceLabel = new JLabel("Position");
<<<<<<< Updated upstream
=======
		positionChoiceLabel.setForeground(new Color(169, 169, 169));
		positionChoiceLabel.setFont(new Font("Arial", Font.BOLD, 12));
>>>>>>> Stashed changes
		positionChoiceLabel.setBounds(220, 40, 80, 25);
		frame.getContentPane().add(positionChoiceLabel);
		positionChoice = new JComboBox(positionOptions);
		positionChoice.setBounds(290, 40, 110, 25);
		frame.getContentPane().add(positionChoice);
		
		passwordLabel = new JLabel("Password");
<<<<<<< Updated upstream
		passwordLabel.setBounds(10, 70, 130, 25);
		frame.getContentPane().add(passwordLabel);
		passwordEntry = new JPasswordField();
=======
		passwordLabel.setForeground(new Color(169, 169, 169));
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
		passwordLabel.setBounds(10, 70, 130, 25);
		frame.getContentPane().add(passwordLabel);
		passwordEntry = new JPasswordField();
		passwordEntry.setBackground(new Color(211, 211, 211));
		passwordEntry.setFont(new Font("Arial", Font.BOLD, 12));
		passwordEntry.setForeground(new Color(47, 79, 79));
>>>>>>> Stashed changes
		passwordEntry.setBounds(140, 70, 100, 25);
		frame.getContentPane().add(passwordEntry);
		
		passwordConfirmLabel = new JLabel("Confirm Password");
<<<<<<< Updated upstream
		passwordConfirmLabel.setBounds(10, 100, 130, 25);
		frame.getContentPane().add(passwordConfirmLabel);
		passwordEntryConfirm = new JPasswordField();
=======
		passwordConfirmLabel.setForeground(new Color(169, 169, 169));
		passwordConfirmLabel.setFont(new Font("Arial", Font.BOLD, 12));
		passwordConfirmLabel.setBounds(10, 100, 130, 25);
		frame.getContentPane().add(passwordConfirmLabel);
		passwordEntryConfirm = new JPasswordField();
		passwordEntryConfirm.setBackground(new Color(211, 211, 211));
		passwordEntryConfirm.setFont(new Font("Arial", Font.BOLD, 12));
		passwordEntryConfirm.setForeground(new Color(47, 79, 79));
>>>>>>> Stashed changes
		passwordEntryConfirm.setBounds(140, 100, 100, 25);
		frame.getContentPane().add(passwordEntryConfirm);
		
		submitButton = new JButton("Submit");
<<<<<<< Updated upstream
=======
		submitButton.setFont(new Font("Arial", Font.BOLD, 12));
		submitButton.setForeground(new Color(47, 79, 79));
		submitButton.setBackground(new Color(143, 188, 143));
>>>>>>> Stashed changes
		submitButton.setBounds(10, 130, 100, 25);
		frame.getContentPane().add(submitButton);
		submitButton.addActionListener(this);
		
		backButton =  new JButton("Back");
<<<<<<< Updated upstream
=======
		backButton.setForeground(new Color(47, 79, 79));
		backButton.setBackground(new Color(143, 188, 143));
		backButton.setFont(new Font("Arial", Font.BOLD, 12));
>>>>>>> Stashed changes
		backButton.setBounds(120, 130, 100, 25);
		frame.getContentPane().add(backButton);
		backButton.addActionListener(this);
		
		passwordMismach = new JLabel();
<<<<<<< Updated upstream
=======
		passwordMismach.setForeground(new Color(255, 0, 0));
		passwordMismach.setFont(new Font("Arial", Font.BOLD, 12));
>>>>>>> Stashed changes
		passwordMismach.setBounds(250, 70, 150, 25);
		frame.getContentPane().add(passwordMismach);
		
		idDisplay = new JLabel();
		idDisplay.setBounds(250, 100, 150, 25);
		frame.getContentPane().add(idDisplay);
<<<<<<< Updated upstream
=======
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
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
>>>>>>> Stashed changes
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
					idDisplay.setForeground(Color.decode("#A9A9A9"));
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
<<<<<<< Updated upstream
			passwordConfirmLabel.setForeground(Color.RED);
=======
>>>>>>> Stashed changes
			conditionsOk = false;
		}else {
			passwordMismach.setText("");
			passwordLabel.setForeground(Color.decode("#A9A9A9"));
			passwordConfirmLabel.setForeground(Color.decode("#A9A9A9"));
			passwordConfirmLabel.setForeground(Color.decode("#A9A9A9"));
		}
		if(firstName.getText().isBlank()) {
			firstNameLabel.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			firstNameLabel.setForeground(Color.decode("#A9A9A9"));
		}
		if(lastName.getText().isBlank()) {
			lastNameLabel.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			lastNameLabel.setForeground(Color.decode("#A9A9A9"));
		}
		if(contactInfo.getText().isBlank()) {
			contactInfoLabel.setForeground(Color.RED);
			conditionsOk = false;
		}else {
			contactInfoLabel.setForeground(Color.decode("#A9A9A9"));
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
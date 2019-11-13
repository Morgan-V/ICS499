package edu.metrostate.ics499.sharedstaff;

/*
 * Class used to launch the program. It begins with a log in screen, verifies employment and directs the user 
 * to the homepage that is relevant to their position
 */
import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import edu.metrostate.ics499.cstaff.CStaff_Homepage;
import edu.metrostate.ics499.host.Host_Homepage;
import edu.metrostate.ics499.manager.Manager_Homepage;
import edu.metrostate.ics499.wstaff.WStaff_Homepage;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JSeparator;

public class Login_Screen {
	// Information to reach the database

	// "RMS" is the name of the database
	private static final String url = "jdbc:mysql://localhost:3306/RMS?useSSL=false";
	// using root user name and password
	private static final String user = "root";
	private static final String password = "root";
	private static String query1 = "";
	private static String query2 = "";

	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	private static ResultSet rs2;
	private JFrame frame;
	private JTextField txtfield_UserID;
	private JPasswordField pwordfield_Password;

	/**
	 * Launch the application.
	 * 
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		// the queries mysql needs

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login_Screen window = new Login_Screen();
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
	public Login_Screen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 863, 516);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblUserLogin = new JLabel("User Login");
		lblUserLogin.setFont(new Font("Verdana", Font.PLAIN, 60));
		lblUserLogin.setBounds(259, 33, 342, 74);
		frame.getContentPane().add(lblUserLogin);

		JLabel lblUserID = new JLabel("User ID");
		lblUserID.setFont(new Font("Georgia", Font.PLAIN, 35));
		lblUserID.setBounds(178, 161, 133, 41);
		frame.getContentPane().add(lblUserID);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Georgia", Font.PLAIN, 35));
		lblPassword.setBounds(149, 242, 183, 50);
		frame.getContentPane().add(lblPassword);

		txtfield_UserID = new JTextField();
		txtfield_UserID.setFont(new Font("Georgia", Font.PLAIN, 30));
		txtfield_UserID.setBounds(380, 161, 342, 41);
		frame.getContentPane().add(txtfield_UserID);
		txtfield_UserID.setColumns(10);

		pwordfield_Password = new JPasswordField();
		pwordfield_Password.setFont(new Font("Georgia", Font.PLAIN, 30));
		pwordfield_Password.setBounds(380, 249, 342, 41);
		frame.getContentPane().add(pwordfield_Password);

		/**
		 * Login button will check the userID the user entered against what is in the
		 * database. If there is no user, the password is set to blank and an error
		 * message occurs when user attempts to login
		 **/

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// may need to refactor - bad practice to save passwords in system
				String retrievedPword = null;
				String position = null;
				String userID = txtfield_UserID.getText();
				@SuppressWarnings("deprecation")
				String userentered_pword = pwordfield_Password.getText();

				// Do not allow the user to put in anything but an int into the userID field
				if (!userID.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(null, "UserID must be a number!");
				} else {
					// queries to find what the password and position is for the userID the user
					// entered
					query1 = "select Password from users where UserId = " + userID + ";";
					query2 = "select Position from users where UserId = " + userID + ";";

					// run the first statement
					try {
						con = (Connection) DriverManager.getConnection(url, user, password);
						stmt = (Statement) con.createStatement();
						rs = stmt.executeQuery(query1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					// run the second statement
					try {
						con = (Connection) DriverManager.getConnection(url, user, password);
						stmt = (Statement) con.createStatement();
						rs2 = stmt.executeQuery(query2);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					// put those statements into a variable we can check data against
					try {
						while (rs.next()) {
							retrievedPword = rs.getString(1);
						}
						while (rs2.next()) {
							position = rs2.getString(1);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					// if there is no password, the user does not exist
					if (retrievedPword == null) {
						JOptionPane.showMessageDialog(null, "User does not exist!");
					}
					// if the password is correct, direct the user to the correct homepage. If
					// password is incorrect, show error message
					String[] arguments = { userID.toString() };
					if (userentered_pword.equals(retrievedPword)) {
						if (position.contentEquals("manager") || position.contentEquals("Manager")) {
							Manager_Homepage.main(arguments);
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						}
						if (position.contentEquals("Cook Staff")) {
							CStaff_Homepage.main(arguments);
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						}
						if (position.contentEquals("Host Staff")) {
							Host_Homepage.main(arguments);
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						}
						if (position.contentEquals("Wait Staff")) {
							WStaff_Homepage.main(arguments);
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						}
					} else {
						JOptionPane.showMessageDialog(null, "Password is incorrect!");
					}
				}
			}
		});
		btnLogin.setBounds(45, 399, 169, 50);
		frame.getContentPane().add(btnLogin);

		// Button to clear the text in the userid and password field
		JButton btnResetFields = new JButton("Reset Fields");
		btnResetFields.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnResetFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtfield_UserID.setText(null);
				pwordfield_Password.setText(null);
			}
		});
		btnResetFields.setBounds(316, 399, 240, 50);
		frame.getContentPane().add(btnResetFields);

		// button to confirm that user would like to exit
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame exitConfirm = new JFrame("exit");
				if (JOptionPane.showConfirmDialog(exitConfirm, "Are you sure you would like to exit?", "RMS Login",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(662, 399, 128, 50);
		frame.getContentPane().add(btnExit);

		JSeparator separator = new JSeparator();
		separator.setBounds(93, 137, 669, 14);
		frame.getContentPane().add(separator);
	}
}

package edu.metrostate.ics499.login;

import java.awt.EventQueue;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import edu.metrostate.ics499.manager.Manager_Homepage;

import javax.swing.JButton;
import java.awt.event.ActionListener;
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

	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	private JFrame frame;
	private JTextField txtfield_Username;
	private JPasswordField pwordfield_Password;

	/**
	 * Launch the application.
	 * 
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		// the queries mysql needs
		String query = "select * from employee";

		try {
			con = (Connection) DriverManager.getConnection(url, user, password);
			stmt = (Statement) con.createStatement();
			rs = stmt.executeQuery(query);
		} finally {
		}
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblUserLogin = new JLabel("User Login");
		lblUserLogin.setFont(new Font("Verdana", Font.PLAIN, 60));
		lblUserLogin.setBounds(259, 33, 342, 74);
		frame.getContentPane().add(lblUserLogin);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Georgia", Font.PLAIN, 35));
		lblUsername.setBounds(135, 161, 183, 41);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Georgia", Font.PLAIN, 35));
		lblPassword.setBounds(149, 242, 183, 50);
		frame.getContentPane().add(lblPassword);

		txtfield_Username = new JTextField();
		txtfield_Username.setFont(new Font("Georgia", Font.PLAIN, 30));
		txtfield_Username.setBounds(380, 161, 342, 41);
		frame.getContentPane().add(txtfield_Username);
		txtfield_Username.setColumns(10);

		pwordfield_Password = new JPasswordField();
		pwordfield_Password.setFont(new Font("Georgia", Font.PLAIN, 30));
		pwordfield_Password.setBounds(380, 249, 342, 41);
		frame.getContentPane().add(pwordfield_Password);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Manager_Homepage.main(null);
			}
		});
		btnLogin.setBounds(45, 399, 169, 50);
		frame.getContentPane().add(btnLogin);

		JButton btnResetFields = new JButton("Reset Fields");
		btnResetFields.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnResetFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				txtfield_Username.setText(null);
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

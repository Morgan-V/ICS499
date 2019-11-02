package edu.metrostate.ics499.manager;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import edu.metrostate.ics499.sharedstaff.Login_Screen;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JSeparator;

public class Manager_Homepage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_Homepage window = new Manager_Homepage();
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
	public Manager_Homepage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 875, 534);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblWelcomeToThe = new JLabel("Manager Homepage");
		lblWelcomeToThe.setFont(new Font("Verdana", Font.PLAIN, 60));
		lblWelcomeToThe.setBounds(104, 34, 634, 86);
		frame.getContentPane().add(lblWelcomeToThe);
		
		//button to take user back to log in - it will log user out of system
		JButton btnBackToLogin = new JButton("Back to Login");
		btnBackToLogin.setFont(new Font("Georgia", Font.PLAIN, 20));
		btnBackToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Login_Screen.main(null);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBackToLogin.setBounds(36, 417, 215, 34);
		frame.getContentPane().add(btnBackToLogin);
		
		//button to close program
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame exitConfirm = new JFrame("exit");
				if (JOptionPane.showConfirmDialog(exitConfirm, "Are you sure you would like to exit?", "RMS System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Georgia", Font.PLAIN, 20));
		btnExit.setBounds(688, 417, 132, 34);
		frame.getContentPane().add(btnExit);
		
		JButton btnManagerUsers = new JButton("Manage Users");
		btnManagerUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Edit/Remove User", "Add User"};
				int n = JOptionPane.showOptionDialog(null, "Please select an action", "",
				        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
				if (n == 0) {
					Manager_EditRemoveUsers.main(null);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

				}
				if (n == 1) {
					Manager_AddUsers.main(null);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

				}
			}
		});
		btnManagerUsers.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnManagerUsers.setBounds(270, 183, 299, 50);
		frame.getContentPane().add(btnManagerUsers);
		
		JButton btnManagerSchedules = new JButton("Manage Schedules");
		btnManagerSchedules.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Manager_EditSchedule.main(null);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnManagerSchedules.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnManagerSchedules.setBounds(229, 282, 389, 50);
		frame.getContentPane().add(btnManagerSchedules);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(65, 146, 673, 2);
		frame.getContentPane().add(separator);
	}

}

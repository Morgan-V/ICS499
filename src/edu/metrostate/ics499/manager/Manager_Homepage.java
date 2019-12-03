package edu.metrostate.ics499.manager;

import java.awt.EventQueue;
import javax.swing.JFrame;
import edu.metrostate.ics499.sharedstaff.Login_Screen;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

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
					window.frame.setResizable(false);
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
		frame.getContentPane().setForeground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 875, 534);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
		panel.setBounds(0, 0, 861, 497);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnManageTables = new JButton("");
		btnManageTables.setIcon(new ImageIcon(Manager_Homepage.class.
				getResource("/img/desk.png")));
		btnManageTables.setBackground(new Color(169, 169, 169));
		btnManageTables.setForeground(new Color(47, 79, 79));
		btnManageTables.setBounds(408, 250, 60, 60);
		btnManageTables.setFont(new Font("Arial", Font.PLAIN, 23));
		panel.add(btnManageTables);
		btnManageTables.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Edit/Remove Table", "Add Table"};
				int n = JOptionPane.showOptionDialog(null,
						"Please select an action", "",
				        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
				if (n == 0) {
					Manager_EditRemoveTables.main(null);
					//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

				}
				if (n == 1) {
					Manager_AddTables.main(null);
				}
			}
		});
		
		JButton btnManagerUsers = new JButton("");
		btnManagerUsers.setIcon(new ImageIcon(Manager_Homepage.class.
				getResource("/img/addU.png")));
		btnManagerUsers.setBackground(new Color(169, 169, 169));
		btnManagerUsers.setForeground(new Color(47, 79, 79));
		btnManagerUsers.setBounds(408, 91, 60, 60);
		panel.add(btnManagerUsers);
		btnManagerUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Edit/Remove User", "Add User"};
				int n = JOptionPane.showOptionDialog(null,
						"Please select an action", "",
				        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
				if (n == 0) {
					Manager_EditRemoveUsers.main(null);
				}
				if (n == 1) {
					Manager_AddUsers.main(null);
				}
			}
		});
		btnManagerUsers.setFont(new Font("Arial", Font.PLAIN, 23));
		
		JButton btnManagerSchedules = new JButton("");
		btnManagerSchedules.setIcon(new ImageIcon(Manager_Homepage.class.
				getResource("/img/Schedule.png")));
		btnManagerSchedules.setBackground(new Color(169, 169, 169));
		btnManagerSchedules.setForeground(new Color(47, 79, 79));
		btnManagerSchedules.setBounds(408, 170, 60, 60);
		panel.add(btnManagerSchedules);
		btnManagerSchedules.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Manager_EditSchedule.main(null);
			}
		});
		btnManagerSchedules.setFont(new Font("Arial", Font.PLAIN, 23));
		
		//button to take user back to log in - it will log user out of system
		JButton btnBackToLogin = new JButton("Back to Login");
		btnBackToLogin.setBackground(new Color(143, 188, 143));
		btnBackToLogin.setForeground(new Color(47, 79, 79));
		btnBackToLogin.setBounds(372, 429, 166, 34);
		panel.add(btnBackToLogin);
		btnBackToLogin.setFont(new Font("Arial", Font.PLAIN, 20));
		
		//button to close program
		JButton btnExit = new JButton("Exit");
		btnExit.setForeground(new Color(47, 79, 79));
		btnExit.setBounds(663, 429, 166, 34);
		panel.add(btnExit);
		btnExit.setBackground(new Color(143, 188, 143));
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame exitConfirm = new JFrame("exit");
				if (JOptionPane.showConfirmDialog(exitConfirm,
						"Are you sure you would like to exit?", "RMS System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Manager_Homepage.class.
				getResource("/img/manager_homepage.jpg")));
		lblNewLabel.setBounds(0, 0, 333, 497);
		panel.add(lblNewLabel);
		
		JLabel lblManagerHomepage = new JLabel("Manager Homepage");
		lblManagerHomepage.setForeground(new Color(143, 188, 143));
		lblManagerHomepage.setFont(new Font("Georgia", Font.PLAIN, 55));
		lblManagerHomepage.setBounds(350, 10, 501, 71);
		panel.add(lblManagerHomepage);
		
		JLabel lblNewLabel_1 = new JLabel("Manage Users");
		lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 33));
		lblNewLabel_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_1.setBounds(497, 101, 267, 45);
		panel.add(lblNewLabel_1);
		
		JLabel lblManageSchedule = new JLabel("Manage Schedule");
		lblManageSchedule.setForeground(new Color(169, 169, 169));
		lblManageSchedule.setFont(new Font("Arial", Font.ITALIC, 33));
		lblManageSchedule.setBounds(497, 180, 267, 49);
		panel.add(lblManageSchedule);
		
		JLabel lblManageTables = new JLabel("Manage Tables");
		lblManageTables.setForeground(new Color(169, 169, 169));
		lblManageTables.setFont(new Font("Arial", Font.ITALIC, 33));
		lblManageTables.setBounds(497, 260, 267, 49);
		panel.add(lblManageTables);
		
		JButton btnViewOrders = new JButton("");
		btnViewOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Manager_ViewRemoveOrder.main(null);
			}
		});
		btnViewOrders.setIcon(new ImageIcon(Manager_Homepage.class.getResource("/img/order.png")));
		btnViewOrders.setForeground(new Color(47, 79, 79));
		btnViewOrders.setFont(new Font("Arial", Font.PLAIN, 23));
		btnViewOrders.setBackground(new Color(169, 169, 169));
		btnViewOrders.setBounds(408, 330, 60, 60);
		panel.add(btnViewOrders);
		
		JLabel lblViewOrders = new JLabel("View Orders");
		lblViewOrders.setForeground(new Color(169, 169, 169));
		lblViewOrders.setFont(new Font("Arial", Font.ITALIC, 33));
		lblViewOrders.setBounds(497, 341, 267, 49);
		panel.add(lblViewOrders);
		btnBackToLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Login_Screen.main(null);
					frame.dispatchEvent(new WindowEvent(
							frame, WindowEvent.WINDOW_CLOSING));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}

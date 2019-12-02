package edu.metrostate.ics499.cstaff;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import edu.metrostate.ics499.sharedstaff.Login_Screen;
import edu.metrostate.ics499.sharedstaff.Orders;
import edu.metrostate.ics499.sharedstaff.Schedule;
import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * 
 * @author Morgan
 * The homepage for the Cook Staff
 */
public class CStaff_Homepage {
	private static String[] arguments;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		arguments = args;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CStaff_Homepage window = new CStaff_Homepage();
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
	public CStaff_Homepage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 873, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblHostHomepage = new JLabel("Cook Staff Homepage");
		lblHostHomepage.setForeground(new Color(143, 188, 143));
		lblHostHomepage.setBackground(new Color(143, 188, 143));
		lblHostHomepage.setFont(new Font("Georgia", Font.PLAIN, 53));
		lblHostHomepage.setBounds(338, 0, 521, 86);
		frame.getContentPane().add(lblHostHomepage);

		JButton btnManageOrders = new JButton("");
		btnManageOrders.setBackground(new Color(169, 169, 169));
		btnManageOrders.setIcon(new ImageIcon(CStaff_Homepage.class.getResource("/img/order.png")));
		btnManageOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Complete Orders", "View Orders"};
				int n = JOptionPane.showOptionDialog(null, "Please select an action", "",
				        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
				if (n == 0) {
					CStaff_CompleteOrders.main(arguments);
				}
				if (n == 1) {
					Orders.main(arguments);
				}
			}
		});
		btnManageOrders.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnManageOrders.setBounds(392, 123, 60, 60);
		frame.getContentPane().add(btnManageOrders);

		JButton btnViewSchedule = new JButton("");
		btnViewSchedule.setBackground(new Color(169, 169, 169));
		btnViewSchedule.setIcon(new ImageIcon(CStaff_Homepage.class.getResource("/img/Schedule.png")));
		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedule.main(arguments);
			}
		});
		btnViewSchedule.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewSchedule.setBounds(392, 309, 60, 60);
		frame.getContentPane().add(btnViewSchedule);

		JButton button_1 = new JButton("Back to Login");
		button_1.setBackground(new Color(143, 188, 143));
		button_1.setForeground(new Color(47, 79, 79));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Login_Screen.main(null);
					frame.dispose();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setFont(new Font("Arial", Font.PLAIN, 20));
		button_1.setBounds(363, 416, 166, 33);
		frame.getContentPane().add(button_1);

		JButton button_2 = new JButton("Exit");
		button_2.setBackground(new Color(143, 188, 143));
		button_2.setForeground(new Color(47, 79, 79));
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame exitConfirm = new JFrame("exit");
				if (JOptionPane.showConfirmDialog(exitConfirm, "Are you sure you would like to exit?", "RMS System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		button_2.setFont(new Font("Arial", Font.PLAIN, 20));
		button_2.setBounds(670, 416, 166, 33);
		frame.getContentPane().add(button_2);

		//The options for what cook staff can do
		JButton btnManageMenu = new JButton("");
		btnManageMenu.setBackground(new Color(169, 169, 169));
		btnManageMenu.setIcon(new ImageIcon(CStaff_Homepage.class.getResource("/img/menu.png")));
		btnManageMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Edit/Remove Menu Items", "Add Menu Items", "View Menu Items" };
				int n = JOptionPane.showOptionDialog(null, "Please select an action", "", JOptionPane.WARNING_MESSAGE,
						0, null, buttons, buttons[1]);
				if (n == 0) {
					CStaff_EditRemoveMenu.main(null);
				}
				if (n == 1) {
					CStaff_AddMenu.main(null);
				}
				if (n == 2) {
				CStaff_ViewMenu.main(arguments);
				}
			}
		});
		btnManageMenu.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnManageMenu.setBounds(392, 215, 60, 60);
		frame.getContentPane().add(btnManageMenu);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(CStaff_Homepage.class.getResource("/img/cookstaff_homepage.jpg")));
		lblNewLabel.setBounds(0, 0, 342, 470);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Manage Orders");
		lblNewLabel_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_1.setBackground(new Color(169, 169, 169));
		lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 33));
		lblNewLabel_1.setBounds(491, 138, 238, 34);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblManageMenu = new JLabel("Manage Menu");
		lblManageMenu.setForeground(new Color(169, 169, 169));
		lblManageMenu.setFont(new Font("Arial", Font.ITALIC, 33));
		lblManageMenu.setBackground(new Color(169, 169, 169));
		lblManageMenu.setBounds(491, 227, 238, 34);
		frame.getContentPane().add(lblManageMenu);
		
		JLabel lblViewSchedule = new JLabel("View Schedule");
		lblViewSchedule.setForeground(new Color(169, 169, 169));
		lblViewSchedule.setFont(new Font("Arial", Font.ITALIC, 33));
		lblViewSchedule.setBackground(new Color(169, 169, 169));
		lblViewSchedule.setBounds(491, 324, 238, 34);
		frame.getContentPane().add(lblViewSchedule);

	}
}

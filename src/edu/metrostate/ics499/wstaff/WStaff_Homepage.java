package edu.metrostate.ics499.wstaff;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import edu.metrostate.ics499.sharedstaff.Login_Screen;
import edu.metrostate.ics499.sharedstaff.Orders;
import edu.metrostate.ics499.sharedstaff.Schedule;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.ImageIcon;

public class WStaff_Homepage {
	public static String[] arguments;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		arguments = args;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WStaff_Homepage window = new WStaff_Homepage();
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
	public WStaff_Homepage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.getContentPane().setForeground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 873, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblHostHomepage = new JLabel("Wait Staff Homepage");
		lblHostHomepage.setForeground(new Color(143, 188, 143));
		lblHostHomepage.setFont(new Font("Georgia", Font.PLAIN, 53));
		lblHostHomepage.setBounds(346, 10, 513, 86);
		frame.getContentPane().add(lblHostHomepage);

		JButton btnViewTables = new JButton("");
		btnViewTables.setBackground(new Color(169, 169, 169));
		btnViewTables.setIcon(new ImageIcon(WStaff_Homepage.class.getResource("/img/order.png")));
		btnViewTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] buttons = { "Edit/Remove Orders", "Add an Order", "View Orders"};
				int n = JOptionPane.showOptionDialog(null, "Please select an action", "",
				        JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
				if (n == 0) {
					WStaff_EditRemoveOrder.main(arguments);
				}
				if (n == 1) {
					WStaff_AddOrder.main(arguments);
				}
				if (n ==2) {
					Orders.main(arguments);
				}
			}
		});
		btnViewTables.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewTables.setBounds(382, 147, 60, 60);
		frame.getContentPane().add(btnViewTables);

		JButton btnViewSchedule = new JButton("");
		btnViewSchedule.setIcon(new ImageIcon(WStaff_Homepage.class.getResource("/img/Schedule.png")));
		btnViewSchedule.setBackground(new Color(169, 169, 169));
		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedule.main(arguments);
			}
		});
		btnViewSchedule.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewSchedule.setBounds(382, 260, 60, 60);
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
		button_1.setBounds(364, 416, 166, 33);
		frame.getContentPane().add(button_1);

		JButton button_2 = new JButton("Exit");
		button_2.setForeground(new Color(47, 79, 79));
		button_2.setBackground(new Color(143, 188, 143));
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
		button_2.setBounds(665, 416, 166, 33);
		frame.getContentPane().add(button_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(WStaff_Homepage.class.getResource("/img/waitstaff_homepage.jpg")));
		lblNewLabel.setBounds(0, 0, 339, 470);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Manage Orders");
		lblNewLabel_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 33));
		lblNewLabel_1.setBounds(472, 159, 230, 40);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblViewSchedule = new JLabel("View Schedule");
		lblViewSchedule.setForeground(new Color(169, 169, 169));
		lblViewSchedule.setFont(new Font("Arial", Font.ITALIC, 33));
		lblViewSchedule.setBounds(472, 268, 263, 40);
		frame.getContentPane().add(lblViewSchedule);
	}
}

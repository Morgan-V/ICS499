package edu.metrostate.ics499.wstaff;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import edu.metrostate.ics499.sharedstaff.Login_Screen;
import edu.metrostate.ics499.sharedstaff.Schedule;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class WStaff_Homepage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		frame.setBounds(100, 100, 873, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblHostHomepage = new JLabel("Wait Staff Homepage");
		lblHostHomepage.setFont(new Font("Verdana", Font.PLAIN, 60));
		lblHostHomepage.setBounds(100, 45, 654, 86);
		frame.getContentPane().add(lblHostHomepage);

		JButton btnViewTables = new JButton("Manage Orders");
		btnViewTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//need to figure out manage orders
			}
		});
		btnViewTables.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewTables.setBounds(264, 176, 299, 50);
		frame.getContentPane().add(btnViewTables);

		JButton btnViewSchedule = new JButton("View Schedule");
		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedule.main(null);
			}
		});
		btnViewSchedule.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewSchedule.setBounds(264, 271, 299, 50);
		frame.getContentPane().add(btnViewSchedule);

		JButton button_1 = new JButton("Back to Login");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Login_Screen.main(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setFont(new Font("Georgia", Font.PLAIN, 20));
		button_1.setBounds(39, 402, 215, 34);
		frame.getContentPane().add(button_1);

		JButton button_2 = new JButton("Exit");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame exitConfirm = new JFrame("exit");
				if (JOptionPane.showConfirmDialog(exitConfirm, "Are you sure you would like to exit?", "RMS System",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		button_2.setFont(new Font("Georgia", Font.PLAIN, 20));
		button_2.setBounds(681, 402, 132, 34);
		frame.getContentPane().add(button_2);
	}

}

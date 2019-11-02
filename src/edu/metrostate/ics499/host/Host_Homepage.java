package edu.metrostate.ics499.host;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import edu.metrostate.ics499.sharedstaff.Login_Screen;
import edu.metrostate.ics499.sharedstaff.Schedule;
import edu.metrostate.ics499.sharedstaff.Tables;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Host_Homepage {
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
					Host_Homepage window = new Host_Homepage();
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
	public Host_Homepage() {
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

		JLabel lblHostHomepage = new JLabel("Host Homepage");
		lblHostHomepage.setFont(new Font("Verdana", Font.PLAIN, 60));
		lblHostHomepage.setBounds(162, 46, 499, 86);
		frame.getContentPane().add(lblHostHomepage);

		JButton btnViewTables = new JButton("View Tables");
		btnViewTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tables.main(null);
			}
		});
		btnViewTables.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewTables.setBounds(264, 176, 299, 50);
		frame.getContentPane().add(btnViewTables);

		JButton btnViewSchedule = new JButton("View Schedule");
		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedule.main(arguments);
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

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
import java.awt.Color;
import javax.swing.ImageIcon;

public class Host_Homepage {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 873, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblHostHomepage = new JLabel("Host Homepage");
		lblHostHomepage.setForeground(new Color(143, 188, 143));
		lblHostHomepage.setFont(new Font("Georgia", Font.PLAIN, 55));
		lblHostHomepage.setBounds(391, 23, 412, 86);
		frame.getContentPane().add(lblHostHomepage);

		JButton btnViewTables = new JButton("");
		btnViewTables.setIcon(new ImageIcon(Host_Homepage.class.getResource("/img/desk.png")));
		btnViewTables.setBackground(new Color(169, 169, 169));
		btnViewTables.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tables.main(null);
			}
		});
		btnViewTables.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewTables.setBounds(391, 163, 60, 60);
		frame.getContentPane().add(btnViewTables);

		JButton btnViewSchedule = new JButton("");
		btnViewSchedule.setIcon(new ImageIcon(Host_Homepage.class.getResource("/img/Schedule.png")));
		btnViewSchedule.setBackground(new Color(169, 169, 169));
		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Schedule.main(null);
			}
		});
		btnViewSchedule.setFont(new Font("Georgia", Font.PLAIN, 35));
		btnViewSchedule.setBounds(391, 278, 60, 60);
		frame.getContentPane().add(btnViewSchedule);

		JButton button_1 = new JButton("Back to Login");
		button_1.setBackground(new Color(143, 188, 143));
		button_1.setForeground(new Color(47, 79, 79));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Login_Screen.main(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setFont(new Font("Arial", Font.PLAIN, 20));
		button_1.setBounds(371, 414, 166, 33);
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
		button_2.setBounds(666, 414, 166, 33);
		frame.getContentPane().add(button_2);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Host_Homepage.class.getResource("/img/host_hompage.jpg")));
		lblNewLabel.setBounds(0, 0, 333, 470);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Manage Tables");
		lblNewLabel_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 33));
		lblNewLabel_1.setBounds(481, 179, 227, 34);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblViewSchedule = new JLabel("View Schedule");
		lblViewSchedule.setForeground(new Color(169, 169, 169));
		lblViewSchedule.setFont(new Font("Arial", Font.ITALIC, 33));
		lblViewSchedule.setBounds(481, 293, 227, 34);
		frame.getContentPane().add(lblViewSchedule);
	}

}

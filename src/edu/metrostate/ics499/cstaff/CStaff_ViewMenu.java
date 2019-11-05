package edu.metrostate.ics499.cstaff;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;
/*
 * author @Rose
 * Menu items Table display
 */

public class CStaff_ViewMenu {

	private JFrame frame;
	private static Connection con;
	private static Statement stmt;
	private static JTable displayMenuTable;
	private static String[] arguments;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		arguments = args;

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CStaff_ViewMenu window = new CStaff_ViewMenu();
					window.frame.setVisible(true);
					
					con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false","root","root");
					//SQL statement to select all menu item contents
					String query = "SELECT * FROM menuitems";						
					stmt =  (Statement) con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					//Converts the result set to a model and feeds it to the table						 						
					displayMenuTable.setModel(DbUtils.resultSetToTableModel(rs));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CStaff_ViewMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 819, 491);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnBack.setFont(new Font("Georgia", Font.BOLD, 16));
		
		//table model
		displayMenuTable = new JTable();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnBack)
					.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
					.addComponent(displayMenuTable, GroupLayout.PREFERRED_SIZE, 620, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(displayMenuTable, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(56, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(335, Short.MAX_VALUE)
					.addComponent(btnBack)
					.addGap(73))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
	
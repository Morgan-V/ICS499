package edu.metrostate.ics499.cstaff;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;
import java.awt.Color;

public class CStaff_ViewMenu {
	private static String MYSQL_URL;
	private static String MYSQL_USERNAME;
	private static String MYSQL_PASSWORD;
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
					
					con = (Connection) DriverManager
							.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
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
		readSettings();
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(47, 79, 79));
		frame.setBackground(new Color(47, 79, 79));
		frame.getContentPane().setForeground(new Color(47, 79, 79));
		frame.setBounds(100, 100, 816, 342);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//table model
		displayMenuTable = new JTable();
		displayMenuTable.setForeground(new Color(47, 79, 79));
		displayMenuTable.setFont(new Font("Arial", Font.BOLD, 15));
		displayMenuTable.setBackground(new Color(169, 169, 169));
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(47, 79, 79));
		btnBack.setBackground(new Color(143, 188, 143));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CStaff_Homepage.main(arguments);
				frame.dispose();
			}
		});
		btnBack.setFont(new Font("Arial", Font.BOLD, 10));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(31)
							.addComponent(btnBack))
						.addComponent(displayMenuTable, GroupLayout.PREFERRED_SIZE, 810, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addComponent(displayMenuTable, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnBack)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	/**
	 * Reads in settings from settings.conf
	 */
	private void readSettings() {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("settings.conf");
		} catch (FileNotFoundException ex) {
			System.out.println("settings.conf not found");
			System.exit(1);
		}
		try {
			prop.load(is);
		} catch (IOException e1) {
			System.out.println("An error occured");
			System.exit(1);
			
		}
		MYSQL_URL = "jdbc:mysql://" + prop.getProperty("MYSQL_IP") +
				":" + prop.getProperty("MYSQL_PORT") + "/" 
				+ prop.getProperty("MYSQL_SCHEMA") + "?useSSL=false";
		MYSQL_USERNAME = prop.getProperty("MYSQL_USER");
		MYSQL_PASSWORD = prop.getProperty("MYSQL_PASS");
	}
}
	
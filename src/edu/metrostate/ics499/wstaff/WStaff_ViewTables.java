package edu.metrostate.ics499.wstaff;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class WStaff_ViewTables {
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;
	private static Connection con;
	private static PreparedStatement stmt;
	private String data[][];
	private JFrame frame;
	private JTable table;

	private Panel bottomPanel = new Panel();
	private Panel buttonPane = new Panel();

	private Panel editPane = new Panel();
	

	DefaultTableModel model;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WStaff_ViewTables window = new WStaff_ViewTables();
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
	public WStaff_ViewTables() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		readSettings();
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		data = getTables(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		if (data == null) {
			data = new String[][] { { "", "", "", "", "", "" } };
		}
		table = new JTable(new DefaultTableModel(0, 0)) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // makes cells uneditable directly
			}
		};
		model = (DefaultTableModel) table.getModel();
		model.addColumn("ID");
		model.addColumn("Type");
		model.addColumn("Occupied");
		model.addColumn("Capacity");
		for (String user[] : data) {
			model.addRow(user); // Adds all users returned by the database
		}

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
			}
		});
		// table.setModel(new DefaultTableModel(data,column));
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 435, 200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane);
		editPane.setBackground(new Color(169, 169, 169));

		editPane.setLayout(new FlowLayout());
		
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(editPane, BorderLayout.NORTH);
		buttonPane.setBackground(new Color(47, 79, 79));

		buttonPane.setLayout(new FlowLayout());

		bottomPanel.add(buttonPane, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

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

	/**
	 * Returns a 2D array of all users in the database
	 * 
	 * @return
	 */
	private String[][] getTables(String url, String username, String password) {
		String[][] userArray = null;
		try {
			con = DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("select * from tables;");
			ResultSet rs = stmt.executeQuery();
			int count = 0; // finding the number of results found
			while (rs.next()) {
				count++;
			}
			rs.first();
			userArray = new String[count][rs.getMetaData().getColumnCount()];
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < rs.getMetaData().getColumnCount(); j++) {
					userArray[i][j] = rs.getString(j + 1);
					if(j == 2) {
						if(rs.getInt(j+1) == 0) {
							userArray[i][j] = "false";
						}else {
							userArray[i][j] = "true";
						}
						
					}
				}
				rs.next();
			}

		} catch (SQLException e) {
		}
		return userArray;
	}	
}



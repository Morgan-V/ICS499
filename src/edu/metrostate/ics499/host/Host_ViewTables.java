package edu.metrostate.ics499.host;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

public class Host_ViewTables implements ActionListener {
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
	private JButton updateButton;
	private int selectedRows[] = null;

	private Panel editPane = new Panel();
	private JTextField editId;
	private JTextField editType;
	private JTextField editOccupied;
	private JTextField editCapacity;

	DefaultTableModel model;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Host_ViewTables window = new Host_ViewTables();
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
	public Host_ViewTables() {
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
				selectedRows = table.getSelectedRows();
				updateFields();

			}
		});
		// table.setModel(new DefaultTableModel(data,column));
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 435, 200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane);
		editPane.setBackground(new Color(169, 169, 169));

		editPane.setLayout(new FlowLayout());
		editId = new JTextField();
		editId.setFont(new Font("Arial", Font.BOLD, 10));
		editId.setEditable(false);
		editId.setColumns(7);
		editType = new JTextField();
		editType.setEditable(false);
		editType.setForeground(new Color(47, 79, 79));
		editType.setFont(new Font("Arial", Font.BOLD, 10));
		editType.setColumns(7);
		editOccupied = new JTextField();
		editOccupied.setForeground(new Color(47, 79, 79));
		editOccupied.setFont(new Font("Arial", Font.BOLD, 10));
		editOccupied.setColumns(7);
		editCapacity = new JTextField();
		editCapacity.setEditable(false);
		editCapacity.setForeground(new Color(47, 79, 79));
		editCapacity.setFont(new Font("Arial", Font.BOLD, 10));
		editCapacity.setColumns(7);
		editPane.add(editId);
		editPane.add(editType);
		editPane.add(editOccupied);
		editPane.add(editCapacity);
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(editPane, BorderLayout.NORTH);
		buttonPane.setBackground(new Color(47, 79, 79));

		buttonPane.setLayout(new FlowLayout());
		updateButton = new JButton("Update");
		updateButton.setBackground(new Color(143, 188, 143));
		updateButton.setForeground(new Color(47, 79, 79));
		updateButton.setFont(new Font("Arial", Font.BOLD, 10));
		updateButton.addActionListener(this);
		buttonPane.add(updateButton);

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
	 * updates a users credentials in the database
	 * 
	 * @param id
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean editTable(int id, String type, boolean occupied, int capacity) {
		//TableID, TableType, Occupied, Capacity
		try {
			con = (Connection) DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("update tables set TableType = ?, Occupied = ?, Capacity = ? where TableId = ?;");
			stmt.setString(1, type); 
			stmt.setBoolean(2, occupied); 
			stmt.setInt(3, capacity); 
			stmt.setInt(4, id); 
			int row = stmt.executeUpdate();
			if (row > 0) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Update") {
			//make sure user enters true or false - do not accept anything but that
			if(!editOccupied.getText().contentEquals("true") && !editOccupied.getText().contentEquals("false")) {
				JOptionPane.showMessageDialog(null, "Please enter true or false");

			}
			else if (editTable(Integer.parseInt(editId.getText()), editType.getText(), Boolean.parseBoolean(editOccupied.getText()),Integer.parseInt(editCapacity.getText()))) {
				model.setValueAt(editId.getText(), selectedRows[0], 0);
				model.setValueAt(editType.getText(), selectedRows[0], 1);
				model.setValueAt(editOccupied.getText(), selectedRows[0], 2);
				model.setValueAt(editCapacity.getText(), selectedRows[0], 3);
			}
		}
	}

	private void updateFields() {
		try {
			editId.setText(model.getValueAt(selectedRows[0], 0).toString());
			editType.setText(model.getValueAt(selectedRows[0], 1).toString());
			editOccupied.setText(model.getValueAt(selectedRows[0], 2).toString());
			editCapacity.setText(model.getValueAt(selectedRows[0], 3).toString());
		} catch (Exception e) {
		}
	}
}




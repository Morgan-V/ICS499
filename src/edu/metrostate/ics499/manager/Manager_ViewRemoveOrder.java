package edu.metrostate.ics499.manager;

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
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

/**
 * 
 * @author Morgan This class allows the manager to view and remove menu items in
 *         the database
 *
 */
public class Manager_ViewRemoveOrder implements ActionListener {
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;
	private static Connection con;
	private static PreparedStatement stmt2;
	private String data[][];
	public JFrame frame;
	private JTable table;

	private Panel bottomPanel = new Panel();
	private Panel buttonPane = new Panel();
	private JButton deleteButton;
	private int selectedRows[] = null;

	private Panel editPane = new Panel();

	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_ViewRemoveOrder window = new Manager_ViewRemoveOrder();
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
	public Manager_ViewRemoveOrder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		readSettings();
		frame = new JFrame();
		frame.setBounds(100, 100, 626, 300);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		data = getOrders(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
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
		model.addColumn("Order ID");
		model.addColumn("Order");
		model.addColumn("Table ID");
		model.addColumn("Special Requests");
		model.addColumn("Order Complete");

		for (String menu[] : data) {
			model.addRow(menu); // Adds all menu items returned by the database
		}

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedRows = table.getSelectedRows();

			}
		});
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 435, 200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane);
		editPane.setBackground(new Color(47, 79, 79));

		editPane.setLayout(new FlowLayout());
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(editPane, BorderLayout.NORTH);
		buttonPane.setBackground(new Color(47, 79, 79));

		buttonPane.setLayout(new FlowLayout());
		deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
		deleteButton.setForeground(new Color(47, 79, 79));
		deleteButton.setBackground(new Color(143, 188, 143));
		deleteButton.addActionListener(this);
		buttonPane.add(deleteButton);

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
		MYSQL_URL = "jdbc:mysql://" + prop.getProperty("MYSQL_IP") + ":" + prop.getProperty("MYSQL_PORT") + "/"
				+ prop.getProperty("MYSQL_SCHEMA") + "?useSSL=false";
		MYSQL_USERNAME = prop.getProperty("MYSQL_USER");
		MYSQL_PASSWORD = prop.getProperty("MYSQL_PASS");
	}

	/**
	 * removeMenuItem allows user to remove menu items from the database
	 * 
	 * @param id       - Menu ID of the item being removed
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean removeMenuItem(int id, String url, String username, String password) {
		try {
			con = (Connection) DriverManager.getConnection(url, password, username);
			stmt2 = con.prepareStatement("delete from orders where OrderID = ?;");
			stmt2.setInt(1, id);
			int row = stmt2.executeUpdate();
			if (row > 0)
				return true;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * Returns a 2D array of all menu items in the database
	 * 
	 * @return
	 */
	private String[][] getOrders(String url, String username, String password) {
		String[][] userArray = null;
		try {
			con = DriverManager.getConnection(url, password, username);
			stmt2 = con.prepareStatement("select * from orders;");
			ResultSet rs = stmt2.executeQuery();
			int count = 0; // finding the number of results found
			while (rs.next()) {
				count++;
			}
			rs.first();
			userArray = new String[count][rs.getMetaData().getColumnCount()];
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < rs.getMetaData().getColumnCount(); j++) {
					userArray[i][j] = rs.getString(j + 1);
					if (j == 4) {
						if (rs.getInt(j + 1) == 0) {
							userArray[i][j] = "false";
						} else {
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

	/**
	 * Update will take inputs from user and update database Delete will remove
	 * order from system
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Delete") {
			int id = Integer.parseInt((String) table.getModel().getValueAt(selectedRows[0], 0));		
			if (removeMenuItem(id, MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)) {
				model.removeRow(selectedRows[0]);
			}
		}

	}

}

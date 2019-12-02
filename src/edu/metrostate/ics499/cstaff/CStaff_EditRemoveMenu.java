package edu.metrostate.ics499.cstaff;

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
import java.awt.Font;
import java.awt.Color;

/**
 * 
 * @author Morgan This class allows the cook staff to remove and update
 *         menu items in the database
 *
 */
public class CStaff_EditRemoveMenu implements ActionListener {
	private String MYSQL_URL;
	private String MYSQL_USERNAME;
	private String MYSQL_PASSWORD;
	private static Connection con;
	private static PreparedStatement stmt;
	private String data[][];
	public JFrame frame;
	private JTable table;

	private Panel bottomPanel = new Panel();
	private Panel buttonPane = new Panel();
	private JButton updateButton;
	private JButton deleteButton;
	private int selectedRows[] = null;

	private Panel editPane = new Panel();
	private JTextField editId;
	private JTextField editMenuName;
	private JTextField editMenuDesc;

	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CStaff_EditRemoveMenu window = new CStaff_EditRemoveMenu();
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
	public CStaff_EditRemoveMenu() {
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
		data = getMenuItems();
		if (data == null) {
			data = new String[][] { { "", "", "" } };
		}
		table = new JTable(new DefaultTableModel(0, 0)) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // makes cells uneditable directly
			}
		};
		model = (DefaultTableModel) table.getModel();
		model.addColumn("Item ID");
		model.addColumn("Item Name");
		model.addColumn("Item Description");

		for (String menu[] : data) {
			model.addRow(menu); // Adds all menu items returned by the database
		}

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedRows = table.getSelectedRows();
				updateFields();

			}
		});
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 435, 200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane);
		editPane.setBackground(new Color(169, 169, 169));

		editPane.setLayout(new FlowLayout());
		editId = new JTextField();
		editId.setEditable(false);
		editId.setColumns(7);
		editMenuName = new JTextField();
		editMenuName.setForeground(new Color(47, 79, 79));
		editMenuName.setFont(new Font("Arial", Font.BOLD, 10));
		editMenuName.setColumns(10);
		editMenuDesc = new JTextField();
		editMenuDesc.setForeground(new Color(47, 79, 79));
		editMenuDesc.setFont(new Font("Arial", Font.BOLD, 10));
		editMenuDesc.setColumns(25);
		editPane.add(editId);
		editPane.add(editMenuName);
		editPane.add(editMenuDesc);
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(editPane, BorderLayout.NORTH);
		buttonPane.setBackground(new Color(47, 79, 79));
		buttonPane.setForeground(new Color(47, 79, 79));

		buttonPane.setLayout(new FlowLayout());
		updateButton = new JButton("Update");
		updateButton.setBackground(new Color(143, 188, 143));
		updateButton.setFont(new Font("Arial", Font.BOLD, 10));
		updateButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.setBackground(new Color(143, 188, 143));
		deleteButton.setFont(new Font("Arial", Font.BOLD, 10));
		deleteButton.addActionListener(this);
		buttonPane.add(updateButton);
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
		MYSQL_URL = "jdbc:mysql://" + prop.getProperty("MYSQL_IP") +
				":" + prop.getProperty("MYSQL_PORT") + "/" 
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
	private boolean removeMenuItem(int id) {
		try {
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("delete from menuitems where MenuItem = ?;");
			stmt.setInt(1, id);
			int row = stmt.executeUpdate();
			if (row > 0)
				return true;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * edit menu allows the user to change the menu item name or menu item
	 * description
	 * 
	 * @param id       - The Menu ID
	 * @param menuItem - The name of the Menu Item
	 * @param menuDesc - The description of the Menu Item
	 * @return The new updated items
	 */
	private boolean editMenu(int id, String menuItem, String menuDesc) {
		try {

			con = (Connection) DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("update menuitems set ItemName = ?, ItemDesc = ? where MenuItem = ?;");
			stmt.setString(1, menuItem);
			stmt.setString(2, menuDesc);
			stmt.setInt(3, id);

			int row = stmt.executeUpdate();
			if (row > 0) {
				return true;
			}
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * Returns a 2D array of all menu items in the database
	 * 
	 * @return
	 */
	private String[][] getMenuItems() {
		String[][] userArray = null;
		try {
			con = DriverManager.getConnection(MYSQL_URL, MYSQL_PASSWORD, MYSQL_USERNAME);
			stmt = con.prepareStatement("select * from menuitems;");
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
			// make sure an item is selected
			if (editMenuName.getText().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "Please select an item");
			} else if (editMenu(Integer.parseInt(editId.getText()), editMenuName.getText(), editMenuDesc.getText())) {
				model.setValueAt(editId.getText(), selectedRows[0], 0);
				model.setValueAt(editMenuName.getText(), selectedRows[0], 1);
				model.setValueAt(editMenuDesc.getText(), selectedRows[0], 2);
			}
		} else if (e.getActionCommand() == "Delete") {
			// make sure an item is selected
			if (editMenuName.getText().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "Please select an item");
			} else {
				int id = Integer.parseInt((String) table.getModel().getValueAt(selectedRows[0], 0));
				if (removeMenuItem(id)) {
					model.removeRow(selectedRows[0]);
				}
			}
		}
	}

	private void updateFields() {
		try {
			editId.setText(model.getValueAt(selectedRows[0], 0).toString());
			editMenuName.setText(model.getValueAt(selectedRows[0], 1).toString());
			editMenuDesc.setText(model.getValueAt(selectedRows[0], 2).toString());

		} catch (Exception e) {
		}
	}
}

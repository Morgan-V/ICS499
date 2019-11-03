package edu.metrostate.ics499.cstaff;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Morgan/Ryan This class allows the cook staff to remove and update
 *         menu items in the database
 *
 */
public class CStaff_EditRemoveMenu implements ActionListener {
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/rms?useSSL=false";
	private final String MYSQL_USERNAME = "root";
	private final String MYSQL_PASSWORD = "root";
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
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		data = getMenuItems(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
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

		editPane.setLayout(new FlowLayout());
		editId = new JTextField();
		editId.setEditable(false);
		editId.setColumns(7);
		editMenuName = new JTextField();
		editMenuName.setColumns(10);
		editMenuDesc = new JTextField();
		editMenuDesc.setColumns(25);
		editPane.add(editId);
		editPane.add(editMenuName);
		editPane.add(editMenuDesc);
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(editPane, BorderLayout.NORTH);

		buttonPane.setLayout(new FlowLayout());
		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		buttonPane.add(updateButton);
		buttonPane.add(deleteButton);

		bottomPanel.add(buttonPane, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

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
	private String[][] getMenuItems(String url, String username, String password) {
		String[][] userArray = null;
		try {
			con = DriverManager.getConnection(url, password, username);
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
			if (editMenu(Integer.parseInt(editId.getText()), editMenuName.getText(), editMenuDesc.getText())) {
				model.setValueAt(editId.getText(), selectedRows[0], 0);
				model.setValueAt(editMenuName.getText(), selectedRows[0], 1);
				model.setValueAt(editMenuDesc.getText(), selectedRows[0], 2);
			}

		}

		else if (e.getActionCommand() == "Delete") {
			int id = Integer.parseInt((String) table.getModel().getValueAt(selectedRows[0], 0));
			if (removeMenuItem(id, MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)) {
				model.removeRow(selectedRows[0]);
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

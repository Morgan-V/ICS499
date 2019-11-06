package edu.metrostate.ics499.wstaff;

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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

/**
 * 
 * @author Morgan This class allows the cook staff to remove and update
 *         menu items in the database
 *
 */
public class WStaff_EditRemoveOrder implements ActionListener {
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/rms?useSSL=false";
	private final String MYSQL_USERNAME = "root";
	private final String MYSQL_PASSWORD = "root";
	private static Connection con;
	private static Statement stmt;
	private static PreparedStatement stmt2;
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
	private JTextField editSR;

	DefaultTableModel model;
	private JComboBox<Integer> editTableID = new JComboBox<Integer>();
	private JComboBox<Integer> editOrder = new JComboBox<Integer>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WStaff_EditRemoveOrder window = new WStaff_EditRemoveOrder();
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
	public WStaff_EditRemoveOrder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 300);
		updateMenuItems();
		updateTables();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		data = getOrders(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		if (data == null) {
			data = new String[][] { { "", "", "", "" } };
		}
		table = new JTable(new DefaultTableModel(0, 0)) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // makes cells uneditable directly
			}
		};
		model = (DefaultTableModel) table.getModel();
		model.addColumn("Order ID");
		model.addColumn("Table ID");
		model.addColumn("Order");
		model.addColumn("Special Requests");

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
		editId.setHorizontalAlignment(SwingConstants.CENTER);
		editId.setEditable(false);
		editId.setColumns(11);
		editSR = new JTextField();
		editSR.setHorizontalAlignment(SwingConstants.CENTER);
		editSR.setColumns(11);
		editPane.add(editId);

		editPane.add(editTableID);

		editPane.add(editOrder);
		editPane.add(editSR);
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
	 * method used to populate table options into JComboBox
	 */
	private void updateTables() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false", "root", "root");
			stmt = con.createStatement();
			String s = "select * from tables;";
			ResultSet rs2 = stmt.executeQuery(s);
			while (rs2.next()) {
				int tableID = rs2.getInt("TableID");
				editTableID.addItem(tableID);
			}
		} catch (SQLException e) {
		}
	}

	/**
	 * method used to populate menu options into JComboBox
	 */
	private void updateMenuItems() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false", "root", "root");
			stmt = con.createStatement();
			String s = "select * from menuitems;";
			ResultSet rs2 = stmt.executeQuery(s);
			while (rs2.next()) {
				int itemName = rs2.getInt("MenuItem");
				editOrder.addItem(itemName);
			}
		} catch (SQLException e) {
		}
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
	 * Allows user to edit existing orders
	 * @param orderID - the ID of the order being changed (not an editable field)
	 * @param tableID - the ID of the table being changed
	 * @param menuItem
	 * @param specialRequest - The special requests for an order
	 * @return - returns 1 if the request is successful, 0 if the request is bad
	 */
	private boolean editOrder(int orderID, Object tableID, Object menuItem, String specialRequest) {
		try {
			con = (Connection) DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt2 = con.prepareStatement(
					"update orders set MenuItem = ?, TableID = ?, SpecialRequest = ? where OrderID = ?;");
			stmt2.setInt(1, (int) menuItem);
			stmt2.setInt(2, (int) tableID);
			stmt2.setString(3, specialRequest);
			stmt2.setInt(4, orderID);
			int row = stmt2.executeUpdate();
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
	// TODO
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
				}
				rs.next();
			}

		} catch (SQLException e) {
		}
		return userArray;
	}
/**
 * Update will take inputs from user and update database
 * Delete will remove order from system
 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Update") {
			// make sure an item is selected
			if (editId.getText().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "Please select an item");
			} else if (editOrder(Integer.parseInt(editId.getText()), editTableID.getSelectedItem(),
					editOrder.getSelectedItem(), editSR.getText())) {
				model.setValueAt(editId.getText(), selectedRows[0], 0);
				model.setValueAt(editTableID.getSelectedItem(), selectedRows[0], 1);
				model.setValueAt(editOrder.getSelectedItem(), selectedRows[0], 2);
				model.setValueAt(editSR.getText(), selectedRows[0], 3);
			}
			} else if (e.getActionCommand() == "Delete") {
				// make sure an item is selected
				if (editId.getText().contentEquals("")) {
					JOptionPane.showMessageDialog(null, "Please select an item");
				} else {
					int id = Integer.parseInt((String) table.getModel().getValueAt(selectedRows[0], 0));
					if (removeMenuItem(id, MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)) {
						model.removeRow(selectedRows[0]);
					}
				}
			}
		
	}
/**
 * updates editable fields for the user
 */
	private void updateFields() {
		try {
			editId.setText(model.getValueAt(selectedRows[0], 0).toString());
			editTableID.setToolTipText(model.getValueAt(selectedRows[0], 1).toString());
			editOrder.setToolTipText(model.getValueAt(selectedRows[0], 2).toString());
			editSR.setText(model.getValueAt(selectedRows[0], 3).toString());

		} catch (Exception e) {
		}
	}
}

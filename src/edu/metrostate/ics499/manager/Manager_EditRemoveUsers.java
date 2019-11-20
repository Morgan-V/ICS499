package edu.metrostate.ics499.manager;

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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

public class Manager_EditRemoveUsers implements ActionListener {
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/rms?useSSL=false";
	private final String MYSQL_USERNAME = "root";
	private final String MYSQL_PASSWORD = "root";
	private static Connection con;
	private static PreparedStatement stmt;
	private String data[][];
	private JFrame frame;
	private JTable table;

	private Panel bottomPanel = new Panel();
	private Panel buttonPane = new Panel();
	private JButton updateButton;
	private JButton deleteButton;
	private int selectedRows[] = null;

	private Panel editPane = new Panel();
	private JTextField editId;
	private JTextField editPosition;
	private JTextField editFName;
	private JTextField editLName;
	private JTextField editPassword;
	private JTextField editPhone;

	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_EditRemoveUsers window = new Manager_EditRemoveUsers();
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
	public Manager_EditRemoveUsers() {
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
		data = getUsers(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
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
		model.addColumn("Position");
		model.addColumn("FirstName");
		model.addColumn("LastName");
		model.addColumn("Password");
		model.addColumn("Phone");
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
		editPosition = new JTextField();
		editPosition.setForeground(new Color(47, 79, 79));
		editPosition.setFont(new Font("Arial", Font.BOLD, 10));
		editPosition.setColumns(7);
		editFName = new JTextField();
		editFName.setForeground(new Color(47, 79, 79));
		editFName.setFont(new Font("Arial", Font.BOLD, 10));
		editFName.setColumns(7);
		editLName = new JTextField();
		editLName.setForeground(new Color(47, 79, 79));
		editLName.setFont(new Font("Arial", Font.BOLD, 10));
		editLName.setColumns(7);
		editPassword = new JTextField();
		editPassword.setForeground(new Color(47, 79, 79));
		editPassword.setFont(new Font("Arial", Font.BOLD, 10));
		editPassword.setColumns(7);
		editPhone = new JTextField();
		editPhone.setForeground(new Color(47, 79, 79));
		editPhone.setFont(new Font("Arial", Font.BOLD, 10));
		editPhone.setColumns(7);
		editPane.add(editId);
		editPane.add(editPosition);
		editPane.add(editFName);
		editPane.add(editLName);
		editPane.add(editPassword);
		editPane.add(editPhone);
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
		deleteButton = new JButton("Delete");
		deleteButton.setBackground(new Color(143, 188, 143));
		deleteButton.setForeground(new Color(47, 79, 79));
		deleteButton.setFont(new Font("Arial", Font.BOLD, 10));
		deleteButton.addActionListener(this);
		buttonPane.add(updateButton);
		buttonPane.add(deleteButton);

		bottomPanel.add(buttonPane, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

	}

	/**
	 * removes a user from the database
	 * 
	 * @param id
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 */
	private boolean removeUser(int id, String url, String username, String password) {
		try {
			con = (Connection) DriverManager.getConnection(url, password, username);
			stmt = con.prepareStatement("delete from users where userId = ?;");
			stmt.setInt(1, id);
			int row = stmt.executeUpdate();
			if (row > 0)
				return true;
		} catch (SQLException e) {
		}
		return false;
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
	private boolean editUser(int id, String position, String FName, String LName, String Password, String Contact) {
		try {
			con = (Connection) DriverManager.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("update users set Position = ?, FirstName = ?, LastName = ?, "
					+ "Password = ?, Contact = ? where UserId = ?;");
			stmt.setString(1, position);
			stmt.setString(2, FName);
			stmt.setString(3, LName);
			stmt.setString(4, Password);
			stmt.setString(5, Contact);
			stmt.setInt(6, id);
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
	private String[][] getUsers(String url, String username, String password) {
		String[][] userArray = null;
		try {
			con = DriverManager.getConnection(url, password, username);
			stmt = con.prepareStatement("select * from users;");
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
			// make sure a user is selected
			if (editFName.getText().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "Please select a user");
			} else if (editUser(Integer.parseInt(editId.getText()), editPosition.getText(), editFName.getText(),
					editLName.getText(), editPassword.getText(), editPhone.getText())) {
				model.setValueAt(editId.getText(), selectedRows[0], 0);
				model.setValueAt(editPosition.getText(), selectedRows[0], 1);
				model.setValueAt(editFName.getText(), selectedRows[0], 2);
				model.setValueAt(editLName.getText(), selectedRows[0], 3);
				model.setValueAt(editPassword.getText(), selectedRows[0], 4);
				model.setValueAt(editPhone.getText(), selectedRows[0], 5);
			}
		} else if (e.getActionCommand() == "Delete") {
			//make sure a user is selected
			if (editFName.getText().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "Please select a user");
			} else {
				int id = Integer.parseInt((String) table.getModel().getValueAt(selectedRows[0], 0));
				if (removeUser(id, MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD)) {
					model.removeRow(selectedRows[0]);
				}
			}
		}
	}

	private void updateFields() {
		try {
			editId.setText(model.getValueAt(selectedRows[0], 0).toString());
			editPosition.setText(model.getValueAt(selectedRows[0], 1).toString());
			editFName.setText(model.getValueAt(selectedRows[0], 2).toString());
			editLName.setText(model.getValueAt(selectedRows[0], 3).toString());
			editPassword.setText(model.getValueAt(selectedRows[0], 4).toString());
			editPhone.setText(model.getValueAt(selectedRows[0], 5).toString());
		} catch (Exception e) {
		}
	}
}

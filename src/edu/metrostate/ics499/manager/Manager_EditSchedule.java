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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

public class Manager_EditSchedule implements ActionListener {
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
	private JButton addButton;
	private int selectedRows[] = null;

	private Panel editPane = new Panel();
	private JTextField editId;
	private JTextField editUserId;
	private JTextField editName;
	private JTextField editDate;
	private JTextField editStart;
	private JTextField editEnd;

	DefaultTableModel model;

	/**
	 * Launch the application.
	 * <p>
	 * <b>**Remove for final iteration**</b>
	 * </p>
	 */
	// FIXME Nuke Main class. Manager_EditSchedule can be called by another process.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_EditSchedule window = new Manager_EditSchedule();
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
	public Manager_EditSchedule() {
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
		data = getSchedules();
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
		model.addColumn("UserId");
		model.addColumn("Name");
		model.addColumn("Date");
		model.addColumn("Start Time");
		model.addColumn("End Time");
		for (String column[] : data) {
			model.addRow(column); // Adds all users returned by the database
		}
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedRows = table.getSelectedRows();
				updateFields();
			}
		});
		JScrollPane pane = new JScrollPane(table);
		pane.setLayout(new ScrollPaneLayout());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane, BorderLayout.CENTER);

		editPane.setLayout(new FlowLayout());
		editId = new JTextField();
		editId.setEditable(false);
		editId.setColumns(7);
		editUserId = new JTextField();
		editUserId.setColumns(7);
		editName = new JTextField();
		editName.setColumns(7);
		editName.setEditable(false);
		editDate = new JTextField();
		editDate.setColumns(7);
		editStart = new JTextField();
		editStart.setColumns(7);
		editEnd = new JTextField();
		editEnd.setColumns(7);
		editPane.add(editId);
		editPane.add(editUserId);
		editPane.add(editName);
		editPane.add(editDate);
		editPane.add(editStart);
		editPane.add(editEnd);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(editPane, BorderLayout.NORTH);

		buttonPane.setLayout(new FlowLayout());
		updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		addButton = new JButton("Add New");
		addButton.addActionListener(this);
		buttonPane.add(updateButton);
		buttonPane.add(deleteButton);
		buttonPane.add(addButton);

		bottomPanel.add(buttonPane, BorderLayout.CENTER);
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
	}

	/**
	 * removes a user from the database
	 */
	private boolean removeSchedule(int id) {
		try {
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("delete from schedules where scheduleId = ?;");
			stmt.setInt(1, id);
			int row = stmt.executeUpdate();
			if (row > 0)
				return true;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * Returns the name of the user associated with the ID
	 * 
	 * @param id
	 * @return
	 */
	private String IdToName(int id) {
		try {
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME,MYSQL_PASSWORD);
			stmt = con.prepareStatement("select FirstName, LastName from users "
					+ "where UserID =?;",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			// Checks if either part of the name is null and does not return it
			// if it is
			String firstName = rs.getString(1);
			if (rs.wasNull()) {
				firstName = "";
			}
			String lastName = rs.getString(2);
			if (rs.wasNull()) {
				lastName = "";
			}
			return firstName + " " + lastName;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * updates a users credentials in the database
	 * 
	 * @param ScheduleId
	 * @param UserId
	 * @param Date
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	private boolean editSchedule(int ScheduleId, int UserId, String date, 
			String startTime, String endTime) {
		try {
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement(
					"update schedules set UserID = ?, date = ?, startTime = ?, " 
							+ "endTime = ? where ScheduleId = ?;");
			stmt.setInt(1, UserId);
			stmt.setString(2, date);
			if (date.compareTo("") == 0) {
				stmt.setNull(2, java.sql.Types.DATE);
			}
			stmt.setString(3, startTime);
			stmt.setString(4, endTime);
			stmt.setInt(5, ScheduleId);
			int row = stmt.executeUpdate();
			if (row > 0) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			// Falls through and returns false
		}
		return false;
	}

	/**
	 * Returns a 2D array of all users in the database
	 * 
	 * @return
	 */
	private String[][] getSchedules() {
		String[][] userArray = null;
		try {
			con = DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("select * from schedules;");
			ResultSet rs = stmt.executeQuery();
			int count = 0; // finding the number of results found
			while (rs.next()) {
				count++;
			}
			rs.first();
			// User array is 1 larger than the number of columns returned because the
			// User's name is found and placed in index 2
			userArray = new String[count][rs.getMetaData().getColumnCount() + 1];
			for (int i = 0; i < count; i++) {
				int currentColumn = 0;
				for (int j = 0; j < rs.getMetaData().getColumnCount() + 1; j++) {
					// users name is found and placed in index 2
					if (j == 2) {
						userArray[i][j] = 
								IdToName(Integer.parseInt(userArray[i][j - 1]));
					} else {
						userArray[i][j] = rs.getString(currentColumn + 1);
						currentColumn++;
					}
				}
				rs.next();
			}
		} catch (SQLException e) {
			// Falls through and returns an empty 2D array if any exceptions are thrown
		}
		return userArray;
	}

	/**
	 * Adds a record to the database. returns the id of the new record or -1 if
	 * failed.
	 * 
	 * @param UserID
	 * @param date
	 * @param StartTime
	 * @param EndTime
	 * @return
	 */
	public int createSchedule(int UserID, String date,
			String StartTime, String EndTime) {
		try {
			// ScheduleID,UserID,Date,StartTime,EndTime
			con = (Connection) DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement(
					"insert into schedules (UserId,Date,StartTime,EndTime)" 
							+ " values ( ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, UserID);
			stmt.setString(2, date);
			stmt.setString(3, StartTime);
			stmt.setString(4, EndTime);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.first();
			return rs.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
			return -1;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Update") {
			if (editSchedule(
					Integer.parseInt(editId.getText()), 
					Integer.parseInt(editUserId.getText()),
					editDate.getText(),
					editStart.getText(),
					editEnd.getText())) {
				
				model.setValueAt(editId.getText(), selectedRows[0], 0);
				model.setValueAt(editUserId.getText(), selectedRows[0], 1);
				model.setValueAt(IdToName
						(Integer.parseInt(editUserId.getText())),selectedRows[0], 2);
				editName.setText(IdToName(Integer.parseInt(editUserId.getText())));
				model.setValueAt(editDate.getText(), selectedRows[0], 3);
				model.setValueAt(editStart.getText(), selectedRows[0], 4);
				model.setValueAt(editEnd.getText(), selectedRows[0], 5);
			}
		} else if (e.getActionCommand() == "Delete") {
			int id = Integer.parseInt((String) table.getModel()
					.getValueAt(selectedRows[0], 0));
			if (removeSchedule(id)) {
				model.removeRow(selectedRows[0]);
			}
		} else if (e.getActionCommand() == "Add New") {
			// Creates an empty record in the database
			String id = createSchedule(1, null, null, null) + "";
			String[] temp = { id, "", "", "", "" };
			model.addRow(temp);
		}
	}

	private void updateFields() {
		try {
			editId.setText(model.getValueAt(selectedRows[0], 0).toString());
			editUserId.setText(model.getValueAt(selectedRows[0], 1).toString());
			editName.setText(model.getValueAt(selectedRows[0], 2).toString());
			editDate.setText(model.getValueAt(selectedRows[0], 3).toString());
			editStart.setText(model.getValueAt(selectedRows[0], 4).toString());
			editEnd.setText(model.getValueAt(selectedRows[0], 5).toString());
		} catch (Exception e) {
		}
	}
}
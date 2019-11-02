package edu.metrostate.ics499.sharedstaff;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

public class Schedule {
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/rms?useSSL=false";
	private final String MYSQL_USERNAME = "root";
	private final String MYSQL_PASSWORD = "root";
	private static Connection con;
	private static PreparedStatement stmt;
	private String data[][];
	private JFrame frame;
	private JTable table;
	private static int id;


	DefaultTableModel model;

	/**
	 * Launch the application.
	 * <p>
	 * <b>**Remove for final iteration**</b>
	 * </p>
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			id = Integer.parseInt(args[0]);
		}
		//TODO Remove
		//id = 1;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Schedule window = new Schedule();
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
	public Schedule() {
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
		data = getSchedules(id);
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
			}
		});
		JScrollPane pane = new JScrollPane(table);
		pane.setLayout(new ScrollPaneLayout());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane, BorderLayout.CENTER);
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
	 * Returns a 2D array of all users in the database
	 * 
	 * @return
	 */
	private String[][] getSchedules(int id) {
		String[][] userArray = null;
		try {
			con = DriverManager
					.getConnection(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
			stmt = con.prepareStatement("select * from schedules where userId = ?;");
			stmt.setInt(1, id);
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
}

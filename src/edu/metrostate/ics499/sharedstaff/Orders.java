package edu.metrostate.ics499.sharedstaff;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Morgan This class allows user to view orders
 *
 */
public class Orders {
	private final String MYSQL_URL = "jdbc:mysql://localhost:3306/rms?useSSL=false";
	private final String MYSQL_USERNAME = "root";
	private final String MYSQL_PASSWORD = "root";
	private static Connection con;
	private static PreparedStatement stmt;
	private String data[][];
	public JFrame frame;
	private JTable table;

	private Panel bottomPanel = new Panel();
	DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Orders window = new Orders();
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
	public Orders() {
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
		data = getOrders(MYSQL_URL, MYSQL_USERNAME, MYSQL_PASSWORD);
		if (data == null) {
			data = new String[][] { { "", "", "", "" } };
		}
		table = new JTable(new DefaultTableModel(0, 0)) {
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
				table.getSelectedRows();

			}
		});
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(0, 0, 435, 200);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		frame.getContentPane().add(pane);
		bottomPanel = new Panel();
		bottomPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

	}

	/**
	 * Returns a 2D array of all orders in the database
	 * 
	 * @return
	 */
	// TODO
	private String[][] getOrders(String url, String username, String password) {
		String[][] userArray = null;
		try {
			con = DriverManager.getConnection(url, password, username);
			stmt = con.prepareStatement("select * from orders;");
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

}

package edu.metrostate.ics499.manager;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.mysql.jdbc.Statement;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Manager_AddTables {

	private JFrame frame;
	private JLabel lblTableId; 
	private JLabel lblTableType;
	private JLabel lblTableOccupancy;
	private JLabel lblTableCapacity;
	private JLabel SuccessAddLabel;
	private JTextField tableIDTextField;
	private JTextField tableTypeTextField;
	private JTextField tableCapacityTextField;
	private JTextField tableOccupancyTextField;

	private static Connection con;
	private static PreparedStatement stmt;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Manager_AddTables window = new Manager_AddTables();
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
	public Manager_AddTables() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 386);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(null);
		
		lblTableId = new JLabel("Table ID");
		lblTableId.setBounds(23, 16, 61, 20);
		frame.getContentPane().add(lblTableId);
		
		lblTableType = new JLabel("Table Type");
		lblTableType.setBounds(23, 64, 79, 20);
		frame.getContentPane().add(lblTableType);
		
		lblTableOccupancy = new JLabel("Table Occupancy");
		lblTableOccupancy.setBounds(23, 114, 120, 20);
		frame.getContentPane().add(lblTableOccupancy);

		lblTableCapacity = new JLabel("Table Capacity");
		lblTableCapacity.setBounds(23, 164, 115, 20);
		frame.getContentPane().add(lblTableCapacity);
						
		tableIDTextField = new JTextField();
		tableIDTextField.setBounds(158, 13, 146, 26);
		tableIDTextField.setColumns(10);
		frame.getContentPane().add(tableIDTextField);
		
		tableTypeTextField = new JTextField();
		tableTypeTextField.setBounds(158, 61, 146, 26);
		tableTypeTextField.setColumns(10);
		frame.getContentPane().add(tableTypeTextField);
		
		tableOccupancyTextField = new JTextField();
		tableOccupancyTextField.setBounds(158, 111, 146, 26);		
		tableOccupancyTextField.setColumns(10);
		frame.getContentPane().add(tableOccupancyTextField);
		
		tableCapacityTextField = new JTextField();
		tableCapacityTextField.setBounds(158, 161, 146, 26);
		tableCapacityTextField.setColumns(10);
		frame.getContentPane().add(tableCapacityTextField);				
		
		//success label, display success if table created
		SuccessAddLabel = new JLabel("");
		SuccessAddLabel.setFont(new Font("Georgia", Font.BOLD, 11));
		SuccessAddLabel.setBounds(245, 218, 69, 20);
		frame.getContentPane().add(SuccessAddLabel);
				
		//submits table information and display message
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkConditions()) {
					String tableNum = tableIDTextField.getText();
					String tableType = tableTypeTextField.getText();
					boolean tableOccupancy = tableOccupancyTextField.getText() != null;
					String tableCapacity = tableCapacityTextField.getText();					
					createTable(tableNum, tableType,tableOccupancy, tableCapacity);
															
				}
			}

			private void createTable(String tableNum, String tableType, boolean tableOccupancy, String tableCapacity) {
				// TODO Auto-generated method stub
				try {
				con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/rms?useSSL=false","root","root");
				stmt =  con.prepareStatement
						("insert into Tables (tableID, TableType, Occupied, Capacity) values ( ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, tableNum);
				stmt.setString(2, tableType);
				stmt.setBoolean(3, false);
				stmt.setString(4, tableCapacity);				
				
				//execute insert statement and update DB
				stmt.executeUpdate();
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next(); 
				} catch (SQLException e) {
				}
			}

			private boolean checkConditions() {
				// TODO Auto-generated method stub
				//check for empty texts
				boolean conditionsOk = true;
				if(tableIDTextField.getText().isEmpty()) {
					SuccessAddLabel.setForeground(Color.RED);
					SuccessAddLabel.setText("Item number cannot be blank");
					lblTableId.setForeground(Color.RED);
					conditionsOk = false;
				}else{
					SuccessAddLabel.setText("");
					lblTableId.setForeground(Color.BLACK);
				}
				if(tableTypeTextField.getText().isEmpty()) {
					lblTableType.setForeground(Color.RED);
					SuccessAddLabel.setText("Item name cannot be blank");
					conditionsOk = false;
				}else {
					SuccessAddLabel.setText("");
					lblTableType.setForeground(Color.BLACK);
				}
				if(tableCapacityTextField.getText().isEmpty()) {
					lblTableCapacity.setForeground(Color.RED);
					SuccessAddLabel.setText("Item description cannot be blank");
					conditionsOk = false;
				}else {
					SuccessAddLabel.setText("Success:  Table Added!");
					lblTableCapacity.setForeground(Color.BLACK);
				}
				
				return conditionsOk;
			}
		});
		btnSubmit.setFont(new Font("Georgia", Font.BOLD, 11));
		btnSubmit.setBounds(204, 268, 115, 29);
		frame.getContentPane().add(btnSubmit);

			
		//back button that returns to managers homepage
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Georgia", Font.BOLD, 11));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Manager_Homepage.main(null);
			}
		});
		btnBack.setBounds(28, 268, 115, 29);
		frame.getContentPane().add(btnBack);
						
	}
}

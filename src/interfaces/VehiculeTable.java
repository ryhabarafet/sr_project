package interfaces;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import database.FactureVehicule;

import javax.swing.JScrollPane;

public class VehiculeTable extends JFrame {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VehiculeTable window = new VehiculeTable();
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
	public VehiculeTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 604, 243);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 568, 182);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public void setData(ArrayList<FactureVehicule> data) {
		// Populate the JTable with data
		DefaultTableModel VehiculeTableModel = new DefaultTableModel();
		VehiculeTableModel.addColumn("code facture");
		VehiculeTableModel.addColumn("date facture");
		VehiculeTableModel.addColumn("client");
		VehiculeTableModel.addColumn("designation");
		VehiculeTableModel.addColumn("quantité");
		VehiculeTableModel.addColumn("prix");
		VehiculeTableModel.addColumn("total");

		for (FactureVehicule fV : data) {
			VehiculeTableModel.addRow(new Object[] { fV.getCode_facture(), fV.getDate_facture(), fV.getClient(),
					fV.getDesignation(), fV.getQte(), fV.getPrix(), fV.getTotal() });
		}
		table.setModel(VehiculeTableModel);
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

}

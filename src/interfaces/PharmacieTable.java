package interfaces;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.FacturePharmacie;
import database.FactureVehicule;

import javax.swing.JScrollPane;

public class PharmacieTable extends JFrame {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PharmacieTable window = new PharmacieTable();
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
	public PharmacieTable() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 581, 225);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 545, 164);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	public void setData(ArrayList<FacturePharmacie> data) {
		// Populate the JTable with data
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Code");
		model.addColumn("Client");
		model.addColumn("Montant");
		for (FacturePharmacie item : data) {
			model.addRow(new Object[] { item.getCode(), item.getNomClient(), item.getMontantVerse() });
		}
		table.setModel(model);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

}

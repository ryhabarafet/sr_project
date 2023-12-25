package interfaces;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import config.ComboBoxItem;
import database.FacturePharmacie;
import database.FactureVehicule;

import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Clients {

	private JFrame frame;
	private static final String SERVER_ADDRESS = "localhost";
	private static final int PORT = 8080;
	PrintStream writer;
	BufferedReader reader;
	Socket socket;
	JComboBox comboBox;
	private JTable table;
	private JTable table2;
	JLabel result;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clients window = new Clients();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public Clients() {
		
	}
	public Clients(String server, int port) {
		try {
			socket = new Socket(server, port);
			writer = new PrintStream(socket.getOutputStream(), true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		ComboBoxItem[] items = { new ComboBoxItem("Factures vehicules", "getFacturesVehicule"),
				new ComboBoxItem("Factures Pharmacies", "getFacturesPharmacie"),
				new ComboBoxItem("Recette Factures vehicules", "getRecetteFacturesVehicule"),
				new ComboBoxItem("Recette Factures Pharmacies", "getRecetteFacturesPharmacie") };
		frame = new JFrame();
		frame.setBounds(100, 100, 476, 166);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Client");
		JButton btnNewButton = new JButton("Envoyer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				result.setText("");
				String message = items[comboBox.getSelectedIndex()].getValue();
				writer.println(message);

				try {
					String response = reader.readLine();
					if (response == null) {
						socket.close();
						System.out.println("Disconnected from server");
					} else {
						switch (message) {
						case "getFacturesVehicule":
							ArrayList<FactureVehicule> array = deserializeArrayList(response);
							fillVehiculeTable(array);
							break;
						case "getRecetteFacturesVehicule":
							result.setText("La recette des factures des véhicules est " + response);
							break;
						case "getRecetteFacturesPharmacie":
							result.setText("La recette des factures de la pharmacie est " + response);
							break;
						case "getFacturesPharmacie":
							ArrayList<FacturePharmacie> arrP = deserializeArrayListPharmacie(response);
							fillPharmacieTable(arrP);
							break;
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(356, 50, 89, 23);
		frame.getContentPane().add(btnNewButton);

		comboBox = new JComboBox<>(items);
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(97, 51, 234, 20);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("Client");
		lblNewLabel.setFont(new Font("Stencil", Font.BOLD, 28));
		lblNewLabel.setBounds(10, 11, 112, 34);
		frame.getContentPane().add(lblNewLabel);

		result = new JLabel("");
		result.setBounds(22, 92, 423, 14);
		frame.getContentPane().add(result);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private static ArrayList<FactureVehicule> deserializeArrayList(String json) {
		return new Gson().fromJson(json, new TypeToken<ArrayList<FactureVehicule>>() {
		}.getType());
	}

	private static ArrayList<FacturePharmacie> deserializeArrayListPharmacie(String json) {
		return new Gson().fromJson(json, new TypeToken<ArrayList<FacturePharmacie>>() {
		}.getType());
	}

	public void fillPharmacieTable(ArrayList<FacturePharmacie> data) {
		PharmacieTable p = new PharmacieTable();
		p.setData(data);
		p.setVisible(true);
	}

	public void fillVehiculeTable(ArrayList<FactureVehicule> data) {
		VehiculeTable v = new VehiculeTable();
		v.setData(data);
		v.setVisible(true);
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}

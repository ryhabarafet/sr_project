package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import database.FacturePharmacie;

public class ServeurUDP extends JFrame {

	private JTextArea textArea;
	private JButton startButton;
	private DatagramSocket socket;
	private Gson gson;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ServeurUDP window = new ServeurUDP();
				window.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ServeurUDP() {
		initialize();
		setTitle("SI Entr.1");
		gson = new Gson();
		try {
			socket = new DatagramSocket(4567);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		startButton = new JButton("Démarrer le serveur");

		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			new Thread(this::startServer).start();
		});

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(startButton, BorderLayout.SOUTH);
	}

	private void startServer() {
		appendToTextArea("Serveur démarré");

		while (true) {
			try {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket);

				InetAddress clientAddress = receivePacket.getAddress();
				int clientPort = receivePacket.getPort();

				String request = new String(receivePacket.getData(), 0, receivePacket.getLength());
				appendToTextArea("Requête reçue : " + request);

				String response = processRequest(request);

				byte[] sendData = response.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
				socket.send(sendPacket);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String processRequest(String request) {
		switch (request) {
		case "getFacturesPharmacie", "getRecetteFacturesPharmacie":
			//String filePath = "C:\\test.json";
			String filePath = "src/database/test.json";
			ArrayList<FacturePharmacie> responseList = readFacturesFromJsonFile(filePath);
			return gson.toJson(responseList);
		default:
			return "Commande non reconnue";
		}
	}

	private ArrayList<FacturePharmacie> readFacturesFromJsonFile(String filePath) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			StringBuilder jsonData = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}

			// Use Gson to deserialize the JSON data into an ArrayList
			java.lang.reflect.Type factureListType = new TypeToken<ArrayList<FacturePharmacie>>() {
			}.getType();
			return gson.fromJson(jsonData.toString(), factureListType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>(); // Return an empty list in case of error
	}

	private void appendToTextArea(String message) {
		SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
	}
	
	
}

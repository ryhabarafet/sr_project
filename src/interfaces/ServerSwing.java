package interfaces;

import java.awt.EventQueue;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import database.FacturePharmacie;
import database.FactureVehicule;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;

public class ServerSwing {

	private JFrame frame;
	private static final int PORT = 8080;
	private static final String TCP_SERVER_HOST = "localhost";
	private static final int TCP_SERVER_PORT = 12345;
	private static final String UDP_SERVER_ADDRESS = "localhost:4567";
	JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerSwing window = new ServerSwing();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws IOException
	 */
	public ServerSwing() {
		initialize();

	}

	private void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			appendToTextArea("Server started on port " + PORT);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				new Thread(new ClientHandler(clientSocket)).start();
			}
		} catch (IOException e) {

		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Server proxy");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		new Thread(this::startServer).start();
	}

	private void appendToTextArea(String message) {
		SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
	}

	class ClientHandler implements Runnable {

		private Socket clientSocket;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

				while (true) {
					String message = reader.readLine();
					if (message == null) {
						break;
					}
					String response = null;
					appendToTextArea(getCurrentDatetime()+" Client message: " + message);
					switch (message) {
					case "getFacturesVehicule":
						response = sendTCPMessage(message, TCP_SERVER_HOST, TCP_SERVER_PORT);
						break;
					case "getRecetteFacturesVehicule":
						response = sendTCPMessage(message, TCP_SERVER_HOST, TCP_SERVER_PORT);
						response = calculRecetteVehicule(response);
						break;
					case "getFacturesPharmacie":
						response = sendUDPMessage(message, UDP_SERVER_ADDRESS);
						break;
					case "getRecetteFacturesPharmacie":
						response = sendUDPMessage(message, UDP_SERVER_ADDRESS);
						response = calculRecettePharmacie(response);
						break;
					}
					writer.println(response);
				}

				clientSocket.close();
				System.out.println("Client disconnected");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private String getCurrentDatetime() {
			
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			return formattedDateTime+":";
		}

		private String calculRecetteVehicule(String message) {
			double recette = 0;
			ArrayList<FactureVehicule> array = deserializeArrayList(message);
			for (FactureVehicule fv : array) {
				recette += fv.getTotal();
			}
			return recette + "";
		}

		private String calculRecettePharmacie(String message) {
			double recette = 0;
			ArrayList<FacturePharmacie> array = deserializeArrayListPharmacie(message);
			for (FacturePharmacie fp : array) {
				recette += fp.getMontantVerse();
			}
			return recette + "";
		}

		private String sendTCPMessage(String message, String host, int port) throws IOException {
			Socket tcpSocket = new Socket(host, port);
			BufferedReader reader = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
			PrintWriter tcpWriter = new PrintWriter(tcpSocket.getOutputStream(), true);
			tcpWriter.println(message);
			tcpWriter.flush();
			String serverResponse = reader.readLine();
			appendToTextArea(getCurrentDatetime()+" Response from the server: " + serverResponse);
			return serverResponse;
		}

		private String sendUDPMessage(String message, String address) throws IOException {
			DatagramSocket udpSocket = new DatagramSocket();
			byte[] data = message.getBytes();
			InetAddress udpServerAddress = InetAddress.getByName(address.split(":")[0]);
			int udpServerPort = Integer.parseInt(address.split(":")[1]);
			DatagramPacket packet = new DatagramPacket(data, data.length, udpServerAddress, udpServerPort);
			udpSocket.send(packet);
			byte[] receiveData = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			udpSocket.receive(receivePacket);
			String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
			return response;
		}
	}

	private static ArrayList<FactureVehicule> deserializeArrayList(String json) {
		return new Gson().fromJson(json, new TypeToken<ArrayList<FactureVehicule>>() {
		}.getType());
	}

	private static ArrayList<FacturePharmacie> deserializeArrayListPharmacie(String json) {
		return new Gson().fromJson(json, new TypeToken<ArrayList<FacturePharmacie>>() {
		}.getType());
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

}

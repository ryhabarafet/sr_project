package interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import database.DTO;
import database.FactureVehicule;

public class ServeurTCP extends JFrame {

	private JFrame frame;
	private JTextArea textArea;
	private JButton startButton;
	private ServerSocket serverSocket;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ServeurTCP window = new ServeurTCP();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ServeurTCP() {
		initialize();
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SI Entr.2");
		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		startButton = new JButton("Démarrer le serveur");

		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			new Thread(this::startServer).start();
		});

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(startButton, BorderLayout.SOUTH);
	}

	private void startServer() {
		final int port = 12345;

		try {
			serverSocket = new ServerSocket(port);
			appendToTextArea("Serveur démarré");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				Thread thread = new Thread(new ClientHandler(clientSocket));
				thread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendToTextArea(String message) {
		SwingUtilities.invokeLater(() -> textArea.append(message + "\n"));
	}

	class ClientHandler implements Runnable {
		private final Socket clientSocket;
		private final Gson gson;

		public ClientHandler(Socket clientSocket) {
			this.clientSocket = clientSocket;
			this.gson = new Gson();
		}

		private void sendArrayListToClient(ArrayList<FactureVehicule> list, PrintWriter writer) {
			// Convert ArrayList to JSON
			String json = gson.toJson(list);

			// Send JSON to the client
			writer.println(json);
		}

		String request;

		@Override
		public void run() {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

				while (true) {
					try {
						request = reader.readLine();
						if (request == null) {
							break;
						}
					} catch (SocketException e) {
						// Client has closed the connection
						break;
					}
					appendToTextArea("Requête reçue : " + request);

					String response = "Réponse à la requête : " + request;
					System.out.println(response);
					switch (request) {
					case "getFacturesVehicule", "getRecetteFacturesVehicule":
						ArrayList<FactureVehicule> responseList = DTO.getAllFactures();
						sendArrayListToClient(responseList, writer);
						break;
					}

					writer.println(response);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

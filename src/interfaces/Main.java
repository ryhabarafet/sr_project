package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;
	private JTextField addr;
	private JTextField portf;
	private JButton btnNewButton_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
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
	public Main() {
		initialize();
		addr.setEnabled(false);
		portf.setEnabled(false);
		btnNewButton_3.setEnabled(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 210, 319);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("SR");
		JButton btnNewButton = new JButton("TCP");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServeurTCP tcp = new ServeurTCP();
				tcp.setVisible(true);
				btnNewButton.setEnabled(false);
			}
		});
		btnNewButton.setBounds(54, 26, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("UDP");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServeurUDP udp = new ServeurUDP();
				udp.setVisible(true);
				btnNewButton_1.setEnabled(false);
			}
		});
		btnNewButton_1.setBounds(54, 60, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Proxy");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServerSwing proxy = new ServerSwing();
				proxy.setVisible(true);
				btnNewButton_2.setEnabled(false);
				addr.setEnabled(true);
				portf.setEnabled(true);
				btnNewButton_3.setEnabled(true);
			}
		});
		btnNewButton_2.setBounds(54, 94, 89, 23);
		frame.getContentPane().add(btnNewButton_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 140, 174, 2);
		frame.getContentPane().add(separator);
		
		addr = new JTextField();
		addr.setBounds(29, 170, 125, 20);
		frame.getContentPane().add(addr);
		addr.setColumns(10);
		
		portf = new JTextField();
		portf.setBounds(29, 215, 125, 20);
		frame.getContentPane().add(portf);
		portf.setColumns(10);
		
		btnNewButton_3 = new JButton("Connexion");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String addrs = addr.getText();
				int port = Integer.parseInt(portf.getText());
				Clients c = new Clients(addrs,port);
				c.setVisible(true);
				frame.dispose();
			}
		});
		btnNewButton_3.setBounds(42, 246, 100, 23);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("Proxy port");
		lblNewLabel.setBounds(29, 201, 71, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Proxy address");
		lblNewLabel_1.setBounds(29, 153, 89, 14);
		frame.getContentPane().add(lblNewLabel_1);
	}
}

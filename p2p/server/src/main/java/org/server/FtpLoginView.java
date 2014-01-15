package org.server;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FtpLoginView {

	private Server server;

	public static String name;
	public static String password;

	private JFrame frame;
	private JTextField userText;
	private JPasswordField passwordText;

	private JButton loginButton;

	// Require to give the server object so we can login here
	// instead of doing weird magic grabbing back name/password credentials
	public FtpLoginView(Server server) {
		this.server = server;
	}

	public void display() {
		frame = new JFrame("Login to ftp");
		JPanel panel = new JPanel();

		frame.setSize(300, 150);
		frame.add(panel);
		placeComponents(panel);
		frame.setVisible(true);
	}

	private void placeComponents(JPanel panel) {

		panel.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(10, 10, 80, 25);
		panel.add(userLabel);

		userText = new JTextField(20);
		userText.setBounds(100, 10, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 40, 80, 25);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 40, 160, 25);
		panel.add(passwordText);

		loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		panel.add(loginButton);
		loginButton.addActionListener(actionListener);

		// Kan je enter indrukken als je het ingevuld hebt
		passwordText.addKeyListener(keylistener);

	}

	private KeyListener keylistener = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() == KeyEvent.VK_ENTER) {
				loginButton.doClick();
			}
		}

		public void keyPressed(KeyEvent arg0) {
		}

		public void keyReleased(KeyEvent arg0) {
		}
	};

	private ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String NameItem = (((JButton) e.getSource()).getText());
			if (NameItem == "Login") {
				name = userText.getText();
				// getPassword.toString() returns weird strings, have to use
				// depricated getText()
				password = passwordText.getText();
				server.loginToStatisticsServer("j2p2p.ymevandergraaf.nl", name,
						password);

				frame.setVisible(false);
			}
		}
	};
}

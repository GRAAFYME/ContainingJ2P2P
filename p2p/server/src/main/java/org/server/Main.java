package org.server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

	private static final JFileChooser fc = new JFileChooser();
	private static JTextArea textArea = new JTextArea(38, 34);
	private static JFrame frame = new JFrame("Controller");
	private static Server server;
	private static JMenuBar menu;

	private static JLabel SpeedText = new JLabel("100");
	private static int Speed = 100;
	private static String ClientStatus = "Offline";
	private static JLabel clientstatus;
	private static JRadioButton enableHeartbeat;

	private static JPanel contentPane = new JPanel();
	private static JPanel textpanel = new JPanel();
	private static JPanel boxpanel = new JPanel();
	private static JPanel serverpanel = new JPanel();

	public static void main(String args[]) {
		server = new Server();
		// Main panel
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);

		Menu();
		TextPanel();
		InfoPanel();
		ServerPanel();
		frame.setJMenuBar(menu);
		frame.setSize(660, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

	}

	/*
	 * MenuItems
	 */
	private static void Menu() {
		menu = new JMenuBar();
		// File Menu
		JMenu fileMenu = new JMenu("File");
		menu.add(fileMenu);

		// Load File
		JMenuItem LoadFile = new JMenuItem("Load File");
		fileMenu.add(LoadFile);
		LoadFile.addActionListener(actionListener);

		// Ftp login
		JMenuItem ftpLogin = new JMenuItem("Login to ftp");
		fileMenu.add(ftpLogin);
		ftpLogin.addActionListener(actionListener);

		// Start
		JMenuItem Start = new JMenuItem("Start server");
		fileMenu.add(Start);
		Start.addActionListener(actionListener);
		// Restart
		JMenuItem Restart = new JMenuItem("Restart server");
		fileMenu.add(Restart);
		Restart.addActionListener(actionListener);
		// Stop
		JMenuItem Stop = new JMenuItem("Stop server");
		fileMenu.add(Stop);
		Stop.addActionListener(actionListener);

		// Quit
		JMenuItem Quit = new JMenuItem("Quit");
		fileMenu.add(Quit);
		Quit.addActionListener(actionListener);

		// Help Menu
		JMenu helpMenu = new JMenu("Help");
		menu.add(helpMenu);
		// About
		JMenuItem About = new JMenuItem("About");
		helpMenu.add(About);
		About.addActionListener(actionListener);
		// Help
		JMenuItem Help = new JMenuItem("Help");
		helpMenu.add(Help);
		Help.addActionListener(actionListener);
	}

	/*
	 * Information of the Server
	 */
	private static void ServerPanel() {
		serverpanel.setLayout(null);
		serverpanel.setBounds(400, 350, 240, 280);
		serverpanel.setBorder(BorderFactory.createTitledBorder("Server"));
		// serverpanel.setBackground(Color.WHITE);'
		JLabel serverLabel = new JLabel("Client: ");
		clientstatus = new JLabel(ClientStatus);
		serverLabel.setBounds(10, 20, 40, 20);
		clientstatus.setBounds(50, 20, 60, 20);
		serverpanel.add(serverLabel);
		// serverpanel.add(clientstatus);
		contentPane.add(serverpanel);

		// Heartbeat Button!!
		enableHeartbeat = new JRadioButton();
		JLabel Heartbeat = new JLabel("Heartbeat");
		Heartbeat.setBounds(30, 45, 70, 30);
		enableHeartbeat.setBounds(10, 45, 20, 30);
		enableHeartbeat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JRadioButton radioButton = (JRadioButton) e.getSource();
				server.enableHeartbeat = radioButton.isSelected();
				System.out.println("Server heartbeat has been toggled to"
						+ ((radioButton.isSelected()) ? " On" : " Off"));

			}
		});
		serverpanel.add(Heartbeat);
		serverpanel.add(enableHeartbeat);

	}

	/*
	 * TextPanel with the TextArea
	 */
	private static void TextPanel() {
		// Text Panel
		textpanel.setBounds(0, 0, 400, 700);
		contentPane.add(textpanel);
		textArea.setEditable(false);
		textpanel.add(textArea);
		JScrollPane scroll = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textpanel.add(scroll);
	}

	private static void InfoPanel() {
		boxpanel.setLayout(null);
		// Info Box for the Slider
		final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Speed = slider.getValue();
				// if the Speed is lower then 100 then it's behind the
				// slider(fix for it)
				if (Speed < 100) {
					if (Speed < 10) {
						SpeedText.setText("00" + Integer.toString(Speed));
					} else
						SpeedText.setText("0" + Integer.toString(Speed));
				} else
					SpeedText.setText(Integer.toString(Speed));
			}
		});
		boxpanel.setBounds(400, 0, 240, 350);
		// boxpanel.setBackground(Color.WHITE);
		slider.setBounds(10, 20, 220, 30);
		SpeedText.setBounds(110, 40, 30, 30);
		boxpanel.add(slider);
		boxpanel.add(SpeedText);
		boxpanel.setBorder(BorderFactory.createTitledBorder("Simulatie"));
		contentPane.add(boxpanel);

	}

	private static ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String NameItem = (((JMenuItem) e.getSource()).getText());
			if (NameItem == "Load File") {
				// all files disabled
				fc.setAcceptAllFileFilterUsed(false);

				// only XML files
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter(
						"xml files (*.xml)", "xml");
				fc.setFileFilter(xmlfilter);

				// Set Directory!!
				fc.setCurrentDirectory(new java.io.File("data"));

				// Open XML
				fc.setDialogTitle("Open XML");
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					String FileLocation = file.getPath();
					textArea.setText("");
					// textArea.setText(FileLocation + "\n" + "\n");

					// Parse XML
					xmlParser parser = new xmlParser();
					final ContainerSetXml containers;
					server.setXMLPath(FileLocation);
					
					try {
						long time = System.nanoTime();
						containers = parser.load(FileLocation);

						System.out.println("It took"
								+ (System.nanoTime() - time)
								+ "ns to parse the xml file");
						// new Thread for display next container after some time
						Thread t = new Thread() {
							public void run() {
								for (ContainerXml c : containers.containers) {
									DateFormat dateFormat = new SimpleDateFormat(
											" HH:mm:ss");
									Calendar now = Calendar.getInstance();
									String Time = "["
											+ dateFormat.format(now.getTime())
											+ "]";

									textArea.append(Time + " " + c.id + " "
											+ c.ownerName + "\n");
									textArea.setCaretPosition(textArea
											.getDocument().getLength());
									try {
										sleep(150); // milliseconds
									} catch (InterruptedException ex) {
									}
								}
							}
						};
						t.start(); // call back run()

					} catch (Exception ex) {
						System.out.println(ex);
					}
				}

			} else if (NameItem == "Start server") {
				// server.start() launches server.run() in a new thread
				// Meaning server.start won't freeze the gui anymore
				server.start(6666);
			}

			else if (NameItem == "Login to ftp") {
				FtpLoginView ftpLoginView = new FtpLoginView(server);
				ftpLoginView.display();
				String name = ftpLoginView.name;
				String password = ftpLoginView.name;

				// server.login() is called in ftpLoginView
			}

			else if (NameItem == "About") {
				JOptionPane.showMessageDialog(null,
						"Mede mogelijk gemaakt door Groep 5!", "About",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (NameItem == "Help") {
				// JOptionPane.showMessageDialog(null,"Moet nog ingevuld worden!","Help",JOptionPane.INFORMATION_MESSAGE);
				HelpView helpview = new HelpView();
			} else if (NameItem == "Quit") {
				System.exit(0);
			} else if (NameItem == "Restart server") {
				server.restart(6666);
			} else if (NameItem == "Stop server") {
				server.stop();
			}
		}
	};

}

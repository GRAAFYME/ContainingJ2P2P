package org.server;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main 
{
	
   private static final JFileChooser fc = new JFileChooser();
   private static JTextArea textArea = new JTextArea(38,34);
   private static JFrame frame = new JFrame("Controller");
   private static Server server;
   private static JMenuBar menu;
   
   private static JLabel SpeedText = new JLabel("100");
   private static int Speed = 100;
   
   
   private static JPanel contentPane = new JPanel();
   private static JPanel textpanel = new JPanel();
   private static JPanel boxpanel = new JPanel();
   private static JPanel serverpanel = new JPanel();
   
   public static void main(String args[]) {

	 server = new Server();
  
    //JFrame frame = new JFrame("Controller");
    //frame.getContentPane().setBackground(Color.WHITE);
    //Main panel
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
    
  }
   
   /*
    * MenuItems
    */
   private static void Menu()
   {
	    menu = new JMenuBar();
	    
	    // File Menu
	    JMenu fileMenu = new JMenu("File");
	    menu.add(fileMenu);

	    //Load File
	    JMenuItem LoadFile = new JMenuItem("Load File");
	    fileMenu.add(LoadFile);
	    LoadFile.addActionListener(actionListener);

	    //Ftp login
	    JMenuItem ftpLogin = new JMenuItem("Login to ftp");
	    fileMenu.add(ftpLogin);
	    ftpLogin.addActionListener(actionListener);

	    //Start
	    JMenuItem Start = new JMenuItem("Start");
	    fileMenu.add(Start);
	    Start.addActionListener(actionListener);
	    
	    //Quit
	    JMenuItem Quit = new JMenuItem("Quit");
	    fileMenu.add(Quit);
	    Quit.addActionListener(actionListener);

	    // Help Menu
	    JMenu helpMenu = new JMenu("Help");
	    menu.add(helpMenu);
	    //About
	    JMenuItem About = new JMenuItem("About");
	    helpMenu.add(About);
	    About.addActionListener(actionListener);
	    //Help
	    JMenuItem Help = new JMenuItem("Help");
	    helpMenu.add(Help);
	    Help.addActionListener(actionListener);
   }
   
   
   /*
    * Information of the Server
    */
   private static void ServerPanel()
   {
	   serverpanel.setBounds(400,350,240,280);
	   serverpanel.setBorder(BorderFactory.createTitledBorder("Server"));
	   //serverpanel.setBackground(Color.WHITE);'
	   JLabel server = new JLabel("Server: ");
	   JLabel serverstatus = new JLabel("Online/Offline");
	   serverpanel.add(server);
	   serverpanel.add(serverstatus);
	   contentPane.add(serverpanel);
   }
   
   /*
    * TextPanel with the TextArea
    */
   private static void TextPanel()
   {
	   	//Text Panel
		textpanel.setBounds(0, 0, 400, 700);
		contentPane.add(textpanel);
		textArea.setEditable(false);
		textpanel.add(textArea);
		JScrollPane scroll = new JScrollPane (textArea, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textpanel.add(scroll);
   }
   
   private static void InfoPanel()
   {
	   //Info Box for the Slider
	    final JSlider slider = new JSlider(JSlider.HORIZONTAL,0,200,100);
	    slider.setMajorTickSpacing(10);
	    slider.setMinorTickSpacing(1);
	    slider.addChangeListener(new ChangeListener(){
	    	public void stateChanged(ChangeEvent e)
	    	{
	    		Speed = slider.getValue();
	    		//if the Speed is lower then 100 then it's behind the slider(fix for it)
	    		if (Speed < 100)
	    		{
	    			if (Speed < 10)
	    			{
	    				SpeedText.setText( "00" + Integer.toString(Speed) );
	    			}
	    			else
	    				SpeedText.setText( "0" + Integer.toString(Speed) );
	    		}
	    		else
	    			SpeedText.setText(Integer.toString(Speed));
	    	}
	    });
	    boxpanel.setBounds(400, 0, 240,350);
	    //boxpanel.setBackground(Color.WHITE);
	    boxpanel.add(slider);
	    boxpanel.add(SpeedText);
	    boxpanel.setBorder(BorderFactory.createTitledBorder("Simulatie"));
	    contentPane.add(boxpanel);
	    
   }

   
   private static ActionListener actionListener = new ActionListener() {
	   public void actionPerformed(ActionEvent e) 
	   {
		   String NameItem = (((JMenuItem)e.getSource()).getText());
	     if (NameItem == "Load File")
	     {
	    	 //all files disabled
	    	 fc.setAcceptAllFileFilterUsed(false);
	    	 
	    	 //only XML files
	    	 FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter( "xml files (*.xml)", "xml");
	         fc.setFileFilter(xmlfilter);
	         
	         //Set Directory!!
	         fc.setCurrentDirectory(new java.io.File("data"));
	         
	         //Open XML
	         fc.setDialogTitle("Open XML");
	    	 int returnVal = fc.showOpenDialog(null);
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	             File file = fc.getSelectedFile();
	             String FileLocation = file.getPath();
	             textArea.setText("");
	             //textArea.setText(FileLocation + "\n" + "\n");
	             
             //Parse XML
             xmlParser parser = new xmlParser();
             final ContainerSetXml containers;
             

             try 
             {
                 long time = System.nanoTime();
                 containers = parser.parse(FileLocation);
                 
                 System.out.println("It took" + (System.nanoTime() - time) + "ns to parse the xml file");
                 //new Thread for display next container after some time
                 Thread t = new Thread() {
                     public void run() {  
                        for (ContainerXml c : containers.containers) 
                        {
                        	DateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss");
                        	Calendar now = Calendar.getInstance();
                        	String Time = "[" +  dateFormat.format(now.getTime()) + "]";
                        	
                        	textArea.append( Time + " " + c.id + " " + c.ownerName +  "\n");
                        	textArea.setCaretPosition(textArea.getDocument().getLength());
                           try 
                           {
                              sleep(150);  // milliseconds
                           } catch (InterruptedException ex) {}
                        }
                     }
                  };
                  t.start();  // call back run()

                     
             }
             catch (Exception ex){
                 System.out.println(ex);
             }
	    	 }
	             
	       }
	     else if (NameItem == "Start")
	     {
	         server.start(6666);
             System.out.println("Client connected");

             while(true)
             {
                 try {
                 Thread.sleep(1000);
                 } catch (Exception exception){}
                 server.sendMessage("YOOOOOOOO");
             }
	     }
	     
	     else if (NameItem == "Login to ftp")
	     {
	    	 FtpLoginView ftpLoginView = new FtpLoginView(server);
	    	 ftpLoginView.display();
	    	 String name = ftpLoginView.name;
	    	 String password = ftpLoginView.name;
	    	 
	    	 //server.login() is called in ftpLoginView
	     }
	     
	     else if (NameItem == "About")
	     {
	    	 JOptionPane.showMessageDialog(null,"Mede mogelijk gemaakt door Groep 5!","About",JOptionPane.INFORMATION_MESSAGE);
	     }
	     else if (NameItem == "Help")
	     {
	    	 JOptionPane.showMessageDialog(null,"Moet nog ingevuld worden!","Help",JOptionPane.INFORMATION_MESSAGE);
	     }
	     else if (NameItem == "Quit")
	     {
	    	 System.exit(0);
	     }
	   }
	 };
	 

	 
}

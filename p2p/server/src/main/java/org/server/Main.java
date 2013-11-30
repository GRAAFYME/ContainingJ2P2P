package org.server;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main 
{
	
   private static final JFileChooser fc = new JFileChooser();
   private static JTextArea textArea = new JTextArea();
   private static JFrame frame = new JFrame("Controller");
   private static Server server;
   
   public static void main(String args[]) {

	 server = new Server();

    //JFrame frame = new JFrame("Controller");
    frame.getContentPane().setBackground(Color.WHITE);
    JMenuBar menu = new JMenuBar();
    
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

    //TextArea
    frame.getContentPane().add(textArea, BorderLayout.NORTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textArea.setEditable(false);
    //Scroll!!
    JScrollPane scroll = new JScrollPane (textArea, 
    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    frame.add(scroll);
    
    frame.setJMenuBar(menu);
    frame.setSize(600, 700);
    frame.setVisible(true);
    
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
	             textArea.setText(FileLocation + "\n" + "\n");
	             
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
                        for (ContainerXml c : containers.containers) {
                        	textArea.append(c.id + " Owner Name: " + c.ownerName +  "\n");
                        	textArea.setCaretPosition(textArea.getDocument().getLength());
                           try {
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

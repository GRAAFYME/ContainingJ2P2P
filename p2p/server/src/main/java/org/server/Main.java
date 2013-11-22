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
   
   public static void main(String args[]) {
    JFrame frame = new JFrame("Controller");
    frame.getContentPane().setBackground(Color.WHITE);
    

    JMenuBar menu = new JMenuBar();

    // File Menu
    JMenu fileMenu = new JMenu("File");
    menu.add(fileMenu);

    //Load File
    JMenuItem LoadFile = new JMenuItem("Load File");
    fileMenu.add(LoadFile);
    LoadFile.addActionListener(actionListener);

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
	             textArea.append(FileLocation + "\n" + "\n");
	    	 
	             
             //Parse XML
             xmlParser parser = new xmlParser();
             ContainerSetXml containers;
             try 
             {
                 long time = System.nanoTime();
                 containers = parser.parse(FileLocation);
                 System.out.println("It took" + (System.nanoTime() - time) + "ns to parse the xml file");
                 for (ContainerXml c : containers.containers)
                 {
                	 textArea.append(c.id + " Owner Name: " + c.ownerName +  "\n");
                 }
                     
             }
             catch (Exception ex){
                 System.out.println(ex);
             }
	    	 }
	             
	       }
	     else if (NameItem == "Start")
	     {
	    	 Server server = new Server();
	         server.start(6666);
	         System.out.println("Client connected");
	     }
	     else if (NameItem == "About")
	     {
	    	 JOptionPane.showMessageDialog(null,"Moet nog ingevuld worden!","About",JOptionPane.INFORMATION_MESSAGE);
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

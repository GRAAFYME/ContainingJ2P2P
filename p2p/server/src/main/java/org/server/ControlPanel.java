package org.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Panel;
import java.awt.BorderLayout;

import javax.swing.JTextPane;

import java.awt.Color;

import javax.swing.JTextArea;

public class ControlPanel 
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

    // Move help menu to right side
    //menu.add(Box.createHorizontalGlue());

    // Help Menu
    JMenu helpMenu = new JMenu("Help");
    menu.add(helpMenu);
    
    JMenuItem About = new JMenuItem("About");
    helpMenu.add(About);
    About.addActionListener(actionListener);
    
    JMenuItem Help = new JMenuItem("Help");
    helpMenu.add(Help);
    Help.addActionListener(actionListener);

    //TextArea
    frame.getContentPane().add(textArea, BorderLayout.NORTH);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textArea.setEditable(false);
    
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
	             textArea.append(FileLocation + "\n");
	             XMLLaad(FileLocation);
	         } 
	    	 else 
	    	 {
	    	   //Do Nothing
	    	 }
	             
	       }
	     else if (NameItem == "About")
	     {
	    	 JOptionPane.showMessageDialog(null,"Moet nog ingevuld worden!","About",JOptionPane.INFORMATION_MESSAGE);
	     }
	     else if (NameItem == "Help")
	     {
	    	 JOptionPane.showMessageDialog(null,"Moet nog ingevuld worden!","Help",JOptionPane.INFORMATION_MESSAGE);
	     }
	   }
	 };
	 
	 
	 private static void XMLLaad(String Filelocatie)
	 {
		 	xmlParser parser = new xmlParser();
	        ContainerSet containers;
	        try {
	            long time = System.nanoTime();
	            containers = parser.parse(Filelocatie);
	            System.out.println("It took" + (System.nanoTime() - time) + "ns to parse the xml file");

	        }
	        catch (Exception e){
	            System.out.println("EORR!");
	        }
	 }
	 
}

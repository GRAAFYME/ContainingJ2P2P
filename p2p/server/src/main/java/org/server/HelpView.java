package org.server;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class HelpView 
{
	private JFrame frame;
	private JPanel mainpanel;
	private JTabbedPane tabbedPane;
	
	public HelpView()
	{
		display();
	}
	
	/*
	 * Display method for Frame
	*/
	private void display()
	{
		frame = new JFrame("HelpMenu");
		frame.setSize(600,400);
		MainPanel();
		frame.add(mainpanel);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	/*
	 * MainPanel of the Frame
	 */
	private void MainPanel()
	{
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(1,1));
		
		//JLabel text = new JLabel("Help");
		//text.setBounds(10,10,100,20);
		//mainpanel.add(text);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//tabbedPane.setBounds(10, 10, 550, 300);
		mainpanel.add(tabbedPane);
		//ImageIcon icon = new ImageIcon("icon.png");
		tabbedPane.addTab("Help", makePanel("Hello and welcome to the Help Menu!" + "\n" + "\n" + "If you need some help then this is the perfect place for you!"));
		//System.out.println(getClass().getResource("data/icon.png"));
		tabbedPane.addTab("How to get started!", makePanel("How to get started!" + "\n" + "\n" + "1. Load a XML-File from the File -> Load File option" + "\n" + "2. Dont start the server if the Client is not open! (else it does nothing)" + "\n" + "3. If you want to update the website then Login to ftp!" + "\n" + "4. Start the Server!"));
			
	}
	

	 private JPanel makePanel(String text) 
	 {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		//JLabel Textlabel = new JLabel(text);
		//Textlabel.setBounds(5,0,400,400);
		JTextArea textArea = new JTextArea(text);
		
		textArea.setBounds(0,0,600,400);
		panel.add(textArea);
		//panel.setLayout(new GridLayout(1, 1));
	    return panel;
	 }

}

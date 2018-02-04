package Client;


/*
 * File: GUI
 * Author: Troy Nechanicky, nech5860, 150405860 
 * 	Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: February 4, 2018
 * 
 * Description: GUI for Client
 * 	Follows CP372 A1 Protocol RFC
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/* Relies on the following files to be in default package:
 * 	Client
 */

public class GUI {

	Client client;
	JFrame guiClient = new JFrame("Client");

	public GUI(Client c) {
		client = c;

		guiClient.setSize (660, 500);
		guiClient.setResizable(false);

		// GUI Elements 

		// Drop box elements
		String[] requestOptions = {"GET","SUBMIT","UPDATE","REMOVE"};
		JComboBox<String> dropBox = new JComboBox<String>(requestOptions);

		// Labels 
		JLabel ipLabel = new JLabel("IP:");
		JLabel portLabel  = new JLabel("Port:");
		JLabel dropLable = new JLabel("Requests Options:");
		JLabel cientTextAreaLabel  = new JLabel("Request:"); 
		JLabel serverTextAreaLable  = new JLabel("Output:");

		// buttons 
		JButton Submit  = new JButton("Send");
		Submit.setEnabled(false);
		JButton Disconnect  = new JButton("Disconnect");
		Disconnect.setEnabled(false);
		
		JButton Connect = new JButton("Connect");

		// check box 
		JCheckBox all = new JCheckBox("All");
		JCheckBox bibtex = new JCheckBox("BibTeX"); 

		// text field 
		JTextField ip = new JTextField(17);
		JTextField port = new JTextField(17);

		// text area 
		JTextArea clientTextArea = new JTextArea(10, 50);
		JTextArea serverTextArea = new JTextArea(10, 50);
		serverTextArea.setEditable(false);


		// Panels
		JPanel communcasPane = new JPanel();
		communcasPane.setLayout(new BoxLayout(communcasPane,BoxLayout.Y_AXIS));
		JPanel ipPanle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel newIpPanle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel communcationsPane = new JPanel();
		communcationsPane.setLayout(new BoxLayout(communcationsPane,BoxLayout.Y_AXIS));
		JPanel ClientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel SendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel ServerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// putting everything together 
		ipPanle.add(portLabel);
		ipPanle.add(port);
		ipPanle.add(ipLabel);
		ipPanle.add(ip);
		ipPanle.add(Connect);
		ipPanle.add(Disconnect);
		newIpPanle.add(dropLable);
		newIpPanle.add(dropBox);
		newIpPanle.add(Submit);
		newIpPanle.add(all);
		newIpPanle.add(bibtex);
		guiClient.setVisible(true);
		communcasPane.add(ipPanle);
		communcasPane.add(newIpPanle);

		guiClient.add(communcasPane, BorderLayout.NORTH);

		ClientPanel.add(cientTextAreaLabel);	
		communcationsPane.add(ClientPanel);
		communcationsPane.add(new JScrollPane(clientTextArea));
		communcationsPane.add(SendPanel);
		ServerPanel.add(serverTextAreaLable);
		communcationsPane.add(ServerPanel);
		communcationsPane.add(new JScrollPane(serverTextArea));

		guiClient.add(communcationsPane);
		guiClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiClient.setVisible(true);
			
			// Conncet actions 
		Connect.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				
				if(Connect.isEnabled()){
				
				String port_b = port.getText(); 
				String ip_b = ip.getText();
				
				
						 
					if (port_b.isEmpty() && ip_b.isEmpty()) {
							
						String s = (String)JOptionPane.showInputDialog(
									guiClient,
									"Please enter an IP address",
									"Error",
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									"127.0.0.1");
						ip.setText(s);
						ip_b = ip.getText();
						String r = (String)JOptionPane.showInputDialog(
									guiClient,
									"Please enter a port number",
									"Error",
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									"");
						port.setText(r);
						port_b = port.getText();

						if (!port_b.isEmpty() && !ip_b.isEmpty()) {
							int result = Integer.parseInt(port_b);
								String response = client.Connect(ip_b, result);
								if(client.isConnected){
									serverTextArea.setText(response);
									Disconnect.setEnabled(true);
									Submit.setEnabled(true);	
									Connect.setEnabled(false);
								}
								
							} else{
								JOptionPane.showMessageDialog(null,  "Must establish a Port number and IP address" , null, JOptionPane.ERROR_MESSAGE);
							}
						} 
						
						
					 else if(port_b.isEmpty()){
							String r = (String)JOptionPane.showInputDialog(
									guiClient,
									"Please enter a port number",
									"Error",
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									"");
							port.setText(r);
							port_b = port.getText();
							if (!port_b.isEmpty()){
								int result = Integer.parseInt(port_b);
								
								//toggleButton.setSelected(false);
								String response = client.Connect(ip_b, result);
								if (client.isConnected){
							
									serverTextArea.setText(response);
									Disconnect.setEnabled(true);
									Submit.setEnabled(true);	
									Connect.setEnabled(false);
								}
								
							} else {
								JOptionPane.showMessageDialog(null,  "Must establish a Port number" , null, JOptionPane.ERROR_MESSAGE);
							}
						} else if(ip_b.isEmpty()) {
							String s = (String)JOptionPane.showInputDialog(
									guiClient,
									"Please enter an IP address",
									"Error",
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									"127.0.0.1");
							ip.setText(s);
							ip_b = ip.getText();
							
							if (!ip_b.isEmpty()) {
								
								
								int result = Integer.parseInt(port_b);
								String response = client.Connect(ip_b, result);
								if (client.isConnected){
									Connect.setText("Disconnect");	
									serverTextArea.setText(response);
									Submit.setEnabled(true);
									Disconnect.setEnabled(true);
									Connect.setEnabled(false);
									
								}
								
							} else {
								JOptionPane.showMessageDialog(null,  "Must give an IP address" , null, JOptionPane.ERROR_MESSAGE);
							}

						} else {

							int result = Integer.parseInt(port_b);

					
							String response = client.Connect(ip_b, result);
							if (client.isConnected){
								serverTextArea.setText(response);
								Submit.setEnabled(true);
								Disconnect.setEnabled(true);
								Connect.setEnabled(false);
								
							}
							
						}
						
						
						
					}else{
						JOptionPane.showMessageDialog(null,  "Button is disabled " , null, JOptionPane.ERROR_MESSAGE);
					}
			}

					@Override
					public void mouseExited(MouseEvent e) {	}

					@Override
					public void mouseEntered(MouseEvent e) {}

					@Override
					public void mouseClicked(MouseEvent e) { }
				});
		
		
		
		
		
		
		
		
			Submit.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				
				if(Submit.isEnabled()){
				String response = "";
				String operation = (String) dropBox.getSelectedItem();
				String content = clientTextArea.getText();
				
				content = content.replaceAll("\n\n\n", "\n");
				content = content.replaceAll("\n\n", "\n");
				content = content.replaceAll("\n\n", "\n");

				if (all.isSelected()) {
					content = "ALL";
				}
				if (bibtex.isSelected()) {
					response = client.sendRequestBibtex(operation, content);
				}
				else {
					response = client.sendRequest(operation, content);
				}

				if ( operation.equals("REMOVE") && response.contains("(Y/N)")) {
				
					int n = JOptionPane.showConfirmDialog(
							guiClient,
							"Confirm that you wish to remove records",
									"Confirm",
									JOptionPane.YES_NO_OPTION);
					if (n == 0) {
						response = client.sendRequest("Y"); 
					}
					else {
						response = client.sendRequest("N");
					}
				}
						
				serverTextArea.setText(response);
				}else{
					
					JOptionPane.showMessageDialog(null,  "Button is disabled " , null, JOptionPane.ERROR_MESSAGE);
					
					
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {	}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		
		
		all.addItemListener(new ItemListener() {    
			public void itemStateChanged(ItemEvent e) { 

				if (e.getStateChange()==1){
					clientTextArea.setEditable(false);

				} else {
					clientTextArea.setEditable(true);
				}
			}    
		});   

		ip.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) {
				String s = ip.getText();

				if (s.equals("")) {
					int n = JOptionPane.showConfirmDialog(
							guiClient,
							"Use the IP address 127.0.0.1 to connect to this computer\n "
									+ "Would you like to use 127.0.0.1 as your IP address?",
									"Help",
									JOptionPane.YES_NO_OPTION);
					if (n == 0) {
						ip.setText("127.0.0.1"); 
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {	}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		

		
			Disconnect.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				
				if(Disconnect.isEnabled()){
				
				try { 
					Submit.setEnabled(false);
					Disconnect.setEnabled(false);
					String response = client.closeConnection();
					serverTextArea.setText(response);
					Connect.setEnabled(true);
					
					
				} catch (IOException ee) {
					
				}	
				}else{
					
					JOptionPane.showMessageDialog(null,  "Button is disabled " , null, JOptionPane.ERROR_MESSAGE);
				}
			
			}

			@Override
			public void mouseExited(MouseEvent e) {	}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) { }
		});
		

		ActionListener  x = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) dropBox.getSelectedItem();

				bibtex.setVisible(false);
				bibtex.setSelected(false);
				all.setVisible(false);
				all.setSelected(false);

				if (s.equals("GET")) {
					all.setVisible(true);
					bibtex.setVisible(true);
				} else if(s.equals("REMOVE")) {
					all.setVisible(true);
				}
			}
		};

		dropBox.addActionListener(x);
	}
}

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;
import javax.swing.AbstractButton;
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
import javax.swing.JToggleButton;

public class Client{
	
	 JFrame guiClient = new JFrame("Client");
			// Creating constructor. 
	 public Client(){
			// Setting up the Fixed Frame size to 600,750.
		 	//guiClient.pack();
		 	guiClient.setSize (850, 800);
		 	guiClient.setResizable(true);
			// Setting up the GUI Frame.  
		 	start();	 
			// Displaying the GUI 
		 	guiClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 	guiClient.setVisible(true);
	 }
			// Starting the GUI. 
	 private void start(){	
		 	/*
			-------------------------------------------------------
			Creating the GUI.
			Use:  start();
			-------------------------------------------------------
			Preconditions:
				no input
			Postconditions:
				returns void
			-------------------------------------------------------
			*/
			// Setting up the JPanles to organize the GUI.		
	
		 	ipPanle();
		 	communicationsPanel();
	 }
	 
	 public void ipPanle(){
			/*
			-------------------------------------------------------
			Creating A JPanle that holds the IP and Port number GUI 
			elements to organize the GUI.
			Use:  IP_PORT_Panle();
			-------------------------------------------------------
			Preconditions:
				no input
			Postconditions:
				returns void
			-------------------------------------------------------
			*/	 
		 	
		 	String[] requestOptions = {"REQUEST TYPE","SUBMIT","UPDATE","REMOVE","GET"};
		 	
		 	JLabel msg = new JLabel("", JLabel.CENTER);
		 	
		 	
		 	JComboBox dropBox = new JComboBox(requestOptions);
		 	
			// Setting up the IP and Port number textFields.
			JTextField ip = new JTextField(10);
			JTextField port = new JTextField(10);
			
			  
			
			JButton Submit  = new JButton("Send");
			Submit.setEnabled(false);
			// Setting up Labels. 
			JLabel ipLabel = new JLabel("IP:");
			JLabel portLabel  = new JLabel("Port:");
			JLabel dropLable = new JLabel("Requests Options:");
			JCheckBox checkbox4 = new JCheckBox("ALL");
			JCheckBox checkbox2 = new JCheckBox("BibTrx"); 
			// Setting up buttons. 
			JToggleButton toggleButton = new JToggleButton("Conect");
			
			// Setting up a JPanle for the IP and port Number.
			JPanel ipPanle = new JPanel();
			ipPanle.setLayout(new FlowLayout());
			
			
			 ActionListener actionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			    	 String  a = port.getText();
			    	 String  b = ip.getText();
			    	 
			        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			        boolean selected = abstractButton.getModel().isSelected();
			        if (selected == true){
			        	if (a.isEmpty()){
			        	JOptionPane.showMessageDialog(null,  "Port Number is Required " , a, JOptionPane.INFORMATION_MESSAGE);
			        	}else if (b.isEmpty()){
			        		JOptionPane.showMessageDialog(null,  "IP Number is Required " , a, JOptionPane.INFORMATION_MESSAGE);
			        	}
			        	else{
			        	Submit.setEnabled(true);
			        	toggleButton.setText("Disconnect");
			        	}
			        }else{
			        	Submit.setEnabled(false);
			        	toggleButton.setText("Conect");
			        }
			       
			      }
			    };
			    
			    
			   toggleButton.addActionListener(actionListener);
			
		

			ActionListener  x = new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
			String s = (String) dropBox.getSelectedItem();
			
			switch (s){
			case "GET":
				
				ipPanle.add(checkbox4);
				ipPanle.add(checkbox2);
				guiClient.setVisible(true);

				 break;
			case "UPDATE":
				ipPanle.remove(checkbox4);
				guiClient.setVisible(true);
				ipPanle.remove(checkbox2);
				guiClient.setVisible(true);
				System.out.println("UPDATE");
				break;
			case "SUBMIT":
				ipPanle.remove(checkbox4);
				guiClient.setVisible(true);
				ipPanle.remove(checkbox2);
				guiClient.setVisible(true);
				System.out.println("UPDATE");
				break;
			case "REMOVE":
				ipPanle.remove(checkbox4);
				guiClient.setVisible(true);
				ipPanle.remove(checkbox2);
				guiClient.setVisible(true);
				System.out.println("UPDATE");
				break;
				
			default:
				ipPanle.remove(checkbox4);
				guiClient.setVisible(true);
				ipPanle.remove(checkbox2);
				guiClient.setVisible(true);
				System.out.println("UPDATE");
				break;
			}
				
			}
			};
			// Adding GUI elements to the JPanle.
			dropBox.addActionListener(x);
			ipPanle.add(ipLabel);
			ipPanle.add(ip);
			ipPanle.add(portLabel);
			ipPanle.add(port);
			ipPanle.add(toggleButton);
			ipPanle.add(Submit);
			ipPanle.add(dropLable);
			ipPanle.add(dropBox);
			ipPanle.add(msg);
			
		
			// Adding the JPanle to the SOUTH section of the frame. 
			guiClient.add(ipPanle, BorderLayout.NORTH);
			
		 
	 }

	 private void communicationsPanel(){
			/*
			-------------------------------------------------------
			Creating A JPanle that holds JPanles to organize the GUI.
			Use:  Communications_Panel();
			-------------------------------------------------------
			Preconditions:
				no input
			Postconditions:
				returns void
			-------------------------------------------------------
			*/	 

			// Setting up the Client and Server textAreas. 
			JTextArea clientTextarea = new JTextArea(10, 50);
			JTextArea serverTextarea = new JTextArea(10, 50);
		 	// Disabling the Editing feature for the Server_textArea
			serverTextarea.setEditable(false);
			//JButton Submit  = new JButton("Send - - - >");
			JLabel Client_textArea_Label  = new JLabel("REQUEST:"); 
			JLabel Server_textArea_Lable  = new JLabel("OUTPUT:");
			
		 
			// Setting up JPanles for Client and Server communications.
			JPanel ComuncationsPane = new JPanel();
			ComuncationsPane.setLayout(new BoxLayout(ComuncationsPane,BoxLayout.Y_AXIS));
			
			// Setting up JPanle for Client Requests.	
			JPanel ClientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			ClientPanel.add(Client_textArea_Label);
			// Adding the Client JPanle to the Communications JPanle.	
			ComuncationsPane.add(ClientPanel);
			ComuncationsPane.add(new JScrollPane(clientTextarea));
			
			// Setting up JPanle for data Communications 
			JPanel SendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			//SendPanel.add(Submit);
			// Adding the SendPanel JPanle to the Communications JPanle.
			ComuncationsPane.add(SendPanel);
			// Setting up JPanle for server
			JPanel ServerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			ServerPanel.add(Server_textArea_Lable);
			// Adding the ServerPanel JPanle to the Communications JPanle.
			ComuncationsPane.add(ServerPanel);
			ComuncationsPane.add(new JScrollPane(serverTextarea));

			guiClient.add(ComuncationsPane);
		
		 
	 }
	 public static void main(String args[]){
			Client x = new Client();	
			
			
	 }

}

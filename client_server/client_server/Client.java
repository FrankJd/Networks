import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
		 	guiClient.setSize (1500, 900);
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
	 
	 private void ipPanle(){
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
			// Setting up the IP and Port number textFields.
			JTextField ip = new JTextField(25);
			JTextField port = new JTextField(10);
			
			// Setting up Labels. 
			JLabel ipLabel = new JLabel("IP:");
			JLabel portLabel  = new JLabel("Port:");
			
			// Setting up buttons. 
			JToggleButton connect = new JToggleButton("Connect");
			
			// Setting up a JPanle for the IP and port Number.
			JPanel ipPanle = new JPanel();
			ipPanle.setLayout(new FlowLayout());
			
			// Adding GUI elements to the JPanle.
			ipPanle.add(ipLabel);
			ipPanle.add(ip);
			ipPanle.add(portLabel);
			ipPanle.add(port);
			ipPanle.add(connect);
			
			// Adding the JPanle to the SOUTH section of the frame. 
			guiClient.add(ipPanle, BorderLayout.SOUTH);
			
		 
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
			JButton Submit  = new JButton("Send - - - >");
			JLabel Client_textArea_Label  = new JLabel("REQUEST:"); 
			JLabel Server_textArea_Lable  = new JLabel("OUTPUT:");
			
		 
			// Setting up JPanles for Client and Server communications.
			JPanel ComuncationsPane = new JPanel();
			ComuncationsPane.setLayout(new BoxLayout(ComuncationsPane,BoxLayout.X_AXIS));
			
			// Setting up JPanle for Client Requests.	
			JPanel ClientPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			ClientPanel.add(Client_textArea_Label);
			// Adding the Client JPanle to the Communications JPanle.	
			ComuncationsPane.add(ClientPanel);
			ComuncationsPane.add(new JScrollPane(clientTextarea));
			
			// Setting up JPanle for data Communications 
			JPanel SendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			SendPanel.add(Submit);
			// Adding the SendPanel JPanle to the Communications JPanle.
			ComuncationsPane.add(SendPanel);
			// Setting up JPanle for server
			JPanel ServerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			ServerPanel.add(Server_textArea_Lable);
			// Adding the ServerPanel JPanle to the Communications JPanle.
			ComuncationsPane.add(ServerPanel);
			ComuncationsPane.add(new JScrollPane(serverTextarea));

			guiClient.add(ComuncationsPane);
		
		 
	 }
	 public static void main(String args[]){
			new Client();		
	 }

}

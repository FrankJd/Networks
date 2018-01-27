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
	JFrame GUI_Client = new JFrame("Client");

	// Creating constructor. 
	 public Client(){
	// Setting up the GUI Frame.  
		
		 start();
		 
	// Setting up the Fixed Frame size to 600,750.
		GUI_Client.setSize (1500, 900);
		GUI_Client.setResizable(true);
		// Displaying the GUI 
		GUI_Client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI_Client.setVisible(true);
	 }
	 
 
	 // Starting the GUI. 
	 private void start(){
		
		// Setting up the JPanles to organize the GUI.		
		IP_PORT_Panle();
		Communications_Panel();

	 }
	 
	 private void IP_PORT_Panle(){
			// Setting up the IP and Port number textFields.
			JTextField IP = new JTextField(25);
			JTextField Port = new JTextField(10);
			
			// Setting up Labels. 
			JLabel IP_Label = new JLabel("IP:");
			JLabel Port_Label  = new JLabel("Port:");
			
			// Setting up buttons. 
			JToggleButton connect = new JToggleButton("Connect");
			
			// Setting up a JPanle for the IP and port Number.
			JPanel IP_PORT_Panle = new JPanel();
			IP_PORT_Panle.setLayout(new FlowLayout());
			
			// Adding GUI elements to the JPanle.
			IP_PORT_Panle.add(IP_Label);
			IP_PORT_Panle.add(IP);
			IP_PORT_Panle.add(Port_Label);
			IP_PORT_Panle.add(Port);
			IP_PORT_Panle.add(connect);
			
			// Adding the JPanle to the SOUTH section of the frame. 
			GUI_Client.add(IP_PORT_Panle, BorderLayout.SOUTH);
			
		 
	 }
  
	 private void Communications_Panel(){
		// Setting up the Client and Server textAreas. 
			JTextArea Client_textArea = new JTextArea(10, 50);
			JTextArea Server_textArea = new JTextArea(10, 50);
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
			ComuncationsPane.add(new JScrollPane(Client_textArea));
			
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
			ComuncationsPane.add(new JScrollPane(Server_textArea));

			GUI_Client.add(ComuncationsPane);
		
		 
	 }
	 public static void main(String args[]){
			new Client();		
	 }

}


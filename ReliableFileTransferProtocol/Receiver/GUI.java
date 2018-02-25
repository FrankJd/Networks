
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class GUI extends javax.swing.JFrame {

	

	Receiver client;
	JFrame guiClient = new JFrame("Client");

	public GUI(Receiver c) {
		client = c;

		guiClient.setSize (660, 230);
		guiClient.setResizable(false);

		// GUI Elements 

		
		
		
		
		
		// Labels 

		JLabel senderIPLabel  = new JLabel("SenderIPAddress:");
		JLabel receiverPortLabel  = new JLabel("Receiver Port:        ");
		JLabel outputFile  = new JLabel("Output File:              ");
		JLabel reliableUnreliable   = new JLabel(" Unreliable transport: ");
		
		JLabel senderPort  = new JLabel("Sender Port:");
		
		JLabel Packets  = new JLabel("Accepted number of accessed packets:");
		

		
		// check box 
		JCheckBox reliableUnreliableCheckBox  = new JCheckBox("");

		
		// button 
		
		JButton Transfer = new JButton("Transfer");
		
		
		// text field 
		JTextField senderip = new JTextField(17);
		JTextField receiverPort = new JTextField(17);
		JTextField file = new JTextField(17);
		JTextField SenderPortText = new JTextField(17);
		
		JTextArea packetsText = new JTextArea(1, 5);
		packetsText.setEditable(false);


		// Panels
		JPanel communcasPane = new JPanel();
		communcasPane.setLayout(new BoxLayout(communcasPane,BoxLayout.Y_AXIS));
		

		
		JPanel ipPanle1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle6 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		
		
		
		JPanel communcationsPane = new JPanel();
		communcationsPane.setLayout(new BoxLayout(communcationsPane,BoxLayout.Y_AXIS));
		
		
	
		ipPanle1.add(senderIPLabel);
		ipPanle1.add(senderip);
		ipPanle1.add(senderPort);
		ipPanle1.add(SenderPortText);
		
		
		
		ipPanle2.add(receiverPortLabel);
		ipPanle2.add(receiverPort);
		
		
		
		ipPanle3.add(outputFile);
		ipPanle3.add(file);
		
		
		ipPanle4.add(reliableUnreliable);
		ipPanle4.add(reliableUnreliableCheckBox);
	
		
		
		ipPanle5.add(Transfer);
		
		
		ipPanle6.add(Packets);
		ipPanle6.add(packetsText);
		
		guiClient.setVisible(true);
		communcasPane.add(ipPanle1);
		communcasPane.add(ipPanle2);
		communcasPane.add(ipPanle3);
		communcasPane.add(ipPanle4);
		communcasPane.add(ipPanle5);
		communcasPane.add(ipPanle6);
	

		guiClient.add(communcasPane, BorderLayout.NORTH);
		


		guiClient.add(communcationsPane);
		guiClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiClient.setVisible(true);
			

		
		
		
  

		

		
	}
}

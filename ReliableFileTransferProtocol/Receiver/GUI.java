/*
 * File: GUI
 * Author: Troy Nechanicky, nech5860, 150405860 
 * Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: February 27, 2018
 * 
 * Description: GUI for Receiver
 * 	Follows CP372 A2 Stop and wait Protocol 
 */
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;


public class GUI extends javax.swing.JFrame
{
	
	static final WaitLayerUI layerUI = new WaitLayerUI();
    Receiver receiver;
    JTextArea packetsText = new JTextArea(1, 5);
    public GUI()
    {   	
    	JFrame guiClient = new JFrame("Client");

			
    
      	guiClient.setSize (660, 150);
    	guiClient.setResizable(false);

    	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel senderAddrLabel  = new JLabel("Sender IP Address:");
		JLabel receiverPortLabel  = new JLabel("Receiver Port:          ");
		JLabel outputFile  = new JLabel("Output File:");
		JLabel Unreliable   = new JLabel(" Unreliable transport: ");
		
		JLabel senderPort  = new JLabel("Sender Port:");
		
		JLabel Packets  = new JLabel("Accepted number of accessed packets:");
		

		
		// check box 
		JCheckBox UnreliableCheckBox  = new JCheckBox("");

		
		// button 
		
		JButton Transfer = new JButton("Transfer");

		
		
		// text field 
		JTextField senderAddr = new JTextField(17);
		JTextField receiverPort = new JTextField(17);
		JTextField file = new JTextField(17);
		JTextField SenderPortText = new JTextField(17);
		
		
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
		
		
	
		ipPanle1.add(senderAddrLabel);
		ipPanle1.add(senderAddr);
		ipPanle1.add(senderPort);
		ipPanle1.add(SenderPortText);
		
		
		
		ipPanle2.add(receiverPortLabel);
		ipPanle2.add(receiverPort);
		
		
		
		ipPanle3.add(outputFile);
		ipPanle3.add(file);
		
		
		ipPanle4.add(Unreliable);
		ipPanle4.add(UnreliableCheckBox);
	
		
		
		ipPanle5.add(Transfer);

		
		
		ipPanle6.add(Packets);
		ipPanle6.add(packetsText);
		
		guiClient.setVisible(true);
		panel.add(ipPanle1);
		panel.add(ipPanle2);
		panel.add(ipPanle3);
		panel.add(ipPanle4);
		panel.add(ipPanle5);
		panel.add(ipPanle6);
	
    	
    	
    	

    	
    	
    
        JLayer<JPanel> jlayer = new JLayer<>(panel, layerUI);

   
 
        guiClient.add(jlayer);
        
         
        
       
        
      
        
        
        Transfer.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}

			@Override
			public void mouseExited(MouseEvent e) {	}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				  try {
					java.awt.EventQueue.invokeLater(new Runnable()
					    {
						  
						    String  port_a = receiverPort.getText();
					        String  port_b = SenderPortText.getText();
					        int port_reveiver = Integer.parseInt(port_a);
					        int prot_sender = Integer.parseInt(port_b);
					        Receiver receiver = new Receiver(GUI.this, port_reveiver, prot_sender, senderAddr.getText(), UnreliableCheckBox.isSelected(), file.getText());
					        
					       
					        @Override
					        public void run()
					        {
					         
					        	
					        //	layerUI.start();
					        receiver.transfer();
					        	//layerUI.start();
					        //	layerUI.stop();
					            
					           
					        }
					    });
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        
        
        
        
    	guiClient.setVisible(true);
		
    }

    
    public void displayPacketCount(int number){
    	
    	packetsText.setTabSize(number);
    
    }
   

   
}

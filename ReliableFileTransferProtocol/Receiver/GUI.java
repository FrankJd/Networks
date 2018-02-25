
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class GUI extends javax.swing.JFrame
{
    static final WaitLayerUI layerUI = new WaitLayerUI();
  
    public GUI(Receiver receiver)
    {
    	
    	
    	
    	JFrame guiClient = new JFrame("Client");

			
    	guiClient.setSize (660, 230);
    

    	JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel senderIPLabel  = new JLabel("SenderIPAddress:");
		JLabel receiverPortLabel  = new JLabel("Receiver Port:        ");
		JLabel outputFile  = new JLabel("Output File:");
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
				
				  java.awt.EventQueue.invokeLater(new Runnable()
			        {

			            @Override
			            public void run()
			            {
			              
			            	
			                layerUI.start();
			                
			               
			            }
			        });
				
			}

			@Override
			public void mouseExited(MouseEvent e) {	}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) { }
		});
        
        
        
        
    	guiClient.setVisible(true);
		
    }

   

   
}

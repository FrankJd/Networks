import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class GUI{
	
	Client c = new Client();
	JFrame guiClient = new JFrame("Client");
 
	public GUI(){
		
		
		guiClient.setSize (570, 500);
		guiClient.setResizable(false);
		
		// GUI Elements 
		
		
		// Drop box elements
		
		String[] requestOptions = {"GET","SUBMIT","UPDATE","REMOVE"};
		JComboBox dropBox = new JComboBox(requestOptions);
		
		// Labels 
		JLabel ipLabel = new JLabel("IP:");
		JLabel portLabel  = new JLabel("Port:");
		JLabel dropLable = new JLabel("Requests Options:");
		JLabel cientTextAreaLabel  = new JLabel("Request:"); 
		JLabel serverTextAreaLable  = new JLabel("Output:");
		
		// buttons 
		JButton Submit  = new JButton("Send");
		Submit.setEnabled(false);
		JToggleButton toggleButton = new JToggleButton("Connect");
		
		
		
		// check box 
		JCheckBox all = new JCheckBox("All");
		JCheckBox bitTex = new JCheckBox("BibTeX"); 
		
		
		// text filed 
		
		JTextField ip = new JTextField(17);
		JTextField port = new JTextField(17);
		
		
		// text area 
		
		JTextArea clientTextarea = new JTextArea(10, 50);
		JTextArea serverTextarea = new JTextArea(10, 50);
		serverTextarea.setEditable(false);
		
		
		// Panels
		
		JPanel comuncasPane = new JPanel();
		comuncasPane.setLayout(new BoxLayout(comuncasPane,BoxLayout.Y_AXIS));
		JPanel ipPanle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel newIpPanle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel comuncationsPane = new JPanel();
		comuncationsPane.setLayout(new BoxLayout(comuncationsPane,BoxLayout.Y_AXIS));
		JPanel ClientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel SendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel ServerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		
		// putting everything together 
		
		ipPanle.add(portLabel);
		ipPanle.add(port);
		ipPanle.add(ipLabel);
		ipPanle.add(ip);
		ipPanle.add(toggleButton);
		newIpPanle.add(dropLable);
		newIpPanle.add(dropBox);
		newIpPanle.add(Submit);
		newIpPanle.add(all);
		newIpPanle.add(bitTex);
		guiClient.setVisible(true);
		comuncasPane.add(ipPanle);
		comuncasPane.add(newIpPanle);

		
		
		guiClient.add(comuncasPane, BorderLayout.NORTH);
		
		ClientPanel.add(cientTextAreaLabel);	
		comuncationsPane.add(ClientPanel);
		comuncationsPane.add(new JScrollPane(clientTextarea));
		comuncationsPane.add(SendPanel);
		ServerPanel.add(serverTextAreaLable);
		comuncationsPane.add(ServerPanel);
		comuncationsPane.add(new JScrollPane(serverTextarea));

		
		
		guiClient.add(comuncationsPane);
		guiClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiClient.setVisible(true);
		
		
	
		
		

		// adding actions 
		
		Submit.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				String s = clientTextarea.getText();
				c.Submit(s);

			}

			@Override
			public void mouseExited(MouseEvent e) {



			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {


			}
		});

		

		all.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) { 
           	 
           	 if (e.getStateChange()==1){
           		clientTextarea.setEditable(false);
           		 
           	 }else{
           		clientTextarea.setEditable(true);
           		 
           	 }
               
            }    
         });   


		ip.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				String s = ip.getText();

				if (s.equals("")){
					int n = JOptionPane.showConfirmDialog(
							guiClient,
							"If you wish to establis an IP connection to the same machine or computer, you should use  127.0.0.1 as your IP address.\n "
							+ "Would you like to use 127.0.0.1 as your IP address?",
							"An Inane Question",
							JOptionPane.YES_NO_OPTION);
					if (n == 0){
						ip.setText("127.0.0.1"); 

					}


				}
			}

			@Override
			public void mouseExited(MouseEvent e) {



			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {


			}
		});






		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String port_b = port.getText(); 
				String ip_b = ip.getText();

				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				
				if (selected == true){

				

					if (port_b.isEmpty() && ip_b.isEmpty()){


						String s = (String)JOptionPane.showInputDialog(
								guiClient,
								"please enter a IP number",
								"Customized Dialog",
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"127.0.0.1");
						ip.setText(s);
						ip_b = ip.getText();
						String r = (String)JOptionPane.showInputDialog(
								guiClient,
								"please enter a port number",
								"Customized Dialog",
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"");
						port.setText(r);
						port_b = port.getText();
						
						
					
						if (!port_b.isEmpty() && !ip_b.isEmpty()){
							int result = Integer.parseInt(port_b);
							
							toggleButton.setText("Disconnect");
							c.Connect(ip_b, result);
							Submit.setEnabled(true);
							
							}else{
								JOptionPane.showMessageDialog(null,  "must establish a Port number and IP address" , null, JOptionPane.ERROR_MESSAGE);
							}
						

					}else if(port_b.isEmpty()){
						String r = (String)JOptionPane.showInputDialog(
								guiClient,
								"please enter a port number",
								"Customized Dialog",
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"");
						port.setText(r);
						port_b = port.getText();
						if (!port_b.isEmpty()){
							
							
							int result = Integer.parseInt(port_b);
							toggleButton.setText("Disconnect");
							c.Connect(ip_b, result);
							Submit.setEnabled(true);
							
							}else{
								JOptionPane.showMessageDialog(null,  "must establish a Port number" , null, JOptionPane.ERROR_MESSAGE);
							}


					}else if(ip_b.isEmpty()){
						String s = (String)JOptionPane.showInputDialog(
								guiClient,
								"please enter a IP number",
								"Customized Dialog",
								JOptionPane.PLAIN_MESSAGE,
								null,
								null,
								"127.0.0.1");
						ip.setText(s);
						ip_b = ip.getText();
						if (!ip_b.isEmpty()){
						toggleButton.setText("Disconnect");		
						int result = Integer.parseInt(port_b);
						c.Connect(ip_b, result);
							
						Submit.setEnabled(true);
					
						}else{
							JOptionPane.showMessageDialog(null,  "must establish an IP number" , null, JOptionPane.ERROR_MESSAGE);
						}

					}else{
						
						
						int result = Integer.parseInt(port_b);
						
						
						Submit.setEnabled(true);
						toggleButton.setText("Disconnect");
						
						c.Connect(ip_b, result);
					}
				}else{
					toggleButton.setText("Conect");
				
					Submit.setEnabled(false);
					c.closeConnection();	

				}






			}









		};


		toggleButton.addActionListener(actionListener);



		ActionListener  x = new ActionListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) dropBox.getSelectedItem();
				
				
				
			
					if (s.equals("GET")){
						all.setVisible(true);
						bitTex.setVisible(true);
						
					}else if(s.equals("REMOVE")){
						all.setVisible(true);
						bitTex.hide();
						
					}else if(s.equals("UPDATE")){
						all.hide();
						bitTex.hide();
						
						
					}else if (s.equals("SUBMIT")) {
						
						all.hide();
						bitTex.hide();
						
					}
				
						
						
						
					}
					
					
					

				
				
				

				
		
		
		};
		
		
	
		dropBox.addActionListener(x);
		
		
	


	


	}
	public static void main(String args[]){
		GUI x = new GUI();	


	}

}

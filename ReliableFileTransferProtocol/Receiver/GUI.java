
/*
 * Author: Troy Nechanicky, nech5860, 150405860 
 * Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: March 6, 2018
 */
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

/* Relies on the following files to be in default package:
 * 	Receiver
 */

public class GUI
{

	//Thread receiver;
	JTextArea packetsText = new JTextArea(1, 15);
	JFrame guiClient;
	JButton Transfer;

	public GUI()
	{   	
		guiClient = new JFrame("Client");

		guiClient.setSize (660, 200);
		guiClient.setResizable(false);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel senderAddrLabel  = new JLabel("Sender IP address:");
		JLabel receiverPortLabel  = new JLabel("Receiver port:");
		JLabel outputFile  = new JLabel("Output file:");
		JLabel Unreliable   = new JLabel("Unreliable transport:");
		JLabel senderPort  = new JLabel("Sender port:");
		JLabel Packets  = new JLabel("Number of accepted packets:");

		// check box 
		JCheckBox UnreliableCheckBox  = new JCheckBox("");

		// button 
		Transfer = new JButton("Transfer");

		// text field 
		JTextField senderAddr = new JTextField(17);
		JTextField receiverPort = new JTextField(17);
		JTextField file = new JTextField(17);
		JTextField SenderPortText = new JTextField(17);

		packetsText.setEditable(false);

		JPanel ipPanle1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel ipPanle6 = new JPanel(new FlowLayout(FlowLayout.LEFT));

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
		ipPanle4.setPreferredSize(new Dimension(ipPanle1.getPreferredSize().width, ipPanle1.getPreferredSize().height));
		panel.add(ipPanle2);
		panel.add(ipPanle3);
		panel.add(ipPanle4);
		panel.add(ipPanle5);
		panel.add(ipPanle6);

		guiClient.add(panel);

		Transfer.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) { }

			@Override
			public void mousePressed(MouseEvent e) { }

			@Override
			public void mouseExited(MouseEvent e) {	}

			@Override
			public void mouseEntered(MouseEvent e) { }

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (Transfer.isEnabled()) {

						String  port_a = receiverPort.getText();
						String  port_b = SenderPortText.getText();
						int port_reveiver = Integer.parseInt(port_a);
						int prot_sender = Integer.parseInt(port_b);
						Receiver r = new Receiver(GUI.this, port_reveiver, prot_sender, senderAddr.getText(), !UnreliableCheckBox.isSelected(), file.getText());
						Thread receiver = new Thread(r);

						Transfer.setEnabled(false);
						receiver.start();
					}

				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERROR: Invalid parameters", null, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});

		guiClient.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				System.exit(0);
			}
		});

		guiClient.setLocationRelativeTo(null);
		guiClient.setVisible(true);
	}

	public void displayPacketCount(Integer number) {
		packetsText.setText(number.toString());
	}

	public void shit() {
		Transfer.setEnabled(true);
	}
}

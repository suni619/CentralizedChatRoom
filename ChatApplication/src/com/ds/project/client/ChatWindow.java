/**
 * Team members:
 * Jagadish Shivanna - 1001050680
 * Jyoti Salitra - 1001055011
 * Prerana Patil - 1001054381
 * Sunil Koundinya – 1000986932
 * 
 */
package com.ds.project.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.ds.project.dbutil.ConnectDB;

/**
 * Class for pop up chat window to send and receive chat messages
 */
public class ChatWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	JTextArea textArea = new JTextArea(5, 30);

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public ChatWindow(final String sender, final String chatRoomTitle,
			final DataOutputStream outStream) {
		setTitle(chatRoomTitle);
		setSize(400, 350);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowListener() {
			
			public void windowOpened(WindowEvent e) {
			}
			
			public void windowIconified(WindowEvent e) {
			}
			
			public void windowDeiconified(WindowEvent e) {
			}
			
			public void windowDeactivated(WindowEvent e) {
			}
			
			public void windowClosing(WindowEvent e) {
			}
			
			public void windowClosed(WindowEvent e) {
				try {
					outStream.writeUTF(sender + "()_delim_()"
							+ chatRoomTitle + "()_delim_()QUIT()_delim_()"
							+ sender + " left.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
			public void windowActivated(WindowEvent e) {
			}
		});
		setResizable(false);
		setVisible(true);
		
		JButton btnSave = new JButton("Save");
		btnSave.setVisible(true);
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// save chat in database 
			 boolean chatHistory = ConnectDB.saveChat(chatRoomTitle, sender, textArea.getText(), new Date().toString());
				if(chatHistory) {
					JOptionPane.showMessageDialog(ChatWindow.this, "Saved", " Chat Saved ", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(ChatWindow.this, "History not saved", " Chat Save Error ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton btnRetrieve = new JButton("Retrieve");
		btnRetrieve.setVisible(true);
		add(btnRetrieve);
		btnRetrieve.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new RetrieveHistory(sender).setVisible(true);
				}
		});
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(335, 250));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textArea.setBounds(20, 20, 350, 250);

		final JTextField userInputField = new JTextField(30);
		
		userInputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String messageFromUser = userInputField.getText();

				if (messageFromUser != null) {
					textArea.append("\nMe: " + messageFromUser);
					textArea.setCaretPosition(textArea.getDocument()
							.getLength());
					try {
						// send the message to the chat room
						outStream.writeUTF(sender + "()_delim_()"
								+ chatRoomTitle + "()_delim_()DATA()_delim_()"
								+ messageFromUser);
					} catch (IOException e) {
						e.printStackTrace();
					}
					userInputField.setText("");
				}
			}
		});

		setLayout(new FlowLayout());
		add(userInputField, SwingConstants.CENTER);
		add(scrollPane, SwingConstants.CENTER);
	}

	
	  public static void main(String[] args) { new ChatWindow("sender", "chatRoomTitle",
				null); }
	 
}

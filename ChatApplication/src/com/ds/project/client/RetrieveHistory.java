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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.ds.project.dbutil.ConnectDB;

/**
 * Retrieve the saved chat history
 */
public class RetrieveHistory extends JFrame {
	private static final long serialVersionUID = 1L;
	JTextArea textArea;
	

	public RetrieveHistory(final String userName) {
		setTitle("Chat History for " + userName);
		setSize(400, 400);
		setVisible(true);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ArrayList<String> sessions = getSessions(userName);
		for (int i = 0; i < sessions.size(); i++) {
			JButton btn = new JButton(sessions.get(i));
			btn.setBounds(20, 10 + i * 40, getWidth()-50, 30);
			add(btn);

			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame retrieve1 = new JFrame("Chat History Retrieve for " + userName);
					retrieve1.setSize(400, 400);
					retrieve1.setVisible(true);
					String history = getHistory(userName, e.getActionCommand());
					
					JTextArea historyArea = new JTextArea();
					JScrollPane scrollPane = new JScrollPane(historyArea);
					scrollPane.setPreferredSize(new Dimension(335, 250));
					historyArea.setLineWrap(true);
					historyArea.setWrapStyleWord(true);
					historyArea.setEditable(false);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
					historyArea.setBounds(20, 20, 350, 250);
					historyArea.setText(history);
					retrieve1.add(scrollPane);
				}

				
			});
		}
	}

	private String getHistory(String userName, String timeStamp) {
		return ConnectDB.retrieveChatHistory(userName, timeStamp);
	}
	
	private ArrayList<String> getSessions(String userName) {
		return ConnectDB.retrieveChat(userName);
	}

/*	public static void main(String[] args) {
		new RetrieveHistory("user1");
	}*/
}
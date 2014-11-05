/**
 * Team members:
 * Jagadish Shivanna - 1001050680
 * Jyoti Salitra - 1001055011
 * Prerana Patil - 1001054381
 * Sunil Koundinya – 1000986932
 * 
 */
package com.ds.project.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.ds.project.dbutil.ConnectDB;

/**
 * Class for the successful user login
 * Displays all the available chat rooms
 */
public class ChatRooms extends JFrame {
	private static final long serialVersionUID = 1L;

	JLabel lblCreateNewChat;
	JButton btnCreateChatRoom;
	JTextField tfCreateNewChat;
	static ChatWindow cw = null;
	static Map<String, ChatWindow> chatWindowMap = new HashMap<String, ChatWindow>();
	ArrayList<String> chatRoomList = new ArrayList<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new ChatRooms("user1()_delim_()pass");
	}

	/**
	 * Create the frame.
	 */
	public ChatRooms(final String userCredentials) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final String userName = userCredentials.split("\\(\\)_delim_\\(\\)")[0];
		setTitle(userName);
		setVisible(true);
		setLayout(null);
		getContentPane().setBackground(new Color(208, 246, 238));
		setSize(400, 700);
		Socket socket = null;
		String serverIp = "localhost";
		int serverPort = 13000;

		JLabel lblWelcomeMsg = new JLabel(
				"<html>Click on any chat room to join the chat room <br>OR"
						+ "&nbsp;Create a new chat room</html>");
		lblWelcomeMsg.setBounds(5, 8, getWidth() - 20, 45);
		lblWelcomeMsg.setForeground(Color.GREEN);
		lblWelcomeMsg.setFont(new Font("Comic Sans",Font.ITALIC, 15));
		add(lblWelcomeMsg);

		try {
			final DataOutputStream outStream;
			final DataInputStream inStream;

			InetAddress serverAddress = InetAddress.getByName(serverIp);
			socket = new Socket(serverAddress, serverPort);
			System.out.println("Connected to server " + serverIp + " on "
					+ serverPort);

			inStream = new DataInputStream(socket.getInputStream());
			outStream = new DataOutputStream(socket.getOutputStream());

			String msgIn = "";

			// send user credentials on login for authentication
			outStream.writeUTF(userCredentials);
			// get authentication result
			msgIn = inStream.readUTF();
			System.out.println("Auth result: " + msgIn);

			// based on authentication result
			if (msgIn.equals("Authentication failed")) {
				JOptionPane.showMessageDialog(this, "Login failed",
						"Wrong Login/Password", JOptionPane.ERROR_MESSAGE);
				new Login().setVisible(true);
			} else {
				String chatRooms = msgIn.replace("Authentication successful",
						"");
				String[] chatRoomList = chatRooms.split("\\(\\)_delim_\\(\\)");

				Thread receiverThread = new Thread(new Runnable() {

					public void run() {
						while (true) {
							try {
								String msgIn = inStream.readUTF();
								System.out.println("Message: " + msgIn);
								String[] inputMsg = msgIn
										.split("\\(\\)_delim_\\(\\)");
								System.out.println("input msg 1: "
										+ inputMsg[1]);
								System.out.println("map get: "
										+ chatWindowMap.get(inputMsg[1]));
								String msgType = inputMsg[2];
								if (msgType.equals("DATA")) {
									chatWindowMap
											.get(inputMsg[1])
											.getTextArea()
											.setText(
													chatWindowMap
															.get(inputMsg[1])
															.getTextArea()
															.getText()
															+ "\n"
															+ inputMsg[0]
															+ ": "
															+ inputMsg[3]);
								} else if (msgType.equals("FETCH")) {
									chatWindowMap
											.get(inputMsg[1])
											.getTextArea()
											.setText(
													"Users already in logged in: "
															+ inputMsg[3]);
								} else if (msgType.equals("JOIN")) {
									chatWindowMap
											.get(inputMsg[1])
											.getTextArea()
											.setText(
													chatWindowMap
															.get(inputMsg[1])
															.getTextArea()
															.getText()
															+ "\n"
															+ inputMsg[3]);
								} else if (msgType.equals("QUIT")) {
									chatWindowMap
											.get(inputMsg[1])
											.getTextArea()
											.setText(
													chatWindowMap
															.get(inputMsg[1])
															.getTextArea()
															.getText()
															+ "\n"
															+ inputMsg[3]);
								} else {
									chatWindowMap
											.get(inputMsg[1])
											.getTextArea()
											.setText(
													chatWindowMap
															.get(inputMsg[1])
															.getTextArea()
															.getText()
															+ "\n"
															+ inputMsg[0]
															+ ": "
															+ inputMsg[3]);
								}

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
				receiverThread.start();

				for (int i = 0; i < chatRoomList.length; i++) {
					JButton btn = new JButton(chatRoomList[i]);
					btn.setBounds(10, 50 + i * 35, getWidth() - 40, 25);
					add(btn);
					btn.addActionListener(new ActionListener() {

						/**
						 * handler method for button click
						 */
						public void actionPerformed(ActionEvent e) {

							String receiver = e.getActionCommand();
							cw = new ChatWindow(userName, receiver, outStream);
							chatWindowMap.put(e.getActionCommand(), cw);
							try {
								// send join message to the server to broadcast
								// to
								// the chat room
								outStream.writeUTF(userName + "()_delim_()"
										+ receiver
										+ "()_delim_()JOIN()_delim_()"
										+ userName + " joined");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							try {
								// ask existing users to the server
								outStream.writeUTF(userName + "()_delim_()"
										+ receiver
										+ "()_delim_()FETCH()_delim_()"
										+ "Fetch already existing users");
							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}
					});

				}

				btnCreateChatRoom = new JButton("Create New Chat");
				lblCreateNewChat = new JLabel("Enter Chat Room Name:");
				tfCreateNewChat = new JTextField();
				lblCreateNewChat.setBounds(10, getHeight() - 180,
						getWidth() - 100, 30);
				btnCreateChatRoom.setBounds(10, getHeight() - 100,
						getWidth() - 40, 30);
				tfCreateNewChat.setBounds(10, getHeight() - 140,
						getWidth() - 40, 30);
				add(btnCreateChatRoom);
				add(lblCreateNewChat);
				add(tfCreateNewChat);
				btnCreateChatRoom.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent eventClick) {
						boolean chatRoomName = ConnectDB
								.createChat(tfCreateNewChat.getText());
						if (chatRoomName) {
							new ChatRooms(userCredentials);
							dispose();
						} else {
							JOptionPane.showMessageDialog(ChatRooms.this,
									"Create Failed", "ChatRoom already Exists",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				});
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ConnectException e) {
			// Server unavailable or error in connection
			lblWelcomeMsg.setText("Server unavailable or error in connection");
			lblWelcomeMsg.setForeground(Color.RED);
		} catch (NoRouteToHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

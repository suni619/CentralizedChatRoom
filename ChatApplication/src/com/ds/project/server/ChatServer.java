/**
 * Team members:
 * Jagadish Shivanna - 1001050680
 * Jyoti Salitra - 1001055011
 * Prerana Patil - 1001054381
 * Sunil Koundinya – 1000986932
 * 
 */
package com.ds.project.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket clientSocket = null;

		System.out.println("Server started.");

		try {
			// server starts at port 13000
			serverSocket = new ServerSocket(13000);
			while (true) {
				System.out.println("Waiting for connection...");
				clientSocket = serverSocket.accept();
				System.out.println("Connected from "
						+ clientSocket.getInetAddress());

				new ChatServerThread(clientSocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

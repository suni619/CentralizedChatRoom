/**
 * Team members:
 * Jagadish Shivanna - 1001050680
 * Jyoti Salitra - 1001055011
 * Prerana Patil - 1001054381
 * Sunil Koundinya – 1000986932
 * 
 */
package com.ds.project.client;

import javax.swing.*;

import com.ds.project.dbutil.ConnectDB;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Register a new user 
 *
 */
@SuppressWarnings("serial")
public class Register extends JFrame implements ActionListener {

	JLabel lblHeader, lblUserName, lblPassword, lblPasswordReenter;
	JTextField tf1;
	JButton btn1, btn2, btn3;
	JPasswordField p1, p2;
	Connection con = null;

	public Register() {
		setTitle("REGISTER for CHAT UTA");
		setVisible(true);
		setSize(600, 400);
		setLayout(null);
		getContentPane().setBackground(Color.lightGray);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		lblHeader = new JLabel("Register here for UTA CHAT APPLICATION");
		lblHeader.setForeground(Color.blue);
		lblHeader.setFont(new Font("Serif", Font.BOLD, 20));

		lblUserName = new JLabel("Enter Login name");
		lblPassword = new JLabel("Enter Password");
		lblPasswordReenter = new JLabel("Re-enter Password");

		tf1 = new JTextField();
		p1 = new JPasswordField();
		p2 = new JPasswordField();
		btn1 = new JButton("Register");
		btn2 = new JButton("Cancel");
		// btn3 = new JButton("Logout");

		lblHeader.setBounds(100, 20, 450, 30);
		lblUserName.setBounds(100, 70, 200, 40);
		lblPassword.setBounds(100, 110, 200, 40);
		lblPasswordReenter.setBounds(100, 150, 200, 40);
		tf1.setBounds(300, 70, 180, 30);
		p1.setBounds(300, 110, 180, 30);
		p2.setBounds(300, 150, 180, 30);
		btn1.setBounds(150, 210, 100, 30);
		btn2.setBounds(300, 210, 100, 30);
		add(lblHeader);
		add(lblUserName);
		add(tf1);
		add(lblPassword);
		add(lblPasswordReenter);
		add(p1);
		add(btn1);
		add(btn2);
		add(p2);
		btn1.addActionListener(this);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				p1.setText("");
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		showRegData();
		dispose();
		//new Login().setVisible(true);
	}

	public void showRegData() {
		String userName = tf1.getText();
		char[] pw1 = p1.getPassword();
		String password = new String(pw1);
		char[] pw2 = p2.getPassword();
		String str3 = new String(pw2);
		System.out.println(userName + password + str3);
		if (!password.equals(str3)) {
			JOptionPane.showMessageDialog(this,
					"Passwords dont match!! Enter again",
					"Enter Password Again", JOptionPane.ERROR_MESSAGE);
			new Register();
		} else {
			boolean loginSuccess = ConnectDB.signUp(userName, password);
			if (loginSuccess) {
				new Login().setVisible(true);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this,
						"User already exists!! Login", "User Exists Already",
						JOptionPane.ERROR_MESSAGE);
				new Login().setVisible(true);
			}
		}
	}

	/*
	 * public static void main(String args[]) { new Register(); }
	 */

}

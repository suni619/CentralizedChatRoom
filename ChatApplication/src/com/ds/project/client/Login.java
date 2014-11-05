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
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Login UI functionality
 */
public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel l1, l2, l3;
	JTextField tf1;
	JButton btn1, btn2, btn3;
	JPasswordField p1;
	Connection con = null;

	public Login() {
		setTitle("LOGIN for CHAT UTA");
		setVisible(true);
		setSize(600, 400);
		setLayout(null);
		getContentPane().setBackground(new Color(160, 162, 162));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		l1 = new JLabel("Login here for UTA CHAT APPLICATION");
		l1.setForeground(Color.yellow);
		l1.setFont(new Font("Arial", Font.BOLD, 20));

		l2 = new JLabel("Login");
		l3 = new JLabel("Password");
		
		l2.setForeground(Color.white);
		l3.setForeground(Color.white);
		tf1 = new JTextField();
		p1 = new JPasswordField();
		btn1 = new JButton("Login");
		btn2 = new JButton("Cancel");
		btn3 = new JButton("Logout");

		l1.setBounds(100, 30, 400, 30);
		l2.setBounds(130, 90, 200, 30);
		l3.setBounds(130, 150, 200, 30);
		tf1.setBounds(230, 90, 200, 30);
		p1.setBounds(230, 150, 200, 30);
		btn1.setBounds(150, 270, 100, 30);
		btn2.setBounds(300, 270, 100, 30);

		add(l1);
		add(l2);
		add(tf1);
		add(l3);
		add(p1);
		add(btn1);
		add(btn2);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ChatRooms(tf1.getText() + "()_delim_()"
						+ new String(p1.getPassword()));
				dispose();
			}
		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tf1.setText("");
				p1.setText("");
			}
		});
	}


	public static void main(String args[]) {
		new Login();
	}
}

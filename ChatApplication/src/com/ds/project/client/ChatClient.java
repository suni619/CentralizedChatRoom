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

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ChatClient extends JFrame implements ActionListener {
	/**
	 * Entry point of the application
	 */
	private static final long serialVersionUID = 1L;
	JLabel l1;
	JButton btn1, btn2;
	JComponent ic;
	ChatClient() {
		setTitle("CHAT UTA Application");
        setVisible(true);
        setSize(500, 300);
        setLocation(200, 200);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 153));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		l1 = new JLabel("Welcome to UTA CHAT APPLICATION");
		btn1 = new JButton("Register");
        btn2 = new JButton("Login");
        l1.setBounds(80, 80, 400, 30);
        btn1.setBounds(125, 190, 100, 30);
        btn2.setBounds(250, 190, 100, 30);
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        add(l1);add(btn1);add(btn2);
        btn1.addActionListener(this);
        btn2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               new Login().setVisible(true);
            }
        });
	}
	
	public void actionPerformed(ActionEvent e) {
	        new Register().setVisible(true);
	}
	
	public static void main(String[] args) {
		new ChatClient();
	}
}

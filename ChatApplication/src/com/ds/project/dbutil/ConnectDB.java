/**
 * Team members:
 * Jagadish Shivanna - 1001050680
 * Jyoti Salitra - 1001055011
 * Prerana Patil - 1001054381
 * Sunil Koundinya – 1000986932
 * 
 */
package com.ds.project.dbutil;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class ConnectDB {
	static Connection con;
	static String userName = "";
	static String passWord = "";
	public static Connection db() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/chatrooms", userName, passWord);
			System.out.println("Connected...." + con);
			return con;
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean signUp(String login, String password) {
		try {
			con = db();
			PreparedStatement ps = con.prepareStatement("insert into chat_users (login, password) values (?,?)");
			ps.setString(1, login);
			ps.setString(2, password);
			PreparedStatement ps1 = con.prepareStatement("select login from chat_users where login=?");
			ps1.setString(1, login);
			ResultSet rs = ps1.executeQuery();
			if(!rs.next()) {
				//System.out.println(rs.getString(1));
				ps.executeUpdate();
				System.out.println("User added");
				return true;
			}
			else {
				System.out.println(rs.getString(1));
				//System.out.println("User already exists");
				return false;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}


	public boolean authenticate(String login, String password) {
		try {
			con = db();
			PreparedStatement ps = con.prepareStatement("select login from chat_users where login=? and password=?");
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				System.out.println("Login" + rs.getString(1));
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean createChat(String chatRoomName) {
		try {
			con = db();
			PreparedStatement ps = con.prepareStatement("insert into chat_rooms (chat_roomname) values (?)");
			ps.setString(1, chatRoomName);
			PreparedStatement ps1 = con.prepareStatement("select chat_roomname from chat_rooms where chat_roomname=?");
			ps1.setString(1, chatRoomName);
			ResultSet rs = ps1.executeQuery();
			if(!rs.next()) {
				ps.executeUpdate();
				//System.out.println( chatRoomName + "added");
				return true;
			}
			else {
				System.out.println(rs.getString(1) + " already exists");
				return false;
			}
		}
		
		catch(Exception e) {
			
		}
		return false;
	}
	
	public String getChatRoomList() {
		String chatRoomsList = ""; 
		try {
			con = db();
			PreparedStatement ps = con.prepareStatement("select chat_roomname from chat_rooms");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String chatRoom = rs.getString(1);
				chatRoomsList += chatRoom + "()_delim_()";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatRoomsList = chatRoomsList.substring(0,chatRoomsList.length()-11);
		return chatRoomsList;
	}
	
	public static boolean saveChat(String chatRoomName, String userName, String chatHistory, String chatTimeStamp) {
		//String chatRoomsList = ""; 
		try {
			con = db();
			PreparedStatement ps = con.prepareStatement("insert into chat_history (chat_roomname, username, chat_history, chat_timestamp) values (?,?,?,?)");
			ps.setString(1, chatRoomName);
			ps.setString(2, userName);
			ps.setString(3, chatHistory);
			ps.setString(4, chatTimeStamp);
			int added = ps.executeUpdate();
			if(added == 1) {
				System.out.println("Save Successs");
				return true;
			}
			/*PreparedStatement ps1 = con.prepareStatement("select chat_roomname from chat_rooms where chat_roomname=?");
			ps1.setString(1, chatRoomName);
			ResultSet rs = ps1.executeQuery();
			if(!rs.next()) {
				//System.out.println(rs.getString(1));
				ps.executeUpdate();
				System.out.println("Save Succcessfully");
				return true;
			} */
			else {
				//System.out.println(rs.getString(1));
				//System.out.println("User already exists");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//chatRoomsList = chatRoomsList.substring(0,chatRoomsList.length()-11);
		return false;
	}
	
	public static ArrayList<String> retrieveChat(String userName) {
	ArrayList<String> historyRetrieve = new ArrayList<String>();
		try {
			con = db();
			PreparedStatement ps1 = con.prepareStatement("select chat_timestamp from chat_history where username=?");
			ps1.setString(1, userName);
			ResultSet rs = ps1.executeQuery();
			while(rs.next()) {
				historyRetrieve.add(rs.getString(1));	
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return historyRetrieve;
	} 
		
	public static String retrieveChatHistory(String userName, String timeStamp) {
		String history = "";
			try {
				con = db();
				PreparedStatement ps1 = con.prepareStatement("select chat_history from chat_history where username=? and chat_timestamp=?");
				ps1.setString(1, userName);
				ps1.setString(2, timeStamp);
				ResultSet rs = ps1.executeQuery();
				if(rs.next()) {
					history = rs.getString(1);	
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return history;
		} 

	public static void main(String[] args) {
		new ConnectDB();
		ConnectDB.saveChat("chatRoom1", "user3", "hhhhh", new Date().toString());
	}
}

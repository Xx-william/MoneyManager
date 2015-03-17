import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class RunService implements Runnable {
	private Socket socket = null;
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Money> moneys = new ArrayList<Money>();
	private ArrayList<LogInfo> logInfos = new ArrayList<LogInfo>();
	private ArrayList<User> users = new ArrayList<User>();

	private String strtemp = "";
	private String str = "";
	private BufferedReader in;
	private PrintWriter out;

	public RunService(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			try {
				strtemp = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			out.flush();
			if (strtemp.contains("end"))
				break;
		}
		
		if (strtemp.charAt(0) == '1') { 
			int p = strtemp.indexOf("#");
			str = strtemp.substring(2, p);

			if (strtemp.charAt(1) == '1') {     //11 search Item 

				DBSearch search = new DBSearch(str);
				items = search.SearchItem();

				try {
					JsonItems js = new JsonItems();
					js.setItems(items);
					String itemsStr = js.toJsonStrItem();
					JSONArray ja = new JSONArray(itemsStr);
					String jasonString = ja.toString();
					byte[] jb = jasonString.getBytes();
					OutputStream outputstream = new DataOutputStream(
							socket.getOutputStream());
					outputstream.write(jb);
					outputstream.flush();
					socket.shutdownOutput();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (strtemp.charAt(1) == '2') {   //12 search Money
				DBSearch search = new DBSearch(str);
				moneys = search.SearchMoney();
				try {
					JsonItems js = new JsonItems();
					js.setMoneys(moneys);
					String itemsStr = js.toJsonStrMoney();
					JSONArray ja = new JSONArray(itemsStr);
					String jasonString = ja.toString();
					byte[] jb = jasonString.getBytes();
					OutputStream outputstream = new DataOutputStream(
							socket.getOutputStream());
					outputstream.write(jb);
					outputstream.flush();
					socket.shutdownOutput();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (strtemp.charAt(1) == '3') {   //13 search LogInfo
				DBSearch search = new DBSearch(str);
				logInfos = search.SearchLogInfo();
				try {
					JsonItems js = new JsonItems();
					js.setLogInfo(logInfos);
					String logInfosStr = js.toJsonStrLogInfo();
					JSONArray ja = new JSONArray(logInfosStr);
					String jasonString = ja.toString();
					byte[] jb = jasonString.getBytes();
					OutputStream outputstream = new DataOutputStream(
							socket.getOutputStream());
					outputstream.write(jb);
					outputstream.flush();
					socket.shutdownOutput();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (strtemp.charAt(1) == '4') {  //14 登陆判断
				DBSearch search = new DBSearch(str);
				Object[] o = new Object[2];
				o = search.Login();
				if((boolean) o[0]){
					String user = "[{'denglu':'true','id':"+ (int)o[1] +"}]";  //json 格式
					byte[] jb1 = user.getBytes();
					OutputStream outputStream;
					try {
						outputStream = new DataOutputStream(socket.getOutputStream());
						outputStream.write(jb1);
						outputStream.flush();
						socket.shutdownOutput();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				else{
					String user = "[{'denglu':'false'}]";  //json 格式
					byte[] jb1 = user.getBytes();
					OutputStream outputStream;
					try {
						outputStream = new DataOutputStream(socket.getOutputStream());
						outputStream.write(jb1);
						outputStream.flush();
						socket.shutdownOutput();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if (strtemp.charAt(1) == '5'){  //15 Search users
				DBSearch search = new DBSearch(str);	
				users = search.SearchUsers();
				try {					
					JsonItems js = new JsonItems();
					js.setUsers(users);
                    String usersStr = js.toJsonStrUsers();                     
					JSONArray ja = new JSONArray(usersStr);
					String jasonString = ja.toString();
					byte[] jb = jasonString.getBytes();
					OutputStream outputstream = new DataOutputStream(
							socket.getOutputStream());
					outputstream.write(jb);
					outputstream.flush();
					socket.shutdownOutput();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if (strtemp.charAt(1) == '6'){   // if userExist
				DBSearch search = new DBSearch(str);	
				Boolean b = search.ifUserExist();
				if(b){  //用户存在，JSON 返回false
					String user = "[{'yonghu':'false'}]";  //json 格式
					byte[] jb1 = user.getBytes();
					OutputStream outputStream;
					try {
						outputStream = new DataOutputStream(socket.getOutputStream());
						outputStream.write(jb1);
						outputStream.flush();
						socket.shutdownOutput();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else{ // 用户不存在，JSON 返回true
					String user = "[{'yonghu':'true'}]";  //json 格式
					byte[] jb1 = user.getBytes();
					OutputStream outputStream;
					try {
						outputStream = new DataOutputStream(socket.getOutputStream());
						outputStream.write(jb1);
						outputStream.flush();
						socket.shutdownOutput();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		if (strtemp.charAt(0) == '2') { 
			int p = strtemp.indexOf("#");
			str = strtemp.substring(1, p);
			DBUpdater update = new DBUpdater(str);
			update.update();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

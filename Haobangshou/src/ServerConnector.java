import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnector {
	static Socket server;
	int port = 10013;

	//byte[] ip1 = new byte[] { (byte) 192, (byte) 168, 1, 100 };
	private String str = "";
	String hostName = "William-Xx";
    
	ServerConnector(String str) {
		this.str = str;
	}

	public void Update() throws UnknownHostException, IOException {
		try {
			InetAddress ad = InetAddress.getByName(hostName);
			byte ip[] = ad.getAddress();
			
			server = new Socket(InetAddress.getByAddress(ip), port);
		} catch (Exception e) {
			Warning war = new Warning("服务器离线");
			war.setVisible(true);
		}
		server.setSoLinger(true, 0);
		PrintWriter out = new PrintWriter(server.getOutputStream());
		out.println(str);
		out.flush();
		server.close();
	}

	@SuppressWarnings("unused")
	public String Con() throws Exception {
		try {
			InetAddress ad = InetAddress.getByName(hostName);
			byte ip[] = ad.getAddress();
			server = new Socket(InetAddress.getByAddress(ip), port);
		} catch (Exception e) {
			Warning war = new Warning("服务器离线");
			war.setVisible(true);
		}

		server.setSoLinger(true, 0);
		PrintWriter out = new PrintWriter(server.getOutputStream());
		out.println(str);
		out.flush();
		String strInputStream = "";

		DataInputStream inputStream = new DataInputStream(
				server.getInputStream());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] by = new byte[4086]; 

		int count = 0;
		while (count == 0) {
			count = inputStream.available();
		}
		byte[] b = new byte[count];
		inputStream.read(b);

		baos.write(b);

		baos.close();

		server.close();
		return baos.toString();
	}

}
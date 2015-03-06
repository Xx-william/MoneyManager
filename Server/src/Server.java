import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static int port = 10013;

	public static void main(String[] args) throws IOException {

		while (true) {

			ServerSocket ss = new ServerSocket(port);
			boolean bRunning = true;
			while (bRunning) {
				Socket s = ss.accept();
				new Thread(new RunService(s)).start();
			}

			ss.close();
		}
	}
}
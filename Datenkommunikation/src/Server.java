import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		final int serverPort = 7777;

		// autocloses stuff
		try (ServerSocket server = new ServerSocket(serverPort);
				Socket client = server.accept();
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
			String line;
			ServerState state = new ServerState();
			int escape = 0;
			while (true) {
				line = in.readLine();
				try {
					escape = Integer.parseInt(line);
				} catch (Exception e) {
				}

				if (escape == -1) {
					break;
				}
				line = state.processCommand(line);
				out.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}

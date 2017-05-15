import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Not enough arguments!");
			return;
		}
		String serverName = args[0];
		int serverPort = Integer.parseInt(args[1]);

		// autocloses stuff
		try (Socket client = new Socket(serverName, serverPort);
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

			String line;
			int escape = 0;
			while (true) {
				line = userInput.readLine();
				out.println(line);
				
				try {
					escape = Integer.parseInt(line);
				} catch (Exception e) {
				}
				
				if (escape == -1) {
					break;
				}
				
				line = in.readLine();
				System.out.println(line);
			}

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}

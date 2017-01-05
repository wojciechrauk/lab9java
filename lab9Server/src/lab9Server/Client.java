package lab9Server;

import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] argv) {
		Client c = new Client();
		c.converse(argv.length == 1 ? argv[0] : "localhost");
	}

	/** Hold one conversation with the named hosts echo server */
	protected void converse(String hostname) {
		Socket sock = null;
		try {
			sock = new Socket(hostname, 1666);
			// echo server.
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader is = new BufferedReader(new InputStreamReader(sock.getInputStream(), "8859_1"));
			PrintWriter os = new PrintWriter(new OutputStreamWriter(sock.getOutputStream(), "8859_1"), true);
			String line;
			do {
				System.out.print(">> ");
				if ((line = stdin.readLine()) == null)
					break;
				if (line.startsWith("****"))
					break;
				// Do the CRLF ourself since println appends only a \r on
				// platforms where that is the native line ending.
				os.print(line + "\r\n");
				os.flush();
				String reply = is.readLine();
				System.out.print("<< ");
				System.out.println(reply);
			} while (line != null);
		} catch (IOException e) { // handles all input/output errors
			System.out.println("error here?");
			System.err.println(e);
		} finally {
			// cleanup
			try {
				if (sock != null)
					sock.close();
			} catch (IOException ignoreMe) {
				// nothing
			}
		}
	}
}
// public static void main(String[] args) throws Exception {
// Client client = new Client();
// client.run();
// }
//
// public void run() throws Exception{
// Socket sock = new Socket("localhost", 1666);
// PrintStream ps = new PrintStream(sock.getOutputStream());
// ps.println("Hello from the client side");
//
// InputStreamReader ir = new InputStreamReader(sock.getInputStream());
// BufferedReader br = new BufferedReader(ir);
//
// String message = br.readLine();
// System.out.println(message);
//
// sock.close();
// }

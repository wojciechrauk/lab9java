package lab9Server;

import java.io.*;
import java.net.*;

public class Server {
	public static final int ECHOPORT = 1666;
	public static final int NUM_THREADS = 4;

	/** Main method, to start the servers. */
	public static void main(String[] av) {
		new Server(ECHOPORT, NUM_THREADS);
	}

	/** Constructor */
	public Server(int port, int numThreads) {
		ServerSocket servSock;
		try {
			servSock = new ServerSocket(port);
		} catch (IOException e) {
			/* Crash the server if IO fails. Something bad has happened */
			throw new RuntimeException("Could not create ServerSocket " + e);
		}
		// Create a series of threads and start them.
		for (int i = 0; i < numThreads; i++) {
			new Handler(servSock, i).start();
		}
	}

	/** A Thread subclass to handle one client conversation. */
	class Handler extends Thread {
		ServerSocket servSock;
		int threadNumber;

		/** Construct a Handler. */
		Handler(ServerSocket s, int i) {
			super();
			servSock = s;
			threadNumber = i;
			setName("Thread " + threadNumber);
		}

		public void run() {
			/*
			 * Wait for a connection. Synchronized on the ServerSocket while
			 * calling its accept() method.
			 */
			while (true) {
				try {
					System.out.println(getName() + " waiting");
					Socket clientSocket;
					// Wait here for the next connection.
					synchronized (servSock) {
						clientSocket = servSock.accept();
					}
					System.out.println(getName() + " starting, IP=" + clientSocket.getInetAddress());
					BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					PrintStream os = new PrintStream(clientSocket.getOutputStream(), true);
					String line;
					while ((line = is.readLine()) != null) {
						os.print(line + "\r\n");
						os.flush();
					}
					System.out.println(getName() + " ENDED ");
					clientSocket.close();
				} catch (IOException ex) {
					System.out.println(getName() + ": IO Error on socket " + ex);
					return;
				}
			}
		}
	}
	// public static void main(String[] args) throws IOException {
	// Server server = new Server();
	// server.run();
	// }
	//
	// public void run() throws IOException{
	// ServerSocket serverSoc = new ServerSocket(444);
	// Socket sock = serverSoc.accept();
	//
	// InputStreamReader ir = new InputStreamReader(sock.getInputStream());
	// BufferedReader br = new BufferedReader(ir);
	//
	// String message = br.readLine();
	// System.out.println(message);
	//
	// if(message != null){
	// PrintStream ps = new PrintStream(sock.getOutputStream());
	// ps.println("Message recieved");
	// }
	//
	// serverSoc.close();
	// }
}

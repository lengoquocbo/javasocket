package severclock;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

public class server extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server frame = new server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public server() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btn = new JButton("K\u00EDch ho\u1EA1t");
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            ServerSocket serverSocket = new ServerSocket(12345);
		            System.out.println("Server is running...");

		            while (true) {
		        
		                Socket clientSocket = serverSocket.accept();
		                System.out.println("Client connected: " + clientSocket);

		                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		                String inputLine;
		                while ((inputLine = in.readLine()) != null) {
		                    if (inputLine.equals("time")) {
		                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                        String currentTime = sdf.format(new Date());
		                        out.println(currentTime);
		                    } else {
		                        out.println("");
		                    }
		                }

		                in.close();
		                out.close();
		                clientSocket.close();
		            }
		           
		            	
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }
			}
		});
		panel.add(btn);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

}

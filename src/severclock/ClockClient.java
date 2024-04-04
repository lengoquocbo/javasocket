package severclock;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClockClient extends JFrame {
    protected JLabel clockLabel;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClockClient() {
        setTitle("Simple Clock");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BorderLayout());

        clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 24));
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(clockLabel, BorderLayout.CENTER);

        try {
            socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    while (true) {
                    	clockLabel.getText();
                        out.println("time");
                        String receivedTime = in.readLine();
                        System.out.println(receivedTime);
                        SwingUtilities.invokeLater(() -> clockLabel.setText(receivedTime));
                        Thread.sleep(1000);
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public JLabel getreceivedTime(){
    	return clockLabel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClockClient client = new ClockClient();
            client.setVisible(true);
        });
    }
}

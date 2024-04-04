package javatcp;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

public class Managerchatter extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textfieldsvport;
    private JTabbedPane tabbedPane;
    ServerSocket srvsocket = null;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Managerchatter frame = new Managerchatter();
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
    public Managerchatter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JLabel labelmanager = new JLabel("Manager port: ");
        labelmanager.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(labelmanager);

        textfieldsvport = new JTextField();
        textfieldsvport.setText("12340");
        panel.add(textfieldsvport);
        textfieldsvport.setColumns(10);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        this.setSize(600, 300);

        int serverport = Integer.parseInt(textfieldsvport.getText());
        try {
            srvsocket = new ServerSocket(serverport);
            Thread t = new Thread(new ServerRunnable());
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ServerRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Socket nhanstaffsocket = srvsocket.accept();
                    if (nhanstaffsocket != null) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(nhanstaffsocket.getInputStream()));
                        String s = br.readLine();
                        int pos = s.indexOf(":");
                        String staffname = s.substring(pos + 1);
                        Jframequanli p = new Jframequanli(nhanstaffsocket, s, staffname);
                        tabbedPane.add(staffname, p);
                        p.updateUI();
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package mercadoNavarro.view;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.model.Admin;
import mercadoNavarro.model.User;

public class LoginFrame {

    JFrame frame;
    JLabel error;
    Timer timer;

    /**
     * Create the application.
     */
    public LoginFrame() {
    	Runnable action = new Runnable() {
			public void run() {
				try {
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		ProgressDialog loading = new ProgressDialog(frame, action, "Loading...");
		loading.setLocationRelativeTo(frame);
		loading.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel basePane = new JPanel(new BorderLayout(0, 0));
        frame.setContentPane(basePane);

        JPanel loginPane = new LoginPane();
        basePane.add(loginPane);
    }

    public class LoginPane extends JPanel {
        private JPasswordField textField;
        private JTextField textField_1;

        /**
         * Create the panel.
         */
        public LoginPane() {
            setLayout(null);

            JButton btnNewButton = new JButton("Login");
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    Runnable action = new Runnable() {
                    	public void run() {
                    		try {
                                User u = null;
                                Admin a = null;
                                HomeFrame window = null;
                                String user = textField_1.getText();
                                String password = new String(textField.getPassword());
                                if ((u = DBDataFacade.getUser(user)) != null) {
                                    if(!u.isEnabled())
                                        showError(3);
                                    else if (u.getPassword().equals(password)) {
                                        frame.setVisible(false);
                                        frame.dispose();
                                        window = new HomeFrame(u);
                                        window.frame.setVisible(true);
                                    } else
                                        showError(1);
                                } else if ((a = DBDataFacade.getAdmin(user)) != null) {
                                    if (a.getPassword().equals(password)) {
                                        frame.setVisible(false);
                                        frame.dispose();
                                        window = new HomeFrame(a);
                                        window.frame.setVisible(true);
                                    }
                                } else {
                                    showError(2);
                                }
                                
                    		} catch (Exception e) {
                    			e.printStackTrace();
                    		}
                    	}
                    };

                    ProgressDialog loading = new ProgressDialog(frame, action, "Loading...");
                    loading.setLocationRelativeTo(frame);
                    loading.setVisible(true);
                }
            });
            btnNewButton.setBounds(117, 213, 97, 25);
            add(btnNewButton);

            JButton btnNewButton_1 = new JButton("Register");
            btnNewButton_1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    RegisterFrame window = new RegisterFrame();
                    window.frame.setVisible(true);
                }
            });
            btnNewButton_1.setBounds(253, 213, 97, 25);
            add(btnNewButton_1);

            textField = new JPasswordField();
            textField.setBounds(117, 170, 233, 22);
            add(textField);
            textField.setColumns(10);

            textField_1 = new JTextField();
            textField_1.setBounds(117, 124, 233, 22);
            add(textField_1);
            textField_1.setColumns(10);

            JLabel lblUsername = new JLabel("E-Mail");
            lblUsername.setBounds(41, 126, 44, 19);
            add(lblUsername);

            JLabel lblPassword = new JLabel("Password");
            lblPassword.setBounds(29, 173, 70, 16);
            add(lblPassword);

            error = new JLabel();
            error.setBounds(100, 190, 250, 22);
            error.setHorizontalAlignment(SwingConstants.CENTER);
            error.setForeground(Color.RED);
            add(error);

            timer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    error.setText("");
                }
            });

			/*JLabel logo = new JLabel("");
            logo.setIcon(new ImageIcon("C:\\Users\\Rodrigo Navarro\\Desktop\\Coin.png"));
			logo.setBounds(114, 30, 277, 64);
			add(logo);*/
        }
    }

    public void showError(int id) {
        switch (id) {
            case 1:
                error.setText("User and password combination incorrect");
                break;
            case 2:
                error.setText("User not found");
                break;
            case 3:
                error.setText("User is not enabled");
        }
        timer.start();
    }
}


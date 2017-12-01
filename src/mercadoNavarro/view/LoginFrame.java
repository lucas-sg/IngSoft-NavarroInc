package mercadoNavarro.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.model.Item;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class LoginFrame {

	JFrame frame;

	/**
	 * Create the application.
	 */
	public LoginFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);

		JPanel loginPane = new LoginPane();
		basePane.add(loginPane);
	}

	public class LoginPane extends JPanel {
		private JTextField textField;
		private JTextField textField_1;

		/**
		 * Create the panel.
		 */
		public LoginPane() {
			setLayout(null);

			JButton btnNewButton = new JButton("Login");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false); //you can't see me!
					frame.dispose(); //Destroy the JFrame object
					HomeFrame window = new HomeFrame();
					window.frame.setVisible(true);
				}
			});
			btnNewButton.setBounds(117, 213, 97, 25);
			add(btnNewButton);

			JButton btnNewButton_1 = new JButton("Register");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false); //you can't see me!
					frame.dispose(); //Destroy the JFrame object
					RegisterFrame window = new RegisterFrame();
					window.frame.setVisible(true);
				}
			});
			btnNewButton_1.setBounds(253, 213, 97, 25);
			add(btnNewButton_1);

			textField = new JTextField();
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
			lblPassword.setBounds(29, 173, 56, 16);
			add(lblPassword);

			/*JLabel logo = new JLabel("");
			logo.setIcon(new ImageIcon("C:\\Users\\Rodrigo Navarro\\Desktop\\Coin.png"));
			logo.setBounds(114, 30, 277, 64);
			add(logo);*/
		}
	}
}


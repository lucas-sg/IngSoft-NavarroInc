package mercadoNavarro.view;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.model.Item;
import mercadoNavarro.model.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

public class AdminFrame {

	JFrame frame;

	/**
	 * Create the application.
	 */
	public AdminFrame() {
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
		frame.setBounds(200, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);

		JPanel usersPane = new UsersPane();
		basePane.add(usersPane);
	}

	public class UsersPane extends JPanel {

		/**
		 * Create the panel.
		 */
		public UsersPane() {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			List<User> users = DBDataFacade.getUsers();
			for (User user : users) {
				JCheckBox checkBox = new JCheckBox(user.getName() + " " + user.getSurname() + " (" + user.geteMail() + ")");
				checkBox.setSelected(user.isEnabled());
				panel.add(checkBox);

				checkBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Runnable action = new Runnable() {
							public void run() {
								try {
									user.setEnabled(checkBox.isSelected());
									DBDataFacade.modifyUser(user);
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
			}
			panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
			add(new JScrollPane(panel));
		}
	}
}

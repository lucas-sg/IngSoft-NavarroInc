package mercadoNavarro.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mercadoNavarro.model.Admin;
import mercadoNavarro.model.Buyer;
import mercadoNavarro.model.User;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.enums.Ordering;
import mercadoNavarro.model.Item;
import mercadoNavarro.model.Seller;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class CartFrame {

	JFrame frame;
	Buyer buyer;

	/**
	 * Create the application.
	 */
	public CartFrame(Buyer buyer) {
		this.buyer = buyer;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(300, 300, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);
		
		JPanel articlesPane = new ArticlesPane();
		basePane.add(articlesPane);
	}
	
	public class ArticlesPane extends JPanel {

		private JPanel mainList;
		
		public ArticlesPane() {
			setLayout(new BorderLayout());

			mainList = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.weighty = 1;
			mainList.add(new JPanel(), gbc);

			add(new JScrollPane(mainList));
			
			int i = 0;
			for (Map.Entry<Item, Integer> entry : buyer.getCart()) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				JPanel data = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JPanel img = new JPanel(new BorderLayout());
				ImageIcon imgIcon = new ImageIcon(entry.getKey().getGallery().get(0));
				Image image = imgIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imgIcon = new ImageIcon(newimg);  // transform it back
				JLabel pic = new JLabel();
				pic.setIcon(imgIcon);
				img.add(pic);
				JPanel info = new JPanel();
				info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
				info.add(new JLabel("Name: " + entry.getKey().getName()));
				info.add(new JLabel("Price: " + entry.getKey().getPrice()));
				info.add(new JLabel("Stock: " + entry.getKey().getStock()));
				JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JButton btn = new JButton("View Item Page");
				buttons.add(btn);
				data.add(img);
				data.add(info);
				panel.add(data);
				panel.add(buttons);
				panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
				gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.weightx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(5, 5, 5, 5);
				mainList.add(panel, gbc, i++);
				
				btn.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  ItemFrame itemWindow = new ItemFrame(entry.getKey().getItemid());
						  itemWindow.frame.setVisible(true);
					  }
				});
			}	
		}
	}
}

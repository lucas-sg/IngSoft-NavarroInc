package mercadoNavarro.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.enums.Ordering;
import mercadoNavarro.model.Item;
import mercadoNavarro.view.HomeFrame.ArticlesPane;
import mercadoNavarro.view.HomeFrame.SearchPane;

public class ItemFrame {

	JFrame frame;
	Item item;

	/**
	 * Create the application.
	 */
	public ItemFrame(int itemId) {
		initialize(itemId);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int itemId) {
		frame = new JFrame();
		frame.setBounds(200, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);
		
		item = DBDataFacade.getItem(itemId);
		
		JPanel galleryPane = new GalleryPane();
		basePane.add(galleryPane, BorderLayout.PAGE_START);
		
		JPanel infoPane = new InfoPane();
		basePane.add(infoPane, BorderLayout.CENTER);
		
		JPanel commentsPane = new CommentsPane();
		basePane.add(commentsPane, BorderLayout.PAGE_END);
		
		frame.validate();
		frame.repaint();
	}
	
	public class GalleryPane extends JPanel {
		
		public GalleryPane() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
			
			for (String img : item.getGallery()) {
				ImageIcon imgIcon = new ImageIcon(img);
				Image image = imgIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imgIcon = new ImageIcon(newimg);  // transform it back
				JLabel pic = new JLabel();
				pic.setIcon(imgIcon);
				panel.add(pic);
			}
			
			JScrollPane scroll = new JScrollPane(panel);
			
			// Disable the vertical scrollbar
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			
			add(scroll);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(250, 100);
		}
	}
	
	public class InfoPane extends JPanel {
		
		public InfoPane() {
			setLayout(new BorderLayout());

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.add(new JLabel("Name: " + item.getName()));
			panel.add(new JLabel("Description: " + item.getDescription()));
			panel.add(new JLabel("Stock: " + item.getStock()));
			panel.add(new JLabel("Pickup: " + item.getPickup()));
			panel.add(new JLabel("Price: " + item.getPrice()));
			panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
			
			add(panel);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(250, 200);
		}
	}
	
	public class CommentsPane extends JPanel {

		private JPanel mainList;
		
		public CommentsPane() {
			setLayout(new BorderLayout());

			mainList = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.weighty = 1;
			mainList.add(new JPanel(), gbc);
			add(new JScrollPane(mainList));
			
			JPanel titlepanel = new JPanel();
			titlepanel.setLayout(new BoxLayout(titlepanel, BoxLayout.Y_AXIS));
			titlepanel.add(new JLabel("Comments: "));
			titlepanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
			gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5, 5, 5, 5);
			mainList.add(titlepanel, gbc, 0);
			
			int i = 1;
			for (String comment : item.getComments()) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				panel.add(new JLabel("#" + i + " - " + comment));
				panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
				gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.weightx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(5, 5, 5, 5);
				mainList.add(panel, gbc, i++);
			}
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(250, 200);
		}
	}
}

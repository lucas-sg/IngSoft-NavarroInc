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
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
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
		Runnable action = new Runnable() {
			public void run() {
				try {
					initialize(itemId);
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
	private void initialize(int itemId) {
		frame = new JFrame();
		frame.setBounds(200, 300, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.item = DBDataFacade.getItem(itemId);
		item.setSeller(DBDataFacade.addSellerPicture(item.getSeller()));
		
		JPanel basePane = new JPanel(new BorderLayout(0, 0));
		frame.setContentPane(basePane);
		
		JPanel containerPane = new JPanel();
		containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));
		
		JPanel infoPane = new InfoPane();
		containerPane.add(infoPane);
		
		JPanel galleryPane = new GalleryPane();
		containerPane.add(galleryPane);
		
		JPanel commentsPane = new CommentsPane();
		containerPane.add(commentsPane);
		
		JScrollPane scroll = new JScrollPane(containerPane);
		basePane.add(scroll);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 
			       scroll.getVerticalScrollBar().setValue(0);
			   }
		});
	}
	
	public class InfoPane extends JPanel {
		
		public InfoPane() {
			FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 20, 10);
			layout.setAlignOnBaseline(true);
			setLayout(layout);

			JPanel itemInfo = new JPanel();
			itemInfo.setLayout(new BoxLayout(itemInfo, BoxLayout.Y_AXIS));
			itemInfo.add(new JLabel("Item Info"));
			itemInfo.add(new JLabel("Name: " + item.getName()));
			itemInfo.add(new JLabel("Description: " + item.getDescription()));
			itemInfo.add(new JLabel("Stock: " + item.getStock()));
			itemInfo.add(new JLabel("Pickup: " + item.getPickup()));
			itemInfo.add(new JLabel("Price: " + item.getPrice()));
			
			JPanel sellerInfo = new JPanel();
			sellerInfo.setLayout(new BoxLayout(sellerInfo, BoxLayout.Y_AXIS));
			sellerInfo.add(new JLabel("Seller Info"));
			sellerInfo.add(new JLabel("Name: " + item.getSeller().getName() + " " + item.getSeller().getSurname()));
			ImageIcon imgIcon = new ImageIcon(item.getSeller().getPhoto());
			Image image = imgIcon.getImage(); // transform it 
			Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			imgIcon = new ImageIcon(newimg);  // transform it back
			JLabel pic = new JLabel();
			pic.setIcon(imgIcon);
			sellerInfo.add(pic);
			
			add(itemInfo);
			add(sellerInfo);
		}
	}
	
	public class GalleryPane extends JPanel {
		
		public GalleryPane() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
			
			for (String img : item.getGallery()) {
				ImageIcon imgIcon = new ImageIcon(img);
				Image image = imgIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(128, 128,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
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
	}
	
	public class CommentsPane extends JPanel {

		private JPanel mainList;
		GridBagConstraints gbc;
		int listIndex = 1;
		
		public CommentsPane() {
			setLayout(new BorderLayout());

			mainList = new JPanel(new GridBagLayout());
			gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.weighty = 1;
			mainList.add(new JPanel(), gbc);
			add(mainList);
			
			JPanel titlepanel = new JPanel();
			titlepanel.setLayout(new BoxLayout(titlepanel, BoxLayout.Y_AXIS));
			titlepanel.add(new JLabel("Comments: "));
			gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5, 5, 5, 5);
			mainList.add(titlepanel, gbc, 0);

			JPanel writeComment = new JPanel();
			writeComment.setLayout(new BoxLayout(writeComment, BoxLayout.X_AXIS));
			JTextArea commentText = new JTextArea();
			commentText.setRows(3);
			JScrollPane scrollPane = new JScrollPane(commentText);
			scrollPane.setPreferredSize(scrollPane.getPreferredSize());
			commentText.setLineWrap(true);
			commentText.setWrapStyleWord(true);
			JButton send = new JButton("Comment");
			send.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {			
					Runnable action = new Runnable() {
						public void run() {
							try {
								String comment = commentText.getText();
								if(comment != "") {
									if(item.addComment(comment)) {
										addComment(comment);
										commentText.setText("");
									}
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
			writeComment.add(scrollPane);
			writeComment.add(Box.createHorizontalStrut(5));
			writeComment.add(send);
			mainList.add(writeComment, gbc, listIndex++);
			for (String comment : item.getComments()) {
				addComment(comment);
			}
		}

		private void addComment(String comment) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			JTextArea commentText = new JTextArea("#" + (listIndex - 1) + " - " + comment);
			commentText.setEditable(false);
			commentText.setLineWrap(true);
			commentText.setWrapStyleWord(true);
			panel.add(commentText);
			panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
			gbc = new GridBagConstraints();
			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(5, 5, 5, 5);
			mainList.add(panel, gbc, listIndex++);
			frame.revalidate();
			frame.repaint();
		}
	}
}
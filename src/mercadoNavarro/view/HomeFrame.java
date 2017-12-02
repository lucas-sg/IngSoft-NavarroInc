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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class HomeFrame {

	JFrame frame;
	User user;
	Admin admin;

	/**
	 * Create the application.
	 */
	public HomeFrame(User user) {
		this.user = user;
		initialize();
	}

	public HomeFrame(Admin admin) {
		this.admin = admin;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);
		
		JPanel optionsPane = new OptionsPane();
		basePane.add(optionsPane, BorderLayout.PAGE_START);
		
		JPanel articlesPane = new ArticlesPane();
		basePane.add(articlesPane, BorderLayout.CENTER);
		
		JPanel searchPane = new SearchPane();
		basePane.add(searchPane, BorderLayout.LINE_START);
	}
	
	public class OptionsPane extends JPanel {
		
		public OptionsPane() {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
			if (user != null && admin == null) {
				JButton modifyBtn = new JButton("Modify User Info");
				panel.add(modifyBtn);
				if (user instanceof Buyer) {
					JButton cartBtn = new JButton("View Cart List");
					panel.add(cartBtn);
					JButton salesBtn = new JButton("View Bought List");
					panel.add(salesBtn);
				}
				else if (user instanceof Seller) {
					JButton cartBtn = new JButton("View For Sale List");
					panel.add(cartBtn);
					JButton salesBtn = new JButton("View Sold List");
					panel.add(salesBtn);
				}
			}
			else if (user == null && admin != null) {
				JButton adminBtn = new JButton("Admin Panel");
				panel.add(adminBtn);
			}
			
			add(panel);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(250, 100);
		}
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
		}

		public ArticlesPane(String search, Ordering orderBy) {
			this();

			List<Item> articles = DBDataFacade.getSearch(search, orderBy);
			int i = 0;
			for (Item article : articles) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				panel.add(new JLabel("Name: " + article.getName()));
				panel.add(new JLabel("Price: " + article.getPrice()));
				panel.add(new JLabel("Stock: " + article.getStock()));
				JButton btn = new JButton("View Item Page");
				panel.add(btn);
				panel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.weightx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.insets = new Insets(5, 5, 5, 5);
				mainList.add(panel, gbc, i++);
				
				btn.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  ItemFrame itemWindow = new ItemFrame(article.getItemid());
						  itemWindow.frame.setVisible(true);
					  }
				});
			}
		}
	}
	
	public class SearchPane extends JPanel {

		private JTextField textField;

		/**
		 * Create the panel.
		 */
		public SearchPane() {
			
			setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
			
			JLabel lblSearchByName = new JLabel("Search by name:");
			
			textField = new JTextField();
			textField.setColumns(10);
			
			JLabel lblOrderBy = new JLabel("Order by:");
			
			JComboBox comboBox = new JComboBox();
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "Lowest Price", "Stars", "Pickup"}));
			
			JButton btnSearch = new JButton("Search");
			GroupLayout groupLayout = new GroupLayout(this);
			groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(lblOrderBy, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblSearchByName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(textField)))
							.addComponent(btnSearch))
						.addContainerGap(269, Short.MAX_VALUE))
			);
			groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblSearchByName)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOrderBy)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnSearch)
						.addContainerGap(204, Short.MAX_VALUE))
			);
			setLayout(groupLayout);
			
			btnSearch.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					  JPanel articlesPane;
					  switch((String)comboBox.getSelectedItem()) {
					  	case "Default": {
					  		articlesPane = new ArticlesPane(textField.getText(), Ordering.DEFAULT);
					  		break;
					  	}
					  	case "Lowest Price": {
					  		articlesPane = new ArticlesPane(textField.getText(), Ordering.LOWEST_PRICE);
					  		break;
					  	}
					  	case "Stars": {
					  		articlesPane = new ArticlesPane(textField.getText(), Ordering.STARS);
					  		break;
					  	}
					  	case "Pickup": {
					  		articlesPane = new ArticlesPane(textField.getText(), Ordering.PICKUP);
					  		break;
					  	}
					  	default: {
					  		articlesPane = new ArticlesPane(textField.getText(), Ordering.DEFAULT);
					  		break;
					  	}
					  }
					  
					  BorderLayout layout = (BorderLayout)frame.getContentPane().getLayout();
					  frame.getContentPane().remove(layout.getLayoutComponent(BorderLayout.CENTER));
					  frame.getContentPane().add(articlesPane, BorderLayout.CENTER);
					  frame.validate();
					  frame.repaint();
				  }
			});
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(250, 200);
		}
	}
}

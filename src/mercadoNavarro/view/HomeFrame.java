package mercadoNavarro.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
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
import mercadoNavarro.enums.PaymentMethod;
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

public class HomeFrame {

	JFrame frame;
	User user;
	Admin admin;

	/**
	 * Create the application.
	 */
	public HomeFrame(User user) {
		this.user = user;
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

	public HomeFrame(Admin admin) {
		this.admin = admin;
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
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
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
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout(20, 0));
			
			JLabel helloMsg = new JLabel("");
			
			JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
			if (user != null && admin == null) {
				JButton modifyBtn = new JButton("Modify User Info");
				buttons.add(modifyBtn);
				if (user instanceof Buyer) {
					helloMsg.setText("Hello, " + user.getName() + " " + user.getSurname() + " (Buyer)");
					JButton cartBtn = new JButton("Cart");
					buttons.add(cartBtn);
					JButton salesBtn = new JButton("Bought List");
					buttons.add(salesBtn);
					
					cartBtn.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent arg0) {
		                    CartFrame window = new CartFrame((Buyer)user);
		                    window.frame.setVisible(true);
		                }
		            });
					
					salesBtn.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent arg0) {
		                    BoughtFrame window = new BoughtFrame((Buyer)user);
		                    window.frame.setVisible(true);
		                }
		            });
				}
				else if (user instanceof Seller) {
					helloMsg.setText("Hello, " + user.getName() + " " + user.getSurname() + " (Seller)");
					JButton forSaleBtn = new JButton("Sell an Article");
					buttons.add(forSaleBtn);
					JButton salesBtn = new JButton("Sold List");
					buttons.add(salesBtn);
					
					forSaleBtn.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent arg0) {
		                    ForSaleFrame window = new ForSaleFrame((Seller)user);
		                    window.frame.setVisible(true);
		                }
		            });
					
					salesBtn.addActionListener(new ActionListener() {
		                public void actionPerformed(ActionEvent arg0) {
		                    SoldFrame window = new SoldFrame((Seller)user);
		                    window.frame.setVisible(true);
		                }
		            });
				}
				
				modifyBtn.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent arg0) {
	                    RegisterFrame window = new RegisterFrame(user);
	                    window.frame.setVisible(true);
	                }
	            });
			}
			else if (user == null && admin != null) {
				helloMsg.setText("Hello, Admin");
				JButton adminBtn = new JButton("Admin Panel");
				buttons.add(adminBtn);
				
				adminBtn.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent arg0) {
	                    AdminFrame window = new AdminFrame();
	                    window.frame.setVisible(true);
	                }
	            });
			}
			
			JButton logOutBtn = new JButton("Log Out");
			buttons.add(logOutBtn);
			
			logOutBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    for (Window window : Window.getWindows()) {
                    	window.setVisible(false);
                    	window.dispose();
                    }
                    	
                    LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
                }
            });
			
			panel.add(helloMsg, BorderLayout.LINE_START);
			panel.add(buttons, BorderLayout.LINE_END);
			add(panel);
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(800, 60);
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
				JPanel data = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JPanel img = new JPanel(new BorderLayout());
				ImageIcon imgIcon = new ImageIcon(article.getGallery().get(0));
				Image image = imgIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imgIcon = new ImageIcon(newimg);  // transform it back
				JLabel pic = new JLabel();
				pic.setIcon(imgIcon);
				img.add(pic);
				JPanel info = new JPanel();
				info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
				info.add(new JLabel("Name: " + article.getName()));
				info.add(new JLabel("Price: $" + article.getPrice()));
				info.add(new JLabel("Stock: " + article.getStock()));
				JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JButton btn = new JButton("View Item Page");
				buttons.add(btn);
				JButton btn2 = new JButton("Add to Cart");
				if (user != null && admin == null && user instanceof Buyer) {
					btn2.setEnabled(true);
					if (((Buyer)user).cartContainsItem(article)) {
						btn2.setText("Already added to Cart");
						btn2.setEnabled(false);
					}
				}
				else
					btn2.setEnabled(false);
				buttons.add(btn2);
				data.add(img);
				data.add(info);
				panel.add(data);
				panel.add(buttons);
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
				
				btn2.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  ((Buyer)user).addItemToCart(article.getItemid(), 1);
						  btn2.setText("Already added to Cart");
						  btn2.setEnabled(false);
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
			
			JComboBox comboBox = new JComboBox(Ordering.values());
			
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
					Runnable action = new Runnable() {
						public void run() {
							try {
								JPanel articlesPane;
								articlesPane = new ArticlesPane(textField.getText(), (Ordering)comboBox.getSelectedItem());

								BorderLayout layout = (BorderLayout)frame.getContentPane().getLayout();
								frame.getContentPane().remove(layout.getLayoutComponent(BorderLayout.CENTER));
								frame.getContentPane().add(articlesPane, BorderLayout.CENTER);
								frame.validate();
								frame.repaint();
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
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(250, 200);
		}
	}
}

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
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.enums.Ordering;
import mercadoNavarro.enums.PaymentMethod;
import mercadoNavarro.enums.PhoneType;
import mercadoNavarro.model.Item;
import mercadoNavarro.model.Seller;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class CartFrame {

	JFrame frame;
	Buyer buyer;
	
	JLabel priceLabel;
	JButton confirmBtn;

	/**
	 * Create the application.
	 */
	public CartFrame(Buyer buyer) {
		this.buyer = buyer;
		
		priceLabel = new JLabel();
		confirmBtn = new JButton();
		
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
		frame.setBounds(300, 300, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);
		
		JPanel articlesPane = new ArticlesPane();
		basePane.add(articlesPane, BorderLayout.CENTER);
		
		JPanel confirmPane = new ConfirmPane();
		basePane.add(confirmPane, BorderLayout.SOUTH);
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
			for (Map.Entry<Integer, Integer> entry : buyer.getCart()) {
				Item aux = DBDataFacade.getPartialItem(entry.getKey());
				
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				JPanel data = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JPanel img = new JPanel(new BorderLayout());
				ImageIcon imgIcon = new ImageIcon(aux.getGallery().get(0));
				Image image = imgIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imgIcon = new ImageIcon(newimg);  // transform it back
				JLabel pic = new JLabel();
				pic.setIcon(imgIcon);
				img.add(pic);
				JPanel info = new JPanel();
				info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
				info.add(new JLabel("Name: " + aux.getName()));
				info.add(new JLabel("Price: $" + aux.getPrice()));
				info.add(new JLabel("Stock: " + aux.getStock()));
				JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JButton btn = new JButton("View Item Page");
				JButton btn2 = new JButton("Remove Item from Cart");
				buttons.add(btn);
				buttons.add(btn2);
				buttons.add(new JLabel("Quantity:"));
				SpinnerModel model = new SpinnerNumberModel(entry.getValue(), new Integer(1), new Integer(aux.getStock()), new Integer(1));
				final JSpinner quantity = new JSpinner(model);
			    JComponent comp = quantity.getEditor();
			    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
			    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
			    formatter.setCommitsOnValidEdit(true);
			    quantity.addChangeListener(new ChangeListener() {
			    	@Override
			    	public void stateChanged(ChangeEvent e) {	       	
			    		Runnable action = new Runnable() {
			    			public void run() {
			    				try {
			    					quantity.setValue((Integer)quantity.getValue());
			    					buyer.addItemToCart(entry.getKey(), (Integer)quantity.getValue());
			    					priceLabel.setText("Total: $" + String.format("%.2f", buyer.getCartTotalPrice()));
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
				buttons.add(quantity);
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
				mainList.add(panel, gbc, i);
				
				btn.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  ItemFrame itemWindow = new ItemFrame(entry.getKey());
						  itemWindow.frame.setVisible(true);
					  }
				});
				
				btn2.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  Runnable action = new Runnable() {
							  public void run() {
								  try {
									  buyer.removeItemFromCart(entry.getKey());
									  mainList.remove(panel);
									  frame.validate();
									  frame.repaint();

									  priceLabel.setText("Total: $" + String.format("%.2f", buyer.getCartTotalPrice()));

									  if (buyer.getCart().isEmpty()) {
										  confirmBtn.setEnabled(false);
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
				
				i++;
			}	
		}
	}
	
	public class ConfirmPane extends JPanel {
		
		public ConfirmPane() {
			setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			priceLabel.setText("Total: $" + String.format("%.2f", buyer.getCartTotalPrice()));
			add(priceLabel);
			
			add(new JLabel("Payment Method:"));
			
			JComboBox payMethod = new JComboBox(PaymentMethod.values());
			add(payMethod);
			
			confirmBtn.setText("Confirm Buy");
			if (buyer.getCart().isEmpty()) {
				  confirmBtn.setEnabled(false);
			}
			
			add(confirmBtn);
			
			confirmBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {  
					Runnable action = new Runnable() {
						public void run() {
							try {
								buyer.confirmBuy((PaymentMethod)payMethod.getSelectedItem());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};

					ProgressDialog loading = new ProgressDialog(frame, action, "Loading...");
					loading.setLocationRelativeTo(frame);
					loading.setVisible(true);

					CartFrame window = new CartFrame(buyer);
					window.frame.setVisible(true);

					frame.setVisible(false);
					frame.dispose();
				}
			});
		}
	}
}

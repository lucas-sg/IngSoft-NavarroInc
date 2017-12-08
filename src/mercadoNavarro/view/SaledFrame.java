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
import mercadoNavarro.model.Item;
import mercadoNavarro.model.Sale;
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

public class SaledFrame {

	JFrame frame;
	Seller seller;

	/**
	 * Create the application.
	 */
	public SaledFrame(Seller seller) {
		this.seller = DBDataFacade.getFullSeller(seller.geteMail());
		initialize();
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
			for (Sale sale : DBDataFacade.getSales(seller.getId())) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
				JPanel data = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
				JPanel img = new JPanel(new BorderLayout());
				ImageIcon imgIcon = new ImageIcon(sale.getArticle().getGallery().get(0));
				Image image = imgIcon.getImage(); // transform it 
				Image newimg = image.getScaledInstance(64, 64,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
				imgIcon = new ImageIcon(newimg);  // transform it back
				JLabel pic = new JLabel();
				pic.setIcon(imgIcon);
				img.add(pic);
				JPanel info = new JPanel();
				info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
				info.add(new JLabel("Name: " + sale.getArticle().getName()));
				info.add(new JLabel("Price: $" + sale.getArticle().getPrice()));
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
				mainList.add(panel, gbc, i);
				
				btn.addActionListener(new ActionListener() {
					  public void actionPerformed(ActionEvent e) {
						  ItemFrame itemWindow = new ItemFrame(sale.getArticle().getItemid());
						  itemWindow.frame.setVisible(true);
					  }
				});
				
				i++;
			}	
		}
	}
}

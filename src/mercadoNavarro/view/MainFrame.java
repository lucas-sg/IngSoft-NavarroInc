package mercadoNavarro.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

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
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class MainFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel homePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(homePane);
		
		JPanel searchPane = new SearchPane();
		homePane.add(searchPane, BorderLayout.LINE_START);
		
		JPanel articlesPane = new ArticlesPane();
		homePane.add(articlesPane, BorderLayout.CENTER);
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
				panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.gridwidth = GridBagConstraints.REMAINDER;
				gbc.weightx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				mainList.add(panel, gbc, i++);
			}
		}
	}
	
	public class SearchPane extends JPanel {

		private JTextField textField;

		/**
		 * Create the panel.
		 */
		public SearchPane() {
			
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

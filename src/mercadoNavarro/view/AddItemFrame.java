package mercadoNavarro.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatter;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.model.Item;
import mercadoNavarro.model.Seller;
import mercadoNavarro.model.User;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;


public class AddItemFrame {

	JFrame frame;
	Seller seller;
	Item item;

	/**
	 * Create the application.
	 */
	public AddItemFrame(Seller seller) {
		Runnable action = new Runnable() {
			public void run() {
				try {
					initialize(seller, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		ProgressDialog loading = new ProgressDialog(frame, action, "Loading...");
		loading.setLocationRelativeTo(frame);
		loading.setVisible(true);
	}
	
	public AddItemFrame(Seller seller, Integer itemId) {
		Runnable action = new Runnable() {
			public void run() {
				try {
					initialize(seller, itemId);
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
	private void initialize(Seller seller, Integer itemId) {
		frame = new JFrame();
		frame.setBounds(100, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.seller = seller;
		if (itemId != null)
			this.item = DBDataFacade.getItem(itemId);

		JPanel basePane = new JPanel(new BorderLayout(0, 0));
		frame.setContentPane(basePane);

		JPanel infoPane = new InfoPane();
		basePane.add(infoPane);
	}

	public class InfoPane extends JPanel {
		private JTextField Nombre;
		private JTextField Pickup;
		private JTextField Precio;
		private JTextField Fotos;

		/**
		 * Create the panel.
		 */
		public InfoPane() {
			setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Nombre");
			lblNewLabel.setBounds(10, 54, 156, 16);
			add(lblNewLabel);
			
			JLabel lblCaracteristicas = new JLabel("Caracteristicas");
			lblCaracteristicas.setBounds(10, 81, 156, 16);
			add(lblCaracteristicas);
			
			JLabel lblStock = new JLabel("Stock");
			lblStock.setBounds(10, 146, 156, 16);
			add(lblStock);
			
			JLabel lblImagenDelProducto = new JLabel("Imagenes del producto");
			lblImagenDelProducto.setBounds(10, 173, 156, 16);
			add(lblImagenDelProducto);
			
			Fotos = new JTextField();
			Fotos.setColumns(10);
			Fotos.setBounds(176, 173, 116, 22);
			Fotos.setEditable(false);
			add(Fotos);
			
			JButton btnSelectPath = new JButton("Select Files...");
			btnSelectPath.setBounds(302, 172, 116, 23);
			add(btnSelectPath);
			
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG Images", "jpg");
			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(true);
			btnSelectPath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int returnVal = chooser.showOpenDialog(frame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						StringBuilder sb = new StringBuilder();
						int i = 0;
						for (File file : chooser.getSelectedFiles()) {
							if (i != 0)
								sb.append("; ");
							sb.append(file.getName());
							i++;
						}
						Fotos.setText(new String(sb));
					}
				}
			});
			
			JLabel lblPickup = new JLabel("Pickup");
			lblPickup.setBounds(10, 215, 156, 16);
			add(lblPickup);
			
			JLabel lblPrecio = new JLabel("Precio");
			lblPrecio.setBounds(10, 258, 156, 16);
			add(lblPrecio);
			
			JTextArea Caracteristicas = new JTextArea();
			//Caracteristicas.setBounds(176, 80, 387, 50);
			Caracteristicas.setRows(3);
			Caracteristicas.setLineWrap(true);
			Caracteristicas.setWrapStyleWord(true);
			JScrollPane scrollPane = new JScrollPane(Caracteristicas);
			scrollPane.setBounds(176, 80, 387, 50);
			scrollPane.setPreferredSize(scrollPane.getPreferredSize());
			
			add(scrollPane);
			
			Nombre = new JTextField();
			Nombre.setBounds(176, 51, 116, 22);
			add(Nombre);
			Nombre.setColumns(10);
			
			JButton Confirm = new JButton("Confirm");
			Confirm.setBounds(176, 310, 97, 25);
			add(Confirm);
			
			Pickup = new JTextField();
			Pickup.setColumns(10);
			Pickup.setBounds(176, 212, 116, 22);
			add(Pickup);
			
			Precio = new JTextField();
			Precio.setBounds(176, 255, 116, 22);
			add(Precio);
			Precio.setColumns(10);
					
			SpinnerModel model = new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(Integer.MAX_VALUE), new Integer(1));
			final JSpinner quantity = new JSpinner(model);
		    JComponent comp = quantity.getEditor();
		    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
		    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
		    formatter.setCommitsOnValidEdit(true);
		    quantity.setBounds(176, 143, 116, 22);
		    add(quantity);
			
			if(item != null) {
				Nombre.setText(item.getName());
				Caracteristicas.setText(item.getDescription());
				quantity.setValue(new Integer(item.getStock()));
				Pickup.setText(item.getPickup());
				Precio.setText(String.format("%.2f", item.getPrice()));
			}
		}
	}
}



package mercadoNavarro.view;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;
import mercadoNavarro.model.Buyer;
import mercadoNavarro.model.Item;
import mercadoNavarro.model.Seller;
import mercadoNavarro.model.User;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class RegisterFrame {

	JFrame frame;
	
	private User user;

	/**
	 * Create the application.
	 */
	public RegisterFrame() {
		initialize(null);
	}
	
	public RegisterFrame(User user) {
		initialize(user);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(User user) {
		frame = new JFrame();
		frame.setBounds(200, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);
		
		this.user = user;

		JPanel registerPane = new RegisterPane();
		basePane.add(registerPane);
	}

	public class RegisterPane extends JPanel {
		private JTextField Email;
		private JTextField password;
		private JTextField nombre;
		private JTextField apellido;
		private JTextField ndoc;
		private JTextField calle;
		private JTextField textField_14;
		private JTextField telefono;
		private JTextField codigoPostal;
		private JTextField numero;

		/**
		 * Create the panel.
		 */
		
		public RegisterPane() {
			setLayout(null);
			
			Email = new JTextField();
			Email.setBounds(94, 13, 116, 22);
			add(Email);
			Email.setColumns(10);
			
			password = new JTextField();
			password.setColumns(10);
			password.setBounds(94, 48, 116, 22);
			add(password);
			
			nombre = new JTextField();
			nombre.setColumns(10);
			nombre.setBounds(94, 83, 116, 22);
			add(nombre);
			
			apellido = new JTextField();
			apellido.setColumns(10);
			apellido.setBounds(94, 118, 116, 22);
			add(apellido);
			
			ndoc = new JTextField();
			ndoc.setColumns(10);
			ndoc.setBounds(94, 188, 116, 22);
			add(ndoc);
			
			calle = new JTextField();
			calle.setColumns(10);
			calle.setBounds(94, 328, 116, 22);
			add(calle);
			
			JButton btnNewButton = new JButton("Register");
			btnNewButton.setBounds(165, 393, 97, 25);
			add(btnNewButton);
			
			JRadioButton rdbtnNewRadioButton = new JRadioButton("Proveedor");
			rdbtnNewRadioButton.setBounds(165, 359, 127, 25);
			add(rdbtnNewRadioButton);
			
			JLabel lblNewLabel = new JLabel("E-Mail");
			lblNewLabel.setBounds(10, 16, 72, 16);
			add(lblNewLabel);
			
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(10, 51, 72, 16);
			add(lblPassword);
			
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 86, 72, 16);
			add(lblNombre);
			
			JLabel lblApellido = new JLabel("Apellido");
			lblApellido.setBounds(10, 121, 72, 16);
			add(lblApellido);
			
			JLabel lblTipoDeDoc = new JLabel("Tipo de Doc");
			lblTipoDeDoc.setBounds(10, 156, 72, 16);
			add(lblTipoDeDoc);
			
			JLabel lblNdoc = new JLabel("N\u00B0Doc");
			lblNdoc.setBounds(10, 191, 72, 16);
			add(lblNdoc);
			
			JLabel lblPais = new JLabel("Pais");
			lblPais.setBounds(10, 226, 72, 16);
			add(lblPais);
			
			JLabel lblProvincia = new JLabel("Provincia");
			lblProvincia.setBounds(10, 261, 72, 16);
			add(lblProvincia);
			
			JLabel lblLocalidad = new JLabel("Localidad");
			lblLocalidad.setBounds(10, 296, 72, 16);
			add(lblLocalidad);
			
			JLabel lblCalle = new JLabel("Calle");
			lblCalle.setBounds(10, 331, 72, 16);
			add(lblCalle);
			
			JLabel lblFotoDePefil = new JLabel("Foto de Perfil");
			lblFotoDePefil.setBounds(219, 191, 77, 16);
			add(lblFotoDePefil);
			
			textField_14 = new JTextField();
			textField_14.setColumns(10);
			textField_14.setBounds(308, 188, 116, 22);
			add(textField_14);
			
			JButton btnSelectPath = new JButton("Select Path...");
			btnSelectPath.setBounds(434, 188, 116, 23);
			add(btnSelectPath);
			
			JLabel lblRubro = new JLabel("Rubro");
			lblRubro.setBounds(219, 156, 77, 16);
			add(lblRubro);
			
			JLabel lblTipoDeTel = new JLabel("Tipo de Tel");
			lblTipoDeTel.setBounds(220, 121, 76, 16);
			add(lblTipoDeTel);
			
			JLabel lblTelefono = new JLabel("Telefono");
			lblTelefono.setBounds(220, 86, 76, 16);
			add(lblTelefono);
			
			telefono = new JTextField();
			telefono.setColumns(10);
			telefono.setBounds(308, 83, 116, 22);
			add(telefono);
			
			JLabel lblCodigoPostal = new JLabel("Codigo Postal");
			lblCodigoPostal.setBounds(219, 51, 77, 16);
			add(lblCodigoPostal);
			
			codigoPostal = new JTextField();
			codigoPostal.setColumns(10);
			codigoPostal.setBounds(308, 48, 116, 22);
			add(codigoPostal);
			
			numero = new JTextField();
			numero.setColumns(10);
			numero.setBounds(308, 13, 116, 22);
			add(numero);
			
			JLabel lblNumero = new JLabel("Numero");
			lblNumero.setBounds(220, 16, 76, 16);
			add(lblNumero);
			
			JComboBox tipoDoc = new JComboBox(DocumentType.values());
			tipoDoc.setBounds(94, 153, 116, 22);
			add(tipoDoc);
			
			JTextField pais = new JTextField();
			pais.setColumns(10);
			pais.setBounds(94, 223, 116, 22);
			add(pais);
			
			JTextField provincia = new JTextField();
			provincia.setColumns(10);
			provincia.setBounds(94, 258, 116, 22);
			add(provincia);
			
			JTextField localidad = new JTextField();
			localidad.setColumns(10);
			localidad.setBounds(94, 293, 116, 22);
			add(localidad);
			
			JComboBox telType = new JComboBox(PhoneType.values());
			telType.setBounds(308, 118, 116, 22);
			add(telType);
			
			JTextField rubro = new JTextField();
			rubro.setColumns(10);
			rubro.setBounds(308, 153, 116, 22);
			add(rubro);
			
			rubro.setEditable(false);
			rubro.setEnabled(false);
			textField_14.setEditable(false);
			textField_14.setEnabled(false);
			btnSelectPath.setEnabled(false);
			
			rdbtnNewRadioButton.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						rubro.setEditable(true);
						rubro.setEnabled(true);
						textField_14.setEditable(true);
						textField_14.setEnabled(true);
						btnSelectPath.setEnabled(true);
					}
					else if (e.getStateChange() == ItemEvent.DESELECTED) {
						rubro.setText("");
						rubro.setEditable(false);
						rubro.setEnabled(false);
						textField_14.setText("");
						textField_14.setEditable(false);
						textField_14.setEnabled(false);
						btnSelectPath.setEnabled(false);
					}
				}
			});
			
			btnSelectPath.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					    JFileChooser chooser = new JFileChooser();
					    FileNameExtensionFilter filter = new FileNameExtensionFilter(
					        "JPG & PNG Images", "jpg", "pgn");
					    chooser.setFileFilter(filter);
					    int returnVal = chooser.showOpenDialog(frame);
					    if(returnVal == JFileChooser.APPROVE_OPTION) {
					    	textField_14.setText(chooser.getSelectedFile().getName());
					    }
				  }
			});
			
			if(user != null) {
				Email.setText(user.geteMail());
				password.setText(user.getPassword());
				nombre.setText(user.getName());
				apellido.setText(user.getSurname());
				ndoc.setText(user.getDocNumber());
				tipoDoc.setSelectedItem(user.getDocType());
				calle.setText(user.getStreet());
				telefono.setText(user.getTelephone());
				codigoPostal.setText(user.getZipCode());
				numero.setText(user.getNumber().toString());
				pais.setText(user.getCountry());
				provincia.setText(user.getProvince());
				localidad.setText(user.getCity());
				
				if (user instanceof Seller) {
					rdbtnNewRadioButton.setSelected(true);
					rubro.setText(((Seller)user).getCategory());
				}
				
				Email.setEditable(false);
				Email.setEnabled(false);
				password.setEditable(false);
				password.setEnabled(false);
				ndoc.setEditable(false);
				ndoc.setEnabled(false);
				tipoDoc.setEnabled(false);
				rdbtnNewRadioButton.setEnabled(false);
				
				btnNewButton.setText("Modify");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						frame.setVisible(false); //you can't see me!
						frame.dispose(); //Destroy the JFrame object
						//update db
					}
				});
			}
			else {
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						frame.setVisible(false); //you can't see me!
						frame.dispose(); //Destroy the JFrame object
						//update db
					}
				});
			}
		}
	}
}

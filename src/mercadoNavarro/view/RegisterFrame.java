package mercadoNavarro.view;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.model.Item;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class RegisterFrame {

	JFrame frame;

	/**
	 * Create the application.
	 */
	public RegisterFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);

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
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false); //you can't see me!
					frame.dispose(); //Destroy the JFrame object
					LoginFrame window = new LoginFrame();
					window.frame.setVisible(true);
				}
			});
			btnNewButton.setBounds(165, 393, 97, 25);
			add(btnNewButton);
			
			JRadioButton rdbtnNewRadioButton = new JRadioButton("Proveedor");
			rdbtnNewRadioButton.setBounds(165, 359, 127, 25);
			add(rdbtnNewRadioButton);
			
			JLabel lblNewLabel = new JLabel("E-Mail");
			lblNewLabel.setBounds(47, 16, 35, 16);
			add(lblNewLabel);
			
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(27, 51, 55, 16);
			add(lblPassword);
			
			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(33, 86, 49, 16);
			add(lblNombre);
			
			JLabel lblApellido = new JLabel("Apellido");
			lblApellido.setBounds(33, 121, 49, 16);
			add(lblApellido);
			
			JLabel lblTipoDeDoc = new JLabel("Tipo de Doc");
			lblTipoDeDoc.setBounds(12, 156, 70, 16);
			add(lblTipoDeDoc);
			
			JLabel lblNdoc = new JLabel("N\u00B0Doc");
			lblNdoc.setBounds(42, 191, 40, 16);
			add(lblNdoc);
			
			JLabel lblPais = new JLabel("Pais");
			lblPais.setBounds(59, 226, 23, 16);
			add(lblPais);
			
			JLabel lblProvincia = new JLabel("Provincia");
			lblProvincia.setBounds(27, 261, 55, 16);
			add(lblProvincia);
			
			JLabel lblLocalidad = new JLabel("Localidad");
			lblLocalidad.setBounds(27, 296, 55, 16);
			add(lblLocalidad);
			
			JLabel lblCalle = new JLabel("Calle");
			lblCalle.setBounds(54, 331, 28, 16);
			add(lblCalle);
			
			JLabel lblFotoDePefil = new JLabel("Foto de Perfil");
			lblFotoDePefil.setBounds(219, 191, 77, 16);
			add(lblFotoDePefil);
			
			textField_14 = new JTextField();
			textField_14.setColumns(10);
			textField_14.setBounds(308, 188, 116, 22);
			add(textField_14);
			
			JLabel lblRubro = new JLabel("Rubro");
			lblRubro.setBounds(261, 156, 35, 16);
			add(lblRubro);
			
			JLabel lblTipoDeTel = new JLabel("Tipo de Tel");
			lblTipoDeTel.setBounds(231, 121, 65, 16);
			add(lblTipoDeTel);
			
			JLabel lblTelefono = new JLabel("Telefono");
			lblTelefono.setBounds(241, 86, 55, 16);
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
			lblNumero.setBounds(247, 16, 49, 16);
			add(lblNumero);
			
			JComboBox tipoDoc = new JComboBox();
			tipoDoc.setBounds(94, 153, 116, 22);
			add(tipoDoc);
			
			JComboBox pais = new JComboBox();
			pais.setBounds(94, 223, 116, 22);
			add(pais);
			
			JComboBox provincia = new JComboBox();
			provincia.setBounds(94, 258, 116, 22);
			add(provincia);
			
			JComboBox localidad = new JComboBox();
			localidad.setBounds(94, 293, 116, 22);
			add(localidad);
			
			JComboBox telType = new JComboBox();
			telType.setBounds(308, 118, 116, 22);
			add(telType);
			
			JComboBox rubro = new JComboBox();
			rubro.setBounds(308, 153, 116, 22);
			add(rubro);

		}
	}
}

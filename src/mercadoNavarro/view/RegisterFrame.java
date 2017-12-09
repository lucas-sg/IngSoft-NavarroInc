package mercadoNavarro.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import mercadoNavarro.db.DBDataFacade;
import mercadoNavarro.enums.DocumentType;
import mercadoNavarro.enums.PhoneType;
import mercadoNavarro.model.*;

public class RegisterFrame {

	JFrame frame;
	
	private User user;

	/**
	 * Create the application.
	 */
	public RegisterFrame() {
		Runnable action = new Runnable() {
			public void run() {
				try {
					initialize(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		ProgressDialog loading = new ProgressDialog(frame, action, "Loading...");
		loading.setLocationRelativeTo(frame);
		loading.setVisible(true);
	}
	
	public RegisterFrame(User user) {
		Runnable action = new Runnable() {
			public void run() {
				try {
					initialize(user);
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
	private void initialize(User user) {
		frame = new JFrame();
		frame.setBounds(200, 200, 650, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel basePane = new JPanel(new BorderLayout(0,0));
		frame.setContentPane(basePane);
		
		this.user = user;

		JPanel registerPane = new RegisterPane();
		basePane.add(registerPane);
	}

	public class RegisterPane extends JPanel {
		JTextField Email;
		JPasswordField password;
		JTextField nombre;
		JTextField apellido;
		JTextField ndoc;
		JTextField calle;
		JTextField provincia;
		JTextField rubro;
		JTextField pais;
		JTextField localidad;
		JTextField foto;
		JRadioButton rdbtnNewRadioButton;
		JFormattedTextField telefono;
		JTextField codigoPostal;
		JTextField numero;
		JLabel error;
		MaskFormatter telephoneMask;

		/**
		 * Create the panel.
		 */
		
		public RegisterPane() {
			setLayout(null);
			
			Email = new JTextField();
			Email.setBounds(94, 13, 116, 22);
			add(Email);
			Email.setColumns(10);

			error = new JLabel();
			error.setBounds(250, 230, 250, 22);
			error.setHorizontalAlignment(SwingConstants.LEFT);
			error.setForeground(Color.RED);
			add(error);

			password = new JPasswordField();
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
			
			rdbtnNewRadioButton = new JRadioButton("Seller");
			rdbtnNewRadioButton.setBounds(165, 359, 127, 25);
			add(rdbtnNewRadioButton);
			
			JLabel lblNewLabel = new JLabel("E-Mail");
			lblNewLabel.setBounds(10, 16, 72, 16);
			add(lblNewLabel);
			
			JLabel lblPassword = new JLabel("Password");
			lblPassword.setBounds(10, 51, 72, 16);
			add(lblPassword);
			
			JLabel lblNombre = new JLabel("Name");
			lblNombre.setBounds(10, 86, 72, 16);
			add(lblNombre);
			
			JLabel lblApellido = new JLabel("Surname");
			lblApellido.setBounds(10, 121, 72, 16);
			add(lblApellido);
			
			JLabel lblTipoDeDoc = new JLabel("ID Type");
			lblTipoDeDoc.setBounds(10, 156, 72, 16);
			add(lblTipoDeDoc);
			
			JLabel lblNdoc = new JLabel("ID Number");
			lblNdoc.setBounds(10, 191, 72, 16);
			add(lblNdoc);
			
			JLabel lblPais = new JLabel("Country");
			lblPais.setBounds(10, 226, 72, 16);
			add(lblPais);
			
			JLabel lblProvincia = new JLabel("Province");
			lblProvincia.setBounds(10, 261, 72, 16);
			add(lblProvincia);
			
			JLabel lblLocalidad = new JLabel("City");
			lblLocalidad.setBounds(10, 296, 72, 16);
			add(lblLocalidad);
			
			JLabel lblCalle = new JLabel("Street");
			lblCalle.setBounds(10, 331, 72, 16);
			add(lblCalle);
			
			JLabel lblFotoDePefil = new JLabel("Profile Picture");
			lblFotoDePefil.setBounds(220, 191, 100, 16);
			add(lblFotoDePefil);
			
			foto = new JTextField();
			foto.setColumns(10);
			foto.setBounds(320, 188, 116, 22);
			add(foto);
			
			JButton btnSelectPath = new JButton("Select File...");
			btnSelectPath.setBounds(446, 188, 116, 23);
			add(btnSelectPath);
			
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "JPG Images", "jpg");
		    chooser.setFileFilter(filter);
			btnSelectPath.addActionListener(new ActionListener() {
				  public void actionPerformed(ActionEvent e) {
					    int returnVal = chooser.showOpenDialog(frame);
					    if(returnVal == JFileChooser.APPROVE_OPTION) {
					    	foto.setText(chooser.getSelectedFile().getAbsolutePath());
					    }
				  }
			});
			
			JLabel lblRubro = new JLabel("Category");
			lblRubro.setBounds(220, 156, 77, 16);
			add(lblRubro);
			
			JLabel lblTipoDeTel = new JLabel("Telephone Type");
			lblTipoDeTel.setBounds(220, 121, 100, 16);
			add(lblTipoDeTel);
			
			JLabel lblTelefono = new JLabel("Telephone");
			lblTelefono.setBounds(220, 86, 76, 16);
			add(lblTelefono);
			
			JLabel lblCodigoPostal = new JLabel("Zip Code");
			lblCodigoPostal.setBounds(220, 51, 77, 16);
			add(lblCodigoPostal);
			
			codigoPostal = new JTextField();
			codigoPostal.setColumns(10);
			codigoPostal.setBounds(320, 48, 116, 22);
			add(codigoPostal);
			
			numero = new JTextField();
			numero.setColumns(10);
			numero.setBounds(320, 13, 116, 22);
			add(numero);
			
			JLabel lblNumero = new JLabel("Street Number");
			lblNumero.setBounds(220, 16, 100, 16);
			add(lblNumero);
			
			JComboBox tipoDoc = new JComboBox(DocumentType.values());
			tipoDoc.setBounds(94, 153, 116, 22);
			add(tipoDoc);
			
			pais = new JTextField();
			pais.setColumns(10);
			pais.setBounds(94, 223, 116, 22);
			add(pais);
			
			provincia = new JTextField();
			provincia.setColumns(10);
			provincia.setBounds(94, 258, 116, 22);
			add(provincia);
			
			localidad = new JTextField();
			localidad.setColumns(10);
			localidad.setBounds(94, 293, 116, 22);
			add(localidad);
			
			JComboBox telType = new JComboBox(PhoneType.values());
			telType.setBounds(320, 118, 116, 22);
			add(telType);

			try {
				telephoneMask = new MaskFormatter(((PhoneType)telType.getSelectedItem()).equals(PhoneType.MOBILE)? "##-####-####" : "####-####");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			telephoneMask.setPlaceholderCharacter('_');

			telType.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						telephoneMask.setMask(((PhoneType)telType.getSelectedItem()).equals(PhoneType.MOBILE)? "##-####-####" : "####-####");
						telephoneMask.install(telefono);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
			});

			telefono = new JFormattedTextField(telephoneMask);
			telefono.setColumns(10);
			telefono.setBounds(320, 83, 116, 22);
			add(telefono);

			rubro = new JTextField();
			rubro.setColumns(10);
			rubro.setBounds(320, 153, 116, 22);
			add(rubro);
			
			rubro.setEditable(false);
			rubro.setEnabled(false);
			foto.setEditable(false);
			btnSelectPath.setEnabled(false);
			
			rdbtnNewRadioButton.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						rubro.setEditable(true);
						rubro.setEnabled(true);
						btnSelectPath.setEnabled(true);
					}
					else if (e.getStateChange() == ItemEvent.DESELECTED) {
						rubro.setText("");
						rubro.setEditable(false);
						rubro.setEnabled(false);
						foto.setText("");
						btnSelectPath.setEnabled(false);
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
				btnSelectPath.setEnabled(false);
				
				btnNewButton.setText("Modify");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {					
						Runnable action = new Runnable() {
							public void run() {
								try {
									boolean dataValid = false;
									if(checkFields()) {
										String pass = new String(password.getPassword());
										user.setName(nombre.getText());
										user.setSurname(apellido.getText());
										user.setCountry(pais.getText());
										user.setProvince(provincia.getText());
										user.setCity(localidad.getText());
										user.setStreet(calle.getText());
										user.setNumber(Integer.parseInt(numero.getText()));
										user.setZipCode(codigoPostal.getText());
										user.setTelephone(telefono.getText());
										user.setTelephoneType(PhoneType.valueOf(telType.getSelectedItem().toString().toUpperCase()));
										if(rdbtnNewRadioButton.isSelected()) {
											((Seller) user).setCategory(rubro.getText());
											dataValid = DBDataFacade.modifySeller((Seller)user);
										} else
											dataValid = DBDataFacade.modifyUser(user);
										if(dataValid) {
											frame.setVisible(false); //you can't see me!
											frame.dispose(); //Destroy the JFrame object
										}
										else
											error.setText("Invalid data or connection error");
									}
									else
										error.setText("All fields are required");
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
			else {
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {						
						Runnable action = new Runnable() {
							public void run() {
								try {
									boolean dataValid = false;
									if(checkFields()) {
										String pass = new String(password.getPassword());
										if(rdbtnNewRadioButton.isSelected()) {
											Seller seller = new Seller(nombre.getText(),pass,apellido.getText(), Email.getText(), pais.getText(), provincia.getText(), localidad.getText()
											, calle.getText(), Integer.parseInt(numero.getText()), codigoPostal.getText(), telefono.getText(), ndoc.getText(),
													PhoneType.valueOf(telType.getSelectedItem().toString().toUpperCase()), DocumentType.valueOf(tipoDoc.getSelectedItem().toString().toUpperCase()));
											seller.setPhoto(chooser.getSelectedFile().getAbsolutePath());
											dataValid = DBDataFacade.addSeller(seller);
										}
										else {
											Buyer buyer = new Buyer(nombre.getText(), pass, apellido.getText(), Email.getText(), pais.getText(), provincia.getText(), localidad.getText()
													, calle.getText(), Integer.parseInt(numero.getText()), codigoPostal.getText(), telefono.getText(), ndoc.getText(),
													PhoneType.valueOf(telType.getSelectedItem().toString().toUpperCase()), DocumentType.valueOf(tipoDoc.getSelectedItem().toString().toUpperCase()));
											dataValid = DBDataFacade.addUser(buyer);
										}
										if(dataValid) {
											frame.setVisible(false); //you can't see me!
											frame.dispose(); //Destroy the JFrame object
										}
										else
											error.setText("Invalid data or connection error");
									}
									else
										error.setText("All fields are required");
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
		}

		public boolean checkFields() {
			String pass = new String(password.getPassword());
			if(Email.getText().trim().equals("") || pass.trim().equals("") || nombre.getText().trim().equals("") || apellido.getText().trim().equals("") ||
					ndoc.getText().trim().equals("") || calle.getText().trim().equals("") || provincia.getText().trim().equals("") || pais.getText().trim().equals("") ||
					localidad.getText().trim().equals("") || telefono.getText().trim().equals("") || codigoPostal.getText().trim().equals("") || numero.getText().trim().equals(""))
				return false;
			if(rdbtnNewRadioButton.isSelected() && (rubro.getText().trim().equals("") || foto.getText().trim().equals("")))
				return false;
			return true;
		}
	}
}

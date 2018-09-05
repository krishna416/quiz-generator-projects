import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class Login {
	JTextField username;
	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 712, 537);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("USERNAME");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBounds(40, 126, 175, 36);
		frame.getContentPane().add(lblNewLabel);
		
		username = new JTextField();
		username.setBounds(40, 170, 175, 27);
		frame.getContentPane().add(	username);
		username.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("PASSWORD");
		lblNewLabel_1.setBounds(41, 210, 123, 22);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String uname=username.getText();
				String pass=passwordField.getText();
				if(uname.equals("krishna")&&pass.equals("nvnjrnsnvssmksp")){
					JOptionPane.showMessageDialog(frame,"sucessfully loggined" );
					frame.dispose();
					 Main sub=new Main();
					sub.setVisible(true);
				}else{
					JOptionPane.showMessageDialog(frame,"invalid login credentials" );
				}
			}
		});
		btnNewButton.setBounds(241, 354, 97, 25);
		frame.getContentPane().add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(40, 242, 175, 27);
		frame.getContentPane().add(passwordField);
	}
}

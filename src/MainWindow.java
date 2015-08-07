import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class MainWindow {

	private JFrame frame;
	private JLabel lblMentalStateTypes;
	private JLabel lblNewMentalState;
	private JTextField txtNewMentalState;
	private JComboBox cmbMentalStateTypes;
	private JButton btnAdd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] strMentalStates = { " " , "Beilef", "Intention", "Motive", "Goal", "Emotion" };
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][grow]"));
		
		lblMentalStateTypes = new JLabel("Mental State Types:");
		lblNewMentalState   = new JLabel("New Mental State:");
		
		cmbMentalStateTypes = new JComboBox(strMentalStates);
		cmbMentalStateTypes.setSelectedIndex(0);
		cmbMentalStateTypes.setPreferredSize(new Dimension(10,25));
		cmbMentalStateTypes.addActionListener((ActionListener) this);
		
		txtNewMentalState = new JTextField();
		txtNewMentalState.setColumns(10);
		
		btnAdd = new JButton("Add");
		
		frame.getContentPane().add(lblMentalStateTypes, "cell 0 0");
		frame.getContentPane().add(cmbMentalStateTypes, "cell 1 0,width 120!");
		frame.getContentPane().add(lblNewMentalState, "cell 0 1");
		frame.getContentPane().add(txtNewMentalState, "cell 1 1,growx");
		frame.getContentPane().add(btnAdd, "cell 1 2,align right,width 80!");
	}

}

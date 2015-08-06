import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;


public class MainWindow {

	private JFrame frame;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JComboBox cmbMentalStates = new JComboBox(strMentalStates);
		cmbMentalStates.setSelectedIndex(0);
		cmbMentalStates.setPreferredSize(new Dimension(10,25));
		cmbMentalStates.addActionListener((ActionListener) this);
		frame.getContentPane().add(cmbMentalStates, BorderLayout.NORTH);
	}

}

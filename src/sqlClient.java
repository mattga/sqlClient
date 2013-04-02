import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class sqlClient extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JComboBox tables;
	public static QueryTable qt;
	public static QueryTableModel qtm;
	public static JLabel statusBar;
	public static QueryMenuBar qmb;
	public static JComboBox<String> jcb;
	private JPanel controls;
	
	public sqlClient() {
		qt = new QueryTable();
		
		jcb = new JComboBox<String>(new DefaultComboBoxModel<String>(ProgramState.tablesModel));
		jcb.setPreferredSize(new Dimension(300,25));
		jcb.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				qtm.executeQuery("SELECT * FROM " + (String)jcb.getSelectedItem());
			}
		});
		
		controls = new JPanel(new FlowLayout());
		controls.add(jcb, FlowLayout.LEFT);
		
		statusBar = new JLabel("Ready");
		statusBar.setSize(100, 16);
		
		qmb = new QueryMenuBar();
		
		this.addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				//write to log file
			}
			public void windowClosed(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowOpened(WindowEvent e) {}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024, 600);
		this.add(controls, BorderLayout.NORTH);
		this.add(new JScrollPane(qt), BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.SOUTH);
		this.setJMenuBar(qmb);
	}
}

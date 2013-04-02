import javax.swing.JFrame;
//import javax.swing.JComboBox;
//import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class SelectView extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel text;
	
	public SelectView() {
		text = new JLabel("");
		text.setVisible(true);
		
		this.setTitle("Select Query");
	}
}

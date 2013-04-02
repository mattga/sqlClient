import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Box;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryMenuBar extends JMenuBar{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu connectMenu, queryMenu, fileMenu, helpMenu, currentUser;
	private JMenuItem changeUser, connect, disconnect, newQuery, insert, delete, update, about, refresh, select, database;
	
	public QueryMenuBar () {
		fileMenu = new JMenu("File");
		connectMenu = new JMenu("Connect");
		queryMenu = new JMenu("Query");
		helpMenu = new JMenu("Help");
		currentUser = new JMenu("No User");
		
		currentUser.setEnabled(false);
		
		changeUser = new JMenuItem("Change User");
		
		connect = new JMenuItem("Connect to...");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				Driver.connectView.setVisible(true);
			}
		});
		
		disconnect = new JMenuItem("Disconnect");
		
		newQuery = new JMenuItem("New Query");
		
		insert = new JMenuItem("Insert");
		
		delete = new JMenuItem("Delete");
		
		update = new JMenuItem("Update");
		
		about = new JMenuItem("About");
		
		refresh = new JMenuItem("Refresh");
		
		select = new JMenuItem("Select");
		
		database = new JMenuItem("Change Database");
		
		connectMenu.add(connect);
		connectMenu.add(disconnect);
		connectMenu.addSeparator();
		connectMenu.add(refresh);
		connectMenu.add(database);
		connectMenu.add(changeUser);
		
		queryMenu.add(newQuery);
		queryMenu.add(select);
		queryMenu.add(insert);
		queryMenu.add(update);
		queryMenu.add(delete);
		
		helpMenu.add(about);
		
		this.add(fileMenu);
		this.add(connectMenu);
		this.add(queryMenu);
		this.add(helpMenu);
		this.add(Box.createHorizontalGlue());
		this.add(currentUser);
	}
	
	public void updateUser() {
		currentUser.setText(ProgramState.user + "@" + ProgramState.host);
	}
}

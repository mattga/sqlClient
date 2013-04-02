import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import ch.rakudave.suggest.JSuggestField;

public class ConnectView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField hostField, userField;
	private JPasswordField passwordField;
	private JLabel hostLabel, userLabel, passwordLabel, databaseLabel, statusBar;
	private JSuggestField databaseField;
	private JButton connect, cancel;
	private JPanel hostPanel, userPanel, passwordPanel, buttonPanel, databasePanel;
	
	public ConnectView () {
		hostField = new JTextField("localhost", 10);
		userField = new JTextField("matt", 10);
		passwordField = new JPasswordField(10);
		hostLabel = new JLabel("Enter SQL Host");
		userLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		databaseLabel = new JLabel("Select Database");
		databaseField = new JSuggestField(this, ProgramState.databasesModel);
		connect = new JButton("Connect");
		cancel = new JButton("Cancel");
		statusBar = new JLabel("Ready");
		
		passwordField.setFocusable(true);
		passwordField.addFocusListener(new DynamicConnect());
		
		connect.addActionListener(new ConnectAction());
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				ConnectView.this.setVisible(false);
				statusBar.setText("Ready");
			}
		});
		
		statusBar.setSize(new Dimension(100, 10));
		statusBar.setFocusable(false);
		
		hostPanel = new JPanel(new GridLayout(1,2));
		hostPanel.add(hostLabel);
		hostPanel.add(hostField);
		hostPanel.setBorder(new EtchedBorder());
		
		userPanel = new JPanel(new GridLayout(1,2));
		userPanel.add(userLabel);
		userPanel.add(userField);
		userPanel.setBorder(new EtchedBorder());
		
		passwordPanel = new JPanel(new GridLayout(1,2));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		passwordPanel.setBorder(new EtchedBorder());
		
		databasePanel = new JPanel(new GridLayout(1,2));
		databasePanel.add(databaseLabel);
		databasePanel.add(databaseField);
		databasePanel.setBorder(new EtchedBorder());
		
		buttonPanel = new JPanel();
		buttonPanel.add(connect, BorderLayout.CENTER);
		buttonPanel.add(cancel, BorderLayout.CENTER);
		
		Container c = new Container();
		c.setLayout(new GridLayout(5,1));
		c.add(hostPanel);
		c.add(userPanel);
		c.add(passwordPanel);
		c.add(databasePanel);
		c.add(buttonPanel);
		this.add(statusBar, BorderLayout.SOUTH);
		
		this.add(c);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(300,200);
		this.setResizable(false);
		this.setTitle("Connect to SQL Server");
		this.getRootPane().setDefaultButton(connect);
	}

	public class DynamicConnect implements FocusListener {
		private String prevURL;
		
		public void focusGained(FocusEvent e) {
			prevURL = ProgramState.getURL();
		}
		public void focusLost(FocusEvent e) {
			ProgramState.host = hostField.getText();
			ProgramState.user = userField.getText();
			ProgramState.password = String.copyValueOf(passwordField.getPassword());
			if ( !prevURL.equals(ProgramState.getURL()) ) {
				try {
					Class.forName(ProgramState.jdbcDriver);
					Connection con = DriverManager.getConnection(ProgramState.getURL());
					statusBar.setText("Success");
					Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ResultSet databases = statement.executeQuery("SHOW DATABASES");
					ProgramState.databasesModel.removeAllElements();
					for(databases.first(); !databases.isLast(); databases.next())
						ProgramState.databasesModel.add(databases.getString(1));
				} catch (Exception ex) {
					statusBar.setText("Failed:" + ex.getMessage());
					statusBar.setToolTipText("Failed:" + ex.getMessage());
				}
			}
		}
	}
	
	public class ConnectAction implements ActionListener {
		public void actionPerformed (ActionEvent e) {
			try {
				ProgramState.host = hostField.getText();
				ProgramState.user = userField.getText();
				ProgramState.password = String.copyValueOf(passwordField.getPassword());
				ProgramState.database = databaseField.getText();
				sqlClient.qtm = new QueryTableModel(ProgramState.getURL());
				sqlClient.qt.setModel(sqlClient.qtm);
				
				Class.forName(ProgramState.jdbcDriver);
				Connection con = DriverManager.getConnection(ProgramState.getURL());
				Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet tables = statement.executeQuery("SHOW TABLES");
				for(tables.first(); !tables.isLast(); tables.next())
					ProgramState.tablesModel.add(tables.getString(1));
				
				statusBar.setText("Ready");
				sqlClient.statusBar.setText("Connected");
				sqlClient.qmb.updateUser();
				ConnectView.this.setVisible(false);
			} catch (Exception ex) {
				statusBar.setText("Failed:" + ex.getMessage());
				statusBar.setToolTipText("Failed:" + ex.getMessage());
			}
		}
	}


}
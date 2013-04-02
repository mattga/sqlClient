import javax.swing.table.AbstractTableModel;
import javax.naming.CommunicationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

public class QueryTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public QueryTableModel (String url) throws SQLException, CommunicationException, ClassNotFoundException{
		Class.forName(ProgramState.jdbcDriver);
		connection = DriverManager.getConnection(url);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rs = statement.executeQuery("SHOW TABLES");
		rsmd = rs.getMetaData();
	}

	public void executeQuery(String query) {
		try {
			rs = statement.executeQuery(query);
			rsmd = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.fireTableDataChanged();
	}
	
	public boolean execute(String query) {
		try {
			return statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.fireTableDataChanged();
		return false;
	}
	
	public int executeUpdate(String query) {
		try {
			return statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.fireTableDataChanged();
		return -1;
	}
	
	public int getColumnCount(){
		try {
			return rsmd.getColumnCount();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	
	// columnIndex for 0...N columns
	public String getColumnName(int columnIndex) {
		try {
			return rsmd.getColumnName(columnIndex+1);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return "NONAME";
	}

	public int getRowCount() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	// rowIndex/columnIndex for 0...N rows/columns
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			rs.absolute(rowIndex+1);
			return rs.getObject(columnIndex+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

import java.util.Vector;

public class ProgramState {
	public static String host = "", user = "", password = "", database = "";
	public static String jdbcDriver = "com.mysql.jdbc.Driver";
	public static Vector<String> tablesModel = new Vector<String>();
	public static Vector<String> databasesModel = new Vector<String>();
	
	public static String getURL() {
		return "jdbc:mysql://" + host + "/" + database + "?user=" + user + "&password=" + password;
	}
}

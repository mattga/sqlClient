public class Driver {
	public static ConnectView connectView;
	
	public static void main (String [] args) {
		sqlClient application = new sqlClient();
		application.setVisible(true);
		
		connectView = new ConnectView();
		connectView.setVisible(true);
	}
}

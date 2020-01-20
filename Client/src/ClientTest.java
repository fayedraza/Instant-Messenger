import java.io.IOException;

import javax.swing.JFrame;

public class ClientTest {
	public static void main(String[]args) throws IOException, ClassNotFoundException {
         Client test;
         test = new Client("127.0.0.1");
         test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     	 test.startRunning();
}
}
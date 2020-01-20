import java.io.IOException;

import javax.swing.JFrame;
public class SeverTest {
public static void main(String[]args) throws IOException, ClassNotFoundException {
	Server test = new Server();
	test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	test.startRunning();
}
}

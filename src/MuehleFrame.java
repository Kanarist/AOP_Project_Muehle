import java.awt.Color;

import javax.swing.JFrame;

public class MuehleFrame extends JFrame{

	public MuehleFrame() {
		setTitle("Mühle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setResizable(false);
		setSize(800, 800);
		getContentPane().setBackground(Color.lightGray);
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);

		add(new DrawBoard());

		setVisible(true);
		
		
		
	}
}

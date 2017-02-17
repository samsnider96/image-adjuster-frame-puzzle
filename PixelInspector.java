package a7;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelInspector {
	public static void main(String[] args) throws IOException {
		Picture p = A7Helper.readFromURL("https://garysking.files.wordpress.com/2016/01/tas2.jpg");
		PixelInspectorWidget inspector_Widget = new PixelInspectorWidget(p, "Pixel Inspector App");

		JFrame main_frame = new JFrame();
		main_frame.setTitle("Assignment 7 Pixel Inspector");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(inspector_Widget, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}

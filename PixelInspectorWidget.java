package a7;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PixelInspectorWidget extends JPanel implements MouseListener{

	private PictureView picture_view;
	private Picture pic1;
	private JLabel pixel_Info_X;
	private JLabel pixel_Info_Y;
	private JLabel pixel_Info_Red;
	private JLabel pixel_Info_Green;
	private JLabel pixel_Info_Blue;
	private JLabel pixel_Info_Brightness;


	public PixelInspectorWidget(Picture picture, String title) {
		setLayout(new BorderLayout());

		pic1 = picture;

		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);

		JLabel title_label = new JLabel(title, SwingConstants.CENTER);
		add(title_label, BorderLayout.NORTH);

		pixel_Info_X = new JLabel("X: ");
		pixel_Info_Y = new JLabel("Y: " );
		pixel_Info_Red = new JLabel("Red: " );
		pixel_Info_Green = new JLabel("Green: " );
		pixel_Info_Blue = new JLabel("Blue: " );
		pixel_Info_Brightness = new JLabel("Brightness: " );


		JPanel info_Panel = new JPanel();
		info_Panel.setLayout(new BoxLayout(info_Panel, BoxLayout.Y_AXIS));
		info_Panel.add(pixel_Info_X);
		info_Panel.add(pixel_Info_Y);
		info_Panel.add(pixel_Info_Red);
		info_Panel.add(pixel_Info_Green);
		info_Panel.add(pixel_Info_Blue);
		info_Panel.add(pixel_Info_Brightness);
		
		
		add(info_Panel, BorderLayout.WEST);
		add(picture_view, BorderLayout.EAST);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		pixel_Info_X.setText("X: " + e.getX());
		pixel_Info_Y.setText("Y: " + e.getY());
		pixel_Info_Red.setText("Red: " + 
								pic1.getPixel( e.getX(), e.getY() ).getRed());
		pixel_Info_Green.setText("Green: " + 
								pic1.getPixel( e.getX(), e.getY() ).getGreen());
		pixel_Info_Blue.setText("Blue: " + 
								pic1.getPixel( e.getX(), e.getY() ).getBlue());
		pixel_Info_Brightness.setText("Brightness: " + 
								pic1.getPixel( e.getX(), e.getY() ).getIntensity());
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
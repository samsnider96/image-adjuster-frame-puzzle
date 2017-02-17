package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterWidget extends JPanel implements ChangeListener{

	private PictureView picture_View;
	private JSlider blurSlider, satSlider, brightSlider;
	private JLabel blurLabel, satLabel, brightLabel;
	private Picture source, copiedSource, blurPicture, satPicture, brightPicture;
	private double red2 = 0, green2 = 0, blue2 = 0;
	
	public ImageAdjusterWidget(Picture picture) {
		setLayout(new BorderLayout());
				
		blurLabel = new JLabel("Blur: ");
		satLabel = new JLabel("Saturation: ");
		brightLabel = new JLabel("Brightness: ");
												// create and format JSliders, add them to a Jpanel
		blurSlider = new JSlider(0, 5, 0);
			blurSlider.setMajorTickSpacing(1);
			blurSlider.setLabelTable(blurSlider.createStandardLabels(1));
			blurSlider.setPaintLabels(true);
			blurSlider.setPaintTicks(true);
			blurSlider.setSnapToTicks(true);
			blurSlider.addChangeListener(this);
			
		satSlider = new JSlider(-100, 100);
			satSlider.setMajorTickSpacing(25);
			satSlider.setLabelTable(blurSlider.createStandardLabels(25));
			satSlider.setPaintLabels(true);
			satSlider.setPaintTicks(true);
			satSlider.addChangeListener(this);
			
		brightSlider = new JSlider(-100, 100);
			brightSlider.setMajorTickSpacing(25);
			brightSlider.setLabelTable(blurSlider.createStandardLabels(25));
			brightSlider.setPaintLabels(true);
			brightSlider.setPaintTicks(true);
			brightSlider.addChangeListener(this);
			
		JPanel sliderPanel = new JPanel();
			sliderPanel.setLayout(new GridLayout(3,2));
			sliderPanel.add(blurLabel);
			sliderPanel.add(blurSlider);
			sliderPanel.add(satLabel);
			sliderPanel.add(satSlider);
			sliderPanel.add(brightLabel);
			sliderPanel.add(brightSlider);
			
			
		picture_View = new PictureView(picture.createObservable());
		
		add(picture_View, BorderLayout.CENTER);
		add(sliderPanel, BorderLayout.SOUTH);
		
											// create pictures we'll be working with
		source = new PictureImpl(picture.getWidth(), picture.getHeight());
		copiedSource = new PictureImpl(picture.getWidth(), picture.getHeight());
		blurPicture = new PictureImpl(source.getWidth(), source.getHeight());
		satPicture = new PictureImpl(picture.getWidth(), picture.getHeight());
		brightPicture = new PictureImpl(source.getWidth(), source.getHeight());
		
		
		for(int i = 0; i < picture.getWidth(); i++){
			for(int j = 0; j < picture.getHeight(); j++){
				source.setPixel(i,  j, picture.getPixel(i, j));;
				copiedSource.setPixel(i,  j, picture.getPixel(i,j));
			}
		}
	}
	
	private void blur(Picture picture, int sliderValue) {
		
		double bRed = 0, 
			   bGreen = 0, 
			   bBlue = 0;
		
		for(int i = 0; i < picture.getWidth(); i++){
			for(int j = 0; j < picture.getHeight(); j++){
				
				if (sliderValue == 0){
					blurPicture.setPixel(i, j, picture.getPixel(i, j));
					copiedSource.setPixel(i, j, picture.getPixel(i, j));
				}
				else{
					double totalRed = 0, 
						   totalGreen = 0, 
						   totalBlue = 0, 
						   numberOfPixels = 0;
					
					bRed = picture.getPixel(i, j).getRed();
					bGreen = picture.getPixel(i, j).getGreen();
					bBlue = picture.getPixel(i, j).getBlue();
					
					int left = 0,
						right = 0,
						top = 0,
						bottom = 0;
					
					if(i + sliderValue >= picture.getWidth() && j + sliderValue >= picture.getHeight()){
						right = picture.getWidth() - 1;
						bottom = picture.getHeight() - 1;
					} 
						else if(i + sliderValue >= picture.getWidth()){
							right = picture.getWidth() - 1;
							bottom = j + sliderValue;
						} 
						else if(j + sliderValue >= picture.getHeight()){
							right = i + sliderValue;
							bottom = picture.getHeight() -1;
						} 
						else{
							right = i + sliderValue;
							bottom = j + sliderValue;
						}
					if (i - sliderValue < 0 && j - sliderValue < 0){
						left = 0;
						top = 0;		
					}
						else if (i - sliderValue < 0){
						left = 0;
						top = j - sliderValue;
						}
						else if (j - sliderValue < 0){
							left = i - sliderValue;
							top = 0;
						}
						else{
							left = i - sliderValue;
							top = j - sliderValue;
						}
					
					SubPicture aroundPixels = picture.extract(new Coordinate(left, top), new Coordinate(right, bottom));
					
					for (int t = 0; t < aroundPixels.getWidth(); t++){
						for (int h = 0; h < aroundPixels.getHeight(); h++){
							
							totalRed += aroundPixels.getPixel(t, h).getRed();
							totalGreen+= aroundPixels.getPixel(t, h).getGreen();
							totalBlue += aroundPixels.getPixel(t, h).getBlue();
							numberOfPixels++;
						}
					}
					
					red2 = (totalRed - bRed) / (numberOfPixels - 1);
					green2 = (totalGreen - bGreen) / (numberOfPixels - 1);
					blue2 = (totalBlue - bBlue) / (numberOfPixels - 1);
					
					blurPicture.setPixel(i,  j, new ColorPixel(red2, green2, blue2));
					copiedSource.setPixel(i, j, new ColorPixel(red2, green2, blue2));
				}
			}	
		}
	}
	
	private void brightness(Picture picture, int sliderValue){

		double bRed = 0, 
			   bGreen = 0, 
			   bBlue = 0;

		for (int i = 0; i < picture.getWidth(); i++){
			for (int j = 0; j < picture.getHeight(); j++){

				bRed = picture.getPixel(i, j).getRed();
				bGreen = picture.getPixel(i, j).getGreen();
				bBlue = picture.getPixel(i, j).getBlue();

				if (sliderValue == 0){
					brightPicture.setPixel(i, j, new ColorPixel(bRed, bGreen, bBlue));
				}
				else if (sliderValue == 100){
					brightPicture.setPixel(i, j, 
							ColorPixel.lighten(picture.getPixel(i, j), brightSlider.getValue()));
				}
				else if (sliderValue == -100){
					brightPicture.setPixel(i, j, new ColorPixel(0, 0, 0));
				}
				else{
					if (sliderValue < 0){
						brightPicture.setPixel(i, j,
								ColorPixel.darken(picture.getPixel(i, j), brightSlider.getValue()));
					}
					else{
						brightPicture.setPixel(i, j, 
								ColorPixel.lighten(picture.getPixel(i, j), brightSlider.getValue()));
					}
				}
				copiedSource.setPixel(i, j, brightPicture.getPixel(i, j));
			}
		}
	}

	private void saturation(Picture picture, int sliderValue){
		
		double sRed,
			   sGreen,
			   sBlue;
		
		for (int i = 0; i < picture.getWidth(); i++){
			for (int j = 0; j < picture.getHeight(); j++){
								
				sRed = picture.getPixel(i, j).getRed();
				sGreen = picture.getPixel(i, j).getGreen();
				sBlue = picture.getPixel(i, j).getBlue();
				
				double intensity = picture.getPixel(i, j).getIntensity();

				if (satSlider.getValue() < 0.0 && satSlider.getValue() >= -100){
					
					red2 = sRed * (1.0 + (sliderValue / 100.0)) - (intensity * sliderValue / 100.0);
					green2 = sGreen * (1 + (sliderValue / 100.0)) - (intensity * sliderValue / 100.0);
					blue2 = sBlue * (1 + (sliderValue / 100.0)) - (intensity * sliderValue / 100.0);
				}
				else if (satSlider.getValue() > 0 && satSlider.getValue() <= 100){
					
					double highestColorVal = Math.max(sRed, Math.max(sGreen, sBlue));
					
					if (sRed == 0.0 && sGreen == 0.0 && sBlue == 0.0){
						red2 = 0.0;
						green2 = 0.0;
						blue2 = 0.0;
					}
					else{
						red2 = sRed * ((highestColorVal + ((1.0 - highestColorVal)
								*(sliderValue / 100.0))) / highestColorVal);
						green2 = sGreen * ((highestColorVal + ((1.0 - highestColorVal)
								*(sliderValue / 100.0))) / highestColorVal);
						blue2 = sBlue * ((highestColorVal + ((1.0 - highestColorVal)
								*(sliderValue / 100.0))) / highestColorVal);
					}
				}
				else if (satSlider.getValue() == 0){
					red2 = picture.getPixel(i, j).getRed();
					green2 = picture.getPixel(i, j).getGreen();
					blue2 = picture.getPixel(i, j).getBlue();
				}
				satPicture.setPixel(i, j, new ColorPixel(red2, green2, blue2));
				copiedSource.setPixel(i, j, satPicture.getPixel(i, j));
			}
		}
	}

private void pictureSet(){
	for (int i = 0; i < source.getWidth(); i++){
		for (int j = 0; j < source.getHeight(); j++){
			picture_View.getPicture().setPixel(i, j, copiedSource.getPixel(i, j));
		}
	}
}

@Override
public void stateChanged(ChangeEvent e) {

	blur(source, blurSlider.getValue());
	brightness(blurPicture, brightSlider.getValue());
	saturation(brightPicture, satSlider.getValue());
	
	pictureSet();
}
}

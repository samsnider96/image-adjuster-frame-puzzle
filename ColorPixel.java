package a7;

public class ColorPixel implements Pixel {

	private double red;
	private double green;
	private double blue;
	
	private static final double RED_INTENSITY_FACTOR = 0.299;
	private static final double GREEN_INTENSITY_FACTOR = 0.587;
	private static final double BLUE_INTENSITY_FACTOR = 0.114;

	private static final char[] PIXEL_CHAR_MAP = {'#', 'M', 'X', 'D', '<', '>', 's', ':', '-', ' ', ' '};
	
	public ColorPixel(double r, double g, double b) {
		if (r > 1.0 || r < 0.0) {
			throw new IllegalArgumentException("Red out of bounds");
		}
		if (g > 1.0 || g < 0.0) {
			throw new IllegalArgumentException("Green out of bounds");
		}
		if (b > 1.0 || b < 0.0) {
			throw new IllegalArgumentException("Blue out of bounds");
		}
		red = r;
		green = g;
		blue = b;
	}
	
	public static Pixel lighten(Pixel pixel, double factor){
		
		double lightenRed = (factor / 100) + pixel.getRed() * (1 - (factor / 100));
		double lightenGreen = (factor / 100) + pixel.getGreen() * (1 - (factor / 100));
		double lightenBlue = (factor / 100) + pixel.getBlue() * (1 - (factor / 100));
		
		return new ColorPixel(lightenRed, lightenGreen, lightenBlue);
	}

	public static Pixel darken(Pixel pixel, double factor){
		
		double darkenRed = pixel.getRed() * (1 - Math.abs(factor / 100));
		double darkenGreen = pixel.getGreen() * (1 - Math.abs(factor / 100));
		double darkenBlue = pixel.getBlue() * (1 - Math.abs(factor / 100));
		
		return new ColorPixel(darkenRed, darkenGreen, darkenBlue);
	}
	
	@Override
	public double getRed() {
		return red;
	}

	@Override
	public double getBlue() {
		return blue;
	}

	@Override
	public double getGreen() {
		return green;
	}

	@Override
	public double getIntensity() {
		return RED_INTENSITY_FACTOR*getRed() + 
				GREEN_INTENSITY_FACTOR*getGreen() + 
				BLUE_INTENSITY_FACTOR*getBlue();
	}
	
	@Override
	public char getChar() {
		int char_idx = (int) (getIntensity()*10.0);
		return PIXEL_CHAR_MAP[char_idx];
	}
	
//	public Pixel lighten(Pixel pixel, double factor){
//		
//		Pixel p = new ColorPixel(1.0, 1.0, 1.0);
//		
//		Pixel l = new ColorPixel((this.getRed()*(1.0-factor)+p.getRed()*factor),
//									(this.getGreen()*(1.0-factor)+p.getGreen()*factor),
//										(this.getBlue()*(1.0-factor)+p.getBlue()*factor));
//
//	return l;
//	}
//
//	public Pixel darken(Pixel pixel, double factor){
//		
//		Pixel p = new ColorPixel(0.0, 0.0, 0.0);
//		
//		Pixel d = new ColorPixel ((this.getRed()*(1.0-factor)+p.getRed()*factor),
//									(this.getGreen()*(1.0-factor)+p.getGreen()*factor),
//										(this.getBlue()*(1.0-factor)+p.getBlue()*factor));
//
//	return d;
//	}
}

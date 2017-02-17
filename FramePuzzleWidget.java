package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class FramePuzzleWidget extends JPanel implements MouseListener, KeyListener{

	private PictureView smallPicArray[][];
	private int blankX = 4;
	private int blankY = 4;
	private Picture pic;
	private JPanel game = new JPanel();

	public FramePuzzleWidget(Picture p) {

		pic = p; 

		game.addKeyListener(this);
		game.setFocusable(true);
		game.requestFocus();

		game.setLayout(new GridLayout(5, 5));

		smallPicArray = new PictureView[5][5];
		int smallWidth = p.getWidth() / 5;
		int smallHeight = p.getHeight() / 5;

										//populates pictureView Array with picture view objects.  Puts blank tile at [4][4].
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				if(i == 4 && j == 4){
					PictureImpl whiteTile = new PictureImpl(smallWidth, smallHeight);
					smallPicArray[j][i] = new PictureView(whiteTile.createObservable());
					smallPicArray[j][i].addMouseListener(this);
					game.add(this.smallPicArray[j][i]);
				}
				else{
					Picture newPicture = p.extract(j*smallWidth, i*smallHeight, smallWidth, smallHeight);
					smallPicArray[j][i] = new PictureView(newPicture.createObservable());
					smallPicArray[j][i].addMouseListener(this);
					game.add(this.smallPicArray[j][i]);
				}
			}
		}
		add(game);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyCode = e.getKeyCode();
		
		switch (keyCode) {

		case KeyEvent.VK_UP:	//if user presses 'up' key:
			try {
				Picture tempUp = smallPicArray[blankX][blankY - 1].getPicture();
				Picture blankPicUp = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
				smallPicArray[blankX][blankY - 1].setPicture(blankPicUp.createObservable());
				smallPicArray[blankX][blankY].setPicture(tempUp.createObservable());
				blankY-=1;}
			catch (ArrayIndexOutOfBoundsException qe){

			}
			break;

		case KeyEvent.VK_DOWN:	//if user presses 'down' key:
			try{
				Picture tempDown = smallPicArray[blankX][blankY + 1].getPicture();
				Picture blankPicDown = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
				smallPicArray[blankX][blankY + 1].setPicture(blankPicDown.createObservable());
				smallPicArray[blankX][blankY].setPicture(tempDown.createObservable());
				blankY+=1; }
			catch (ArrayIndexOutOfBoundsException qe){

			}

			break;

		case KeyEvent.VK_LEFT:	//if user presses 'left' key:
			try {
				Picture tempLeft = smallPicArray[blankX - 1][blankY].getPicture();
				Picture blankPicLeft = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
				smallPicArray[blankX - 1][blankY].setPicture(blankPicLeft.createObservable());
				smallPicArray[blankX][blankY].setPicture(tempLeft.createObservable());
				blankX-=1; }
			catch (ArrayIndexOutOfBoundsException qe){

			}
			break;

		case KeyEvent.VK_RIGHT :	//if user presses 'right' key:
			try {
				Picture tempRight = smallPicArray[blankX + 1][blankY].getPicture();
				Picture blankPicRight = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
				smallPicArray[blankX + 1][blankY].setPicture(blankPicRight.createObservable());
				smallPicArray[blankX][blankY].setPicture(tempRight.createObservable());
				blankX+=1; }
			catch (ArrayIndexOutOfBoundsException qe){

			}
			break;
		}
		setFocusable(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {	

		int mouseX = -1;
		int mouseY = -1;

		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				if(e.getSource() == smallPicArray[i][j]){
					mouseX = i;
					mouseY = j;
				}
			}
		}
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				if(mouseX == blankX){

					if(mouseY>blankY){  //if user clicks below the blank
						int numberMoves = mouseY - blankY;
						for(int k = 0; k < numberMoves; k++){
							Picture tempUp = smallPicArray[blankX][blankY + 1].getPicture();
							Picture blankPicUp = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
							smallPicArray[blankX][blankY + 1].setPicture(blankPicUp.createObservable());
							smallPicArray[blankX][blankY].setPicture(tempUp.createObservable());
							blankY+=1;
						}
					}

					else if (mouseY < blankY){  //if user clicks above the blank
						int numberMoves = blankY - mouseY;
						for(int k = 0; k < numberMoves; k++){
							Picture tempDown = smallPicArray[blankX][blankY - 1].getPicture();
							Picture blankPicDown = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
							smallPicArray[blankX][blankY - 1].setPicture(blankPicDown.createObservable());
							smallPicArray[blankX][blankY].setPicture(tempDown.createObservable());
							blankY-=1;  
						}
					}
				}

				if(mouseY == blankY){
					if(mouseX > blankX){  //if user clicks to left of blank
						int numberMoves = mouseX - blankX;
						for(int k = 0; k < numberMoves; k++){
							Picture tempLeft = smallPicArray[blankX + 1][blankY].getPicture();
							Picture blankPicLeft = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
							smallPicArray[blankX + 1][blankY].setPicture(blankPicLeft.createObservable());
							smallPicArray[blankX][blankY].setPicture(tempLeft.createObservable());
							blankX+=1;  
						}
					}
					else if (mouseX < blankX){  //if user clicks to right of blank
						int numberMoves = blankX - mouseX;
						for(int k = 0; k < numberMoves; k++){
							Picture tempRight = smallPicArray[blankX - 1][blankY].getPicture();
							Picture blankPicRight = new PictureImpl(pic.getWidth()/5, pic.getHeight()/5);
							smallPicArray[blankX - 1][blankY].setPicture(blankPicRight.createObservable());
							smallPicArray[blankX][blankY].setPicture(tempRight.createObservable());
							blankX-=1; 
						}
					}
				}
			}
		}
	}
	
											//	unused event listeners
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
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
	
											//   Abandoned Swap Method
	
	//public void keySwap(){
	//		//temp = A
	//		//A = B
	//		//B = temp
	//		//	PictureView temp;
	//		temp = smallPicArray[copyCoord.getX()][copyCoord.getY()];
	//		
	//		smallPicArray[copyCoord.getX()][copyCoord.getY()] = smallPicArray[solidCoord.getX()][solidCoord.getY()];
	//		
	//		smallPicArray[solidCoord.getX()][solidCoord.getY()] = temp;
	//		
	//	//	solidCoord = new Coordinate(copyCoord.getX(),copyCoord.getY());		
	//	}
}

import java.awt.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

public class Clothing extends Rectangle {
	private Image clothing;

	// Constructor that creates a new card object
	public Clothing(Point position, Image newClothing, Component parentFrame) {
		super(position.x, position.y, 0, 0);

		// Load up the appropriate image file for this card
//		if (type == 1) {
//			clothing = clothing.getScaledInstance(200, 175, Image.SCALE_DEFAULT);
//			setSize(200, 175);
//		} else {
//			clothing = clothing.getScaledInstance(250, 200, Image.SCALE_DEFAULT);
//			setSize(250, 200);
//		}
		clothing = newClothing.getScaledInstance(250, 200, Image.SCALE_DEFAULT);
		setSize(250,200);
	}

	// Change the position of the card by the difference between
	// the two given points
	public void move(Point initialPos, Point finalPos) {
		translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);
	}

	/**
	 * Draws a card in a Graphics context
	 * 
	 * @param g
	 *            Graphics to draw the card in
	 */
	public void draw(Graphics g) {
		g.drawImage(clothing, x, y, null);
	}

}

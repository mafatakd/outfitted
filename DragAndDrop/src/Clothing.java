import java.awt.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

public class Clothing extends Rectangle {
	private static Image shirt = new ImageIcon("shirt.png").getImage();
	private static Image pants = new ImageIcon("pants.png").getImage();
	private static Image sweater = new ImageIcon("sweater.png").getImage();
	
	// images of tops
	ArrayList<Image> tops = new ArrayList<Image>();
	
	
			
//	File topsFile = new File("C:\\Users\\Mr.Long HYFR\\Desktop\\Eclipse Workspace\\DragAndDrop\\Tops");
//	ArrayList<String> topsName = new ArrayList<String>(Arrays.asList(topsFile.list()));
//	
//	for (int i = 0; i < topsName.size(); i++)
//	{
//		tops[i] = new ImageIcon(topsName.get(i)).getImage();
//	}
	
	// images of bottoms
	ArrayList<Image> bottoms = new ArrayList<Image>();
	
	private Image clothing;

	// Constructor that creates a new card object
	public Clothing(Point position, int type, int number, Component parentFrame) {
		super(position.x, position.y, 0, 0);
		
		// Add images of tops
		tops.add(shirt.getScaledInstance(100, 90, Image.SCALE_DEFAULT));
		tops.add(sweater.getScaledInstance(100, 90, Image.SCALE_DEFAULT));

		// bottoms
		bottoms.add(pants.getScaledInstance(100, 90, Image.SCALE_DEFAULT));
		
		// Load up the appropriate image file for this card
		if (type == 1) {
			clothing = tops.get(number);
			clothing = clothing.getScaledInstance(200, 175, Image.SCALE_DEFAULT);
			setSize(200,175);
		} else {
			clothing = pants;
			clothing = clothing.getScaledInstance(250, 200, Image.SCALE_DEFAULT);
			setSize(250,200);
		}				
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

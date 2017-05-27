
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class MoveClothing extends JFrame {

	// Images
	Image shirt = new ImageIcon("shirt.png").getImage();
	Image pants = new ImageIcon("pants.png").getImage();
	Image sweater = new ImageIcon("sweater.png").getImage();
	int number;

	boolean displayTops = false;
	boolean displayBottoms = false;

	// images of tops
	ArrayList<Image> tops = new ArrayList<Image>();
	ArrayList<Image> bottoms = new ArrayList<Image>();

	Label statusMessage;
	CardArea designSpace;

	public MoveClothing() {
		// Set up the original frame
		super("Outfitted");

		// Add
		tops.add(shirt.getScaledInstance(100, 90, Image.SCALE_DEFAULT));
		tops.add(sweater.getScaledInstance(100, 90, Image.SCALE_DEFAULT));

		bottoms.add(pants.getScaledInstance(100, 90, Image.SCALE_DEFAULT));

		// Add in the card area for drawing and managing cards
		setLayout(new BorderLayout());
		designSpace = new CardArea();
		add(designSpace, BorderLayout.CENTER);

		// Create a status panel with a clear button and a message area
		JPanel statusPanel = new JPanel();

		JButton tops = new JButton("Tops");
		JButton bottoms = new JButton("Bottoms");
		JButton clearCards = new JButton("Clear");
		clearCards.addActionListener(new ActionListener() {
			/**
			 * Responds to the Clear Pieces button being pressed
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				designSpace.clearSpace();
			}
		});

		tops.addActionListener(new ActionListener() {
			/**
			 * Responds to the Clear Pieces button being pressed
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				designSpace.tops();
			}
		});

		bottoms.addActionListener(new ActionListener() {
			/**
			 * Responds to the Clear Pieces button being pressed
			 * 
			 * @param event
			 *            The event that selected this menu option
			 */
			public void actionPerformed(ActionEvent event) {
				designSpace.bottoms();
			}
		});
		statusPanel.add(tops);
		statusPanel.add(bottoms);
		statusPanel.add(clearCards);

		statusMessage = new Label("Welcome to Outfitted");
		statusPanel.add(statusMessage);
		// Add this new panel to the bottom of the screen
		add("South", statusPanel);

	}

	// Changes the statusMessage
	public void showStatus(String message) {
		statusMessage.setText(message);
	}

	/**
	 * Inner class to look after the Card Area
	 */
	private class CardArea extends JPanel {
		// Information about the cards
		Clothing[] myClothes;
		int noOfClothes;
		int type = 0;
		Clothing selectedClothing;

		// Keeps track of the last position of the card
		// Used to find out how much to move the card
		Point lastPoint;

		/**
		 * Constructs a new DrawingPanel object
		 */
		public CardArea() {
			this.setPreferredSize(new Dimension(700, 500));
			setBackground(new Color(255, 255, 255));
			// Add mouse listeners to the drawing panel
			this.addMouseListener(new MouseHandler());
			this.addMouseMotionListener(new MouseMotionHandler());

			// Set up the initial Card list information
			// TODO change to list
			myClothes = new Clothing[52];
			clearSpace();
		}

		/**
		 * Repaint the drawing panel
		 * 
		 * @param g
		 *            The Graphics context
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Draw the cards
			for (int i = 0; i < noOfClothes; i++)
				myClothes[i].draw(g);

			// Draw the selected card last (on top)
			if (selectedClothing != null)
				selectedClothing.draw(g);

			// display tops
			if (displayTops)
				for (int i = 0; i < tops.size(); i++)
					g.drawImage(tops.get(i), 25 + i * 100, 400, this);

			// display tops
			if (displayBottoms)
				for (int i = 0; i < bottoms.size(); i++)
					g.drawImage(bottoms.get(i), 25 + i * 100, 400, this);

			repaint();

		} // paint component method

		public void clearSpace() {
			noOfClothes = 0;
			selectedClothing = null;
			repaint();
		}

		public void tops() {
			type = 1;
			// Display tops
			displayTops = true;

			repaint();
		}

		public void bottoms() {
			type = 2;

			displayBottoms = true;

			repaint();
		}

		// Inner class to handle mouse events
		private class MouseHandler extends MouseAdapter {
			public void mousePressed(MouseEvent event) {
				Point selectedPoint = event.getPoint();

				// Select clothing
				if (displayTops) {
					number = (int) ((selectedPoint.getX() - 25) / 100);
					displayTops = false;
					repaint();
				} else if (displayBottoms) {
					number = (int) ((selectedPoint.getX() - 25) / 100);
					displayBottoms = false;
					repaint();
				} else {

					for (int i = noOfClothes - 1; i >= 0; i--)
						if (myClothes[i].contains(selectedPoint)) {

							selectedClothing = myClothes[i];
							lastPoint = selectedPoint;
							showStatus("Selected: " + selectedClothing);

							repaint();
							return;
						}

					// Make new clothing
					if (type != 0) {
						myClothes[noOfClothes] = new Clothing(selectedPoint, type, number, MoveClothing.this);
						noOfClothes++;
						repaint();
					}
				}
				repaint();
			}

			public void mouseReleased(MouseEvent event) {
				if (selectedClothing != null) {
					selectedClothing = null;
					showStatus("Card Dropped");
					repaint();
				}
			}
		}

		// Inner Class to handle mouse movements
		private class MouseMotionHandler implements MouseMotionListener {
			public void mouseMoved(MouseEvent event) {
				// Set the cursor to the hand if we are on a card
				Point currentPoint = event.getPoint();
				// Count down, since higher cards are on top
				for (int i = noOfClothes - 1; i >= 0; i--)
					if (myClothes[i].contains(currentPoint)) {
						setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						return;
					}

				// Otherwise we just use the default cursor
				showStatus("No cards here...");
				setCursor(Cursor.getDefaultCursor());
			}

			public void mouseDragged(MouseEvent event) {
				Point currentPoint = event.getPoint();

				if (selectedClothing != null) {
					showStatus("Moving: " + selectedClothing);
					// We use the difference between the lastPoint and the
					// currentPoint to move the card so that the position of
					// the mouse on the card doesn't matter.
					// i.e. we can drag the card from any point on the card
					// image
					selectedClothing.move(lastPoint, currentPoint);
					lastPoint = currentPoint;
					repaint();
				}
			}
		}
	}

	public static void main(String[] args) {
		MoveClothing frame = new MoveClothing();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	} // main method
}

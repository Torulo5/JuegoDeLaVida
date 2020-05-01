package views;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

import controllers.GameController;
import models.NextStateEvent;

public class GridsCanvas extends JPanel implements NextStateEvent{

	private static final long serialVersionUID = 1L;

	private int width, height;
	private double rows;
	private double cols;

	// tamaño de cada casila
	private int rowHt;
	private int rowWid;
	private int stroke;

	private GameController gController = null;

	private final boolean DEBUG = false;

	GridsCanvas(int w, int h, int ladoCuadrado) {
		setSize(width = w, height = h);
		rowHt = rowWid = ladoCuadrado;
		stroke = 1;

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				Point newPoint = new Point(x / rowHt, y / rowWid);
				gController.saveAlivePoint(newPoint);

				repaint();
			}
		});

	}

	public void setgController(GameController gController) {
		this.gController = gController;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		width = getSize().width;
		height = getSize().height;
		rows = (double) height / rowHt;
		cols = (double) width / rowWid;

		paintLines(g);
		
		ArrayList<Point> pointstoCheck = gController.getPointsToPaint(2);
		paintRectangles(g,pointstoCheck,Color.green);
		
		ArrayList<Point> pointsAlive = gController.getPointsToPaint(1);
		paintRectangles(g,pointsAlive,Color.RED);
		
		ArrayList<Point> nextsPointsAlive = gController.getPointsToPaint(3);
		paintOvals(g,nextsPointsAlive,Color.blue);

	}

	private void paintLines(Graphics g) {
		int i;
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(stroke));

		for (i = 0; i < rows; i++)
			g2d.drawLine(0, i * rowHt, width, i * rowHt);

		for (i = 0; i < cols; i++)
			g2d.drawLine(i * rowWid, 0, i * rowWid, height);
	}

	private void paintRectangles(Graphics g,ArrayList<Point> pointsAlive, Color color) {

		int strokeSpacer = 1;
		if (stroke != 1)
			strokeSpacer = Math.round((float) stroke / 2);

		for (Point point : pointsAlive) {
			if (DEBUG) {
				System.out.println("----------------------PAINT RECTANGLE------------------------");
				System.out.println("InitialX: " + point.x + " InitialY: " + point.y);
				System.out.println("FinalX  : " + point.x * rowHt + " FinalY  : " + point.y * rowWid);
				System.out.println("STROKE: " + stroke + " strokeSpacer: " + strokeSpacer);
				System.out.println("-------------------------------------------------------------");
			}
			g.setColor(color);
			g.fillRect(point.x * rowHt + strokeSpacer, point.y * rowWid + strokeSpacer, rowHt - stroke,
					rowWid - stroke);
		}
	}
	
	private void paintOvals(Graphics g,ArrayList<Point> pointsAlive, Color color) {


		for (Point point : pointsAlive) {
			if (DEBUG) {
				System.out.println("----------------------PAINT RECTANGLE------------------------");
				System.out.println("InitialX: " + point.x + " InitialY: " + point.y);
				System.out.println("FinalX  : " + point.x * rowHt + " FinalY  : " + point.y * rowWid);
				System.out.println("-------------------------------------------------------------");
			}
			g.setColor(color);
			g.fillOval(point.x * rowHt + Math.round((float) rowHt / 4), point.y * rowWid + Math.round((float) rowWid / 4), 10, 10);

		}
	}

	@Override
	public void nextStateEvent(String command) {
		this.repaint();
	}

}
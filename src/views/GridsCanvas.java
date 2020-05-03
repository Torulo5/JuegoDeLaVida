package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controllers.GameController;

public class GridsCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private final boolean DEBUG = false;
	
	private GameController gController = null;
	
	//tamaño ventana y numero de columas
	private int width = 0, height = 0;
	private double rows = 0;
	private double cols = 0;

	// tamaño de cada casila
	private int rowHt = 0;
	private int rowWid = 0;
	private int stroke = 0;

	// flags para controlar el comportamiento del grid
	private boolean paintNextPointsAlive = true;
	private boolean paintNextPointsNeededToCheck = true;
	private boolean setNewPoints = true;
	private boolean isChangeSize = true;

	// auxiliar arrays to save currentState to paint
	private ArrayList<Point> pointsAlive;
	private ArrayList<Point> nextPointsAlive;
	private ArrayList<Point> pointsNeededToCheck;

	GridsCanvas(int w, int h, int ladoCuadrado) {
		setSize(this.width = w, this.height = h);
		this.rowHt = this.rowWid = ladoCuadrado;
		this.stroke = 1;

		this.pointsAlive = new ArrayList<Point>();
		this.nextPointsAlive = new ArrayList<Point>();
		this.pointsNeededToCheck = new ArrayList<Point>();

		
        MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (!setNewPoints) 
					return;
				int x = e.getX();
				int y = e.getY();
				Point newPoint = new Point(x / rowHt, y / rowWid);
				
				if (SwingUtilities.isLeftMouseButton(e)) {
					gController.saveAlivePoint(newPoint);
					repaint();
				} else if(SwingUtilities.isRightMouseButton(e)) {
					gController.removePoint(newPoint);
					repaint();
				}
			}
		
            @Override
            public void mouseDragged(MouseEvent e) {
				if (!setNewPoints) 
					return;
				int x = e.getX();
				int y = e.getY();
				Point newPoint = new Point(x / rowHt, y / rowWid);
				if (SwingUtilities.isLeftMouseButton(e)) {
					gController.saveAlivePoint(newPoint);
					repaint();
				} else if(SwingUtilities.isRightMouseButton(e)) {
					gController.removePoint(newPoint);
					repaint();
				}
            }

        };
		
		addMouseListener(ma);
		addMouseMotionListener(ma);
		
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(!isChangeSize)
					return;					
		        if (e.getWheelRotation() < 0) {
		            rowHt++;
		            rowWid++;	
					repaint();
		        } else {
		            if(!(rowHt == 5 || rowWid == 5)) {
			            rowHt--;
			            rowWid--;	
						repaint();
		            }
		        }
			}
		});

	}

	public void setgController(GameController gController) {
		this.gController = gController;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.width = this.getSize().width;
		this.height = this.getSize().height;
		this.rows = (double) this.height / this.rowHt;
		this.cols = (double) this.width / this.rowWid;

		paintLines(g);

		if (paintNextPointsNeededToCheck)
			paintRectangles(g, this.pointsNeededToCheck, Color.green);

		paintRectangles(g, this.pointsAlive, Color.RED);

		if (paintNextPointsAlive)
			paintOvals(g, this.nextPointsAlive, Color.blue);

	}

	private void paintLines(Graphics g) {
		int i;
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setStroke(new BasicStroke(this.stroke));

		for (i = 0; i < this.rows; i++)
			g2d.drawLine(0, i * this.rowHt, this.width, i * this.rowHt);

		for (i = 0; i < this.cols; i++)
			g2d.drawLine(i * this.rowWid, 0, i * this.rowWid, this.height);
	}

	private void paintRectangles(Graphics g, ArrayList<Point> pointsAlive, Color color) {

		int strokeSpacer = 1;
		if (this.stroke != 1)
			strokeSpacer = Math.round((float) this.stroke / 2);

		for (Point point : pointsAlive) {
			if (DEBUG) {
				System.out.println("----------------------PAINT RECTANGLE------------------------");
				System.out.println("InitialX: " + point.x + " InitialY: " + point.y);
				System.out.println("FinalX  : " + point.x * this.rowHt + " FinalY  : " + point.y * this.rowWid);
				System.out.println("STROKE: " + this.stroke + " strokeSpacer: " + strokeSpacer);
				System.out.println("-------------------------------------------------------------");
			}
			g.setColor(color);
			g.fillRect(point.x * this.rowHt + strokeSpacer, point.y * this.rowWid + strokeSpacer,
					this.rowHt - this.stroke, this.rowWid - this.stroke);
		}
	}

	private void paintOvals(Graphics g, ArrayList<Point> pointsAlive, Color color) {

		for (Point point : pointsAlive) {
			if (DEBUG) {
				System.out.println("----------------------PAINT RECTANGLE------------------------");
				System.out.println("InitialX: " + point.x + " InitialY: " + point.y);
				System.out.println("FinalX  : " + point.x * this.rowHt + " FinalY  : " + point.y * this.rowWid);
				System.out.println("-------------------------------------------------------------");
			}
			g.setColor(color);
			g.fillOval(point.x * this.rowHt + Math.round((float) this.rowHt / 4),
					point.y * this.rowWid + Math.round((float) this.rowWid / 4), 10, 10);

		}
	}

	public void resetArray(String array, Map<String, List<Point>> newState) {
		List<Point> auxArray = null;
		switch (array) {
			case "ALIVE":
				auxArray = this.pointsAlive;
				break;
			case "CHECK":
				auxArray = this.pointsNeededToCheck;
				break;
			case "NEXTALIVE":
				auxArray = this.nextPointsAlive;
				break;
		}

		auxArray.clear();
		List<Point> arrayToCopy = newState.get(array);
		for (Point point : arrayToCopy) {
			auxArray.add(point);
		}
	}

	public void setPaintNextPointsAlive(boolean paintNextPointsAlive) {
		this.paintNextPointsAlive = paintNextPointsAlive;
		this.repaint();
	}

	public void setPaintNextPointsNeededToCheck(boolean paintNextPointsNeededToCheck) {
		this.paintNextPointsNeededToCheck = paintNextPointsNeededToCheck;
		this.repaint();
	}

	public void setSetNewPoints(boolean setNewPoints) {
		this.setNewPoints = setNewPoints;
	}

}
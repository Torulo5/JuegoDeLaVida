package views;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import controllers.GameController;

public class GameFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	private GameController gController = null;
	private GridsCanvas grindCanvas = null;

	public GameFrame(GameController gController) {
		this.gController = gController;

		grindCanvas = new GridsCanvas(200, 200, 20);
		grindCanvas.setgController(this.gController);
		this.add(grindCanvas);
		this.addKeyListener(this);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		gController.nextTurn();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public GridsCanvas getGrindCanvas() {
		return grindCanvas;
	}
}
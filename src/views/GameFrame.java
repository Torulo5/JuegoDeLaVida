package views;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import controllers.GameController;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class GameFrame extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	private GameController gController = null;
	private GridsCanvas grindCanvas = null;

	public GameFrame(GameController gController) {
		this.gController = gController;
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

		grindCanvas = new GridsCanvas(200, 200, 20);
		grindCanvas.setgController(this.gController);
		getContentPane().add(grindCanvas);
		grindCanvas.setLayout(new BoxLayout(grindCanvas, BoxLayout.X_AXIS));
		this.addKeyListener(this);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnConfiguracion = new JMenu("Configuracion");
		menuBar.add(mnConfiguracion);
		
		JMenuItem mntmVisualizacion = new JMenuItem("Visualizacion");
		mnConfiguracion.add(mntmVisualizacion);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
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
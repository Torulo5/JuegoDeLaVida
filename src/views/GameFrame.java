package views;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import controllers.GameController;
import controllers.NextStateEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFrame extends JFrame implements KeyListener, NextStateEvent {

	private static final long serialVersionUID = 1L;

	private GameController gController = null;
	private GridsCanvas grindCanvas = null;
	private JMenuBar menuBar = null;
	private JMenu mnConfiguracion = null;

	private JLabel labelEstado = null;
	private JLabel labelPuntos = null;
	private JLabel labelStepTime = null;
	
	public GameFrame(GameController gController) {
		this.gController = gController;
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		
		labelEstado = new JLabel("Steps: 0");
		panel.add(labelEstado);
		
		labelPuntos = new JLabel("Puntos: 0");
		panel.add(labelPuntos);
		
		labelStepTime = new JLabel("StepTime(nanos): 0");
		panel.add(labelStepTime);

		grindCanvas = new GridsCanvas(200, 200, 20);
		grindCanvas.setgController(this.gController);
		getContentPane().add(grindCanvas);
		grindCanvas.setLayout(new BoxLayout(grindCanvas, BoxLayout.X_AXIS));
		this.addKeyListener(this);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnConfiguracion = new JMenu("Configuracion");
		menuBar.add(mnConfiguracion);
		
		JMenu mnVisualizacion = new JMenu("Visualizacion");
		mnConfiguracion.add(mnVisualizacion);
		
		JCheckBox chckbxPointtocheck = new JCheckBox("PointsToCheck");
		chckbxPointtocheck.addActionListener ( new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          boolean selected = abstractButton.getModel().isSelected();
		          grindCanvas.setPaintNextPointsNeededToCheck(selected);
		        }
		});
		chckbxPointtocheck.setSelected(true);
		mnVisualizacion.add(chckbxPointtocheck);
		
		JCheckBox chckbxNextpoints = new JCheckBox("NextPoints");
		chckbxNextpoints.addActionListener ( new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          boolean selected = abstractButton.getModel().isSelected();
		          grindCanvas.setPaintNextPointsAlive(selected);
		        }
		});
		chckbxNextpoints.setSelected(true);
		mnVisualizacion.add(chckbxNextpoints);
		
		JCheckBox chckbxLines = new JCheckBox("Lines");
		chckbxLines.addActionListener ( new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          boolean selected = abstractButton.getModel().isSelected();
		          grindCanvas.setPaintLines(selected);
		        }
		});
		chckbxLines.setSelected(true);
		mnVisualizacion.add(chckbxLines);
		
		JCheckBox chckbxNuevospuntos = new JCheckBox("NuevosPuntos");
		chckbxNuevospuntos.addActionListener ( new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		          AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		          boolean selected = abstractButton.getModel().isSelected();
		          grindCanvas.setSetNewPoints(selected);
		        }
		});
		chckbxNuevospuntos.setSelected(true);
		chckbxNuevospuntos.setFocusable(false);
		menuBar.add(chckbxNuevospuntos);
		
		JButton btnResettablero = new JButton("ResetTablero");
		btnResettablero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gController.clearGame();
			}
		});
		btnResettablero.setFocusable(false);
		menuBar.add(btnResettablero);
		
		JMenu mnTurnos = new JMenu("ConfigTurnos");
		mnTurnos.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(mnTurnos);
		
		JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem("Automatico");
		chckbxmntmNewCheckItem.addItemListener( new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {
	        	JCheckBoxMenuItem aux = (JCheckBoxMenuItem) e.getItem();
	        	gController.setTimerState(aux.getState());
	        }
	    });
		mnTurnos.add(chckbxmntmNewCheckItem);
		
		JSlider slider = new JSlider();
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				gController.resetTiemer();
			}
		});
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				gController.setTimerRate(slider.getValue());
			}
		});
		slider.setMinimum(10);
		slider.setMaximum(2000);
		slider.setValue(1000);
		mnTurnos.add(slider);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}
	
	@Override
	public void nextStateEvent(Map<String, List<Point>> newState, int stept, int points, long timeElapsedToCalculateStep) {
		grindCanvas.resetArray("ALIVE",newState);
		grindCanvas.resetArray("CHECK",newState);
		grindCanvas.resetArray("NEXTALIVE",newState);
		labelEstado.setText("Steps: " + stept);
		labelPuntos.setText("Points: " + points);
		labelStepTime.setText("StepTime(nanos): " + timeElapsedToCalculateStep);
		this.repaint();
	}

	@Override
	public void newPointEvent(Map<String, List<Point>> newState, int points) {
		grindCanvas.resetArray("ALIVE",newState);
		grindCanvas.resetArray("CHECK",newState);
		grindCanvas.resetArray("NEXTALIVE",newState);
		labelPuntos.setText("Points: " + points);
		this.repaint();
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

}
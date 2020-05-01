import controllers.GameController;
import models.GameModel;
import views.GameFrame;

public class main {

	public static void main(String[] a) {
		

		GameModel gModel = new GameModel();
		GameController gController = new GameController(gModel);
		GameFrame gFrame = new GameFrame(gController);	
		gController.addNextStatelisteners(gFrame.getGrindCanvas());
		
		//mostramos pantalla
		gFrame.pack();
		gFrame.setSize(300,300); 
		gFrame.setVisible(true);
		
		
		
//		JFrame frame = new JFrame("Flow Layout");
//		JButton button,button1, button2, button3,button4;
//		button = new JButton("button 1");
//		button1 = new JButton("button 2");
//		button2 = new JButton("button 3");
//		button3 = new JButton("button 4");
//		button4 = new JButton("button 5");
//		frame.add(button);
//		frame.add(button1);
//		frame.add(button2);
//		frame.add(button3);
//		frame.add(button4);
//		frame.setLayout(new GridLayout(2,3));
//		frame.setSize(300,300);  
//		frame.setVisible(true);
	}

}

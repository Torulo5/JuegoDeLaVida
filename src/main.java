import controllers.GameController;
import models.GameModel;
import views.GameFrame;

public class main {

	public static void main(String[] a) {
		

		GameModel gModel = new GameModel();
		GameController gController = new GameController(gModel);
		GameFrame gFrame = new GameFrame(gController);	
		gController.addNextStatelisteners(gFrame);
		
		//mostramos pantalla
		gFrame.pack();
		gFrame.setSize(300,300); 
		gFrame.setVisible(true);
		
//		GameFrame gFrame1 = new GameFrame(gController);
//		gFrame1.pack();
//		gFrame1.setSize(300,300); 
//		gFrame1.setVisible(true);
//		gController.addNextStatelisteners(gFrame1);

	}

}

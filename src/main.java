import controllers.GameController;
import models.GameModel;
import views.GameFrame;

public class main {

	public static void main(String[] a) {
		

		GameModel gModel = new GameModel(2);
		GameController gController = new GameController(gModel);
		GameFrame gFrame = new GameFrame(gController);	
		gController.addNextStatelisteners(gFrame);
		gFrame.setTitle("HashMap");
		gFrame.pack();
		gFrame.setSize(300,300); 
		gFrame.setVisible(true);
		
//		GameModel gModel1 = new GameModel(1);
//		GameController gController1 = new GameController(gModel1);
//		GameFrame gFrame1 = new GameFrame(gController1);
//		gFrame1.setTitle("Array");
//		gFrame1.pack();
//		gFrame1.setSize(300,300); 
//		gFrame1.setVisible(true);
//		gController1.addNextStatelisteners(gFrame1);

	}

}

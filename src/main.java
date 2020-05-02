import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controllers.GameController;
import models.GameModel;
import views.GameFrame;

public class main {

	public static void main(String[] a) {
		


		GameModel gModelHashMap = new GameModel(2);
//		GameModel gModelArray = new GameModel(1);
		
		GameController gController = new GameController();

		
		GameFrame gFrame = new GameFrame(gController);	
		gFrame.setTitle("HashMap");
		gFrame.pack();
		gFrame.setSize(300,300); 

		
//		GameFrame gFrame1 = new GameFrame(gController);
//		gFrame1.setTitle("Array");
//		gFrame1.pack();
//		gFrame1.setSize(300,300); 

		
		gController.addGameModelAndListener(gModelHashMap,gFrame);
		//gController.addGameModelAndListener(gModelArray,gFrame1);

		gFrame.setVisible(true);
		//gFrame1.setVisible(true);
		

	    try {
			String LAF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			UIManager.setLookAndFeel(LAF);
		    SwingUtilities.updateComponentTreeUI(gFrame);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

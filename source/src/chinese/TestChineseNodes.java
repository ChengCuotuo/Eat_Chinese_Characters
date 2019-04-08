package chinese;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;

public class TestChineseNodes extends Application{
	private Pane pane = new Pane();
	private ChineseNodes cn = new ChineseNodes("晨");
	private Rectangle[][] rectangle= new Rectangle[16][16];
	@Override
	public void start(Stage primaryStage) {
		pane.setPadding(new Insets(20, 20, 20, 20));
		rectangle = cn.getNextArrays();
		double x = 0, y = 0;
		
		//将返回的矩形二维数组进行转换，把相对位置转换成实际位置
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (rectangle[i][j] != null) {
					x = rectangle[i][j].getX() * 20;
					y = rectangle[i][j].getY() * 20;
	//System.out.println(x + " " + y);
					rectangle[i][j].setX(x);
					rectangle[i][j].setY(y);
					pane.getChildren().add(rectangle[i][j]);
				}
			}
		}
		
		Scene scene = new Scene(pane);
		primaryStage.setTitle("TestChineseNodes");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

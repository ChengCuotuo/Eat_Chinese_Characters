/*
 * 时间：2018.9.23
 * 书写：王春雷
 */

package snakeGUI;
//将Snake_Control中转换好的汉字点阵矩形数组加到面板中
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
//import javafx.geometry.Insets;

import snake.Snake_Control;
import snake.Snake_Body;
import java.util.LinkedList;

public class TestMainPane extends Application{
	
	private MainPane mainPane = new MainPane();
	private Snake_Control sc = new Snake_Control();
	
	@Override
	public void start(Stage primaryStage) {
		Pane pane= mainPane.getMainPane();
//pane.setPadding(new Insets(10, 10, 10, 10));
		
		LinkedList<Snake_Body> body = sc.getBody();
		LinkedList<Snake_Body> chineseList = sc.getChineseList();
		
		for (int i = 0; i < 5; i++) {
			sc.moveAsDirection();
		}
		sc.setDirection(2);
		for (int i = 0; i < 5; i++) {
			sc.moveAsDirection();
		}
		for (int i = 0; i < chineseList.size(); i++) {
			pane.getChildren().add(chineseList.get(i).getRc());
		}
		for (int i = 0; i < body.size(); i++) {
			pane.getChildren().add(body.get(i).getRc());
		}
		
		Scene scene = new Scene(pane, 400, 400);
		primaryStage.setTitle("TestMainPane");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

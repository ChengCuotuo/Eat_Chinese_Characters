package snake;
import java.util.LinkedList;
import javafx.scene.shape.Rectangle;
public class TestLinkedList {
	public static void main(String[] args) {
		LinkedList<Snake_Body> list = new LinkedList<>();
		for (int i = 0; i < 10; i++) {
			list.add(new Snake_Body(i ,i + 1, new Rectangle(2, 5)));
		}
		
		System.out.println(list.indexOf(new Snake_Body(2, 3)));
	}
}

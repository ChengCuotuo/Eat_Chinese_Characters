package snakeGUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.ArrayList;

public class MainPane {
	private final int DISTANCE = 17;
	private final int W_NUM = 25;
	private final int H_NUM = 25;
	private final Color COLOR = Color.RED;
	private ArrayList<Line> al_Lines = new ArrayList<>();
	private Pane pane;
	
	public MainPane() {
		createLines();
		pane = new Pane();
		//…Ë÷√√Ê∞Â±ﬂøÚ
		this.pane.setStyle("-fx-border-color: blue");
	}
	public Pane getMainPane() {
		return this.pane;
	}
	
	public void addLines() {
		for(int i = 0; i < al_Lines.size(); i++) {
			pane.getChildren().add(al_Lines.get(i));
		}
	}
	
	private void createLines() {
		for (int i = 0; i < W_NUM; i++) {
			Line line = new Line(0, i * DISTANCE, (W_NUM - 1) * DISTANCE, i * DISTANCE);
			line.setStroke(COLOR);
			line.setFill(COLOR);
			al_Lines.add(line);
		}
		for (int i = 0; i < H_NUM; i++) {
			Line line = new Line(i * DISTANCE, 0, i * DISTANCE, (H_NUM - 1) * DISTANCE);
			line.setStroke(COLOR);
			line.setFill(COLOR);
			al_Lines.add(line);
		}
	}
	public void deleteLines() {
		for(int i = 0; i < al_Lines.size(); i++) {
			pane.getChildren().remove(al_Lines.get(i));
		}
	}
}

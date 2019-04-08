package snake;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Snake_Body implements Serializable{
	private static final long serialVersionUID = 1L;
	private Rectangle rc;
	private Color color = Color.RED;
	private int width = 15;
	
	public Snake_Body(double x, double y) {
		rc = new Rectangle(x, y, width, width);
		rc.setFill(color);
	}
	
	public Snake_Body(double x, double y, Rectangle rec) {
		this.rc = rec;
		this.rc.setX(x);
		this.rc.setY(y);
	}
	
	public Rectangle getRc() {
		return this.rc;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public boolean equals(Object ob) {
		Snake_Body sb = (Snake_Body)ob;
		if ((sb.getRc().getX() == rc.getX()) && (sb.getRc().getY() == rc.getY())) {
			return true;
		}
		else
			return false;
	}
}

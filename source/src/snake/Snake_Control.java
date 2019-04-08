package snake;

import java.util.LinkedList;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

import chinese.ChineseNodes;

public class Snake_Control implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String chinese = "中秋";
	private enum Directions{up, right, down, left};
	
	private ChineseNodes cn;
	
	private LinkedList<Snake_Body> body;
	private LinkedList<Snake_Body> chineseList;
	
	private Directions dir = Directions.right;//下一次贪吃蛇的移动方向
	private final int DISTANCE = 17;//每个贪吃蛇身体的长度
	//偏移量，就是贪吃蛇页面的左边不用来显示汉字点阵的空白长度，应该是DISTANCE + 2（17）的倍数
	private final int DEVIATION = 68;//25-16=9/2=4*17=68	

	//记录下一个时间点，贪吃蛇蛇头应该在的位置
	private double x;
	private double y;
//这个只是暂时设定，需要结合最后的面板大小来定
	private final int MAX_HEIGH = 400;
	private final int MAX_WIDTH = 400;
	
	public Snake_Control() {
		cn = new ChineseNodes(this.chinese);
		this.x = this.y = 1;
		this.body = new LinkedList<>();
		this.chineseList = new LinkedList<>();
		setChineseList(cn.getNextArrays());
		initBody();
	}
	
	public void setChinese(String chinese) {
		cn.setChinese(chinese);
	}
	
	public void clear() {
		this.body.clear();
		this.chineseList.clear();
		this.x = this.y = 1;
		initBody();
		dir = Directions.right;
	}
	
	public void startSet() {
		this.body.clear();
		setChineseList(cn.getNextArrays());
		this.x = this.y = 1;
		initBody();
		
		dir = Directions.right;
	}
	
	public void setChineseList() {
		setChineseList(cn.getNextArrays());
	}
	//贪吃蛇初始化长度为2，起始位置在左上角
	private void initBody() {
		this.body.add(new Snake_Body(this.x, this.y));
		this.x = this.x + DISTANCE;
		this.body.add(new Snake_Body(this.x, this.y));
		
	}
	
	public LinkedList<Snake_Body> getBody(){
		return this.body;
	}
	
	public LinkedList<Snake_Body> getChineseList(){
		return this.chineseList;
	}
	
	//设置方向
	public void setDirection(int d) {
		switch (d) {
		case 0:
			if (this.dir != Directions.down) {
				this.dir = Directions.up;
			}
			break;
		case 1:
			if (this.dir != Directions.left) {
				this.dir = Directions.right;
			}
			break;
		case 2:
			if (this.dir != Directions.up) {
				this.dir = Directions.down;
			}
			break;
		case 3:
			if (this.dir != Directions.right) {
				this.dir = Directions.left;
			}
			break;
		}
	}
	
	public void setNextChineseList() {
		setChineseList(cn.getNextArrays());
	}
	
	public void setChineseList(Rectangle[][] rec_array) {
		chineseList.clear();
//int total = 0;
//System.out.println(total + " " + chineseList.size());
		//将汉字形成的矩形点阵加到一个列表中，但是没有确定合适的位置
		double r_x = 0, r_y = 0;//汉字点阵中矩形在实际中的位置
		for (int i = 0; i < rec_array.length; i++) {
			for (int j = 0; j < rec_array[0].length; j++) {
				if(rec_array[i][j] != null) {
					r_x = rec_array[i][j].getX() * DISTANCE + DEVIATION + 1;
					r_y = rec_array[i][j].getY() * DISTANCE + DEVIATION + 1;
					Snake_Body sb = new Snake_Body(r_x, r_y, rec_array[i][j]);
					if (body.indexOf(sb) == -1) {
//total++;
						chineseList.add(sb);
					}
				}
			}
		}
//System.out.println(total + " " + chineseList.size());
	}
	//根据确定的位置信息，将贪吃蛇移动到下一个位置
	public boolean moveAsDirection() {
		boolean moveResult = true;
		switch (this.dir) {
		case up :
			this.y = y - DISTANCE;
			moveResult = moveUp(); break;
		case down:
			this.y = this.y + DISTANCE;
			moveResult = moveDown(); break;
		case left:
			this.x = this.x - DISTANCE;
			moveResult = moveLeft(); break;
		case right:
			this.x = this.x + DISTANCE;
			moveResult = moveRight(); break;
		}
		
		return moveResult;
	}

	private boolean moveUp() {
		if (this.y < 0) {
			return false;
		}
		
		Snake_Body newBody = new Snake_Body(this.x, this.y);
		
		int newBody_index = 0;
		
		if (body.indexOf(newBody) != -1) {
			return false;
		}
		
		if ((newBody_index = chineseList.indexOf(newBody)) != -1) {
			body.addFirst(newBody);
			chineseList.remove(newBody_index);
//System.out.println("添加一个身体");
		}
		else {
			body.addFirst(newBody);
			body.removeLast();
		}
		
		return true;
	}
	private boolean moveDown() {
		if (this.y > MAX_HEIGH) {
			return false;
		}
		
		Snake_Body newBody = new Snake_Body(this.x, this.y);
		
		int newBody_index = 0;
		
		if (body.indexOf(newBody) != -1) {
			return false;
		}
		
		if ((newBody_index = chineseList.indexOf(newBody)) != -1) {
			body.addFirst(newBody);
			chineseList.remove(newBody_index);
//System.out.println("添加一个身体");
		}
		else {
			body.addFirst(newBody);
			body.removeLast();
		}
		return true;
		
	}
	private boolean moveLeft() {
		if (this.x < 0) {
			return false;
		}
		
		Snake_Body newBody = new Snake_Body(this.x, this.y);
		
		int newBody_index = 0;
		
		if (body.indexOf(newBody) != -1) {
			return false;
		}
		
		if ((newBody_index = chineseList.indexOf(newBody)) != -1) {
			body.addFirst(newBody);
			chineseList.remove(newBody_index);
//System.out.println("添加一个身体");
		}
		else {
			body.addFirst(newBody);
			body.removeLast();
		}
		
		return true;
	}
	private boolean moveRight() {
		if (this.x > MAX_WIDTH) {
			return false;
		}
		Snake_Body newBody = new Snake_Body(this.x, this.y);
		
		int newBody_index = 0;
		
		if (body.indexOf(newBody) != -1) {
			return false;
		}
		
		if ((newBody_index = chineseList.indexOf(newBody)) != -1) {
			body.addFirst(newBody);
			chineseList.remove(newBody_index);
//System.out.println("添加一个身体");
		}
		else {
			body.addFirst(newBody);
			body.removeLast();
		}
		
		return true;
	}
	
	public void setRandomNode() {
		double[] a = getRandom();
		Snake_Body sb = new Snake_Body(a[0], a[1], cn.getRandomRec());
		while (chineseList.indexOf(sb) != -1) {
			a = getRandom();
			sb = new Snake_Body(a[0], a[1], cn.getRandomRec());
		}
		chineseList.add(sb);
//System.out.println(x + " " + y);
	}
	
	public double[] getRandom() {
		double x = (int)(Math.random() * 20) + 1;
		double y = (int)(Math.random() * 20) + 1;
		x = x * DISTANCE + 1;
		y = y * DISTANCE+ 1;
		double[] a = {x, y};
		return a;
	}
	//返回贪吃蛇长度
	public int getLength() {
		return body.size();
	}
}

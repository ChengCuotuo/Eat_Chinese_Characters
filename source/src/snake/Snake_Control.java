package snake;

import java.util.LinkedList;

import javafx.scene.shape.Rectangle;

import java.io.Serializable;

import chinese.ChineseNodes;

public class Snake_Control implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String chinese = "����";
	private enum Directions{up, right, down, left};
	
	private ChineseNodes cn;
	
	private LinkedList<Snake_Body> body;
	private LinkedList<Snake_Body> chineseList;
	
	private Directions dir = Directions.right;//��һ��̰���ߵ��ƶ�����
	private final int DISTANCE = 17;//ÿ��̰��������ĳ���
	//ƫ����������̰����ҳ�����߲�������ʾ���ֵ���Ŀհ׳��ȣ�Ӧ����DISTANCE + 2��17���ı���
	private final int DEVIATION = 68;//25-16=9/2=4*17=68	

	//��¼��һ��ʱ��㣬̰������ͷӦ���ڵ�λ��
	private double x;
	private double y;
//���ֻ����ʱ�趨����Ҫ�����������С����
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
	//̰���߳�ʼ������Ϊ2����ʼλ�������Ͻ�
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
	
	//���÷���
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
		//�������γɵľ��ε���ӵ�һ���б��У�����û��ȷ�����ʵ�λ��
		double r_x = 0, r_y = 0;//���ֵ����о�����ʵ���е�λ��
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
	//����ȷ����λ����Ϣ����̰�����ƶ�����һ��λ��
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
//System.out.println("���һ������");
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
//System.out.println("���һ������");
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
//System.out.println("���һ������");
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
//System.out.println("���һ������");
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
	//����̰���߳���
	public int getLength() {
		return body.size();
	}
}

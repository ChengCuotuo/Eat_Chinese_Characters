package chinese;

import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.paint.Color;
import java.io.Serializable;

public class ChineseNodes implements Serializable{
	private static final long serialVersionUID = 1L;
	//给类加了一个序列码，然而不加也是可以自动生成的。不加的话eclipse会给警告。
	private String chinese;
	private Rectangle[][] c_arrays;//存放汉字点阵的矩形
	private Color color = Color.BLUE;//汉字填充的颜色
	private int index;//确定当前汉字
	private int length;// 汉字长度
	private int width;//汉字点阵矩形的宽度
	
	public ChineseNodes() {
		this("我");
	}
	//改变输入的汉字内容
	public void setChinese(String chinese) {
		this.chinese = judge(chinese);
		this.index = 0;
	}
	public ChineseNodes(String chinese) {
		this.chinese = judge(chinese);
		this.length = this.chinese.length();
		this.width = 15;
		this.index = 0;
		this.c_arrays = new Rectangle[16][16];
		//init();
	}
	//处理用于初始化的字符串是否是汉字
	private String judge(String chinese) {
		boolean judge = true;
		int len = chinese.length();
		for (int i = 0; i < len; i++) {
			if (chinese.charAt(i) < '\u4e00' || chinese.charAt(i) > '\u9fcb') {
//System.out.println(chinese.charAt(i));
				judge = false;
				break;
			}
		}
		
		if (judge) {
			return chinese;
		}
		else
			return "我";
	}
//	//初始化汉字点阵
//	private void init() {
//		for (int i = 0; i < 16; i++) {
//			for (int j = 0; j < 16; j++) {
//				//前两个位置参数做简单的处理，方便后面加入面板的时候，后面两个参数是矩形的宽和高
//				this.c_arrays[i][j] = new Rectangle(i, j, width, width);
//				this.c_arrays[i][j].setFill(Color.WHITE);
//			}
//		}
//	}
	//获取汉字点阵的byte[]
	private byte[] setChineseArray(String str) {
		byte[] chineseInfo = new byte[32];
		try {
			byte[] chineseArray = str.getBytes("GB2312");
			int qh = (chineseArray[0] - 0xa0) & 0xff;//区号
			int wh = (chineseArray[1] - 0xa0) & 0xff;//位号
			
			long offset = (94 * (qh - 1) + (wh - 1)) * 32;
			FileInputStream in = new FileInputStream("HZK16.txt");
			in.skip(offset);
			in.read(chineseInfo);
			
			in.close();
		}catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
		return chineseInfo;
	}
	//将第index个汉字进行点阵化
	private void setChineseNode() {
		int x = 0;
//int total = 0;
		//循环进行汉字点阵化
		this.index = this.index < this.length ? this.index : 0;
//System.out.println(this.index);
		char c = this.chinese.charAt(index);
//System.out.println(c);
		this.index++;
		byte[] chineseArray = this.setChineseArray(c+ ""); 
		
		if (chineseArray != null) {
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 2; j++) {
					for (int k = 0; k < 8; k++) {
						x = j * 8 + k;
						if ((chineseArray[i * 2 + j] & 128 >> k) != 0) {
							//????什么情况
							Rectangle rec = new Rectangle(x, i, width, width);
							rec.setFill(color);
							this.c_arrays[x][i] = rec;
						}
						else//这个是必须的，不然就很尴尬的，在Snake_Control就无法忽略前一个汉字点阵占用的位置
							this.c_arrays[x][i] = null;
					}
				}
			}
		}
	}
	public Rectangle getRandomRec() {
		Rectangle rec = new Rectangle(1, 1, width, width);
		rec.setFill(color);
		return rec;
	}
	//获取第index已经填充的矩形点阵数组
	public Rectangle[][] getNextArrays(){
		this.setChineseNode();
		return this.c_arrays;
	}
}

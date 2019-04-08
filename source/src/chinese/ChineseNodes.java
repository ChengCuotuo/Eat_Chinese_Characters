package chinese;

import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.paint.Color;
import java.io.Serializable;

public class ChineseNodes implements Serializable{
	private static final long serialVersionUID = 1L;
	//�������һ�������룬Ȼ������Ҳ�ǿ����Զ����ɵġ����ӵĻ�eclipse������档
	private String chinese;
	private Rectangle[][] c_arrays;//��ź��ֵ���ľ���
	private Color color = Color.BLUE;//����������ɫ
	private int index;//ȷ����ǰ����
	private int length;// ���ֳ���
	private int width;//���ֵ�����εĿ��
	
	public ChineseNodes() {
		this("��");
	}
	//�ı�����ĺ�������
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
	//�������ڳ�ʼ�����ַ����Ƿ��Ǻ���
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
			return "��";
	}
//	//��ʼ�����ֵ���
//	private void init() {
//		for (int i = 0; i < 16; i++) {
//			for (int j = 0; j < 16; j++) {
//				//ǰ����λ�ò������򵥵Ĵ�����������������ʱ�򣬺������������Ǿ��εĿ�͸�
//				this.c_arrays[i][j] = new Rectangle(i, j, width, width);
//				this.c_arrays[i][j].setFill(Color.WHITE);
//			}
//		}
//	}
	//��ȡ���ֵ����byte[]
	private byte[] setChineseArray(String str) {
		byte[] chineseInfo = new byte[32];
		try {
			byte[] chineseArray = str.getBytes("GB2312");
			int qh = (chineseArray[0] - 0xa0) & 0xff;//����
			int wh = (chineseArray[1] - 0xa0) & 0xff;//λ��
			
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
	//����index�����ֽ��е���
	private void setChineseNode() {
		int x = 0;
//int total = 0;
		//ѭ�����к��ֵ���
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
							//????ʲô���
							Rectangle rec = new Rectangle(x, i, width, width);
							rec.setFill(color);
							this.c_arrays[x][i] = rec;
						}
						else//����Ǳ���ģ���Ȼ�ͺ����εģ���Snake_Control���޷�����ǰһ�����ֵ���ռ�õ�λ��
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
	//��ȡ��index�Ѿ����ľ��ε�������
	public Rectangle[][] getNextArrays(){
		this.setChineseNode();
		return this.c_arrays;
	}
}

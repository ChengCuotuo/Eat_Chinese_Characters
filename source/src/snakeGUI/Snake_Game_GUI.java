package snakeGUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import javafx.geometry.Insets;

import java.util.LinkedList;
import snake.Snake_Control;
import snake.Snake_Body;

public class Snake_Game_GUI extends Application{
	private Timeline chinese_animation;
	private Timeline nodes_animation;
	private boolean controllable;
	//��֤��һ��ʱ������ֻ��ִ��һ�α䷽��Ĳ�����
	//����ͨ��Snake_Contrl�е��ж���һ���ƶ��Ƿ���Snake����ķ������п��ܷ���false
	//���磬̰�������������ƶ������ٰ�S��A��Ҳ�ͱ�ʾ����ͷ��ԭλ�ò�ת��������ĵڶ���λ�ã����Ǵ����
	private Button bt_Start; 
	private Label lb_Explain = new Label("���뺺��");
	private TextField tf_Chinese = new TextField();
	private Button bt_Chinese = new Button("ȷ��");
	private ComboBox<String> cb_Style = new ComboBox<>();
	private ComboBox<Double> cb_Speed = new ComboBox<>();
	private BorderPane bp_Game = new BorderPane();
	private Pane mainPane;
	private HBox hb_Control = new HBox(5);
	
//���Ҫ��һ���ӿڣ����Ըı�sc�еĺ��֣������Ϳ��԰����û�������������Ӧ�ĺ��ֵ���
//��ʱ��Ĭ��״̬��"�������"
	private Snake_Control sc = new Snake_Control();
	private MainPane mp = new MainPane();
	
	private LinkedList<Snake_Body> body = sc.getBody();
	private LinkedList<Snake_Body> chineseList = sc.getChineseList();
	
	private Label lb_info = new Label();
	@Override
	public void start(Stage primaryStage) {
		setUp();
		bt_Chinese_Action();
		setAction();
		hb_Control.getChildren().addAll(cb_Style, cb_Speed, bt_Start, lb_Explain, tf_Chinese, bt_Chinese);
		mainPane = mp.getMainPane();
		mp.deleteLines();
		
		initMainPane();
		bp_Game.setStyle("-fx-border-color: blue");
		bp_Game.setPadding(new Insets(2, 2, 2, 2));
		bp_Game.setCenter(mainPane);
		bp_Game.setTop(hb_Control);
		
		chinese_animation();
		nodes_animation();
		
		Scene scene = new Scene(bp_Game, 430, 430);
		primaryStage.setScene(scene);
		primaryStage.setTitle("��������֮���������_������");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	//��ʼ����壬��ӵ�һ�����ֵ��󣬺ͳ�ʼ��̰����	
	public void initMainPane() {
		for (int i = 0; i < chineseList.size(); i++) {
			mainPane.getChildren().add(chineseList.get(i).getRc());
		}
		
		for (int i = 0; i < body.size(); i++) {
			mainPane.getChildren().add(body.get(i).getRc());
		}
	}
	//�������
	public void setUp() {
		bt_Start = new Button("Start");
		bt_Start.setStyle("-fx-color: red");
		bt_Start.setPrefWidth(100);
		
		cb_Style.setStyle("-fx-color: blue");
		cb_Style.getItems().addAll("����", "����");
		cb_Style.setValue("����");
		
		cb_Speed.setStyle("-fx-color: blue");
		cb_Speed.getItems().addAll(1.0, 2.0, 3.0, 4.0, 5.0);
		cb_Speed.setValue(1.0);
		
		tf_Chinese.setPrefColumnCount(6);
		bt_Chinese.setStyle("-fx-color: blue");
	}
	public void bt_Chinese_Action() {
		bt_Chinese.setOnAction(e ->{
			String info = tf_Chinese.getText();
			if ((info != null) && (!info.equals(""))) {
				sc.setChinese(info);
				chinese_remove();
				sc.setChineseList();
				add(false, 1);
			}
		});
	}

	//�������
	public void setAction() {
		cb_Style.setOnAction(e -> {
			if (!bt_Start.getText().equals("Pause")) {
				if (bt_Start.getText().equals("Finish")) {
					mainPane.getChildren().remove(lb_info);
				}
				if (cb_Style.getValue().equals("����")) {
					cb_Speed.setValue(1.0);
					bt_Start.setText("Start");
					eatNodes();
					hb_Control.getChildren().removeAll(lb_Explain, tf_Chinese, bt_Chinese);
	//System.out.println("����");
				}
				if (cb_Style.getValue().equals("����")) {
					cb_Speed.setValue(1.0);
					bt_Start.setText("Start");
					eatChineseNodes();
					hb_Control.getChildren().addAll(lb_Explain, tf_Chinese, bt_Chinese);
				}
			}
			
		});
		
		//�ٶȸ�ѡ��ȷ��̰�����ƶ����ٶ�
		cb_Speed.setOnAction(e -> {
			if (cb_Style.getValue().equals("����")) {
				chinese_animation.setRate(cb_Speed.getValue());
			}
			if (cb_Style.getValue().equals("����")) {
				nodes_animation.setRate(cb_Speed.getValue());
			}
		});
		
		//һ������������Ϸ��ʼ����ͣ�İ�ť
		bt_Start.setOnAction(e -> {
			if (cb_Style.getValue().equals("����")) {
				if (bt_Start.getText() == "Start") {
					chinese_animation.play();
					bt_Start.setText("Pause");
					tf_Chinese.setEditable(false);
				}
				else if (bt_Start.getText() == "Pause") {
					chinese_animation.pause();
					bt_Start.setText("Start");
					tf_Chinese.setEditable(true);
				}
				else if (bt_Start.getText() == "Finish") {
					mainPane.getChildren().remove(lb_info);
					cb_Speed.setValue(1.0);
					bt_Start.setText("Start");
					eatChineseNodes();
				}	
			}
			else if (cb_Style.getValue().equals("����")) {
				sc.setRandomNode();
				if (bt_Start.getText() == "Start") {
					nodes_animation.play();
					bt_Start.setText("Pause");
				}
				else if (bt_Start.getText() == "Pause") {
					nodes_animation.pause();
					bt_Start.setText("Start");
				}
				else if(bt_Start.getText() == "Finish") {
					mainPane.getChildren().remove(lb_info);
					cb_Speed.setValue(1.0);
					bt_Start.setText("Start");
					eatNodes();
				}
			}
		});
		//ͨ�����̵��������Ҽ�����̰���ߵ��˶�����
		bp_Game.setOnKeyPressed(e -> {
			if ((bt_Start.getText() == "Pause") && (controllable == true)) {
				switch(e.getCode()) {
				case W:
					sc.setDirection(0); break;
				case D:
					sc.setDirection(1); break;
				case S:
					sc.setDirection(2); break;
				case A:
					sc.setDirection(3); break;
				default:
				}
			}
			controllable = false;
		});
	}
	//����ģʽ���������
	public void eatNodes() {
		chinese_remove();
		sc.clear();	
//System.out.println(chineseList.size());
		initMainPane();
		chinese_animation();
	}
	
	//��ģʽ���Ժ��ֵ������еľ��巽����û�����
	public void eatChineseNodes() {
		nodes_remove();
		sc.startSet();
		initMainPane();
		chinese_animation();
	}

	//����ʱ�䶯��
	public void chinese_animation() {
		chinese_animation = new Timeline(new KeyFrame(Duration.millis(700), e ->  {
			controllable = true;
			chinese_remove();
			add(true, 1);
		}));
	
		chinese_animation.setCycleCount(Timeline.INDEFINITE);
	}
	//�����ʱ�䶯��
	public void nodes_animation() {
		nodes_animation = new Timeline(new KeyFrame(Duration.millis(700), e ->  {
			controllable = true;
			nodes_remove();
			nodes_add(true);
		}));
	
		nodes_animation.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void nodes_remove() {
		chinese_remove();
	}
	
	public void nodes_add(boolean move) {
		add(move, 2);
	}
	//�����ǰ����еĺ��ֵ����̰����
	private void chinese_remove() {
		for (int i = 0; i < chineseList.size(); i++) {
			mainPane.getChildren().remove(chineseList.get(i).getRc());
		}
		
		for (int i = 0; i < body.size(); i++) {
			mainPane.getChildren().remove(body.get(i).getRc());
		}
	}
	//����ǰ�ĺ��ֵ����̰������ʾ�������
	private void add(boolean move, int choose) {
		boolean change = true;
		if (move) {
			change = sc.moveAsDirection();
		}
		if (change == false) {
			if (choose == 1) {
				chinese_animation.stop();
			}
			if (choose == 2) {
				nodes_animation.stop();
			}
			
			bt_Start.setText("Finish");
			lb_info.setText("��ǰ�÷֣� " + body.size() * 10 + "");
			mainPane.getChildren().add(lb_info);
		}
		else {
			for (int i = 0; i < chineseList.size(); i++) {
				mainPane.getChildren().add(chineseList.get(i).getRc());
			}
			
			for (int i = 0; i < body.size(); i++) {
				mainPane.getChildren().add(body.get(i).getRc());
			}
			//����ʾ�ĺ��ֵ��󱻳���֮�󣬾���ʾ��һ�����ֵ���
			//��ChineseNodes�����õķ�ʽ�ǽ���ѭ��չʾ��ʼ����ĺ���
			if (chineseList.size() == 0) {
				if (choose == 1) {
					sc.setNextChineseList();
				}
				if (choose == 2) {
					sc.setRandomNode();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}

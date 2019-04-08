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
	//保证在一个时间间隔内只能执行一次变方向的操作，
	//否则，通过Snake_Contrl中的判断下一次移动是否是Snake身体的方法就有可能返回false
	//比如，贪吃蛇正在向右移动，快速按S和A，也就表示，蛇头在原位置并转向了身体的第二个位置，这是错误的
	private Button bt_Start; 
	private Label lb_Explain = new Label("输入汉字");
	private TextField tf_Chinese = new TextField();
	private Button bt_Chinese = new Button("确定");
	private ComboBox<String> cb_Style = new ComboBox<>();
	private ComboBox<Double> cb_Speed = new ComboBox<>();
	private BorderPane bp_Game = new BorderPane();
	private Pane mainPane;
	private HBox hb_Control = new HBox(5);
	
//这个要有一个接口，可以改变sc中的汉字，这样就可以按照用户的输入产生相对应的汉字点阵
//此时，默认状态的"中秋快乐"
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
		primaryStage.setTitle("闲着无聊之离乡过中秋_碾作尘");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	//初始化面板，添加第一个汉字点阵，和初始的贪吃蛇	
	public void initMainPane() {
		for (int i = 0; i < chineseList.size(); i++) {
			mainPane.getChildren().add(chineseList.get(i).getRc());
		}
		
		for (int i = 0; i < body.size(); i++) {
			mainPane.getChildren().add(body.get(i).getRc());
		}
	}
	//配置组件
	public void setUp() {
		bt_Start = new Button("Start");
		bt_Start.setStyle("-fx-color: red");
		bt_Start.setPrefWidth(100);
		
		cb_Style.setStyle("-fx-color: blue");
		cb_Style.getItems().addAll("汉字", "经典");
		cb_Style.setValue("汉字");
		
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

	//组件功能
	public void setAction() {
		cb_Style.setOnAction(e -> {
			if (!bt_Start.getText().equals("Pause")) {
				if (bt_Start.getText().equals("Finish")) {
					mainPane.getChildren().remove(lb_info);
				}
				if (cb_Style.getValue().equals("经典")) {
					cb_Speed.setValue(1.0);
					bt_Start.setText("Start");
					eatNodes();
					hb_Control.getChildren().removeAll(lb_Explain, tf_Chinese, bt_Chinese);
	//System.out.println("经典");
				}
				if (cb_Style.getValue().equals("汉字")) {
					cb_Speed.setValue(1.0);
					bt_Start.setText("Start");
					eatChineseNodes();
					hb_Control.getChildren().addAll(lb_Explain, tf_Chinese, bt_Chinese);
				}
			}
			
		});
		
		//速度复选框，确定贪吃蛇移动的速度
		cb_Speed.setOnAction(e -> {
			if (cb_Style.getValue().equals("汉字")) {
				chinese_animation.setRate(cb_Speed.getValue());
			}
			if (cb_Style.getValue().equals("经典")) {
				nodes_animation.setRate(cb_Speed.getValue());
			}
		});
		
		//一个用来控制游戏开始和暂停的按钮
		bt_Start.setOnAction(e -> {
			if (cb_Style.getValue().equals("汉字")) {
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
			else if (cb_Style.getValue().equals("经典")) {
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
		//通过键盘的上下左右键控制贪吃蛇的运动方向
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
	//经典模式，吃随机点
	public void eatNodes() {
		chinese_remove();
		sc.clear();	
//System.out.println(chineseList.size());
		initMainPane();
		chinese_animation();
	}
	
	//新模式，吃汉字点阵，其中的具体方法还没有完成
	public void eatChineseNodes() {
		nodes_remove();
		sc.startSet();
		initMainPane();
		chinese_animation();
	}

	//汉字时间动画
	public void chinese_animation() {
		chinese_animation = new Timeline(new KeyFrame(Duration.millis(700), e ->  {
			controllable = true;
			chinese_remove();
			add(true, 1);
		}));
	
		chinese_animation.setCycleCount(Timeline.INDEFINITE);
	}
	//随机点时间动画
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
	//清除当前面板中的汉字点阵和贪吃蛇
	private void chinese_remove() {
		for (int i = 0; i < chineseList.size(); i++) {
			mainPane.getChildren().remove(chineseList.get(i).getRc());
		}
		
		for (int i = 0; i < body.size(); i++) {
			mainPane.getChildren().remove(body.get(i).getRc());
		}
	}
	//将当前的汉字点阵和贪吃蛇显示到面板中
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
			lb_info.setText("当前得分： " + body.size() * 10 + "");
			mainPane.getChildren().add(lb_info);
		}
		else {
			for (int i = 0; i < chineseList.size(); i++) {
				mainPane.getChildren().add(chineseList.get(i).getRc());
			}
			
			for (int i = 0; i < body.size(); i++) {
				mainPane.getChildren().add(body.get(i).getRc());
			}
			//当显示的汉字点阵被吃完之后，就显示下一个汉字点阵，
			//在ChineseNodes中设置的方式是进行循环展示开始输入的汉字
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

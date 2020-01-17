package application;

import java.util.ArrayList;
import java.util.Optional;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

@SuppressWarnings("restriction")
public class SimulatorView  extends BorderPane {
	private Pane battleField = new Pane();
	// 여러 컨테이너들을 배치하기 위한 Pane
	private TableView<Process> processTable;
	// 입력된 processData를 표 형태로 가시화하기 위한 TableView
	private ObservableList<Process> processData = FXCollections.observableArrayList();
	// 사용자에게 프로세스의 정보를 입력받을 ObservableList
	private final HBox hboxFirst1 = new HBox(10);
	// tableName, menuBtn를 저장하여 수평으로 배치할 HBox 
	private final HBox hboxFirst2 = new HBox(10);
	// modMessage, modInfo를 저장하여 수평으로 배치할  HBox
	private final HBox hboxFirstTotal = new HBox();
	// hboxFirst1, hboxFirst2를 저장하여 수평으로 배치할 HBox
	private final HBox hboxSecond = new HBox(15);
	// MainIcon과 processTable를 저장하여 수평으로 배치할 HBox 
	private final HBox hboxThird1 = new HBox(4);
	// addProcessName, addAT, addBT, addBtn, subShot, subMilk, subChoco, submitBtn을 저장하여 수평으로 배치할 HBox
	private final HBox hboxThird2 = new HBox(4);
	// startBtn을 저장하는 HBox
	private final HBox hboxThirdTotal = new HBox(210);
	// hboxThird1, hboxThird2를 저장하여 수평으로 배치할 HBox
	private final HBox hboxFourth = new HBox();
	// base rectangle을 저장하는 HBox  	
	private final VBox vbox = new VBox();
	// hboxFirstTotal, hboxSecond, hboxThirdTotal, hboxFourth를 저장하여 수직으로 배치할 VBox
	private final int SPACEHEIGHT = 600;
	// battleField의 가로를 지정하는 변수
	private final int SPACEWIDTH = 672;
	// battleField의 세로를 지정하는 변수
	private Integer choose;
	// 선택된 스케줄링 기법을 저장하는 변수
	private int PN;
	// 프로세스의 수를 저장하는 변수 
	private int TQ;
	// RR 스케줄링 기법에서 입력된 제한 시간을 저장하는 변수
	private int shot;
	// 한 번에 만드는 shot의 개수를 저장하는 변수
	private int milk;
	// 한 번에 만드는 milk의 개수를 저장하는 변수
	private int choco;
	// 한 번에 만드는 choco의 개수를 저장하는 변수
	private ImageView Mainicon;
	// Mainicon으로 설정할 이미지를 저장하는 변수 
	private ObservableList<Label> now = FXCollections.observableArrayList();
	// 각각의 프로세스들이 얼마나 작업했는지 시간들을 가시화하기 위한 ObservableList
	private ObservableList<Label> name = FXCollections.observableArrayList();
	// 각각의 프로세스들의 Name들을 그래프 내부에 가시화하기 위한 ObservableList
	private ObservableList<Rectangle> box = FXCollections.observableArrayList();
	// 각각의 프로세스들의 작업 순서를 그래프로 가시화하기 위한 ObservableList

	public void simulatorConfirmDialog(String title, String header, String content){ 
		// 프로그램 실행 시 첫 번째 화면을 설계하는 함수
		Alert alert = new Alert(AlertType.CONFIRMATION); 
		// AlertType을 CONFIRMATION으로 하여 alert를 생성한다.
		// CONFIRMATION : 사용자의 확인을 받는 대화 방식
		alert.setTitle(title); 
		// alert의 Pane의 제목은 title로 설정한다.
		alert.setHeaderText(header); 
		// alert의 상단 부분은 header로 설정한다.
		alert.setContentText(content);
		// alert의 하단 부분은 content로 설정한다.

		ImageView icon = new ImageView(new Image("application/OS_icon.png"));
		// 첫 번째 화면에 전시할 이미지를 icon에 저장하여 생성한다.
		icon.setFitHeight(80);
		// icon의 세로를 80으로 설정한다.
		icon.setPreserveRatio(true);
		// icon의 가로, 세로 비율을 유지하여 가로를 조절한다.
		alert.setGraphic(icon);
		// icon을 alert의 그래픽으로 설정한다.

		Optional<ButtonType> result = alert.showAndWait(); 
		// alert을 전시하며 사용자의 Confirmation을 기다린다.
		// Confirmation의 결과를 result에 저장한다.
		if (!result.get().equals(ButtonType.OK)){ 
			// result가 OK가 아니라면
			System.exit(0); 
			// application을 종료한다.
		}
	}
	/*
1. simulatorConfirmDialog는 프로그램 실행 시 가장 처음 나오는 화면을 설계하는 함수이다.
2. AlertType을 CONFIRMATION으로 하여 alert를 생성한다.
3. alert의 title, header, content를 화면에 전시 한다.
4. icon의 가로, 세로를 설정하여 화면에 전시한다.
5. 사용자의 Confirmation 결과를 result에 저장하여 OK가 아니라면 application을 종료한다.
	 */

	public void init() {
		// 프로그램 메인 화면을 설계하는 함수
		// 프로그램을 재시작 하지 않아도 초기 실행 상태로 되돌릴 수 있도록 정의한다.
		choose = -1; 
		// init 호출 시 프로그램을 재시작 하지 않아도 스케줄링 기법을 선택하지 않은 상태로 만들기 위해 choose에 –1을 저장한다.
		PN = 0; 
		// init 호출 시 프로그램을 재시작 하지 않아도 초기 실행 상태로 되돌리기 위해 등록된 프로세스의 수 PN을 0으로 초기화 한다.
		processTable = new TableView<>(); 
		// init 호출 시 프로그램을 재시작 하지 않아도 초기 상태로 테이블을 설정하여 다시 Start를 눌러 진행하더라도 테이블이 하나만 존재하게 만들기 위해 Table을 새로 생성한다.

		battleField.setPrefSize(SPACEWIDTH, SPACEHEIGHT); 	
		// battleField의 가로, 세로를 설정한다.
		battleField.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		// battleField의 Stylesheet를 설정한다.
		hboxFirstTotal.setSpacing(400);
		// hboxFirstst1, hboxFirst2의 사이의 공백을 400로 설정한다.

		setTable(); 
		// 입력된 프로세스의 데이터를 표 형태로 보여줄 Table을 설정한다.
		setMainIcon(false); 
		// Mainicon을 false 상태로 설정한다.
		// true를 전달하면 Ours용 아이콘이 전시되지만 false를 전달하면 그 외 스케줄링 기법의 아이콘이 전시되며 이를 기본으로 한다. 
		setChoiceBox();
		// 스케줄링 기법을 선택할 수 있는 ChoiceBox를 설정한다.
		setStart(); 
		// Start Button을 설정한다.
		setBaseRectangle();
		// 그래프 전시의 틀이 되는 기본 직사각형을 설정한다.
		setBoxes(); 
		// hbox, vbox 등 컨테이너들을 설정한다.
		setMenu(); 
		// Menu Bar를 설정한다.
		setCenter(battleField); 
		// battleField center로 설정한다.
	}
	/*
1. init 함수는 프로그램 실행 시 메인 화면을 설계 하는 함수로서 프로그램을 재시작 하지 않아도 초기 실행 상태로 되돌릴 수 있도록 정의한 함수이다.
2. choose를 –1, PN을 0, processTable을 새로 생성함으로서 프로그램을 재시작 하지 않아도 초기 실행 상태로 되돌릴 수 있도록 하였다.
3. battleField의 크기, stylesheet을 설정한다.
4. 입력된 프로세스의 데이터를 표 형태로 보여줄 Table을 설정한다.
5. Mainicon을 false 상태로 설정하여 Ours가 아닌 스케줄링 기법의 아이콘이 기본으로 전시되도록 한다.
6. 스케줄링 기법을 선택할 수 있는 ChoiceBox를 설정한다.
7. Start Button을 설정한다.
8. 그래프 전시의 틀이 되는 기본 직사각형을 설정한다.
9. hbox, vbox 등 컨테이너들을 설정한다.
10. Menu Bar를 설정한다.
11. battleField center로 설정한다.
	 */		

	public void simulatorInfoDialog(String title, String header, String content){
		// 지정한 안내문을 대화창의 형태로 화면에 전시하는 함수
		Alert alert = new Alert(AlertType.INFORMATION); 
		// AlertType을 INFORMATION으로 하여 alert를 생성한다.
		// INFORMATION : 사용자에게 정보를 제공하기 위한 AlertType

		alert.setTitle(title);
		// 매개변수로 입력받은 title로 alert의 Title을 설정한다.
		alert.setHeaderText(header);
		// 매개변수로 입력받은 header로 alert HeaderText를 설정한다.
		alert.setContentText(content);
		// 매개변수로 입력받은 content로 alert의 ContentText를 설정한다.

		ImageView icon = new ImageView(new Image("application/warning.png")); 
		// alert에 전시할 이미지를 icon에 저장하여 생성한다.
		icon.setFitHeight(150); 
		// icon의 세로를 150으로 설정한다.
		icon.setPreserveRatio(true); 
		// icon의 가로, 세로 비율을 유지하여 가로를 조절한다.
		alert.setGraphic(icon); 
		// icon을 alert의 그래픽으로 설정한다.

		alert.showAndWait(); 
		// alert를 전시하며 사용자의 확인을 기다린다.
	}
	/*
1. simulatorInfoDialog 함수는 지정한 안내문을 대화창의 형태로 화면에 전시하는 함수이다.
2. AlertType을 INFORMATION으로 하여 alert를 생성한다.
3. 매개변수로 입력받은 title, header, content로 alert의 Title, HeaderText, ContentText로 설정한다.
4. alert에 전시할 이미지를 icon에 저장하여 생성한다.
5. icon의 세로를 150으로 설정한 후 가로, 세로 비율을 유지하여 가로를 조절한다.
6. icon을 alert의 그래픽으로 설정한다.
7. alert를 전시하며 사용자의 확인을 기다린다.
	 */

	public void setGantt(ArrayList<Integer> processSequence, ArrayList<Integer> processRuntime, ArrayList<String> processName) { 
		// 입력 값과 스케줄링 기법에 따라 그래프를 가시화하는 함수
		int time = 0;
		// 현재 시간을 저장하는 변수
		int totalTime = 0;
		// 전체 시간을 저장하는 변수

		battleField.getChildren().removeAll(now);
		// 그래프 화면을 초기화하기 위해 battleField에서 now를 제거한다.
		battleField.getChildren().removeAll(name);
		// 그래프 화면을 초기화하기 위해 battleField에서 name을 제거한다.
		battleField.getChildren().removeAll(box);
		// 그래프 화면을 초기화하기 위해 battleField에서 box를 제거한다.
		box.clear();
		// box의 데이터를 모두 제거한다.
		name.clear();
		// name의 데이터를 모두 제거한다.
		now.clear();
		// now의 데이터를 모두 제거한다.

		now.add(new Label("0"));
		// now에 처음 시간을 의미하는 “0” Label을 추가한다.
		now.get(0).setLayoutX(25);
		// now의 첫 번째 원소에 대해 가로를 25로 설정한다.
		now.get(0).setLayoutY(SPACEHEIGHT-195);
		// now의 첫 번째 원소에 대해 세로를 SPACEHEIGHT-195로 설정한다.

		for(int i=0; i<processSequence.size(); i++) {
			// 전체 시간 계산을 위해 processSequence의 크기만큼 반복한다.
			totalTime += processRuntime.get(i);
			// processRuntime은 각 프로세스들의 Burst Time이 저장 되어 있으며, 이 원소들의 합이 totalTime(전체 시간)이 된다.
		}

		for(int i=0; i<processSequence.size(); i++) {
			// 모든 작업수행에 대해 Rectangle, Label 생성 및 작업 순서를 명시해야하므로 processSequence의 크기만큼 반복한다.
			box.add(new Rectangle(processRuntime.get(i)*630.0/totalTime, 50));
			// 프로세스들의 작업 순서를 명시하기 위해 box리스트에 사각형을 추가한다.
			// 사각형의 가로는 processRuntime의 index*630.0/totalTime으로 설정한다.
			// 그래프의 가로의 길이를 630으로 설정했기 때문에 전체 시간을 기준으로 프로세스들이 각각 얼마만큼 일했는지 비율에 맞춰 가시화하기 위함이다. 
			// 사각형의 세로는 50으로 지정한다.

			if(processSequence.get(i)==-1)
				// processSequence의 원소가 -1이라는 것은 해당 순서에서 어떠한 작업도 수행하지 않음을 의미한다.
				box.get(i).setFill(Color.WHITE);
			// -1인 원소에 대해 해당 순서 box의 Color를 WHITE로 설정한다.
			else
				// processSequence의 index i에 순서가 지정되어 작업을 수행했다면	
				box.get(i).setFill(Color.rgb((255*(processSequence.get(i)%5+1)*10)%256,
						(255*(processSequence.get(i)%4+1)*processSequence.get(i)*15)%256, 
						(255*processSequence.get(i)*(processSequence.get(i)%7+1)*(processSequence.get(i)+2)*22)%256));
			// box의 index i를 red, blue, green 색들로 조합해 색을 채운다.
			// 작업 순서를 그래프에 노출하는 과정이기 때문에 프로세스마다 색을 달리 하기 위해 processSequence의 index i를 그대로 사용한다.

			box.get(i).setLayoutX(25.0+(time*630.0/totalTime));
			// box의 index i의 가로를 설정한다.
			// time*630.0/totalTime은 현재 시간을 전체 시간으로 나누어 그래프의 비율 분포에 관여한다.
			// 25.0을 더해주는 이유는 now.get(0).setLayoutX에서 가로를 25로 설정해 놓았기 때문이다.(비율 고려)
			box.get(i).setLayoutY(SPACEHEIGHT-175);
			// box의 인덱스 i의 세로를 지정
			// SPACEHEIGHT(600)-175값을 세로로 지정하여 그래프의 비율 분포에 관여함

			time += processRuntime.get(i);
			// 변화 하는 현재 시간을 반영하기 위해 processRuntime의 index i값을 time에 더한다.

			if(i==processSequence.size()-1||processSequence.get(i)!=processSequence.get(i+1)) {
				// 작업을 수행하는 프로세스가 processSequence 상에서 마지막 차례이거나
				// 현재 작업을 수행하는 프로세스의 다음차례가 자기 자신이 아닌 다른 프로세스일 때
				FontLoader fontLoader = Toolkit.getToolkit().getFontLoader(); 
				// Label의 길이를 계산하기 위한 FontLoader

				now.add(new Label(Integer.toString(time)));
				// time(현재시간)을 문자열로 변환하여 now에 Label 형태로 추가한다.
				now.get(now.size()-1).setLayoutX(25.0+(time*630.0/totalTime)
						- fontLoader.computeStringWidth(now.get(now.size()-1).getText(), now.get(now.size()-1).getFont())/2);
				// now의 마지막 원소의 가로를 지정하는 과정이다.
				// (25.0+(time*630.0/totalTime)한 값이 존재하고 box의 가로를 참고한다.
				// now의 마지막 원소에 저장된 텍스트와 폰트를 참조해 가로길이를 계산 한 뒤 2로 나눈 값이 존재한다.
				// 두 값을 뺀 결과를 now의 마지막 원소의 가로로 설정한다.
				now.get(now.size()-1).setLayoutY(SPACEHEIGHT-195);
				// now의 마지막 원소의 세로를 설정하는 과정이다.
				// SPACEHEIGHT(600)-195를 세로로 설정하여 그래프의 비율 분포에 관여한다.

				name.add(new Label(processName.get(i)));
				// name에 processName의 i번째 원소를 Label의 형태로 추가한다.
				name.get(name.size()-1).setLayoutX((now.get(now.size()-2).getLayoutX()+now.get(now.size()-1).getLayoutX())/2
						- fontLoader.computeStringWidth(name.get(name.size()-1).getText(), name.get(name.size()-1).getFont())/2);
				// name의 마지막 원소의 가로를 설정하는 과정이다.
				// now의 마지막에서 두 번째 index의 가로에 마지막 index의 가로를 더한 뒤 2로 나눈 값이 존재한다. 
				// name의 마지막 index에 저장된 텍스트와 폰트를 참조해 가로 길이를 계산한 뒤 2로 나눈 값이 존재한다.
				// 두 값을 뺀 결과를 name의 마지막 원소의 가로로 설정한다.
				// 결과적으로 name은 그래프에 now의 영향을 받아 가운데 정렬된다.
				name.get(name.size()-1).setLayoutY(SPACEHEIGHT-155);
				// name의 마지막 원소의 세로를 설정하는 과정이다.
				// SPACEHEIGHT(600)-155값을 세로로 설정하여 그래프의 비율 분포에 관여한다.
			}
		}
		battleField.getChildren().addAll(box);
		// box의 모든 구성요소들을 battleField에 추가한다.
		battleField.getChildren().addAll(now);
		// now의 모든 구성요소들을 battleField에 추가한다.
		battleField.getChildren().addAll(name);
		// name의 모든 구성요소들을 battleField에 추가한다.
	}
	/*
	1. setGantt 함수는 입력된 스케줄링 기법에 따라 그래프를 가시화 하는 함수이다.
	2. 그래프 화면을 초기화하기 위해 주된 구성 요소인 now, name, box를 battleField에서 제거한다.
	3. now, name, box의 모든 데이터를 제거한다.
	4. now에 처음 시간을 의미하는 “0” Label을 추가한다.
	5. now의 첫 번째 원소에 대해 가로를 25, 세로를 SPACEHEIGHT-195로 설정한다.
	6. 전체 시간 계산을 위해 totalTime 에 processRuntime의 원소를 모두 더한다.
	7. 모든 작업수행에 대해 Rectangle, Label 생성 및 작업 순서를 명시해야하므로 processSequence의 크기만큼 반복한다.
	8. 프로세스들의 작업 순서를 명시하기 위해 box리스트에 사각형을 추가한다.
	   - 사각형의 가로는 processRuntime의 index*630.0/totalTime으로 설정한다.
	   - 그래프의 가로 길이를 630으로 설정했기 때문에 전체 시간을 기준으로 프로세스들이 각각 얼마만큼 일했는지 비율에 맞춰 가시화하기 위함이다.
	   - 사각형의 세로는 50으로 지정한다.
	9. processSequence에 –1인 원소 즉 어떠한 작업도 수행하지 않은 순서에 대해 box의 Color를 WHITE로 설정한다.
	10. processSequence의 index에 순서가 지정되어 작업을 수행했다면 box를 red, blue, green 색들로 조합해 채운다.
	11. box의 i번째 원소에 대해 25.0+(time*630.0/totalTime)로 가로를 설정한다.     
	12. box의 I번째 원소에 대해 SPACEHEIGHT-175로 세로를 설정한다.
	13. 변화하는 현재 시간을 반영하기 위해 processRuntime의 i번째 원소를 time에 더한다.
	14. 작업을 수행하는 프로세스가 processSequence 상에서 마지막 차례이거나, 다음차례가 자기 자신이 아닌 다른 프로세스이면
	   - Label의 길이를 계산하기 위한 FontLoader를 선언한다.
	   - time(현재시간)을 문자열로 변환하여 now에 Label 형태로 추가한다.
	   - now의 마지막 원소의 가로, 세로를 설정한다.
	   - name에 processName의 i번째 원소를 Label의 형태로 추가한다.
	   - name의 마지막 원소의 가로, 세로를 설정한다.
	15. battleField에 box, now, name을 추가한다.
	 */

	private void clearAll() {
		// 여러 box들과 그래프, viewTable과 데이터 모두를 초기화 하는 함수
		hboxFirst1.getChildren().clear();
		// hboxFirst1의 모든 구성요소를 제거한다.
		hboxFirst2.getChildren().clear();
		// hboxFirst2의 모든 구성요소를 제거한다.
		hboxFirstTotal.getChildren().clear();
		// hboxFirstTotal의 모든 구성요소를 제거한다.
		hboxSecond.getChildren().clear();
		// hboxSecond의 모든 구성요소를 제거한다.
		hboxThird1.getChildren().clear();
		// hboxThird1의 모든 구성요소를 제거한다.
		hboxThird2.getChildren().clear();
		// hboxThird2의 모든 구성요소를 제거한다.
		hboxThirdTotal.getChildren().clear();
		// hboxThirdTotal의 모든 구성요소를 제거한다.
		hboxFourth.getChildren().clear();
		// hboxFourth의 모든 구성요소를 제거한다.
		vbox.getChildren().clear();
		// vbox의 모든 구성요소를 제거한다.
		battleField.getChildren().clear();
		// battleField의 모든 구성요소를 제거한다.
		processTable.getColumns().clear();
		// processTable의 모든 구성요소를 제거한다.
		processData.clear();
		// processData를 제거한다.
	}
	/*
	1. clearAll함수는 View를 위한 모든 구성 요소들을 clear 시킨다.
	2. hboxFirst1, 2, Total, hboxSecond, hboxThird1, 2, Total, hboxFourth, vbox, battleField, processTable, processData를 구성하는 요소가 제거 대상이다. 
	 */      

	// Set VeiwTable(ProcessTable)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setTable() {
		// VeiwTable의 구성요소를 설정하는 함수
		final Label tableName = new Label("Process List");
		// Process List를 내용으로 가지는 Label을 생성한다.
		// TableView는 TableColumn들에 의해 만들어진다.
		TableColumn<Process, String> processName = new TableColumn("Process Name");
		// processName은 TableView의 한 열이며, TableView의 첫 줄에 Process Name을 출력한다.
		TableColumn<Process, Integer> AT = new TableColumn("AT");
		// AT는 TableView의 한 열이며, TableView의 첫 줄에 AT를 출력한다.
		TableColumn<Process, Integer> BT = new TableColumn("BT");
		// BT는 TableView의 한 열이며, TableView의 첫 줄에 BT를 출력한다.
		TableColumn<Process, Integer> WT = new TableColumn("WT");
		// WT는 TableView의 한 열이며, TableView의 첫 줄에 WT를 출력한다.
		TableColumn<Process, Integer> TT = new TableColumn("TT");
		// TT는 TableView의 한 열이며, TableView의 첫 줄에 TT를 출력한다.
		TableColumn<Process, Double> NTT = new TableColumn("NTT");
		// NTT는 TableView의 한 열이며, TableView의 첫 줄에 NTT를 출력한다.
		TableColumn<Process, Process> Del = new TableColumn("Delete");
		// Del은 TableView의 한 열이며, TableView의 첫 줄에 Delete를 출력한다.
		// Del은 입력된 프로세스 자체를 삭제하는 것에 관여한다.

		processName.setPrefWidth(100);
		// TableColumn 중 processName의 가로를 100으로 설정한다.
		processName.setCellValueFactory(new PropertyValueFactory<>("processName"));
		// “processName”을 통해 단일 TableColumn인 processName에 cell로서 값을 설정한다.
		AT.setPrefWidth(75);
		// AT의 가로를 75로 설정한다.
		AT.setCellValueFactory(new PropertyValueFactory<>("AT"));
		// “AT”을 통해 단일 TableColumn인 AT에 cell로서 값을 설정한다.
		BT.setPrefWidth(75);
		// TableColumn 중 BT의 가로를 75로 설정한다.
		BT.setCellValueFactory(new PropertyValueFactory<>("BT"));
		// “BT”을 통해 단일 TableColumn인 BT에 cell로서 값을 설정한다.
		WT.setPrefWidth(50);
		// TableColumn 중 WT의 가로를 50으로 설정한다.
		WT.setCellValueFactory(new PropertyValueFactory<>("WT"));
		// “WT”을 통해 단일 TableColumn인 WT에 cell로서 값을 설정한다.
		TT.setPrefWidth(50);
		// TableColumn 중 TT의 가로를 50으로 설정한다.
		TT.setCellValueFactory(new PropertyValueFactory<>("TT"));
		// “TT”을 통해 단일 TableColumn인 TT에 cell로서 값을 설정한다.
		NTT.setPrefWidth(75);
		// TableColumn 중 NTT의 가로를 75로 설정한다.
		NTT.setCellValueFactory(new PropertyValueFactory<>("NTT"));
		// “NTT”을 통해 단일 TableColumn인 NTT에 cell로서 값을 설정한다.

		Del.setPrefWidth(75);
		// TableColumn 중 Del의 가로를 75로 설정한다.
		Del.setCellFactory( new Callback<TableColumn<Process, Process>, TableCell<Process, Process>>() {
			// Callback은 콜백을 요구하는 API를 정의하기 위한 인터페이스이며 첫 번째 매개 변수는 두 번째 매개 변수로 호출 method에 전달 된 개체의 형식을 설정하고 두 번째 매개 변수는 메서드의 반환 형식을 설정한다.
			// Callback 인터페이스를 통해 단일 Del의 cell을 설정한다.

			@Override
			public TableCell<Process, Process> call(final TableColumn<Process, Process> param) {
				// DeleteButton을 설정하고 Button을 통해 입력받은 프로세스의 삭제를 제어하는 함수
				final TableCell<Process, Process> cell = new TableCell<Process, Process>() {
					// TableColumn을 위한 TableCell인 cell을 설정한다.
					private final Button delBtn = new Button("Delete");
					// 입력받은 프로세스를 삭제하기 위한 Button이며 Delete를 출력한다.
					@Override
					public void updateItem(Process item, boolean empty) {
						// 해당 행의 Process가 비어있는 지에 따라 item으로 받아올지 말지 여부를 결정하는 함수
						// 해당 행이 비어있지 않을 경우, 해당 cell을 받아와 Process형 변수 item에 넣고 empty를 false로 설정한다. 
						// 해당 행이 비어있을 경우 empty가 true로 설정한다.
						super.updateItem(item, empty);
						// item과 empty의 값을 updateItem 생성자로 초기화한다.
						// (TableCell이 updateItem는 함수 안에 있는 추상 함수이기 때문)
						if(empty) {
							// item이 비어 있을 경우
							setGraphic(null); 
							// 그래픽은 null값(공백)으로 설정한다.
						}
						else {
							// item이 비어있지 않을 경우
							setGraphic(delBtn); 
							// 그래픽으로 delBtn을 설정한다.
							delBtn.setOnAction((ActionEvent event) -> {
								// delBtn의 클릭이벤트가 발생하면
								int selectedItem = getTableRow().getIndex();
								// viewTable에서 삭제하기로 한 프로세스의 모든 item(cell)들이 index로 selectedItem에 저장한다.
								processData.remove(selectedItem);
								// 해당 index의 프로세스를 삭제한다.
								PN--;
								// PN을 감소시킨다.
							});
						}
					}
				};
				return cell;
				// 수정, 변경된 cell 반환한다.
			}
		});

		tableName.setFont(new Font(20));
		// tableName의 폰트 크기를 20으로 설정한다.

		processTable.setPrefSize(500, 200);
		// processTable의 사이즈를 가로를 500, 세로를 200으로 설정한다.
		processTable.setItems(processData);
		// processTable의 processData를 아이템(cell)으로 설정한다.
		processTable.getColumns().addAll(processName, AT, BT, WT, TT, NTT, Del);
		// processTable에 열로 processName, AT, BT, WT, TT, NTT, Del 값을 추가한다.

		hboxFirst1.getChildren().addAll(tableName);
		// tableName을 hboxFirst1의 구성요소로 추가한다.
		hboxSecond.getChildren().add(processTable);
		// processTable을 hboxSecond에 구성요소로 추가한다.
		setAddLine();
		// setAddLine() 함수를 호출하여 값을 입력받기 위한 TextField를 설정한다.
	}
	/* 
	1. setTable함수는 ViewTable의 구성요소로 설정하는 함수이다.
	2. Process List를 내용으로 가지는 tableName Label을 생성한다.
	3. ViewTable의 구성요소들로서 첫 줄에 processName, AT, BT, WT, TT, NTT, Del를 생성한다.
	4. ViewTable의 구성요소들의 가로의 크기와 값을 설정한다.
	5. Del Button을 설정한다. 
	   - Callback 인터페이스를 통해 TableCell() 함수를 호출하여 updateItem() 함수를 Overriding 한다.
	   - TableCell() 함수는 DeleteButton을 설정하고 Button을 통해 입력받은 프로세스의 삭제를 제어하는 함수이다.
	   - updateItem() 함수는 해당 행의 Process가 비어있는 지에 따라 item으로 받아올지 말지 여부를 결정하는 함수이다.
	   - item이 비어있을 경우, 그래픽을 공백으로 설정한다.
	   - item이 비어있지 않을 경우, 그래픽으로 delBtn을 설정한다.
	   - delBtn 클릭 이벤트 발생 시 해당 index의 프로세스를 삭제하고 PN을 감소시킨다.  
	   - 변경된 cell을 반환한다.
	6. tableName의 폰트 크기를 설정한다.
	7. processTable의 크기와 item과 column요소들을 설정한다.
	8. battleField에 배치하기 위해 hboxFirst1에 tableName을 추가한다.
	9. battleField에 배치하기 위해 hboxSecond에 processTable을 추가한다.
	10. setAddLine() 함수를 호출하여 값을 입력받기 위한 TextField를 설정한다.   
	 */ 

	private void setAddLine() {
		// 사용자로부터 ProcessName, AT, BT를 입력받기 위한 TextField를 관리하는 함수
		final Button addBtn = new Button("Add");
		// TextField에 사용자가 입력한 내용을 processData observableList에 추가하기 위한 Add Button을 생성한다.

		final TextField addProcessName = new TextField();
		// ProcessName을 입력할 TextField를 생성한다.
		final TextField addAT = new TextField();
		// AT를 입력할 TextField를 생성한다.
		final TextField addBT = new TextField();
		// BT를 입력할 TextField를 생성한다.

		addProcessName.setPromptText("Process Name");
		// addProcessName에 PromptText를 “Process Name”로 설정한다.
		addProcessName.setPrefWidth(120);
		// addProcessName의 가로를 120으로 설정한다.
		addAT.setPromptText("AT");
		// addAT에 PromptText를 “AT”로 설정한다.
		addAT.setPrefWidth(65);
		// addAT의 가로를 65로 설정한다.
		addBT.setPromptText("BT");
		// addBT의 PromptText를 “BT”로 설정한다.
		addBT.setPrefWidth(65);
		// addBT의 가로를 65로 설정한다.

		hboxThird1.getChildren().addAll(addProcessName, addAT, addBT, addBtn);
		// hboxThird1에 addProcessName, addAT, addBT, addBtn를 추가하여 수평으로 배치한다.

		addBtn.setOnAction((ActionEvent event) -> {
			// addBtn을 클릭했을 때의 Action을 정의한다.
			if(isNum(addAT.getText())&&isNum(addBT.getText())
					&&addProcessName.getText().replaceAll(" ", "").length()>0
					&&Integer.parseInt(addAT.getText())>=0&&Integer.parseInt(addBT.getText())>0) {
				// 데이터 입력의 조건(모두 만족)
				// 1. addAT, addBT에서 입력받은 데이터가 숫자만으로 이루어져 있다.
				// 2. addProcessName에서 입력받은 데이터가 공백을 제외하고 1글자 이상이다.
				// 3. addAT에서 입력받은 숫자는 0 이상이다. addBT에서 입력받은 숫자는 0보다 크다.
				processData.add(new Process(addProcessName.getText(), 
						Integer.parseInt(addAT.getText()), 
						Integer.parseInt(addBT.getText())));
				// 입력의 조건을 모두 만족 한다면 입력한 내용을 멤버로 가지는 process 객체를 생성한다.
				// AT, BT는 int 타입으로 변환 후 초기화한다.

				addProcessName.clear();
				addAT.clear();
				addBT.clear();
				// 등록이 완료되면 addProcessName, addAT, addBT를 모두 빈칸으로 초기화 한다.

				PN++; 
				// PN을 증가시킨다.
			}
			else { simulatorInfoDialog("!!!ERROR!!!", null
					, "Please, Enter the Process Name.\n" + "Also Enter AT and BT only Integers."); }
		});
		// 입력의 조건을 하나라도 만족하지 못한다면 대화창을 띄워 에러문과 조건에 대한 안내문을 출력한다.
	}
	/*
1. setAddLine함수는 사용자로부터 ProcessName, AT, BT를 입력받기 위한 TextField를 관리하는 함수이다.
2. 사용자가 입력한 내용을 processData ObservableList에 추가하기 위한 addBtn을 생성한다.
3. 사용자가 입력할 addProcessName, addAT, addBT TextField를 생성한다.
4. addProcessName, addAT, addBT에 PromptText, Width를 설정한다.
5. addProcessName, addAT, addBT, addBtn을 추가한다.
6. addBtn을 클릭했을 때의 Action을 정의한다.
   - 입력받은 AT, BT가 모두 숫자로만 이루어져 있고 processName이 공백을 제외하고 1글자 이상 입력되었다면 입력받은 내용을 멤버로 가지는 Process 객체를 생성하고 processData에 추가한다.
   - 이 때, AT, BT는 정수형으로 변환하여 저장한다.
   - 등록이 완료되면 addProcessName, addAT, addBT를 모두 빈칸으로 초기화한다.
   - PN을 1 증가시킨다.
   - 입력조건을 만족하지 못한다면 대화창을 띄워 에러문과 조건에 대한 안내문을 출력한다.
	 */

	private void setMainIcon(boolean isOurs) {
		// MainIcon을 설정하는 함수
		try {
			hboxSecond.getChildren().remove(Mainicon);
			// Mainicon이 중복 전시 되는 것을 방지하기 위해 이전에 사용한 hboxSecond에 있는 Mainicon을 제거한다.
		}
		catch(Exception e) {  }
		// Mainicon이 정의 되어있지 않을 경우를 예외처리 한다.

		if(!isOurs) {
			// 스케줄링 기법이 Ours가 아니면
			try {
				Mainicon = new ImageView(new Image("application/Base.gif"));
				// 해당 경우에 맞는 이미지를 Mainicon으로 설정한다.
			}
			catch(Exception e) {  }
			// 이미지가 모듈 안에 존재하지 않으면 예외처리 한다.
		}
		else {
			// 선택한 스케줄링 기법이 Ours면
			try {
				Mainicon = new ImageView(new Image("application/Ours.gif"));
				// 해당 경우에 맞는 이미지를 Mainicon으로 설정한다.
			}
			catch(Exception e) {  }
			// 이미지가 모듈 안에 존재하지 않으면 예외처리 한다.
		}
		try {
			Mainicon.setFitHeight(150);
			// Mainicon의 세로를 150으로 설정한다.
			Mainicon.setPreserveRatio(true);
			// Mainicon의 가로, 세로 비율을 유지하여 가로를 조절한다.
			hboxSecond.getChildren().add(Mainicon);
			// hboxSecond에 Mainicon을 추가한다.
		}
		catch(Exception e) {  }
		// Mainicon이 없어 설정하지 못했다면 예외처리한다.
	}
	/*
1. setMainIcon함수는 MainIcon을 설정하는 함수이다.
2. MainIcon 중복전시를 방지하기 위해 hboxSecond에 기존에 추가되어 있던 Mainicon을 제거한다.
3. 선택한 스케줄링 기법에 맞는 이미지를 메인아이콘으로 설정한다.
4. Mainicon의 세로를 150으로 설정한다.
5. Mainicon의 가로, 세로 비율을 고려하여 가로를 조절한다.
6. hboxSecond에 Mainicon을 추가한다.
	 */

	@SuppressWarnings("rawtypes")
	private void setChoiceBox() { 
		// 스케줄링 기법 종류들을 가시화할 ChoiceBox 설정과 사용자에 의해 스케줄링 기법이 선택되었을 때, 가시화할 요소들을 설정하는 함수
		@SuppressWarnings({ "unchecked" })
		final ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
				"FCFS", "RR", "SPN", "SRTN", "HRRN", "Ours")
				);
		// Mode 이름을 저장하는 observableArrayList를 ChoiceBox 형태로 생성한다.
		final String[] mod = new String[] { "FCFS", "RR", "SPN", "SRTN", "HRRN", "Ours" };
		// Mode의 이름을 저장하는 String 배열 mod를 생성한다.
		final Label modMessage = new Label("Scheduling");
		// modMessage에 ‘Scheduling’이라는 텍스트를 지정하여 Label을 생성한다.
		Label modInfo = new Label();
		// modInfo Label을 생성한다.

		cb.setTooltip(new Tooltip("Select the MOD"));
		// cb ChoiceBox에 마우스를 올리면 “Select the MOD”라는 Tooltip이 나오게 설정한다.
		cb.setPrefWidth(70);
		// cb ChoicBox의 가로를 70으로 설정한다.
		cb.setLayoutX(SPACEWIDTH-cb.getPrefWidth());
		// cb ChoiceBox의 가로위치를 오른쪽 끝에서 Width만큼 지정한다.
		cb.setLayoutY(30);
		// cb ChoiceBox의 세로위치를 위에서 30으로 지정한다.

		modInfo.setLayoutX(cb.getLayoutX()+5);
		// modInfo Label의 가로위치를 cb ChoiceBox 보다 5픽셀 오른쪽으로 설정한다.
		modInfo.setLayoutY(cb.getLayoutY());
		// modInfo Label의 세로위치를 cb ChoiceBox 와 동일하게 설정한다.
		modMessage.setLayoutX(cb.getLayoutX()-70);
		// modMessage Label의 가로위치를 cb ChoiceBox 보다 70픽셀 왼쪽으로 설정한다.
		modMessage.setLayoutY(cb.getLayoutY()+5);
		// modMessage Label의 세로위치를 cb ChoiceBox 보다 5픽셀 아래로 설정한다.

		hboxFirst2.getChildren().addAll(modMessage, cb);
		// hboxFirst2에 modMessage, cb를 추가하여 수평으로 배치한다.
		cb.getSelectionModel().selectedIndexProperty().addListener(
				(ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
					// 사용자가 스케줄링 기법을 선택했을 때 동작을 정의한다.
					choose = new_val.intValue(); 
					// 새롭게 선택한 스케줄링 기법을 int type으로 변환하여 choose에 저장한다.
					hboxFirst2.getChildren().remove(cb);
					// hboxFirst2에서 cb ChoiceBox를 제거
					modInfo.setText(mod[new_val.intValue()]);
					// 사용자가 선택한 스케줄링 기법을 modInfo의 text로 설정한다.
					modInfo.setFont(new Font("Cambria", 18));
					// modInfo의 글자체는 “Cambria”, 글자크기는 18로 설정한다.
					hboxFirst2.getChildren().add(modInfo);
					// modInfo를 hboxFirst에 추가한다.
					if(choose==1) { 
						setTQ(); 
						// 선택한 스케줄링 기법이 RR이라면 setTQ()함수를 호출한다.
					}
					if(choose==5) {
						// 선택한 스케줄링 기법이 Ours라면
						hboxFirst1.getChildren().clear();
						// hboxFirst1에 추가되어 있던 모든 요소를 제거한다.
						hboxFirst2.getChildren().clear();
						// hboxFirst2에 추가되어 있던 모든 요소를 제거한다.
						Label tableName = new Label("Duck's coffee Order List");
						// “Duck’s coffee Order List”라는 tableName Label을 생성한다.
						Button menuBtn = new Button("Menu");
						// 클릭하면 Menu를 보여줄 MenuBtn Button 생성한다.
						tableName.setFont(new Font(20));
						// tableName Label의 글자크기를 20으로 설정한다.
						menuBtn.setOnAction((ActionEvent event) -> {
							// menuBtn을 클릭했을 때
							Stage stage = new Stage();
							Group root = new Group();
							Scene scene = new Scene(root, 690, 517);
							// Menu를 표현할 stage, root, scene 생성한다.

							ImageView icon = new ImageView(new Image("application/menu.png"));
							// icon에 메뉴에 해당하는 이미지를 등록한다.
							root.getChildren().add(icon);
							// icon을 root에 추가한다.

							stage.initStyle(StageStyle.DECORATED);
							// stage의 StageStyle을 DECORATED로 설정한다.
							// (제목 표시줄이 있는 일반적인 윈도우 형태)
							stage.setTitle("Duck's coffee Menu");
							// stage의 Title을 “Duck’s Coffee Menu”로 설정한다.
							stage.setResizable(false);
							// stage의 크기를 변경할 수 없도록 설정한다.
							stage.setScene(scene);
							// root를 담고있는 scene을 stage의 scene으로 설정한다.
							stage.show();
							// stage 전시한다.
						});
						hboxFirst1.getChildren().addAll(tableName, menuBtn);
						// 새로 설정한 tableName, menuBtn를 hboxFirst1에 추가하여 수평으로 배치한다.
						hboxFirst2.getChildren().addAll(modMessage, modInfo);
						// 새로 설정한 modMessage, modInfo를 hboxFirst2에 추가하여 수평으로 배치한다.

						setMainIcon(true);
						// Mainicon을 Ours인 현재 스케줄링 기법에 맞게 변경하기 위해 true를 전달하여 호출한다.

						hboxThird1.getChildren().clear();
						// 기존에 있던 hboxThird1의 모든 요소를 제거한다. 
						// 이전의 스케줄링 기법이 Ours일 경우 subShot, subMilk, subChoco가 남아있는 것을 방지한다.
						setAddLine();
						// addBtn, addProcessName, addAT, addBT의 생성 및 정의를 위해 setAddLine() 호출한다.

						final Button submitBtn = new Button("Submit");
						// submitBtn을 생성한다.

						final TextField subShot = new TextField();
						// Shot을 입력받을 subShot TextField를 생성한다.
						final TextField subMilk = new TextField();
						// Milk를 입력받을 subMilk TextField를 생성한다.
						final TextField subChoco = new TextField();
						// Choco를 입력받을 subChoco TextField를 생성한다.

						// Set TextField PromptText and Width
						subShot.setPromptText("Shot");
						// subShot의 PromptText를 “Shot”으로 설정한다.
						subShot.setPrefWidth(65);
						// subShot의 가로를 65로 설정한다.
						subMilk.setPromptText("Milk");
						// subMilk의 PromptText를 “Milk”으로 설정한다.
						subMilk.setPrefWidth(65);
						// subMilk의 가로를 65으로 설정한다.
						subChoco.setPromptText("Choco");
						// subChoco의 PromptText를 “Choco”로 설정한다.
						subChoco.setPrefWidth(65);
						// subChoco의 가로를 65로 설정한다.

						hboxThird1.getChildren().addAll(subShot, subMilk, subChoco, submitBtn);
						// 생성한 subShot, subMilk, subChoco, submitBtn을 hboxThird1에 추가한다.
						hboxFirstTotal.setSpacing(223);
						// hboxFirstst1, hboxFirst2의 사이의 공백을 223로 설정한다.

						submitBtn.setOnAction((ActionEvent event) -> {
							// submitBtn을 클릭했을 때 동작을 정의한다.
							if(isNum(subShot.getText())&&isNum(subMilk.getText())&&isNum(subChoco.getText())
									&&Integer.parseInt(subShot.getText())>0&&Integer.parseInt(subMilk.getText())>0
									&&Integer.parseInt(subChoco.getText())>0) {
								// subShot, subMilk, subChoco의 입력조건을 모두 만족하는 경우
								// (모두 0을 초과하는 숫자로 입력되어야 한다.)
								hboxThird1.getChildren().removeAll(subShot, subMilk, subChoco, submitBtn);
								// hboxThird의 subShot, subMilk, subChoco TextField, submitBtn을 제거한다.
								shot = Integer.parseInt(subShot.getText());
								// subShot에서 입력받은 내용을 정수로 변환하여 멤버 shot에 저장한다.
								milk = Integer.parseInt(subMilk.getText());
								// subMilk에서 입력받은 내용을 정수로 변환하여 멤버 milk에 저장한다.
								choco = Integer.parseInt(subChoco.getText());
								// subChoco에서 입력받은 내용을 정수로 변환하여 멤버 choco에 저장한다.
							}
						});
					}
				});
	}
	/*
1. setChoiceBox함수는 스케줄링 기법 종류들을 가시화할 ChoiceBox 설정과 사용자에 의해 스케줄링 기법이 선택되었을 때, 가시화할 요소들을 설정하는 함수이다.
2. Mode 이름을 저장하고 있는 ObservableArrayList를 ChoiceBox cb로 만든다.
3. Mode 이름을 저장하고 있는 Mod String 배열 생성한다.
4. “Scheduling” modMessage Label 생성한다.
5. 사용자가 선택한 스케줄링 기법을 보여줄 modInfo Label 생성한다.
6. cb ChoiceBox에 마우스를 올리면 “Select the Mod” 라는 Tooltip을 보여주도록 설정한다.
7. cb ChoiceBox의 Width, 위치(LayoutX, LayoutY)를 설정한다.
8. modInfo, modMessage의 위치(LayoutX, LayoutY)를 설정한다.
9. hboxFirst2에 modMessage, cb를 추가한다.
10. 사용자가 스케줄링 기법기법을 선택했을 때의 행동을 정의한다.
   - choose에 새로 선택한 스케줄링 기법의 번호를 저장한다.
   - hboxFirst2에서 기존에 추가되었던 cb ChoiceBox를 제거한다.
   - modInfo에 선택한 스케줄링 기법의 이름을 지정하고, 글꼴을 “Cambria”, 크기를 18로 한다.
   - hboxFirst2에 modInfo를 추가한다.
11. 스케줄링 기법 별 추가행동을 정의한다.
12. 선택한 스케줄링 기법이 RR인 경우 setTQ()를 호출한다.
13. 선택한 스케줄링 기법이 Ours인 경우
   - 기존에 추가되었던 hboxFirst의 모든 요소를 제거한다.
	 * 이전의 스케줄링 기법이 Ours였을 경우 subShot, subMilk, subChoco가 계속 남아 있는 것을 방지한다.
   - “Duck’s coffe Order List”라는 새로운 tableName Label을 생성한다.
   - menuBtn을 생성한다.
   - tableName의 글자크기를 20으로 설정한다.
   - menuBtn을 클릭했을 때 행동을 정의한다.
	 * 새 창을 띄워 Menu를 보여주기 위해 stage, root, scene을 생성한다.
	 * “application/menu.png”를 전시할 icon Imageview를 생성한다.
	 * root에 icon을 추가한다.
	 * StageStyle은 UNDECORATED(제목표시줄이 있는 일반 윈도우창 형태)로 설정한다.
	 * stage의 Title을 “Duck’s coffe Menu”로 설정한다.
	 * stage의 크기를 조정할 수 없도록 설정한다.
	 * stage의 Scene을 scene(새로 생성한 root를 포함하는 scene)으로 설정한다.
	 * stage를 전시한다.
   - hboxFirst1에 생성한 tableName, menuBtn을 추가한다.
   - hboxFirst2에 생성한 modMessage, modInfo를 추가한다.
   - Mainicon을 Ours인 현재 스케줄링 기법에 맞게 변경하기 위해 true를 전달하여 호출한다.
   - 기존에 추가되어 있던 hboxThird1의 모든 요소를 제거한다.
   - addBtn, addProcessName, addAT, addBT의 생성 및 정의를 위해 setAddLine() 호출한다.
   - submitBtn Button을 생성한다.
   - subShot, subMilk, subChoco TextField를 생성한다.
   - subShot, subMilk, subChoco TextField의 PromptText 및 Width를 설정한다.
   - hboxThird1에 subShot, subMilk, subChoco, submitBtn을 추가한다.
   - submitBtn을 클릭했을 때 행동을 정의한다.
	 * 입력조건(subShot, subMilk, subChoco가 모두 숫자이며 0보다 크다.)을 만족하면 hboxThird1에 추가되어있던 subShot, subMilk, subChoco, submitBtn을 모두 제거하고 멤버인 shot, milk, choco에 대응하는 입력값을 정수의 형태로 저장한다.
	 */

	private void setTQ() {
		// 사용자로부터 TQ를 입력받기 위한 Label, TextField, Button을 설정하는 함수
		final Label TQMessage = new Label();
		// TQMessage Label 생성
		final TextField subTQ = new TextField();
		// 사용자에게 TQ를 입력받을 subTQ TextField 생성
		Button submit = new Button("Submit");
		// TQ를 제출할 submit Button 생성한다.

		subTQ.setPrefWidth(130);
		// subTQ TextField의 Width를 130으로 설정한다.
		subTQ.setLayoutX(SPACEWIDTH-subTQ.getPrefWidth()-20);
		// subTQ의 가로 위치를 화면 우측에서 Width+20만큼 왼쪽으로 설정한다.
		subTQ.setLayoutY(90);
		// subTQ의 세로 위치를 화면 상단에서 90 아래로 설정한다.
		subTQ.setPromptText("Enter the TQ.");
		// subTQ의 PromptText를 “Enter the TQ.”로 설정한다.

		TQMessage.setLayoutX(subTQ.getLayoutX());
		// TQMessage의 가로위치를 subTQ와 동일하게 설정한다.
		TQMessage.setLayoutY(90);
		// TQMessage의 세로위치를 subTQ와 동일하게 90으로 설정한다.
		submit.setLayoutX(subTQ.getLayoutX());
		// submit Button의 가로위치를 subTQ와 동일하게 설정한다.
		submit.setLayoutY(60);
		// submit Button의 세로위치를 화면 상단에서 60 아래로 설정한다.

		battleField.getChildren().addAll(subTQ, submit, TQMessage);
		// battleField Pane에 subTQ, submit, TQMessage 추가한다.

		submit.setOnAction((ActionEvent event) -> {
			// submit Button을 클릭했을 때 행동을 정의한다.	
			if(isNum(subTQ.getText())&&Integer.parseInt(subTQ.getText())>0) {
				// 입력조건(subTQ가 숫자로 이루어져 있고 0보다 크다.)을 만족한다면
				battleField.getChildren().removeAll(subTQ, submit);
				// subTQ, submit을 battleField에서 제거한다.
				TQMessage.setText("TQ "+subTQ.getText());
				// TQMessage에 사용자가 입력한 TQ를 보여주는 Text를 설정한다.
				TQ = Integer.parseInt(subTQ.getText());
				// 멤버 TQ에 subTQ에서 입력받은 TQ를 정수의 형태로 저장한다.
			}
			else { simulatorInfoDialog("!!!ERROR!!!", null
					, "Enter an Integer greater than 0."); }
			// 입력조건이 하나라도 만족하지 않을 경우 에러 및 입력조건을 출력하는 대화창을 화면에 전시한다.
		});
	}
	/*
1. setTQ함수는 사용자로부터 TQ를 입력받기 위한 Label, TextField, Button을 설정하는 함수이다.
2. TQ에 대한 정보를 보여줄 TQMessage Label을 생성한다.
3. TQ를 사용자에게 입력받을 subTQ TextField를 생성한다.
4. 사용자가 TQ를 제출할 때 사용할 submit Button을 생성한다.
5. subTQ의 Width를 130으로 설정한다.
6. subTQ의 가로 위치를 오른쪽 끝에서 Width+20 만큼 왼쪽으로 설정한다.
7. subTQ의 세로 위치를 상단에서 90 아래로 설정한다.
8. subTQ의 PromptText를 “Enter the TQ.”로 설정한다.
9. TQMessage의 가로 위치를 subTQ와 동일하게 설정한다.
10. TQMessage의 세로 위치를 상단에서 90 아래로 설정한다.
11. submit의 가로 위치를 subTQ와 동일하게 설정한다.
12. submit의 세로 위치를 상단에서 60 아래로 설정한다.
13. submit을 클릭했을 때 행동을 정의한다.
   - 입력조건(subTQ가 숫자로 이루어져 있고 0보다 크다)을 만족할 경우
	 * 제출이 완료되었으므로 subTQ와, submit을 battleField에서 제거한다. 그리고 TQMessage를 통해 입력받은 TQ를 상시 전시하며 멤버 TQ에 정수의 형태로 subTQ에서 입력받은 값을 저장한다.
   - 입력조건을 만족하지 않을 경우 에러 메시지와 함께 입력조건을 출력하는 대화창을 화면에 전시한다.
	 */

	private void setStart() {
		// 사용자에 의해 선택된 스케줄링 기법에 따라 값을 입력받아 controller를 생성하는 함수
		final Button startBtn = new Button("START!");
		// startBtn Button을 생성한다.

		startBtn.setPrefSize(140, 40);
		// startBtn의 크기를 140, 40으로 설정한다.
		hboxThird2.getChildren().addAll(startBtn);
		// hboxThird2에 StartBtn을 추가한다.

		startBtn.setOnAction((ActionEvent event) -> {
			// startBtn을 클릭했을 때 행동을 정의한다.
			Controller controller;
			// 각 데이터 저장 및 프로세스 처리를 위한 controller 객체를 선언한다.
			if(processData.size()>0&&choose!=-1) {
				// 프로세스 데이터가 존재하고, 스케줄링 기법이 선택된 경우
				if(choose == 1) { 
					// 선택된 스케줄링 기법이 RR인 경우
					if(TQ>0)
						// 적절한 TQ가 입력될 경우
						controller = new Controller(choose, PN, processData, TQ); 
					// choose, PN, processData, TQ를 전달하여 controller 생성
					else {
						// 입력되지 않은 경우
						// setTQ()에서 TQ값의 유효성을 검사했으므로 해당 else문이 수행되는 조건은 TQ 입력 없이 Start를 눌렀을 때뿐이다.
						simulatorInfoDialog("!!!ERROR!!!", null
								, "Enter the TQ before Starting."); // 에러문과 “Enter the TQ before Starting”이라는 텍스트를 대화창 형태로 화면에 전시한다.
						return ;
						// setStart() 함수를 종료한다.
					}
				}
				else if(choose == 5) {
					// Ours를 선택한 경우
					if(shot>0&&milk>0&&choco>0)
						// 적절한 shot, milk, choco가 입력되었을 때
						controller = new Controller(choose, PN, processData, shot, milk, choco); 
					// choose, PN, processData, shot, milk, choco를 전달하여 controller 생성한다.
					else { 
						// shot, milk, choco가 입력되지 않았을 경우
						// setChoiceBox에서 shot, milk, choco에 대해 유효성 검사를 했으므로 해당 else문이 수행되는 조건은 입력되지 않은 경우뿐이다.
						simulatorInfoDialog("!!!ERROR!!!", null
								, "Enter the Shot, Milk and Choco before Starting."); 
						// 에러문과 “Enter the Shot, Milk and Choco before Starting” 이라는 텍스트를 대화창 형태로 화면에 전시한다.
						return ;
					}
				}
				else { 
					// RR, Ours를 제외한 스케줄링 기법이 선택된 경우
					controller = new Controller(choose, PN, processData); 
					// choose, PN, processData를 전달하여 controller 생성한다.
				}
				controller.newSimulation(); 
				// newSimulation() 함수를 호출하여 프로세스 작업 수행한다.
				processData.setAll(controller.getProcess()); 
				// controller에서 Pro를 받아 멤버 processData에 저장한다.
				setGantt(controller.getSequence(), controller.getRuntime(), controller.getName()); 
				// controller에서 Sequence, Runtime, Name을 전달 받아 Gantt차트를 만든다.
			} 

			else if(choose==-1) { simulatorInfoDialog("!!!ERROR!!!", null
					, "Choose Scheduling Mod before Starting.");
			// 스케줄링 기법을 선택하지 않은 경우 에러문과 “Choose Scheduling Mod before Starting.” 텍스트를 대화창 형태로 화면에 전시한다.
			} 
			else { simulatorInfoDialog("!!!ERROR!!!", null
					, "Number of Process is Lake!!\nPlease, Add MORE Processes."); } 
			// 프로세스를 하나도 입력하지 않았을 때 에러문과 “Number of Process is Lake!!\nPlease, Add MORE Processes.” 텍스트를 대화창 형태로 화면에 전시한다.
		});
	}
	/*
1. setStart함수는 사용자에 의해 선택된 스케줄링 기법에 따라 값을 입력받아 controller를 생성하는 함수이다.
2. StartBtn을 생성하고 size를 결정한 후 hboxThird2에 추가한다.
3. StartBtn을 클릭했을 때 행동을 정의한다.
4. ProcessData가 1개 이상 입력되었고 스케줄링 기법 선택이 되었는지 검사한다.
5. 선택한 스케줄링 기법이 RR인 경우
   - 적절한 TQ가 입력되었다면 choose, PN, processData, TQ를 전달하여 controller를 생성한다.
   - TQ가 입력되지 않은 경우 에러 대화창을 화면에 전시한다.
6. 선택한 스케줄링 기법이 Ours인 경우
   - 적절한 shot, milk, choco가 입력되었다면 choose, PN, processData, shot, milk, choco를 전달하여 controller를 생성한다.
   - shot, milk, choco 중 하나라도 입력되지 않은 경우 에러문을 화면에 출력한다.
7. 선택한 스케줄링 기법이 RR 혹은 Ours가 아니라면 choose, PN, processData를 전달하여 controller를 생성한다.
8. controller에서 각 기법에 맞는 프로세스 스케줄링 작업을 실시한 후 controller에서 processData를 받아와 저장한다.
9. Sequence, Runtime, Name을 controller로부터 전달받아 Gantt chart를 만든다.
10. 스케줄링 기법이 선택되지 않은 경우, ProcessData를 하나도 입력하지 않은 경우 에러 대화창을 화면에 전시한다.
	 */

	private void setBaseRectangle() {
		//Gantt Chart를 전시할 기본 직사각형을 설정하는 함수
		Rectangle base = new Rectangle(660,280);
		// Gantt Chart를 전시할 기본 직사각형을 생성한다.	

		base.setFill(Color.WHITESMOKE); 
		// 내부 Color를 WHITESMOKE로 설정한다.
		base.setStroke(Color.BLACK);
		// 테두리의 Color를 BLACK으로 설정한다.

		hboxFourth.getChildren().add(base);
		// hboxFourth에 base Rectangle 추가한다.
	}
	/*
1. setBaseRectangle함수는 Gantt Chart를 전시할 기본 직사각형을 설정하는 함수이다.
2. Gantt chart 전시를 위해 사이즈(660, 280)를 결정하여 base Rectangle을 생성한 뒤 내부 Color는 WHITESMOKE로, 테두리의 Color는 BLACK로 설정한다.
3. hboxFourth에 base Rectangle을 추가한다.
	 */

	private void setMenu() {
		// menu구성 및 설정과 menu이용 시 발생하는 event를 처리하는 함수
		MenuBar menuBar = new MenuBar();	
		// menuBar 생성한다.
		Menu menuFile = new Menu("File");	
		// File 메뉴 생성한다.
		Menu menuInfo = new Menu("Info");	
		// Info 메뉴 생성한다.

		MenuItem clear = new MenuItem("Clear");
		// Clear 메뉴 항목 생성한다.
		clear.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
		// clear의 단축키를 “Ctrl+X”로 설정한다.
		clear.setOnAction((ActionEvent t) -> {
			// clear의 행동을 정의한다.
			clearAll();
			// clearAll() 함수를 호출하여 모든 컨테이너의 요소를 제거한다.
			init();
			// init() 함수를 호출하여 모든 데이터 제거 및 초기화한다.
		});

		MenuItem exit = new MenuItem("Exit"); 
		// Exit 메뉴 항목 생성한다.
		exit.setAccelerator(KeyCombination.keyCombination("Esc"));
		// exit의 단축키를 “ESC”로 설정한다.
		exit.setOnAction((ActionEvent t) -> {
			// exit의 행동을 정의한다.
			System.exit(0); 
			// application을 종료한다.
		});

		MenuItem help = new MenuItem("Help"); 
		// Help 메뉴 항목 생성한다.
		help.setOnAction((ActionEvent t) -> { 
			// help의 행동을 정의한다.
			simulatorInfoDialog("Description of Ours", null, "First, Submit the Number of Shots, Milks and Chocolates what you make at once.\n"
					+ "Thus, Add the processes.\n" + "Finally, Click the Start Button!!\n\n" + "Now, Enjoy your Drink!\n\n"
					+ "Ps. If you want to see the Menu, Click the menu button.");
			// 대화창을 통하여 Ours의 구동 방법을 설명한다.
		}); 

		MenuItem creator = new MenuItem("Creator"); 
		// Creator 메뉴 항목 생성한다.
		creator.setOnAction((ActionEvent t) -> { 
			// creator의 행동을 정의한다.
			simulatorInfoDialog("Special People in OS Class", null
					, "2017136128 Jin EDEN\n"
							+ "2017136075 Lee NaRae\n"
							+ "2017136088 Lee YunJeong\n"
							+ "2017136082 Lee YeJi\n"
							+ "2014136087 Lee GyeongHyeong");
		});
		// 대화창의 형태로 학번, 이름을 화면에 전시한다.

		menuFile.getItems().addAll(clear, exit);
		// File 메뉴에 clear, exit를 추가한다.
		menuInfo.getItems().addAll(help, creator);
		// Info 메뉴에 help, creator를 추가한다.
		menuBar.getMenus().addAll(menuFile, menuInfo);
		// menuBar에 File, Info 메뉴를 추가한다.

		menuBar.setPrefWidth(SPACEWIDTH+10);
		// menuBar의 Width를 SPACEWIDTH+10으로 한다.

		battleField.getChildren().addAll(menuBar);
		// menuBar를 battleField에 추가한다.
	}
	/*
1. setMenu함수는 menu구성 및 설정과 menu이용 시 발생하는 event를 처리하는 함수이다.
2. MenuBar, menuFile, menuInfo를 생성한다.
3. Clear 메뉴항목을 생성하여 단축키를 “Ctrl+X”로 설정한다.
4. clear의 행동을 정의한다,
   - 모든 컨테이너의 구성요소 제거
   - 모든 데이터 삭제 및 초기화
5. Exit 메뉴항목을 생성하여 단축키를 “ESC”로 설정한다.
6. Exit의 행동을 정의한다.
   - application을 종료한다.
7. Help 메뉴항목을 생성한다.
8. Help의 행동을 정의한다.
   - Ours에 대한 설명을 대화창을 통해 화면에 전시한다.
9. Creator 메뉴항목을 생성한다.
10. creator의 행동을 정의한다.
   - 조원의 학번, 이름을 대화창을 통해 화면에 전시한다.
11. clear, exit 메뉴 항목을 File 메뉴(menuFile)에 추가한다.
12. help, creator 메뉴 항목을 Info 메뉴(menuInfo)에 추가한다.
13. menuFile과 menuInfo를 menuBar에 추가한다.
14. menuBar의 크기를 SPACEWIDTH+10으로 설정한다.
15. menuBar를 battleField Pane에 추가한다.
	 */

	private void setBoxes() {
		//hbox들과 VBox들의 구성을 설정하는 함수
		hboxFirst2.setAlignment(Pos.CENTER_LEFT);
		// hboxFirst 요소들의 Alignment를 CENTER_LEFT로 설정한다.
		// (세로는 중앙, 가로는 왼쪽)
		hboxSecond.setAlignment(Pos.BOTTOM_LEFT);
		// hboxSecond 요소들의 Alignment를 BOTTOM_LEFT로 설정한다.
		hboxFourth.setAlignment(Pos.BOTTOM_LEFT);
		// hboxFourth 요소들의 Alignment를 BOTTOM_LEFT로 설정한다.

		hboxFirstTotal.getChildren().addAll(hboxFirst1, hboxFirst2);
		// hboxFirst1, 2를 hboxFirstTotal에 추가한다.
		hboxThirdTotal.getChildren().addAll(hboxThird1, hboxThird2);
		// hboxThirdTotal1, 2를 hboxThirdTotal에 추가한다.

		vbox.setSpacing(5); 
		// vbox 내부 구성요소 간의 간격을 5로 설정한다.
		vbox.setPadding(new Insets(35, 0, 0, 5)); 
		// vbox에 위, 우측, 아래, 좌측에 30, 0, 0, 10의 padding을 설정한다.
		vbox.getChildren().addAll(hboxFirstTotal, hboxSecond, hboxThirdTotal, hboxFourth);
		// 모든 hbox를 vbox에 추가한다.

		battleField.getChildren().add(vbox);
		// vbox를 battleField Pane에 추가한다.
	}
	/*
1. setBoxes함수는 hbox들과 VBox들의 구성을 설정하는 함수이다.
2. hboxFirst, hboxSecond, hboxFourth의 요소들에 CENTER_LEFT, BOTTOM_LEFT,  BOTTOM_LEFT의 Alignment를 각각 설정한다.
3. vbox의 간격을 5로 설정한다.
4. vbox의 위, 우측, 아래, 좌측에 30, 0, 0, 10의 padding을 설정한다.
5. vbox에 모든 hbox를 추가한다.
6. vbox를 battleField Pane에 추가한다.
	 */

	private boolean isNum(String s) {
		try {
			Integer.parseInt(s);
			// s가 정수형으로 변환가능하면 true를 반환한다.
			return true;
		} catch(NumberFormatException e) {
			return false; 
			// 숫자가 아닌 문자가 들어와 NumberFormatException이 발생하면 false를 반환한다.
		}
	}
}
/*
1. inNum함수는 매개변수가 정수형으로 변환 가능한지 반환하는 함수이다.
2. s가 정수형으로 변환가능하면 true를 반환한다.
3. 숫자가 아닌 문자가 들어와 NumberFormatException이 발생하면 false를 반환한다.
 */
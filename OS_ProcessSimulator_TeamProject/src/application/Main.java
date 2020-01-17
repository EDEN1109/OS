package application;

import javafx.application.Application;
import javafx.stage.Stage; 
import javafx.scene.Scene; 

public class Main extends Application {
	SimulatorView view = new SimulatorView(); 
	// SimulatorView 클래스의 객체 view 생성

	@Override
	public void start(Stage primaryStage) {
		// 응용 프로그램이 실행될 준비를 한 뒤 프로그램 시작하는 함수
		try {
			view.simulatorConfirmDialog("Hello, Operating System!"
					, "######How to Use###### \nFirst, Choose the MOD! \nSecond, Setting the Condition \nFinal, Click the Start Button!"
					, "Do you want to OS simulating??");
			// 프로그램의 첫 화면에서 지정한 안내문을 내용과 icon을 통해 가시화한다.
			view.init();
			// 메인 화면을 초기화한다.

			primaryStage.setTitle("OS Process Simulator"); 
			// 메인화면의 창 제목을 설정한다.
			primaryStage.setScene(new Scene(view)); 
			// 메인화면을 view를 통해 설정한다.
			primaryStage.setResizable(false);
			// 메인화면의 크기를 고정한다.
			primaryStage.show();
			// 메인화면을 출력한다.

		} catch(Exception e) {
			e.printStackTrace(); 
			// application.css가 존재하지 않으면 표준 에러스트림을 출력한다.
		}
	}

	public static void main(String[] args) {
		launch(args); 
		// 실행한다.
	}
}
/* 
1. main 클래스는 프로그램의 실행을 담당하는 클래스이다.
2. JavaFx의 Application을 상속한 Main 클래스에 SimulatorView 클래스의 객체 view를  생성한다.
3. start함수는 프로그램의 첫 화면과 메인화면을 가시화하고 예외 발생 시 에러스트림을 출력하는 함수이다.
4. 프로그램을 실행한다.
 */
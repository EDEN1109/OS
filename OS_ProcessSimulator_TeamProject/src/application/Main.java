package application;

import javafx.application.Application;
import javafx.stage.Stage; 
import javafx.scene.Scene; 

public class Main extends Application {
	SimulatorView view = new SimulatorView(); 
	// SimulatorView Ŭ������ ��ü view ����

	@Override
	public void start(Stage primaryStage) {
		// ���� ���α׷��� ����� �غ� �� �� ���α׷� �����ϴ� �Լ�
		try {
			view.simulatorConfirmDialog("Hello, Operating System!"
					, "######How to Use###### \nFirst, Choose the MOD! \nSecond, Setting the Condition \nFinal, Click the Start Button!"
					, "Do you want to OS simulating??");
			// ���α׷��� ù ȭ�鿡�� ������ �ȳ����� ����� icon�� ���� ����ȭ�Ѵ�.
			view.init();
			// ���� ȭ���� �ʱ�ȭ�Ѵ�.

			primaryStage.setTitle("OS Process Simulator"); 
			// ����ȭ���� â ������ �����Ѵ�.
			primaryStage.setScene(new Scene(view)); 
			// ����ȭ���� view�� ���� �����Ѵ�.
			primaryStage.setResizable(false);
			// ����ȭ���� ũ�⸦ �����Ѵ�.
			primaryStage.show();
			// ����ȭ���� ����Ѵ�.

		} catch(Exception e) {
			e.printStackTrace(); 
			// application.css�� �������� ������ ǥ�� ������Ʈ���� ����Ѵ�.
		}
	}

	public static void main(String[] args) {
		launch(args); 
		// �����Ѵ�.
	}
}
/* 
1. main Ŭ������ ���α׷��� ������ ����ϴ� Ŭ�����̴�.
2. JavaFx�� Application�� ����� Main Ŭ������ SimulatorView Ŭ������ ��ü view��  �����Ѵ�.
3. start�Լ��� ���α׷��� ù ȭ��� ����ȭ���� ����ȭ�ϰ� ���� �߻� �� ������Ʈ���� ����ϴ� �Լ��̴�.
4. ���α׷��� �����Ѵ�.
 */
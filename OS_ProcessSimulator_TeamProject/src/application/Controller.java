package application;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class Controller {
	private int Choose;    
	// 사용할 scheduling Mode를 저장할 변수
	private int PN;
	// 프로세스의 수를 저장할 변수
	private int TQ;
	// RR에서 사용할 Time quantum을 저장할 변수
	private int Shot;
	private int Milk;
	private int Choco;
	// Shot, Milk, Choco : Ours에서 프로세스가 사용할 재료
	private Process[] Pro;
	// Process들을 저장할 배열
	private ObservableList<Process> processData;
	// 사용자에게 프로세스의 정보를 입력받을 ObservableList 이다.
	private ArrayList<Integer> processSequence = new ArrayList<Integer> ();
	// Gantt Chart를 출력할 때, 프로세스들의 처리 순서를 저장하여 다음 프로세스와 현재 가시화 중인 프로세스가 서로 같은 프로세스인지 구분하기 위한 ArrayList이다.
	private ArrayList<Integer> processRuntime = new ArrayList<Integer> ();
	// Gantt Chart를 출력할 때, 프로세스들의 작업 수행시간 저장하여 해당 프로세스 그래프의 면적 결정을 위한 ArrayList이다.
	private ArrayList<String> processName = new ArrayList<String> ();
	// Gantt Chart를 출력할 때, 작업을 진행한 프로세스의 이름을 저장하여 출력을 위한 ArrayList이다.
	public Controller(int Choose, int PN, ObservableList<Process> processData) { 
		// 사용자가 입력을 마치면 Controller를 생성하는 생성자
		// Ours, RR을 제외한 스케줄링 기법을 선택한 경우에 해당한다.
		this.processData = processData;    
		// 입력받은 프로세스 정보를 저장한다.
		this.Choose = Choose;    
		// 선택한 스케줄링 기법을 저장한다.
		this.PN = PN;
		// 입력받은 프로세스의 수를 저장한다.
		Pro = new Process[PN];    
		// PN만큼 Pro 배열을 할당한다.
	}
	public Controller(int Choose, int PN, ObservableList<Process> processData, int TQ) { // init Type2
		// 사용자가 RR을 선택하여 TQ를 입력받을 경우의 생성자
		this.processData = processData;
		this.Choose = Choose;  
		this.PN = PN;
		this.TQ = TQ;    
		// 입력받은 TQ를 저장한다.
		Pro = new Process[PN]; 
	}
	public Controller(int Choose, int PN, ObservableList<Process> processData, int Shot, int Milk, int Choco) { // init Type2
		// 사용자가 Ours를 선택하여 Shot, Milk, Choco를 입력받을 경우의 생성자
		this.processData = processData;    
		this.Choose = Choose;   
		this.PN = PN;   
		this.Shot = Shot;    
		// 입력받은 shot을 저장한다.
		this.Milk = Milk;    
		// 입력받은 milk를 저장한다.
		this.Choco = Choco;    
		// 입력받은 choco를 저장한다.
		Pro = new Process[PN];    
	}

	public void newSimulation() {
		// 입력받은 스케줄링 기법에 따라 실질적으로 프로세스 스케줄링을 실시하는 함수
		init();    
		// Pro 배열에 입력받은 Process를 저장하며, 모든 프로세스를 작업수행 이전으로 되돌린다.
		if(Choose == 0) {    
			// FCFS를 선택한 경우
			sortingArray(0);    
			// AT를 기준으로 프로세스를 오름차순으로 정렬한다.
			FCFS fcfs = new FCFS(Pro);    
			// Pro 배열을 넘겨주면서 FCFS 생성한다.
			Pro = fcfs.launcher();
			// FCFS를 실행한다.
			processSequence = fcfs.getSequence();
			// FCFS에서 processSequence를 받아와 저장한다.
			processRuntime = fcfs.getRuntime();
			// FCFS에서 processRuntime을 받아와 저장한다.
			processName = fcfs.getName();
			// FCFS에서 processName을 받아와 저장한다.
		}
		else if(Choose == 1) {     
			// RR을 선택한 경우
			sortingArray(0);    
			// AT를 기준으로 프로세스를 오름차순으로 정렬한다.
			RR rr = new RR(Pro, TQ);
			// Pro 배열과 TQ를 넘겨주면서 RR을 생성한다.
			Pro = rr.launcher();    
			// RR을 실행한다.
			processSequence = rr.getSequence();
			// RR에서 processSequence를 받아와 저장한다.
			processRuntime = rr.getRuntime();
			// RR에서 processRuntime을 받아와 저장한다.
			processName = rr.getName();
			// RR에서 processName을 받아와 저장한다.
		}
		else if(Choose == 2) { 
			// SPN을 선택한 경우
			sortingArray(1);    
			// SPN의 경우 BT가 작은 순서대로 프로세스를 처리하기 때문에 BT를 기준으로 프로세스를 오름차순으로 정렬한다.
			SPN spn = new SPN(Pro);
			// Pro 배열을 넘겨주면서 SPN을 생성한다.
			Pro = spn.launcher();    
			// SPN을 실행한다.
			processSequence = spn.getSequence();
			// SPN에서 processSequence를 받아와 저장한다.
			processRuntime = spn.getRuntime();
			// SPN에서 processRuntime를 받아와 저장한다.
			processName = spn.getName();
			// SPN에서 processName을 받아와 저장한다.
			sortingArray(0);
			// 최종 결과 출력을 위해 AT를 기준으로 프로세스를 오름차순으로 정렬한다.
		}
		else if(Choose == 3) {    
			// SRTN을 선택한 경우
			SRTN srtn = new SRTN(Pro);
			// Pro 배열을 넘겨주면서 SRTN을 생성한다.
			Pro = srtn.launcher();    
			// SRTN을 실행한다.
			processSequence = srtn.getSequence();
			// SRTN에서 processSequence를 받아와 저장한다.
			processRuntime = srtn.getRuntime();
			// SRTN에서 processRuntime를 받아와 저장한다.
			processName = srtn.getName();
			// SRTN에서 processName을 받아와 저장한다.
			sortingArray(0);
			// 최종 결과 출력을 위해 AT를 기준으로 프로세스를 오름차순으로 정렬한다.
		}
		else if(Choose == 4) { 
			// HRRN을 선택한 경우
			sortingArray(0);    
			// AT를 기준으로 프로세스를 오름차순으로 정렬한다.
			HRRN hrrn = new HRRN(Pro);
			// Pro 배열을 넘겨주면서 HRRN을 생성한다.
			Pro = hrrn.launcher();    
			// HRRN을 실행한다.
			processSequence = hrrn.getSequence();
			// HRRN에서 processSequence를 받아와 저장한다.
			processRuntime = hrrn.getRuntime();
			// HRRN에서 processRuntime를 받아와 저장한다.
			processName = hrrn.getName();
			// HRRN에서 processName을 받아와 저장한다.
			sortingArray(0);
			// 최종 결과 출력을 위해 AT를 기준으로 프로세스를 오름차순으로 정렬한다.
		}
		else if(Choose == 5) {     
			// Ours를 선택한 경우
			Ours ours = new Ours(Pro, Shot, Milk, Choco);
			// Pro 배열, 각 재료(Shot, Milk, Choco)를 넘겨주면서 Ours를 생성한다.
			Pro = ours.launcher();    
			// Ours를 실행한다.
			processSequence = ours.getSequence();
			// Ours에서 processSequence를 받아와 저장한다.
			processRuntime = ours.getRuntime();
			// Ours에서 processRuntime를 받아와 저장한다.

			processName = ours.getName();
			// Ours에서 processName을 받아와 저장한다.
			sortingArray(0);
			// 최종 결과 출력을 위해 AT를 기준으로 프로세스를 오름차순으로 정렬한다.
		}
	}

	private void init() {    
		// 모든 프로세스를 초기화 시키기 위해 정의한 함수
		for(int i=0; i<PN; i++) {
			Pro[i] = processData.get(i);  
			// ProcessData를 받아와 Pro에 저장한다.
			Pro[i].resetProcessed(); 
			// 모든 프로세스의 처리상태를 true로 설정한다.(아직 처리되지 않은 상태로 설정을 의미)
		}
	}
	public Process[] getProcess() {
		// 프로세스들을 반환하는 함수
		return Pro;
	}
	public ArrayList<Integer> getRuntime() {
		// processRuntime ArrayList를 반환하는 함수
		return processRuntime;
	}
	public ArrayList<Integer> getSequence() {	
		// processSequence ArrayList를 반환하는 함수
		return processSequence;
	}
	public ArrayList<String> getName() {
		// processName ArrayList를 반환하는 함수
		return processName;
	}
	public void sortingArray(int SortType) {
		// 프로세스들을 정렬하기 위한 함수이다.
		Process ProCopy; // swap을 위해 선언한 임시 변수

		for(int i=0; i<PN-1; i++) {
			for(int j=i+1; j<PN; j++) {
				if(SortType == 0) { 
					// AT를 기준으로 오름차순 정렬한다.
					if(Pro[i].getAT()>Pro[j].getAT()) {
						// Pro[j]의 AT가 Pro[i]의 AT보다 작으면 swap한다.
						ProCopy = Pro[i];
						Pro[i] = Pro[j];
						Pro[j] = ProCopy;
					}
				}
				else if(SortType == 1) { // Sort By BT
					// BT를 기준으로 오름차순 정렬한다.
					if(Pro[i].getBT()>Pro[j].getBT()) {
						// Pro[j]의 BT가 Pro[i]의 BT보다 작으면 swap한다.
						ProCopy = Pro[i];
						Pro[i] = Pro[j];
						Pro[j] = ProCopy;
					}
				}
			}
		}
	}
}
/* 
1. Controller 클래스는 프로세스 스케줄링을 실질적으로 처리하고 제어하기 위한 변수와 함수를 정의한 클래스이다.
2. Choose, PN, TQ, Shot, Milk, Choco 변수와 Pro배열을 정의한다. 
3. Gantt Chart 출력을 위한 processSequence, processRuntime, processName ArrayList를 선언한다.
4. Controller의 생성자는 사용자가 입력을 마치면 입력받은 Choose, PN, processData, TQ(RR의 경우), shot·milk·choco(Ours의 경우)를 받아와 멤버를 초기화한다.
5. new Simulation함수는 입력받은 스케줄링 기법에 따라 실질적으로 프로세스 스케줄링을 실시하는 함수이다.
   - init()을 호출하여 모든 프로세스를 작업수행 이전으로 되돌려 Pro에 저장한다.
   - 사용자가 선택한 스케줄링 기법에 따라 AT 혹은 BT로 프로세스를 정렬한다.
   - 사용자가 선택한 스케줄링 기법에 따라 객체를 생성하고 프로세스 스케줄링을 실행한다.
   - 스케줄링 기법 객체에서 processSequence, processRuntime, processName을 받아와 저장한다.
   - 필요에 따라 최종결과 출력을 위해 프로세스를 AT 기준으로 오름차순 정렬한다.
6. init함수는 모든 프로세스를 초기화 시키는 함수이다.
   - processData를 받아와 Pro 배열에 저장한다.
   - Pro에 저장된 모든 프로세스의 처리상태를 true로 설정한다.(아직 처리되지 않은 상태로 설정을 의미)
7. getProcess함수는 프로세스들을 반환하는 함수이다.
8. getRuntime, getSequence, getName함수는 ProcessRuntime, processSequence, processName ArrayList를 반환하는 함수이다.
9. sortingArray함수는 프로세스를 AT 혹은 BT기준으로 오름차순 정렬하는 함수이다.
 */
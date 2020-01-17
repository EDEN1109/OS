package application;

public class Process {
	private String ProcessName; 
	// 프로세스의 Name을 저장해 그래프에서 Name을 가시화하는 배열
	private int AT; 
	// Arrival Time을 저장하는 변수
	private int BT; 
	// Burst Time을 저장하는 변수
	private boolean Processed; 
	// 프로세스가 하는 일의 진행 결과를 저장하는 변수
	private int TT = 0;
	// Turnarround Time을 저장하는 변수
	private int WT = 0;
	// Waiting Time을 저장하는 변수
	private double NTT = 0;
	// Normalized Turnarround Time을 저장하는 변수
	
	public Process(String ProcessName, int AT, int BT) {
	// 사용자가 입력한 내용을 바탕으로 프로세스를 생성하는 생성자
		this.ProcessName = ProcessName;
		this.AT = AT;
		this.BT = BT;
		Processed = true; 
		// 초기 프로세스는 일을 처리하지 않은 상태로 설정한다.
	}
	
	public boolean isArrival(int time) {
	// time에 따라 프로세스의 도착 여부를 반환하는 함수 
		return AT<=time;
		// AT가 tiem보다 작거나 같으면 True, 크면 False를 반환한다.
	}
	
	public void resetProcessed() {
	// 프로세스를 아직 처리되지 않은 상태로 설정하는 함수
		Processed = true;
	}
	
	public String getProcessName() {
	// 프로세스의 Name을 반환하는 함수
		return ProcessName;
	}

	public int getAT() {
	// AT를 반환하는 함수
		return AT;
	}
		
	public int getBT() {
	// BT를 반환하는 함수
		return BT;
	}
	
	public boolean getProcessed() {
	// Processed를 반환하는 함수
		return Processed;
	}
	
	public void setProcessed() {
	// 프로세스를 처리한 상태로 설정하는 함수
		Processed = false;
	}
	
	public void setResult(int TT, int WT, double NTT) {
	// 계산된 TT, WT, NTT를 바탕으로 실제 TT, WT, NTT를 재설정하는 함수
		this.TT = TT;
		this.WT = WT;
		this.NTT = NTT;
	}
	
	public int getTT() {
	// TT를 반환하는 함수
		return TT;
	}
	
	public int getWT() {
	// WT를 반환하는 함수
		return WT;
	}
	
	public double getNTT() {
	// NTT를 반환하는 함수
		return NTT;
	}
}
/* 
1. Process 클래스는 프로그램 실행 시 필요한 변수들과 함수들을 정의하는 클래스이다.
2. ProcessName, AT, BT, Processed, TT, WT, NTT 변수를 정의한다.
3. Process의 생성자는 사용자가 입력한 내용을 바탕으로 AT, BT, Processed를 초기화한다.
4. isArrival함수는 time에 따라 프로세스의 도착 여부를 반환하는 함수이다.
5. resetProcessed함수는 프로세스를 아직 처리되지 않은 상태로 설정하는 함수이다.
6. getProcessName함수는 프로세스의 Name을 반환하는 함수이다.
7. getAT함수는 AT를 반환하는 함수이다.
8. getBT함수는 BT를 반환하는 함수이다.
9. getProcessed함수는 Processed를 반환하는 함수이다.
10. setProcessed함수는 프로세스를 처리한 상태로 설정하는 함수이다.
11. setResult함수는 계산된 TT, WT, NTT를 바탕으로 실제 TT, WT, NTT를 재설정하는 함수이다.
12. getTT함수는 TT를 반환하는 함수이다.
13. getWT함수는 WT를 반환하는 함수이다.
14. getNTT함수는 NTT를 반환하는 함수이다.
*/
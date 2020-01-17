package application;

import java.util.ArrayList;

public abstract class BasicTool {
	public abstract Process[] launcher(); 
	// 각 scheduling class들이 상속받아 프로세스들을 처리하는 알고리즘을 정의 할 abstract 함수이다.
	protected ArrayList<Integer> processSequence = new ArrayList<Integer> ();
	// Gantt Chart를 출력할 때, 프로세스들의 처리 순서를 저장하여 다음 프로세스와 현재 가시화 중인 프로세스가 서로 같은 프로세스인지 구분하기 위한 ArrayList이다.
	protected ArrayList<Integer> processRuntime = new ArrayList<Integer> ();
	// Gantt Chart를 출력할 때, 프로세스들의 작업 수행시간 저장하여 해당 프로세스 그래프의 면적 결정을 위한 ArrayList이다.
	protected ArrayList<String> processName = new ArrayList<String> ();
	// Gantt Chart를 출력할 때, 작업을 진행한 프로세스의 이름을 저장하여 출력을 위한 ArrayList이다.
	public int calculatingTT (int time, int AT) {
		return time-AT;
		// 작업을 종료한 프로세스의 TT를 계산하는 함수이다.(해당 프로세스 처리 종료시간–AT)
	}
	public int calculatingWT (int time, int AT, int BT) {
		return time-AT-BT;
		// 작업을 종료한 프로세스의 WT를 계산하는 함수이다.(TT-BT=해당 프로세스 처리 종료시간-AT-BT)
	}
	public double calculatingNTT (int time, int AT, int BT) {
		return (time-AT)/(double)BT;
		// 작업을 종료한 프로세스의 NTT를 계산하는 함수이다.(TT/BT=(해당 프로세스 처리 종료시간-AT)/BT)
	}
	public ArrayList<Integer> getRuntime() {
		return processRuntime; 
		// 처리한 프로세스의 processRuntime ArrayList를 반환한다.
	}
	public ArrayList<Integer> getSequence() {
		return processSequence;
		// 처리한 프로세스의 processSequence ArrayList를 반환한다.
	}
	public ArrayList<String> getName() {
		return processName;
		// 처리한 프로세스의 processName ArrayList를 반환한다.
	}
}
/*
1. BasicTool 클래스는 프로세스 스케줄링을  처리하고 제어하기 위한 변수와 함수를 정의한 클래스이다.
2. 각 scheduling class 들이 상속받아 각각의 알고리즘을 정의할 함수 launcher를 abstract로 정의한다.
3. processSequence, processRuntime, processName ArrayList를 정의한다.
4. calculatingTT, calculatingWT, calculatingNTT함수는 각각 처리 완료된 프로세스의 TT, WT, NTT를 계산하는 함수이다.
4. getSequence, getRuntime, getName함수는 각각 처리한 프로세스의 processSequence, processRuntime, processName을 반환하는 함수이다.
 */

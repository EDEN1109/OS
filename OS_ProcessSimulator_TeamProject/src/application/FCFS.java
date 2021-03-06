package application;

public class FCFS extends BasicTool {
	private Process[] Pro;

	public FCFS(Process[] Pro) {
		this.Pro = Pro;
	}

	@Override
	public Process[] launcher() {
		int time = 0; 
		// 현재 시간이 얼마나 지났는지 저장하는 변수
		int count = 0; 
		// 현재 일을 마친 프로세스가 몇 개인지 저장하는 변수
		int i = 0; 
		// 정렬된 프로세스를 차례로 불러오기 위한 Index 변수

		while(count!=Pro.length) { 
			// count가 프로세스 수와 같을 때 까지 수행
			// (모든 프로세스가 처리될 때 까지 수행)
			if(Pro[i].isArrival(time)&&Pro[i].getProcessed()) {
				// 아직 처리되지 않은 프로세스가 도착한 상태라면
				time += Pro[i].getBT(); 
				// 처리완료된 프로세스의 BT를 time에 더한다.
				int TT = calculatingTT(time, Pro[i].getAT());
				// 프로세스의 TT를 계산한다.       
				int WT = calculatingWT(time, Pro[i].getAT(), Pro[i].getBT());
				// 프로세스의 WT를 계산한다.
				double NTT = calculatingNTT(time, Pro[i].getAT(), Pro[i].getBT());
				// 프로세스의 NTT를 계산한다.
				Pro[i].setResult(TT, WT, NTT);
				// 해당 프로세스의 TT, WT, NTT의 값을 설정한다.
				Pro[i].setProcessed();
				// 해당 프로세스가 처리완료 되었다.
				processSequence.add(i);
				// processSequence에 index i를 추가한다.
				processRuntime.add(Pro[i].getBT());
				// processRuntime에 해당 프로세스의 Burst Time을 추가한다.
				processName.add(Pro[i].getProcessName());
				// processName에 해당 프로세스의 Name을 추가한다.
				count++;
				// 처리완료된 프로세스의 수를 증가시킨다.
				i = (i+1)%Pro.length;
				// i를 증가시켜 다음 프로세스를 가리키게 한다.
				// i가 Pro.length를 넘지 않도록 % Pro.length 연산을 수행한다.
			}
			else { 
				time++;
				// 아직 도착한 프로세스가 없거나, 도착한 프로세스들이 모두 처리 완료된 상태이면 수행할 작업이 없으므로 time만 1 증가시켜 준다.
				processSequence.add(-1);
				// processSequence에 –1을 추가, 공백을 의미한다.
				processRuntime.add(1);
				// processRuntime에 1을 추가, 공백을 한 칸 만들어준다.
				processName.add("Blank");
				// processName에 문자열 “Blank”를 추가, 이름을 “Blank”으로 설정한다.
			}
		}
		return Pro; // 프로세스 배열을 반환한다.
	}
}
/*
1. FCFS 클래스는 First Come First Served 스케줄링 기법을 구현한 클래스이다.
2. 모든 프로세스가 작업을 종료할 때 까지 반복문을 실행한다.
3. 아직 처리되지 않은 프로세스가 도착한 상태라면
   - 프로세스의 BT를 time에 더한다.
   - 해당 프로세스의 TT, WT, NTT의 값을 설정한다.
   - processSequence에 index i를 추가한다.
   - processRuntime에 Burst Time을 추가한다.
   - processName에 해당 프로세스의 Name을 추가한다.
   - count를 1 증가 시킨다.
   - i를 증가시켜 다음 프로세스를 가리키게 한다,
4. 아직 도착한 프로세스가 없거나, 도착한 프로세스들이 모두 처리 완료된 상태이면 
   - 수행할 작업이 없으므로 time만 1 증가 시켜준다.
   - processSequence에 공백을 의미하는 –1을 추가한다.
   - processRuntime에 공백 한 칸을 의미하는 1을 추가한다.
   - processName에 문자열 “Blank”를 추가한다.
   - 아직 처리되지 않은 프로세스가 도착할 때까지 위의 작업을 수행한다.
5. 프로세스 배열을 반환한다.
 */
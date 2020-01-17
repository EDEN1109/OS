package application;
public class HRRN extends BasicTool {
	private Process[] Pro;

	public HRRN(Process[] Pro) {
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
		double[] Ratio_Array = new double[Pro.length]; 
		// Response ratio를 저장하는 배열
		while(count!= Pro.length) {
			// count가 프로세스 수와 같을 때 까지 수행(모든 프로세스가 처리될 때 까지 수행)
			for(i=0;i<Pro.length;i++) {
				double Watingtime = time-Pro[i].getAT();
				// Response ratio 계산을 위해 WT(time-AT)를 저장하는 변수
				Ratio_Array[i]=caculatingRatio(Watingtime, Pro[i].getBT());
				// Response ratio를 계산하여 Ratio_Array에 저장
			}

			double max=0; 
			// 가장 큰 Response ratio를 저장할 변수
			for(i=0;i<Pro.length;i++) {
				if(max<Ratio_Array[i]&&Pro[i].getProcessed()&&Pro[i].isArrival(time))
					max=Ratio_Array[i];
				// Response ratio가 max 보다 크면서 아직 처리되지 않은 프로세스가 도착한 상태라면 max에 Ratio_Array[i]를 저장한다.
			}

			i = 0;
			while(i<Pro.length) {
				if(Pro[i].isArrival(time)&&Pro[i].getProcessed()&&max==Ratio_Array[i]) {
					// Response ratio의 최대값을 가진 프로세스가 아직 처리되지 않았고 도착한 상태라면
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
					break;
					// Response ratio가 최대인 프로세스를 찾아 처리했으므로 다시 Response ratio를 검사하거나, 모든 프로세스가 처리됐을 경우 루프를 종료한다.
				}
				else if(i==(Pro.length-1)) { 
					time++;
					// 아직 도착한 프로세스가 없거나, 도착한 프로세스들이 
					// 모두 처리 완료된 상태이면 수행할 작업이 없으므로 time만 1 증가시켜 준다.
					processSequence.add(-1);
					// processSequence에 –1을 추가, 공백을 의미한다.
					processRuntime.add(1);
					// processRuntime에 1을 추가, 공백을 한 칸 만들어준다.
					processName.add("Blank");
					// processName에 문자열 “Blank”를 추가한다.
				} 

				i++;
			}
		}
		return Pro; 
		// 프로세스 배열을 반환한다.
	}
	public double caculatingRatio(double WT, int BT) { 
		// 프로세스의 ResponseRatio를 계산하는 함수
		return (WT+BT)/BT; 
		// Response ratio = (WT+BT)/BT
	}
}
/*
1. HRRN 클래스는 Highest Response Ratio next 스케줄링 기법을 구현한 클래스이다.
2. 모든 프로세스가 작업을 종료할 때 가지 반복문을 실행한다.
3. Watingtime에 time-AT를 저장한다. 이를 이용하여 계산한 Response ratio를 Ratio_Array에 저장한다.
4. 가장 큰 Response ratio를 저장할 변수 max를 선언한다.
5. 도착한 미처리 프로세스들의 Response ratio 중 최대를 결정하여 max에 저장한다.
6. Response ratio의 최대값을 가진 프로세스가 아직 처리되지 않았고 도착한 상태라면
   - 프로세스의 BT를 time에 더한다.
   - 프로세스의 TT, WT, NTT를 설정한다.
   - processSequence에 index i를 추가한다.
   - processRuntime에 해당 프로세스의 Burst Time을 추가한다.
   - processName에 해당 프로세스의 Name을 추가한다.
   - count를 1 증가시킨다.
7. Response ratio가 최대인 프로세스를 찾아 처리했으므로 다시 Response ratio를 검사하거나, 모든 프로세스가 처리됐을 경우 루프를 종료한다.
8. 아직 도착한 프로세스가 없거나, 도착한 프로세스들이 모두 처리 완료된 상태이면
   - 수행할 작업이 없으므로 time만 1 증가 시켜준다.
   - processSequence에 공백을 의미하는 –1을 추가한다.
   - processRuntime에 공백 한 칸을 의미하는 1을 추가한다.
   - processName에 문자열 “Blank”를 추가한다.
   - 아직 처리되지 않은 프로세스가 도착할 때까지 위의 작업을 수행한다.
9. 프로세스 배열을 반환한다.
 */
// SRTN
package application;

public class SRTN extends BasicTool {
	private Process[] Pro;

	public SRTN(Process[] Pro) {
		this.Pro = Pro;
	}

	@Override
	public Process[] launcher() {
		int time = 0; 
		// 현재 시간이 얼마나 지났는지 저장하는 변수
		int count = 0; 
		// 현재 일을 마친 프로세스가 몇 개인지 저장하는 변수
		int[] BT_Array = new int[Pro.length]; 
		// 프로세스 각각의 잔여 BT를 저장하는 배열

		for(int i=0; i<Pro.length; i++) {
			BT_Array[i] = Pro[i].getBT(); 
			// 초기값 BT를 BT_Array에 저장한다.
		}
		while(count != Pro.length) {
			// count가 프로세스 수와 같을 때 까지 수행한다.
			// (모든 프로세스가 처리될 때 까지 수행)
			int minBT = -1;
			// 가장 작은 잔여 BT의 Index를 저장하는 minBT 변수를 –1로 초기화한다.

			for(int i=0; i<Pro.length; i++) {
				if(minBT==-1&&Pro[i].getProcessed()) {
					minBT = i;
					// 첫 번째 검사의 경우 도착한 프로세스가 있다면 Index를 minBT에 저장한다. 
				}
				if(minBT!=-1&&Pro[i].isArrival(time)&&Pro[i].getProcessed()&&BT_Array[minBT]>BT_Array[i])
					minBT = i;
				// 두 번째 검사부터 도착한 프로세스가 minBT보다 작은 잔여 BT를 가졌다면 Index를 minBT에 저장한다.
			}
			
			if(minBT!=-1&&Pro[minBT].isArrival(time)&&Pro[minBT].getProcessed()) {
				BT_Array[minBT] -= 1;
				// minBT에 해당하는 프로세스에 대하여 시간 1만큼 작업을 수행한다.
				time++; 
				// 1만큼 작업을 수행했으므로 time을 1만큼 증가시킨다.
				processSequence.add(minBT);
				// processSequence에 index minBT를 추가한다.
				processName.add(Pro[minBT].getProcessName());
				// processName에 해당 프로세스의 Name을 추가한다.
				processRuntime.add(1);
				// time 1만큼 작업을 수행했으므로 processRuntime에 1을 추가한다.
				if(BT_Array[minBT]<=0) {
					// 프로세스가 처리 완료되었다면
					int TT = calculatingTT(time, Pro[minBT].getAT());
					// 프로세스의 TT를 계산한다.       
					int WT = calculatingWT(time, Pro[minBT].getAT(), Pro[minBT].getBT());
					// 프로세스의 WT를 계산한다.      
					double NTT = calculatingNTT(time, Pro[minBT].getAT(), Pro[minBT].getBT());
					// 프로세스의 NTT를 계산한다.
					Pro[minBT].setResult(TT, WT, NTT);
					// minBT에 해당하는 프로세스의 TT, WT, NTT의 값을 설정한다.
					Pro[minBT].setProcessed();
					// minBT에 해당하는 프로세스가 처리완료 되었다.
					count++;
					// 처리완료된 프로세스의 수를 증가시킨다.
				}
			}
			else { 
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
		}
		return Pro; 
		// 프로세스 배열을 반환한다.
	}
}
/*
1. SRTN 클래스는 Shortest Remaining Time Next 스케줄링 기법을 구현한 클래스 이다.
2. 초기값 BT를 BT_Array에 저장한다.
3. 모든 프로세스가 처리완료될 때 까지 수행한다.
4. 아직 처리되지 않은 도착한 프로세스 중 잔여 BT가 최소인 프로세스를 결정하여 minBT에 index를 저장한다.
5. minBT에 해당하는 프로세스의 잔여 수행시간을 1 감소시키고 및 time을 1 증가시킨다.
6. processSequence에 index minBT를 추가한다.
7. processName에 해당 프로세스의 Name을 추가한다.
8. time 1만큼 작업을 수행했으므로 processRuntime에 1을 추가한다.
7. 프로세스가 처리완료 되었다면 TT, WT, NTT를 설정한 후 count를 1 증가시킨다.
8. 아직 도착한 프로세스가 없거나, 도착한 프로세스들이 모두 처리완료된 상태이면
   - time만 1 증가시켜 준다.
   - processSequence에 공백을 의미하는 –1을 추가한다.
   - processRuntime에 공백 한 칸을 의미하는 1을 추가한다.
   - processName에 문자열 “Blank”를 추가한다.
9. 모든 프로세스가 처리 완료되면 프로세스 배열을 반환한다.
 */
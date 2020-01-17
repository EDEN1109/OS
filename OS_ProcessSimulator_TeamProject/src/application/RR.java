package application;

import java.util.ArrayList;

public class RR extends BasicTool {
	private Process[] Pro; 
	private int TQ; 
	// Time Quantum

	public RR(Process[] Pro, int TQ) {
		this.Pro = Pro; 
		this.TQ = TQ; 
		// Time Quantum을 초기화한다.
	}

	@Override
	public Process[] launcher() {
		//  TODO Auto-generated method stub
		int time = 0; 
		// 현재 시간이 얼마나 지났는지 저장하는 변수
		int count = 0; 
		// 현재 일을 마친 프로세스가 몇 개인지 저장하는 변수
		ArrayList<Integer> order = new ArrayList<Integer> (); 
		// 프로세스들의 실제 작업 Sequence를 저장하는 ArrayList
		int[] BT_Array = new int[Pro.length];
		// 프로세스 각각의 잔여 Burst Time들을 저장하는 배열

		for(int i=0; i<Pro.length; i++) {
			BT_Array[i] = Pro[i].getBT();
			// 초기값 BT를 BT_Array에 저장한다.
			if(Pro[i].isArrival(time)) { order.add(i); }
			// 1. 프로세스가 도착하면
			// 2. 해당 프로세스의 AT가 time보다 작거나 같다면
			// 3. 해당 프로세스의 index를 order에 저장한다.
		}

		while(count!=Pro.length) {
			// count가 프로세스 수와 같을 때 까지 수행한다.
			// (모든 프로세스가 처리될 때 까지 수행)
			if(order.isEmpty()&&count!=Pro.length) { 
				// 작업 Sequence가 배정된 프로세스가 없고 모든 프로세스가 처리되지 않았다면				
				time++;
				// 수행할 작업이 없으므로 time만 1 증가시켜준다.
				processSequence.add(-1);
				// processSequence에 공백을 의미하는 –1을 추가한다.
				processRuntime.add(1);
				// processRuntime에 공백 한 칸을 의미하는 1을 추가한다.
				processName.add("Blank");
				// processName에 문자열 “Blank”를 추가한다.

				for(int i=0; i<Pro.length; i++) {
					if(!order.contains(i)&&Pro[i].isArrival(time)) 
						// 도착한 프로세스가 order에 없으면
					{ order.add(i); }
					// order에 도착한 프로세스의 index를 추가한다.
				}
			}
			else if(Pro[order.get(0)].getProcessed())
				// order에 있는 첫 번째 index의 프로세스가 처리되지 않았다면
			{ 
				processSequence.add(order.get(0));
				// processSequence에 해당 프로세스의 index를 추가한다.
				processName.add(Pro[order.get(0)].getProcessName());
				// processName에 해당 프로세스의 Name을 추가한다.

				if(BT_Array[order.get(0)]-TQ>=0) { 
					// 해당 프로세스의 남은 BT가 TQ보다 크다면
					time += TQ;
					// TQ 동안만 프로세스가 일을 처리할 수 있기 때문에 time에 TQ을 더한다.
					processRuntime.add(TQ); 
					// processRuntime에 TQ를 추가한다.
				}				
				else { 
					// 프로세스의 남은 BT가 TQ보다 작거나 같다면
					time += BT_Array[order.get(0)]; 
					// 남은 BT동안 프로세스가 일을 처리할 수 있기 때문에 time에 남은 BT를 더해준다.
					processRuntime.add(BT_Array[order.get(0)]);
					// processRuntime에 해당 프로세스의 BT를 추가한다.
				}
				BT_Array[order.get(0)] -= TQ;
				//  해당 프로세스의 남은 BT에서 TQ를 뺀 값을 다시 해당 BT_Array에 저장한다.
				
				for(int i=0; i<Pro.length; i++) {
					if(!order.contains(i)&&Pro[i].isArrival(time)) { order.add(i); }
					// 1. 도착한 프로세스가 order에 없으면
					// 2. order에 도착한 프로세스의 index를 추가한다.
				}

				if(BT_Array[order.get(0)]<=0) {
					// 남은BT가 0보다 작거나 같으면 
					Pro[order.get(0)].setProcessed();
					// 해당 프로세스가 처리완료 되었다.
					int TT = calculatingTT(time, Pro[order.get(0)].getAT());
					// 프로세스의 TT를 계산한다.
					int WT = calculatingWT(time, Pro[order.get(0)].getAT(), Pro[order.get(0)].getBT());
					// 프로세스의 WT를 계산한다.
					double NTT = calculatingNTT(time, Pro[order.get(0)].getAT(), Pro[order.get(0)].getBT());
					// 프로세스의 NTT를 계산한다.
					Pro[order.get(0)].setResult(TT, WT, NTT);
					// 해당 프로세스의 TT, WT, NTT의 값을  설정한다.
					count++;
					// 처리완료 된 프로세스의 수를 증가시킨다.
				}
				else { order.add(order.get(0)); }
				// 남은 BT가 0보다 크면 해당 프로세스를 order의 제일 뒤에 추가한다.
				order.remove(0);
				// order의 첫 번째에 해당하는 프로세스에 대한 작업이 끝났으므로 첫 번째 원소를 제거한다.
			}
			else { order.remove(0); }
			// 더 이상 수행할 프로세스가 없을 때 order의 첫 번째 원소를 제거한다.
			// 오류 방지 문이다.
		}
		return Pro;
		// 프로세스 배열을 반환한다.
	}
}
/* 
1. RR 클래스는 Round-Robin 스케줄링 기법을 구현한 클래스이다.
2. TQ라는 변수를 사용해 프로세스가 한 번에 일할 수 있는 시간에 제한을 둔다.
3. 프로세스들의 실제 작업 Sequence를 저장하는 order ArrayList를 생성한다.
4. BT_Array에 초기값 BT를 저장한다.
5. 프로세스가 도착했다면 order에 추가한다. 
6. 모든 프로세스가 작업을 종료할 때 까지 반복문을 실행한다.
7. order가 비어있으며 모든 프로세스가 처리되지 않은 경우
   - 수행할 작업이 없으므로 time만 1 증가 시켜준다.
   - processSequence에 공백을 의미하는 –1을 추가한다.
   - processRuntime에 공백 한 칸을 의미하는 1을 추가한다.
   - processName에 문자열 “Blank”를 추가한다.
8. order에 있는 첫 번째 index의 프로세스가 처리되지 않았다면
   - processSequence에 index i를 추가한다.
   - processRuntime에 Burst Time을 추가한다.
   - processName에 해당 프로세스의 Name을 추가한다. 
   - 해당 프로세스의 남은 BT가 TQ보다 크다면 현재시간에 TQ을 더한다.
   - 해당 프로세스의 남은 BT가 TQ보다 작거나 같다면 현재시간에 남은 BT를 더한다.
   - 해당 프로세스의 남은 BT에서 TQ를 뺀 값을 다시 해당 BT_Array에 저장한다.
   - 도착한 프로세스가 order에 없으면, order에 도착한 프로세스의 index를 추가한다.
   - 남은 BT가 0보다 작거나 같으면 프로세스가 처리 완료 되었다는 의미 이므로 해당 프로세스의 TT, WT, NTT를 설정한다. 
   - 남은 BT가 0보다 크면 해당 프로세스를 order의 제일 뒤에 추가하고 order의 첫 번째 원소를 제거한다.
9. 더 이상 수행할 프로세스가 없을 때 오류 방지를 위해 order의 첫 번째 원소를 제거한다.
10. 프로세스 배열을 반환한다.
 */
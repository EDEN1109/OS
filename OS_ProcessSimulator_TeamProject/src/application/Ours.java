package application;

import java.util.ArrayList; 
import java.util.Random;

public class Ours extends BasicTool {
	private Process[] Pro;
	private ArrayList<ArrayList<Integer>> BT_Array = new ArrayList<ArrayList<Integer>> ();
	// 메뉴 범위 안에 있는 BT값으로 각 프로세스의 주어진 BT를 분할하여 저장하는 ArrayList
	// 어떤 프로세스에 주어진 BT가 분할되었을 때 일차원 ArrayList로 BT를 나열하면 BT들이 어떤 프로세스의 것인지 파악할 수 없다.
	// 따라서, 어떤 프로세스에서 어떻게 BT가 분할되었는지 파악하기 위해 이차원 ArrayList로 구현되었다.
	private int shot;
	// 아메리카노, 카페라떼, 카페모카를 만들기 위해서는 shot이 필요하다.
	// 자원의 효율적 관리를 위해 한 번에 만드는 shot의 개수를 저장하는 변수
	private int milk;
	// 카페라떼, 코코아, 카페모카를 만들기 위해서는 milk이 필요하다.
	// 자원의 효율적 관리를 위해 한 번에 만드는 milk의 개수를 저장하는 변수
	private int choco;
	// 코코아, 카페모카를 만들기 위해서는 choco가 필요하다.
	// 자원의 효율적 관리를 위해 한 번에 만드는 choco의 개수를 저장하는 변수
	private int numShot = 0;
	// 현재 shot의 개수
	// 처음에는 만들어 놓은 shot이 없음으로 0으로 초기화한다.
	private int numMilk = 0;
	// 현재 milk의 개수
	// 처음에는 만들어 놓은 milk가 없음으로 0으로 초기화한다.
	private int numChoco = 0;
	// 현재 choco의 개수
	// 처음에는 만들어 놓은 choco가 없음으로 0으로 초기화한다.

	public Ours(Process[] Pro, int shot, int milk, int choco) {
		// 프로세스의 배열과 한 번에 만드는 shot과 milk, choco를 입력받아 초기화 한다.
		this.Pro = Pro;
		this.shot = shot;
		this.milk = milk;
		this.choco = choco;
	}

	@Override
	public Process[] launcher() {
		//  TODO Auto-generated method stub
		int time = 0; 
		// 현재 시간이 얼마나 지났는지 저장하는 변수
		int count = 0; 
		// 현재 일을 마친 프로세스가 몇 개인지 저장하는 변수
		int i = 0; 
		// 프로세스를 차례로 불러오기 위한 index 변수
		for(i=0; i<Pro.length; i++) { 
			Random rand = new Random();
			int BT = Pro[i].getBT(); 
			// 해당 프로세스의 기존 BT값을 변경시키지 않기 위한 변수
			ArrayList<Integer> subArray = new ArrayList<Integer> ();
			// BT를 분할하여 저장하기 위한 ArrayList
			// BT_Array의 원소가 된다.
			while(BT>4) { 
				// BT가 4보다 크면 
				int decreaseBT = rand.nextInt(4)+1;
				// BT를 분할하기 위한 변수
				// subArray의 원소가 되는 변수이며, 1~4 사이의 난수를 생성한다.
				subArray.add(decreaseBT);
				// decreaseBT를 subArray에 추가한다.
				BT -= decreaseBT;
				// 기존 BT에서 decreaseBT를 빼는 과정을 통해 분할시킨다.
			}
			subArray.add(BT);
			// 분할되고 남은 범주(1,4)안의 마지막 BT를 subArray에 추가한다.
			BT_Array.add(subArray);
			// 완성된 subArray를 BT_Array에 추가한다.
		}
		while(count!=Pro.length) { 
			// 모든 프로세스가 일을 처리할 때 까지 반복한다.
			boolean isMake = false;
			// 이전에 음료의 제작 여부를 판단하는 변수
			// 처음에 안 만들었다고 가정하여 false로 초기화한다.
			for(i=0; i<Pro.length; i++) { 
				// 프로세스 개수만큼 반복한다.
				if(Pro[i].getProcessed()&&Pro[i].isArrival(time)&&isProduceable(BT_Array.get(i))!=-1) { 
					// 도착한 미처리 주문 프로세스에 대해 지금 가진 재료들로 음료를 만들 수 있다면
					int drink = isProduceable(BT_Array.get(i));
					// i번째 주문에서 만들 수 있는 음료의 index 번호를 저장하는 변수
					makeDrink(BT_Array.get(i).get(drink));
					// i번째 주문에서 만들 수 있는 음료를 만든다. 
					time += BT_Array.get(i).get(drink);
					// 음료 생산 시 드는 PRICE를 time에 더한다. 
					processSequence.add(i);
					// i번째 주문 프로세스의 index를 processSequence에 추가한다.
					processRuntime.add(BT_Array.get(i).get(drink));
					// i번째 주문 프로세스의 PRICE를 processRuntime에 추가한다.
					processName.add(Pro[i].getProcessName());
					// i번째 주문 프로세스의 Name을 processName에 추가한다.
					BT_Array.get(i).remove(drink);
					// 제작 완료한 음료를 삭제한다.
					if(BT_Array.get(i).isEmpty()) {
						// BT ArrayList가 비어 있다면
						// i번째 주문에서 더 이상 만들 음료가 없다면
						int TT = calculatingTT(time, Pro[i].getAT());
						// 프로세스의 TT를 계산한다.
						int WT = calculatingWT(time, Pro[i].getAT(), Pro[i].getBT());
						// 프로세스의 WT를 계산한다.
						double NTT = calculatingNTT(time, Pro[i].getAT(), Pro[i].getBT());
						// 프로세스의 NTT를 계산한다.
						Pro[i].setResult(TT, WT, NTT);
						// 해당 프로세스의 TT, WT, NTT의 값을  설정한다.
						Pro[i].setProcessed();
						// 해당 프로세스가 처리완료 되었다.
						count++;
						// 처리 완료된 프로세스의 수를 증가시킨다.
					}
					isMake = true;
					// 음료 한 잔을 완성시킨다.
					break; 
					// 먼저 도착한 주문 프로세스에서 더 만들 수 있는 음료를 찾아보거나 더 만들 수 있는 음료가 없을 경우 루프를 종료한다.
				}
			}
			if(!isMake) {
				// 현재의 자원으로 만들 수 있는 것이 없다면 혹은 주문이 도착하지 않았다면
				for(i=0; i<Pro.length; i++) {
					if(Pro[i].getProcessed()&&Pro[i].isArrival(time)) {
						// 아직 처리되지 않은 프로세스가 도착한 상태라면
						// 자원이 없어서 제작대기를 하고 있다면
						makeResource(BT_Array.get(i).get(0));
						// 해당 프로세스가 첫 번째로 주문한 음료의 자원을 생성한다.
						makeDrink(BT_Array.get(i).get(0));
						// i번째 주문에서 첫 번째 음료를 만든다. 
						time += BT_Array.get(i).get(0);
						// 음료 생산 시 드는 PRICE를 time에 더한다. 

						processSequence.add(i);
						// i번째 주문 프로세스의 index를 processSequence에 추가한다.
						processRuntime.add(BT_Array.get(i).get(0));
						// i번째 주문 프로세스의 첫 번째 음료의 PRICE를 processRuntime에 추가한다.
						processName.add(Pro[i].getProcessName());
						// i번째 주문 프로세스의 Name을 processName에 추가한다.

						BT_Array.get(i).remove(0);
						// 제작 완료한 음료를 삭제한다.

						if(BT_Array.get(i).isEmpty()) {
							// BT ArrayList가 비어 있다면
							// i번째 주문에서 더 이상 만들 음료가 없다면
							int TT = calculatingTT(time, Pro[i].getAT());
							// 프로세스의 TT를 계산한다.
							int WT = calculatingWT(time, Pro[i].getAT(), Pro[i].getBT());
							// 프로세스의 WT를 계산한다.	
							double NTT = calculatingNTT(time, Pro[i].getAT(), Pro[i].getBT());
							// 프로세스의 NTT를 계산한다.
							Pro[i].setResult(TT, WT, NTT);
							// 해당 프로세스의 TT, WT, NTT의 값을  설정한다.
							Pro[i].setProcessed();
							// 해당 프로세스가 처리완료 되었다.
							count++;
							// 처리 완료된 프로세스의 수를 증가시킨다.
						}
						break;
						// 생성한 자원을 즉시 음료 제작에 사용하기 위해 break 한다.
					}
					else if(i==Pro.length-1) { 
						// 프로세스가 도착하지 않았거나, 모든 주문 프로세스가 필요한 자원이 없어 음료제작이 불가한 상태라면
						time++; 
						// 수행할 작업이 없으므로 time만 1 증가시켜준다.
						processSequence.add(-1);
						// processSequence에 –1을 추가, 공백을 의미한다.
						processRuntime.add(1);
						// processRuntime에 1을 추가, 공백을 한 칸 만들어준다.
						processName.add("Blank");
						// processName에 문자열“Blank”를 추가, 이름을 “Blank”으로 설정한다.
					}
				}
			}
		}
		return Pro;
		// 프로세스 배열을 반환한다.
	}
	private int isProduceable(ArrayList<Integer> BT) { 
		// BT ArrayList를 받아서 음료를 만들 수 있는 상태인지 반환하는 함수
		for(int i=0; i<BT.size(); i++) {
			if(BT.get(i)==1&&numShot>0) { return i; } 
			// 음료가 아메리카노일 때, shot이 있어서 만들 수 있는 상태라면 해당 index를 반환한다.
			else if(BT.get(i)==2&&numShot>0&&numMilk>0) { return i; }
			// 음료가 카페라떼일 때, shot과 milk가 있어서 만들 수 있는 상태라면 해당 index를 반환한다.
			else if(BT.get(i)==3&&numMilk>0&&numChoco>0) { return i; }
			// 음료가 코코아일 때, milk와 choco가 있어서 만들 수 있는 상태라면 해당 index를 반환한다.
			else if(BT.get(i)==4&&numShot>0&&numMilk>0&&numChoco>0){ return i; } 
			// 음료가 카페모카일 때, shot과 milk와 choco가 있어서 만들 수 있는 상태라면 해당 index를 반환한다.
		}
		return -1;
		// 만들 수 있는 음료가 없다면 –1을 반환한다.
	}
	private void makeDrink(int BT) { 
		// 주문 번호를 입력 받아서 자원을 소비해 음료를 만드는 함수
		if(BT==1) { numShot--; } 
		// 아메리카노라면 shot을 1 감소한다.
		else if(BT==2) { numShot--; numMilk--; } 
		// 카페라떼 라면 shot과 milk를 1 감소한다.
		else if(BT==3) { numMilk--; numChoco--; } 
		// 코코아라면 milk와 choco를 1 감소한다.
		else { numShot--; numMilk--; numChoco--; } 
		// 카페모카라면 shot, milk, choco를 1 감소한다.
	}
	private void makeResource(int BT) { 
		// 자원이 부족할 때 자원을 만드는 함수	
		if(numShot==0&&BT!=3)
			numShot += shot;
		// 코코아 이외의 음료를 주문했을 때 numShot의 개수가 없다면 한 번에 만드는 shot의 개수만큼 numShot에 더한다.
		// 코코아 이외의 모든 음료에는 shot이 들어간다.
		if(numMilk==0&&BT!=1)
			numMilk += milk;
		// 아메리카노 이외의 음료를 주문하고 numMilk의 개수가 없다면 한 번에 만드는 milk의 개수만큼 numMilk에 더한다.
		// 아메리카노 이외의 모든 음료에는 milk가 들어간다.
		if(numChoco==0&&BT!=1&&BT!=2)
			numChoco += choco;
		// 아메리카노, 카페라떼 이외의 음료를 주문하고 numChoco의 개수가 없다면 한 번에 만드는 초코의 개수만큼 numChoco에 더한다.
		// 아메리카노, 카페리떼 이외의 모든 음료에는 choco가 들어간다.
	}
}

/* 
1. Ours 클래스는 커피숍에서 주문한 음료를 처리하는 과정을 모티브하여 스케줄링 기법으로 구현한 클래스이다.
2. 이차원 ArrayList인 BT_Array를 이용하여 한 프로세스의 BT를 범위(1~4)로 나누어 여러 개의 BT를 저장할 수 있도록 한다.
   - 만약 처음 입력한 값이 범위에서 벗어났다면 랜덤 값을 통해 여러 개의 BT로 나눈다.
3. Pro.length 만큼 subArray를 생성한다.
   - BT_Array의 원소를 1개 이상 저장할 수 있도록 subArray를 선언한다.
   - subArray는 BT_Array의 하위 ArrayList이다. 
   - 기존 BT가 4보다 크면 1과 4 사이의 난수 decreaseBT를 subArray에 추가한다. 또한 기존 BT에서 decreaseBT를 빼는 과정을 통해 분할시킨다.
   - 1과 4 사이의 기존 BT는 subArray에 추가 되어 최종적으로 subArray이 BT_Array에 추가한다.    
4. 모든 프로세스가 작업을 종료할 때 까지 반복문을 실행한다.
5. 아직 처리되지 않은 프로세스가 도착하고 지금 가진 재료들로 음료를 만들 수 있다면
   - 음료를 만들고 현재시간을 처리한다.    
   - i번째 주문 프로세스의 index를 processSequence에 추가한다.
   - i번째 주문 프로세스에서 생산한 음료의 PRICE를 processRuntime에 추가한다.
   - i번째 주문 프로세스의 Name을 processName에 추가한다. 
   - 제작 완료한 음료를 삭제한다.
   - 더 이상 만들 음료가 없다면 해당 프로세스의 TT, WT, NTT를 설정한다. 
   - 먼저 주문한 음료를 처리할 수 있는 동안 실행한 후 탈출한다. 
6. 현재의 자원으로 만들 수 있는 것이 없거나 주문이 도착하지 않았다면
   - 아직 처리되지 않은 프로세스가 도착한 상태라면
   - 해당 프로세스가 첫 번째로 주문한 음료의 자원을 생성하고 음료를 제작한다.
   - 음료 생산 시 드는 PRICE를 time에 더한다. 
   - i번째 주문 프로세스의 index를 processSequence에 추가한다.
   - i번째 주문 프로세스의 첫 번째 음료의 PRICE를 processRuntime에 추가한다.
   - i번째 주문 프로세스의 Name을 processName에 추가한다.
   - 제작 완료한 음료를 삭제한다.
   - i번째 주문에서 더 이상 만들 음료가 없다면 해당 프로세스의 TT, WT, NTT를 설정한다. 
   - 불필요한 새로운 자원을 생성하는 것을 방지하기 위해 탈출한다. 
   - 프로세스가 도착하지 않았거나, 모든 주문 프로세스가 필요한 자원이 없어 음료제작이 불가한 상태라면
   - 수행할 작업이 없으므로 time만 1 증가 시켜준다.
   - processSequence 에 공백을 의미하는 –1을 추가한다.
   - processRuntime에 공백 한 칸을 의미하는 1을 추가한다.
   - processName에 문자열 “Blank”를 추가한다.
   - 프로세스 ArrayList을 반환한다.
   - isProduceable 함수는 BT ArrayList를 받아서 음료를 만들 수 있는 상태인지 확인하고 제작 가능 음료가 존재하면 해당 subArray의 index를 반환하고 없다면 -1을 반환하는 함수이다.
   - makeDrink 함수는 주문 번호를 입력 받아서 음료를 제작하고 사용한 자원의 수를 감소시키는 함수이다.
   - makeResource 함수는 주문 번호를 입력 받아서 남은 자원이 부족한 경우 자원을 만드는 함수이다.
 */
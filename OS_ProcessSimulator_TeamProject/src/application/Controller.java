package application;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class Controller {
	private int Choose;    
	// ����� scheduling Mode�� ������ ����
	private int PN;
	// ���μ����� ���� ������ ����
	private int TQ;
	// RR���� ����� Time quantum�� ������ ����
	private int Shot;
	private int Milk;
	private int Choco;
	// Shot, Milk, Choco : Ours���� ���μ����� ����� ���
	private Process[] Pro;
	// Process���� ������ �迭
	private ObservableList<Process> processData;
	// ����ڿ��� ���μ����� ������ �Է¹��� ObservableList �̴�.
	private ArrayList<Integer> processSequence = new ArrayList<Integer> ();
	// Gantt Chart�� ����� ��, ���μ������� ó�� ������ �����Ͽ� ���� ���μ����� ���� ����ȭ ���� ���μ����� ���� ���� ���μ������� �����ϱ� ���� ArrayList�̴�.
	private ArrayList<Integer> processRuntime = new ArrayList<Integer> ();
	// Gantt Chart�� ����� ��, ���μ������� �۾� ����ð� �����Ͽ� �ش� ���μ��� �׷����� ���� ������ ���� ArrayList�̴�.
	private ArrayList<String> processName = new ArrayList<String> ();
	// Gantt Chart�� ����� ��, �۾��� ������ ���μ����� �̸��� �����Ͽ� ����� ���� ArrayList�̴�.
	public Controller(int Choose, int PN, ObservableList<Process> processData) { 
		// ����ڰ� �Է��� ��ġ�� Controller�� �����ϴ� ������
		// Ours, RR�� ������ �����ٸ� ����� ������ ��쿡 �ش��Ѵ�.
		this.processData = processData;    
		// �Է¹��� ���μ��� ������ �����Ѵ�.
		this.Choose = Choose;    
		// ������ �����ٸ� ����� �����Ѵ�.
		this.PN = PN;
		// �Է¹��� ���μ����� ���� �����Ѵ�.
		Pro = new Process[PN];    
		// PN��ŭ Pro �迭�� �Ҵ��Ѵ�.
	}
	public Controller(int Choose, int PN, ObservableList<Process> processData, int TQ) { // init Type2
		// ����ڰ� RR�� �����Ͽ� TQ�� �Է¹��� ����� ������
		this.processData = processData;
		this.Choose = Choose;  
		this.PN = PN;
		this.TQ = TQ;    
		// �Է¹��� TQ�� �����Ѵ�.
		Pro = new Process[PN]; 
	}
	public Controller(int Choose, int PN, ObservableList<Process> processData, int Shot, int Milk, int Choco) { // init Type2
		// ����ڰ� Ours�� �����Ͽ� Shot, Milk, Choco�� �Է¹��� ����� ������
		this.processData = processData;    
		this.Choose = Choose;   
		this.PN = PN;   
		this.Shot = Shot;    
		// �Է¹��� shot�� �����Ѵ�.
		this.Milk = Milk;    
		// �Է¹��� milk�� �����Ѵ�.
		this.Choco = Choco;    
		// �Է¹��� choco�� �����Ѵ�.
		Pro = new Process[PN];    
	}

	public void newSimulation() {
		// �Է¹��� �����ٸ� ����� ���� ���������� ���μ��� �����ٸ��� �ǽ��ϴ� �Լ�
		init();    
		// Pro �迭�� �Է¹��� Process�� �����ϸ�, ��� ���μ����� �۾����� �������� �ǵ�����.
		if(Choose == 0) {    
			// FCFS�� ������ ���
			sortingArray(0);    
			// AT�� �������� ���μ����� ������������ �����Ѵ�.
			FCFS fcfs = new FCFS(Pro);    
			// Pro �迭�� �Ѱ��ָ鼭 FCFS �����Ѵ�.
			Pro = fcfs.launcher();
			// FCFS�� �����Ѵ�.
			processSequence = fcfs.getSequence();
			// FCFS���� processSequence�� �޾ƿ� �����Ѵ�.
			processRuntime = fcfs.getRuntime();
			// FCFS���� processRuntime�� �޾ƿ� �����Ѵ�.
			processName = fcfs.getName();
			// FCFS���� processName�� �޾ƿ� �����Ѵ�.
		}
		else if(Choose == 1) {     
			// RR�� ������ ���
			sortingArray(0);    
			// AT�� �������� ���μ����� ������������ �����Ѵ�.
			RR rr = new RR(Pro, TQ);
			// Pro �迭�� TQ�� �Ѱ��ָ鼭 RR�� �����Ѵ�.
			Pro = rr.launcher();    
			// RR�� �����Ѵ�.
			processSequence = rr.getSequence();
			// RR���� processSequence�� �޾ƿ� �����Ѵ�.
			processRuntime = rr.getRuntime();
			// RR���� processRuntime�� �޾ƿ� �����Ѵ�.
			processName = rr.getName();
			// RR���� processName�� �޾ƿ� �����Ѵ�.
		}
		else if(Choose == 2) { 
			// SPN�� ������ ���
			sortingArray(1);    
			// SPN�� ��� BT�� ���� ������� ���μ����� ó���ϱ� ������ BT�� �������� ���μ����� ������������ �����Ѵ�.
			SPN spn = new SPN(Pro);
			// Pro �迭�� �Ѱ��ָ鼭 SPN�� �����Ѵ�.
			Pro = spn.launcher();    
			// SPN�� �����Ѵ�.
			processSequence = spn.getSequence();
			// SPN���� processSequence�� �޾ƿ� �����Ѵ�.
			processRuntime = spn.getRuntime();
			// SPN���� processRuntime�� �޾ƿ� �����Ѵ�.
			processName = spn.getName();
			// SPN���� processName�� �޾ƿ� �����Ѵ�.
			sortingArray(0);
			// ���� ��� ����� ���� AT�� �������� ���μ����� ������������ �����Ѵ�.
		}
		else if(Choose == 3) {    
			// SRTN�� ������ ���
			SRTN srtn = new SRTN(Pro);
			// Pro �迭�� �Ѱ��ָ鼭 SRTN�� �����Ѵ�.
			Pro = srtn.launcher();    
			// SRTN�� �����Ѵ�.
			processSequence = srtn.getSequence();
			// SRTN���� processSequence�� �޾ƿ� �����Ѵ�.
			processRuntime = srtn.getRuntime();
			// SRTN���� processRuntime�� �޾ƿ� �����Ѵ�.
			processName = srtn.getName();
			// SRTN���� processName�� �޾ƿ� �����Ѵ�.
			sortingArray(0);
			// ���� ��� ����� ���� AT�� �������� ���μ����� ������������ �����Ѵ�.
		}
		else if(Choose == 4) { 
			// HRRN�� ������ ���
			sortingArray(0);    
			// AT�� �������� ���μ����� ������������ �����Ѵ�.
			HRRN hrrn = new HRRN(Pro);
			// Pro �迭�� �Ѱ��ָ鼭 HRRN�� �����Ѵ�.
			Pro = hrrn.launcher();    
			// HRRN�� �����Ѵ�.
			processSequence = hrrn.getSequence();
			// HRRN���� processSequence�� �޾ƿ� �����Ѵ�.
			processRuntime = hrrn.getRuntime();
			// HRRN���� processRuntime�� �޾ƿ� �����Ѵ�.
			processName = hrrn.getName();
			// HRRN���� processName�� �޾ƿ� �����Ѵ�.
			sortingArray(0);
			// ���� ��� ����� ���� AT�� �������� ���μ����� ������������ �����Ѵ�.
		}
		else if(Choose == 5) {     
			// Ours�� ������ ���
			Ours ours = new Ours(Pro, Shot, Milk, Choco);
			// Pro �迭, �� ���(Shot, Milk, Choco)�� �Ѱ��ָ鼭 Ours�� �����Ѵ�.
			Pro = ours.launcher();    
			// Ours�� �����Ѵ�.
			processSequence = ours.getSequence();
			// Ours���� processSequence�� �޾ƿ� �����Ѵ�.
			processRuntime = ours.getRuntime();
			// Ours���� processRuntime�� �޾ƿ� �����Ѵ�.

			processName = ours.getName();
			// Ours���� processName�� �޾ƿ� �����Ѵ�.
			sortingArray(0);
			// ���� ��� ����� ���� AT�� �������� ���μ����� ������������ �����Ѵ�.
		}
	}

	private void init() {    
		// ��� ���μ����� �ʱ�ȭ ��Ű�� ���� ������ �Լ�
		for(int i=0; i<PN; i++) {
			Pro[i] = processData.get(i);  
			// ProcessData�� �޾ƿ� Pro�� �����Ѵ�.
			Pro[i].resetProcessed(); 
			// ��� ���μ����� ó�����¸� true�� �����Ѵ�.(���� ó������ ���� ���·� ������ �ǹ�)
		}
	}
	public Process[] getProcess() {
		// ���μ������� ��ȯ�ϴ� �Լ�
		return Pro;
	}
	public ArrayList<Integer> getRuntime() {
		// processRuntime ArrayList�� ��ȯ�ϴ� �Լ�
		return processRuntime;
	}
	public ArrayList<Integer> getSequence() {	
		// processSequence ArrayList�� ��ȯ�ϴ� �Լ�
		return processSequence;
	}
	public ArrayList<String> getName() {
		// processName ArrayList�� ��ȯ�ϴ� �Լ�
		return processName;
	}
	public void sortingArray(int SortType) {
		// ���μ������� �����ϱ� ���� �Լ��̴�.
		Process ProCopy; // swap�� ���� ������ �ӽ� ����

		for(int i=0; i<PN-1; i++) {
			for(int j=i+1; j<PN; j++) {
				if(SortType == 0) { 
					// AT�� �������� �������� �����Ѵ�.
					if(Pro[i].getAT()>Pro[j].getAT()) {
						// Pro[j]�� AT�� Pro[i]�� AT���� ������ swap�Ѵ�.
						ProCopy = Pro[i];
						Pro[i] = Pro[j];
						Pro[j] = ProCopy;
					}
				}
				else if(SortType == 1) { // Sort By BT
					// BT�� �������� �������� �����Ѵ�.
					if(Pro[i].getBT()>Pro[j].getBT()) {
						// Pro[j]�� BT�� Pro[i]�� BT���� ������ swap�Ѵ�.
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
1. Controller Ŭ������ ���μ��� �����ٸ��� ���������� ó���ϰ� �����ϱ� ���� ������ �Լ��� ������ Ŭ�����̴�.
2. Choose, PN, TQ, Shot, Milk, Choco ������ Pro�迭�� �����Ѵ�. 
3. Gantt Chart ����� ���� processSequence, processRuntime, processName ArrayList�� �����Ѵ�.
4. Controller�� �����ڴ� ����ڰ� �Է��� ��ġ�� �Է¹��� Choose, PN, processData, TQ(RR�� ���), shot��milk��choco(Ours�� ���)�� �޾ƿ� ����� �ʱ�ȭ�Ѵ�.
5. new Simulation�Լ��� �Է¹��� �����ٸ� ����� ���� ���������� ���μ��� �����ٸ��� �ǽ��ϴ� �Լ��̴�.
   - init()�� ȣ���Ͽ� ��� ���μ����� �۾����� �������� �ǵ��� Pro�� �����Ѵ�.
   - ����ڰ� ������ �����ٸ� ����� ���� AT Ȥ�� BT�� ���μ����� �����Ѵ�.
   - ����ڰ� ������ �����ٸ� ����� ���� ��ü�� �����ϰ� ���μ��� �����ٸ��� �����Ѵ�.
   - �����ٸ� ��� ��ü���� processSequence, processRuntime, processName�� �޾ƿ� �����Ѵ�.
   - �ʿ信 ���� ������� ����� ���� ���μ����� AT �������� �������� �����Ѵ�.
6. init�Լ��� ��� ���μ����� �ʱ�ȭ ��Ű�� �Լ��̴�.
   - processData�� �޾ƿ� Pro �迭�� �����Ѵ�.
   - Pro�� ����� ��� ���μ����� ó�����¸� true�� �����Ѵ�.(���� ó������ ���� ���·� ������ �ǹ�)
7. getProcess�Լ��� ���μ������� ��ȯ�ϴ� �Լ��̴�.
8. getRuntime, getSequence, getName�Լ��� ProcessRuntime, processSequence, processName ArrayList�� ��ȯ�ϴ� �Լ��̴�.
9. sortingArray�Լ��� ���μ����� AT Ȥ�� BT�������� �������� �����ϴ� �Լ��̴�.
 */
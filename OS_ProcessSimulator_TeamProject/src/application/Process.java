package application;

public class Process {
	private String ProcessName; 
	// ���μ����� Name�� ������ �׷������� Name�� ����ȭ�ϴ� �迭
	private int AT; 
	// Arrival Time�� �����ϴ� ����
	private int BT; 
	// Burst Time�� �����ϴ� ����
	private boolean Processed; 
	// ���μ����� �ϴ� ���� ���� ����� �����ϴ� ����
	private int TT = 0;
	// Turnarround Time�� �����ϴ� ����
	private int WT = 0;
	// Waiting Time�� �����ϴ� ����
	private double NTT = 0;
	// Normalized Turnarround Time�� �����ϴ� ����
	
	public Process(String ProcessName, int AT, int BT) {
	// ����ڰ� �Է��� ������ �������� ���μ����� �����ϴ� ������
		this.ProcessName = ProcessName;
		this.AT = AT;
		this.BT = BT;
		Processed = true; 
		// �ʱ� ���μ����� ���� ó������ ���� ���·� �����Ѵ�.
	}
	
	public boolean isArrival(int time) {
	// time�� ���� ���μ����� ���� ���θ� ��ȯ�ϴ� �Լ� 
		return AT<=time;
		// AT�� tiem���� �۰ų� ������ True, ũ�� False�� ��ȯ�Ѵ�.
	}
	
	public void resetProcessed() {
	// ���μ����� ���� ó������ ���� ���·� �����ϴ� �Լ�
		Processed = true;
	}
	
	public String getProcessName() {
	// ���μ����� Name�� ��ȯ�ϴ� �Լ�
		return ProcessName;
	}

	public int getAT() {
	// AT�� ��ȯ�ϴ� �Լ�
		return AT;
	}
		
	public int getBT() {
	// BT�� ��ȯ�ϴ� �Լ�
		return BT;
	}
	
	public boolean getProcessed() {
	// Processed�� ��ȯ�ϴ� �Լ�
		return Processed;
	}
	
	public void setProcessed() {
	// ���μ����� ó���� ���·� �����ϴ� �Լ�
		Processed = false;
	}
	
	public void setResult(int TT, int WT, double NTT) {
	// ���� TT, WT, NTT�� �������� ���� TT, WT, NTT�� �缳���ϴ� �Լ�
		this.TT = TT;
		this.WT = WT;
		this.NTT = NTT;
	}
	
	public int getTT() {
	// TT�� ��ȯ�ϴ� �Լ�
		return TT;
	}
	
	public int getWT() {
	// WT�� ��ȯ�ϴ� �Լ�
		return WT;
	}
	
	public double getNTT() {
	// NTT�� ��ȯ�ϴ� �Լ�
		return NTT;
	}
}
/* 
1. Process Ŭ������ ���α׷� ���� �� �ʿ��� ������� �Լ����� �����ϴ� Ŭ�����̴�.
2. ProcessName, AT, BT, Processed, TT, WT, NTT ������ �����Ѵ�.
3. Process�� �����ڴ� ����ڰ� �Է��� ������ �������� AT, BT, Processed�� �ʱ�ȭ�Ѵ�.
4. isArrival�Լ��� time�� ���� ���μ����� ���� ���θ� ��ȯ�ϴ� �Լ��̴�.
5. resetProcessed�Լ��� ���μ����� ���� ó������ ���� ���·� �����ϴ� �Լ��̴�.
6. getProcessName�Լ��� ���μ����� Name�� ��ȯ�ϴ� �Լ��̴�.
7. getAT�Լ��� AT�� ��ȯ�ϴ� �Լ��̴�.
8. getBT�Լ��� BT�� ��ȯ�ϴ� �Լ��̴�.
9. getProcessed�Լ��� Processed�� ��ȯ�ϴ� �Լ��̴�.
10. setProcessed�Լ��� ���μ����� ó���� ���·� �����ϴ� �Լ��̴�.
11. setResult�Լ��� ���� TT, WT, NTT�� �������� ���� TT, WT, NTT�� �缳���ϴ� �Լ��̴�.
12. getTT�Լ��� TT�� ��ȯ�ϴ� �Լ��̴�.
13. getWT�Լ��� WT�� ��ȯ�ϴ� �Լ��̴�.
14. getNTT�Լ��� NTT�� ��ȯ�ϴ� �Լ��̴�.
*/
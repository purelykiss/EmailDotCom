package kr.ac.kopo.util;

public class Timer {
	long StartTime = 0;
	long EndTime = 0;
	
	public Timer(){
		StartTime = 0;
		EndTime = 0;
	}
	
	public void startTimer() {
		StartTime = System.currentTimeMillis();
	}
	
	public void endTimer() {
		EndTime = System.currentTimeMillis();
		System.out.printf("[%d] 밀리초 걸렸습니다.\n", EndTime - StartTime);
	}
}

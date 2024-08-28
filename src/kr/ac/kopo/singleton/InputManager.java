package kr.ac.kopo.singleton;

import java.util.Scanner;

public class InputManager {
	public static InputManager instance;
	private Scanner sc;
	
	private InputManager() {
		sc = new Scanner(System.in);
	}
	
	public static InputManager instanciate() throws Exception {
		if(instance == null)
			instance = new InputManager();
		else
			throw new Exception("이미 InputManager 싱글톤이 있습니다.");
		if(instance == null)
			throw new Exception("싱글톤 InputManager 생성에 실패했습니다.");
		
		return instance;
	}
	
	public static InputManager getInstance(){
		if(instance == null) {
			System.out.println("아직 InputManager 싱글톤이 만들어지지 않았습니다.");
			return null;
		}else {
			return instance;
		}
	}
	
	public String nextLine() {
		return sc.nextLine();
	}
	
	public int nextInt() {
		boolean flag = false;
		int input = 0;
		while(!flag)
		try {
			input = Integer.parseInt(sc.nextLine());
			flag = true;
		} catch (Exception e) {
			System.out.println("오류! 정수를 입력하십시오.");
		}
		
		return input;
	}
}

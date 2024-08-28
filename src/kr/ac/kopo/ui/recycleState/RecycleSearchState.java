package kr.ac.kopo.ui.recycleState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import kr.ac.kopo.singleton.EmailService;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.ViewManager;
import kr.ac.kopo.ui.mail.BaseMailState;
import kr.ac.kopo.ui.mail.IMailUI;
import kr.ac.kopo.vo.EmailVO;

public class RecycleSearchState extends BaseMailState {

	public RecycleSearchState(IMailUI parent) {
		super(parent, "search", "검색하기");
	}

	@Override
	public void execute() {
		boolean flagValid = false;
		int inputNum = 0;
		while(!flagValid) {
			System.out.println("검색할 카테고리를 선택하십시오.");
			System.out.println("1. 제목");
			System.out.println("2. 내용");
			System.out.println("3. 제목 + 내용");
			System.out.println("4. 보낸이");
			inputNum = InputManager.getInstance().nextInt();
			if(inputNum <= 4 && inputNum >= 1) {
				flagValid = true;
			}else {
				System.out.println("선택지 안에서 번호를 입력하십시오.");
			}
			System.out.println();
			System.out.println();
		}
		
		System.out.print("검색할 키워드를 입력하십시오. : ");
		StringTokenizer inputStr = new StringTokenizer(InputManager.getInstance().nextLine());
		List<StringBuilder> searchPlusList = new ArrayList();
		List<StringBuilder> searchMinusList = new ArrayList();
		while(inputStr.hasMoreTokens()) {
			searchPlusList.add(new StringBuilder(inputStr.nextToken()));
		}
		for(int i = searchPlusList.size() - 1; i >= 0; i--) {
			if(searchPlusList.get(i).charAt(0) == '+')
				searchPlusList.get(i).deleteCharAt(0);
			else if(searchPlusList.get(i).charAt(0) == '-') {
				searchPlusList.get(i).deleteCharAt(0);
				searchMinusList.add(searchPlusList.get(i));
				searchPlusList.remove(i);
			}
		}
		
		List<EmailVO> searchList = null;
		
		switch(inputNum) {
		case 1->{
				searchList = EmailService.getInstance().searchEmail(3, 1, searchPlusList, searchMinusList);
			}
		case 2->{
				searchList = EmailService.getInstance().searchEmail(3, 2, searchPlusList, searchMinusList);
			}
		case 3 -> {
				Set<Integer> tmp = new HashSet<Integer>();
				tmp.add(1);
				tmp.add(2);
				searchList = EmailService.getInstance().searchEmail(3, tmp, searchPlusList, searchMinusList);
			}
		case 4->{
				Set<Integer> tmp = new HashSet<Integer>();
				tmp.add(5);
				tmp.add(6);
				searchList = EmailService.getInstance().searchEmail(3, tmp, searchPlusList, searchMinusList);
			}
		}
		System.out.println();
		System.out.println();
		System.out.printf("총 %d개의 결과가 검색됐습니다.\n", searchList.size());
		System.out.println();
		System.out.println();
		
		if(searchList.size() > 0) {
			int emailPerPage;
			int curPage;
			
			System.out.print("페이지당 노출할 이메일 개수를 입력하십시오: ");
			emailPerPage = InputManager.getInstance().nextInt();
			System.out.print("볼 페이지를 입력하십시오(첫 페이지 1): ");
			curPage = InputManager.getInstance().nextInt();
			
			if((curPage-1)*emailPerPage + 1 > searchList.size())		//설정한 페이지에 결과가 1도 없을 경우 curPage를 1로 설정
				curPage = 1;

			System.out.println();
			System.out.println();
			
			boolean flagStateFinished = false;
			while(!flagStateFinished) {
				if(searchList.size() > (curPage-1)*emailPerPage + 1) {
					System.out.printf("%-5s%-5s%-15s%-7s%-7s%-5s\n","번호", "코드", "제목", "보낸이", "아이디", "받은날짜");
					
					
					for(int i = 0; i < emailPerPage && (curPage-1)*emailPerPage + i < searchList.size(); i++) {
						EmailVO item = searchList.get(i);
						System.out.printf("%-6d%-6d%-15s%-7s%-7s%-5s\n", (curPage-1)*emailPerPage + 1 + i, item.getCode(), item.getTitle(), item.getSenderName(), item.getSenderID(), item.getDate());
					}
					
					System.out.println("현제페이지: " + curPage);
					System.out.println();
					System.out.println();
					boolean flagValid2 = false;
					while(!flagValid2) {
						System.out.println("메일 번호를 입력해서 열람, 0번을 눌러 뒤로가기,");
						System.out.println("다음페이지로 넘어가려면 아무 입력 없이 엔터");
						String input = InputManager.getInstance().nextLine();
						if(input.equals("")) {
							curPage++;
							System.out.println(curPage);
							flagValid2 = true;
						}else if(input.equals("0")) {
							parent.changeState(0);
							flagValid2 = true;
							flagStateFinished = true;
						}else {
							boolean flagInt = false;
							try {
								int n = Integer.parseInt(input);
								if(n >= (curPage-1)*emailPerPage + 1 & n <= curPage*emailPerPage) {
									ViewManager.getInstance().ViewMail(3, searchList.get(n - 1 - (curPage - 1) * emailPerPage).getCode());
									flagInt = true;
								}
							} catch (Exception e) {}
							if(flagInt) {
								flagValid2 = true;
							}
						}
					}
				}else {
					flagStateFinished = true;
				}
			}
		}
	}
	
}

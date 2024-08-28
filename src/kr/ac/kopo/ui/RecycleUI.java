package kr.ac.kopo.ui;

import kr.ac.kopo.ui.mail.BaseMailUI;
import kr.ac.kopo.ui.recycleState.RecycleBycodeState;
import kr.ac.kopo.ui.recycleState.RecycleListViewState;
import kr.ac.kopo.ui.recycleState.RecycleLobbyState;
import kr.ac.kopo.ui.recycleState.RecycleSearchState;

public class RecycleUI  extends BaseMailUI{
	public RecycleUI() {
		super("recycle", "휴지통");
		stateList.add(new RecycleLobbyState(this));	//List에 추가를 하면서 각 State들을 선언함! new IUIState(this) 형식으로 선언!
		stateList.add(new RecycleListViewState(this));
		stateList.add(new RecycleBycodeState(this));
		stateList.add(new RecycleSearchState(this));
		initialize();
	}

	@Override
	public void execute() {
		curState.execute();	
	}
}

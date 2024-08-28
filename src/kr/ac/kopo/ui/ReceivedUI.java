package kr.ac.kopo.ui;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.ui.mail.BaseMailUI;
import kr.ac.kopo.ui.mail.IMailState;
import kr.ac.kopo.ui.mail.IMailUI;
import kr.ac.kopo.ui.receivedState.ReceivedBycodeState;
import kr.ac.kopo.ui.receivedState.ReceivedListViewState;
import kr.ac.kopo.ui.receivedState.ReceivedLobbyState;
import kr.ac.kopo.ui.receivedState.ReceivedSearchState;

public class ReceivedUI  extends BaseMailUI{
	public ReceivedUI() {
		super("received", "받은 메일함");
		stateList.add(new ReceivedLobbyState(this));	//List에 추가를 하면서 각 State들을 선언함! new IUIState(this) 형식으로 선언!
		stateList.add(new ReceivedListViewState(this));
		stateList.add(new ReceivedBycodeState(this));
		stateList.add(new ReceivedSearchState(this));
		initialize();
	}

	@Override
	public void execute() {
		curState.execute();	
	}
}

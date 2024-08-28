package kr.ac.kopo.ui;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.ui.mail.BaseMailUI;
import kr.ac.kopo.ui.mail.IMailState;
import kr.ac.kopo.ui.mail.IMailUI;
import kr.ac.kopo.ui.sentState.SentBycodeState;
import kr.ac.kopo.ui.sentState.SentListViewState;
import kr.ac.kopo.ui.sentState.SentLobbyState;
import kr.ac.kopo.ui.sentState.SentSearchState;

public class SentUI  extends BaseMailUI{
	public SentUI() {
		super("sent", "보낸 메일함");
		stateList.add(new SentLobbyState(this));	//List에 추가를 하면서 각 State들을 선언함! new IUIState(this) 형식으로 선언!
		stateList.add(new SentListViewState(this));
		stateList.add(new SentBycodeState(this));
		stateList.add(new SentSearchState(this));
		initialize();
	}

	@Override
	public void execute() {
		curState.execute();	
	}
}

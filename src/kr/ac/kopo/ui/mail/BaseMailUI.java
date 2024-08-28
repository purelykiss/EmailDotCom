package kr.ac.kopo.ui.mail;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.ui.base.IUI;

public abstract class BaseMailUI extends BaseUI implements IMailUI{
	public List<IMailState> stateList;
	public IMailState curState;
	
	public BaseMailUI(String id, String description) {
		super(id, description);
		stateList = new ArrayList<IMailState>();
		curState = null;
	}

	@Override
	public void initialize() {
		changeState(0);
	}
	
	@Override
	public void changeUI(String id) {//아직 옵저버 패턴을 모르니 IMailState에선 이것으로 UI를 바꾼다.
		UIManager.getInstance().changeUI(id);
		initialize();
	}

	@Override
	public void changeUI(int index) {
		UIManager.getInstance().changeUI(index);
		initialize();
	}

	@Override
	public void changeState(String id) {
		for(IMailState item : stateList) {
			if(id.equals(item.getID())) {
				curState = item;
			}
		}
	}

	@Override
	public void changeState(int index) {
		if(stateList.size() > index)
			curState = stateList.get(index);
	}

	public String getStateDescription(String id) {
		for(IMailState item : stateList) {
			if(id.equals(item.getID())) {
				return item.getDescription();
			}
		}
		return null;
	}
	
	public String getStateDescription(int index) {
		if(stateList.size() > index)
			return stateList.get(index).getDescription();
		else
			return null;
	}
}

package view;

import view.Display.AppScreen;

@SuppressWarnings("serial")
public class ActionScreen extends Screen {

	public ActionScreen(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AppScreen getScreenId() {
		// TODO Auto-generated method stub
		return AppScreen.SELECT_ACTION;
		
	}
	
	public static void main(String[] args) {
		new Display(null).setScreen(AppScreen.SELECT_ACTION);
	}
}

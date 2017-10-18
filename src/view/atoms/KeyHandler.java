package view.atoms;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

/**
 * 
 * Most of the time when we need to handle a key, we have a lot of redundancy inside a class
 * e.g. empty press or release methods.
 * 
 * @author lausek
 *
 */
public class KeyHandler implements KeyListener {
	
	public enum HandleTarget {
		PRESS, RELEASE, TYPED
	}
	
	private Consumer<KeyEvent> pressAction, releaseAction, typedAction;
	
	/**
	 * Choose on which event the given action should be fired
	 * @param target
	 * @param action
	 * @return
	 */
	public KeyHandler handle(HandleTarget target, Consumer<KeyEvent> action) {
		
		switch(target) {
		case PRESS:
			pressAction = action;
			break;
		case RELEASE:
			releaseAction = action;
			break;
		case TYPED:
		default:
			typedAction = action;
			break;
		}
		
		return this;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) { 
		if(pressAction != null) {
			pressAction.accept(arg0);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) { 
		if(releaseAction != null) {
			releaseAction.accept(arg0);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) { 
		if(typedAction != null) {
			typedAction.accept(arg0);
		}
	}

}

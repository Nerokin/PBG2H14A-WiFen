package wifen.commons.impl;

import wifen.commons.CustomEvent;

/**
 * Put description here
 * 
 * @author unknown
 *
 */
public class CustomEventImpl implements CustomEvent {

	private boolean isConsumed;
	@Override
	public boolean isConsumed() {
		// TODO Auto-generated method stub
		return isConsumed;
	}

	@Override
	public void consume() {
		// TODO Auto-generated method stub
		isConsumed = true;
	}

}

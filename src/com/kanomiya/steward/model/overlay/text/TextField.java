package com.kanomiya.steward.model.overlay.text;


/**
 * @author Kanomiya
 *
 */
public class TextField extends Text implements IEditableText {

	public static TextField create()
	{
		return new TextField();
	}

	protected ConfirmHandler<String, ConfirmResult> confirmHandler;
	protected boolean enabled;
	protected int caretIndex;

	protected TextField()
	{
		super("");

		enabled = true;
	}



	/**
	* @inheritDoc
	*/
	@Override
	public boolean multiLineIsAvailable()
	{
		return false;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public char getConfirmationChar()
	{
		return '\n';
	}



	/**
	* @inheritDoc
	*/
	@Override
	public TextField confirmHandler(ConfirmHandler<String, ConfirmResult> confirmHandler) {
		this.confirmHandler = confirmHandler;
		return this;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public boolean hasConfirmHandler() {
		return (confirmHandler != null);
	}

	/**
	* @inheritDoc
	*/
	@Override
	public ConfirmHandler getConfirmHandler() {
		return confirmHandler;
	}


	/**
	* @inheritDoc
	*/
	@Override
	public int getCaretIndex()
	{
		return caretIndex;
	}


	/**
	* @inheritDoc
	*/
	@Override
	public void setCaretIndex(int index)
	{
		caretIndex = index;
	}


	/**
	* @inheritDoc
	*/
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public TextField enabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}


}

package com.kanomiya.steward.common.model.event;

import com.kanomiya.steward.common.model.area.Area;
import com.kanomiya.steward.common.model.assets.Assets;
import com.kanomiya.steward.common.model.icon.Icon;

/**
 * @author Kanomiya
 *
 */
public class Player extends Event {

	public boolean debugMode = false;

	public PlayerMode mode = PlayerMode.NORMAL;

	public int selectedX, selectedY;
	public int focusedX, focusedY;



	public Player(Area area, int x, int y, Icon icon)
	{
		super("player", area, x, y, icon);
	}

	int turnCount = 0;
	/**
	* @inheritDoc
	*/
	@Override public void turn(Assets assets) {
		super.turn(assets);

		// Area area = assets.getArea(areaId);

		// if (area.tileExists(x, y)) System.out.println("tip(" + x + "," + y + "): " + area.getTile(x, y).toString());

		// System.out.println("Turn " + ++turnCount); // TODO: デバッグ用コード
	}





}

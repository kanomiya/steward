package com.kanomiya.steward.view.component;

import java.awt.Graphics2D;

import com.kanomiya.steward.model.assets.Assets;
import com.kanomiya.steward.model.event.Player;
import com.kanomiya.steward.view.ViewConsts;


/**
 * @author Kanomiya
 *
 */
public class VCSelect implements IViewComponent<Player> {

	/**
	* @inheritDoc
	*/
	@Override
	public void paint(Graphics2D g, Player player, Assets assets, int frame)
	{
		if (player.getMode().enableSelecter())
		{
			g.translate(player.focusedX *ViewConsts.tileSize, player.focusedY *ViewConsts.tileSize);
			ViewConsts.vcMarker.paint(g, ViewConsts.colorFocused, assets, frame);
			g.translate(-player.focusedX *ViewConsts.tileSize, -player.focusedY *ViewConsts.tileSize);

			g.translate(player.selectedX *ViewConsts.tileSize, player.selectedY *ViewConsts.tileSize);
			ViewConsts.vcBorder.paint(g, ViewConsts.colorSelected, assets, frame);
			g.translate(-player.selectedX *ViewConsts.tileSize, -player.selectedY *ViewConsts.tileSize);
		}

	}

}

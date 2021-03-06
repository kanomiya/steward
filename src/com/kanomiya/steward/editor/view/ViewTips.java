package com.kanomiya.steward.editor.view;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Iterator;

import com.kanomiya.steward.Game;
import com.kanomiya.steward.editor.FrameTip;
import com.kanomiya.steward.model.area.Tip;
import com.kanomiya.steward.model.texture.Texture;
import com.kanomiya.steward.view.ViewBuffered;
import com.kanomiya.steward.view.ViewConsts;

/**
 * @author Kanomiya
 *
 */
public class ViewTips extends ViewBuffered<Game> {

	public Collection<Tip> tipList;
	public FrameTip frameTip;

	/**
	 * @param maxFrame
	 */
	public ViewTips() {
		super(ViewConsts.FPS);

	}

	/**
	* @inheritDoc
	*/
	@Override
	public void paintBuffer(Graphics2D g, Game game, int frame)
	{
		if (tipList == null)
		{
			tipList = game.assets.tipList();
		}

		Iterator<Tip> itr = tipList.iterator();

		int y = 0;

		while (itr.hasNext())
		{
			for (int x=0; x<8; x++)
			{
				if (itr.hasNext())
				{
					Tip tip = itr.next();
					Texture texture = tip.getIcon();

					g.translate(x *ViewConsts.tileSize, y *ViewConsts.tileSize);
					ViewConsts.vcTexture.paint(g, texture, game.assets, frame);
					g.translate(-x *ViewConsts.tileSize, -y *ViewConsts.tileSize);

				}
			}

			y ++;
		}

		if (frameTip != null)
		{
			int x = frameTip.tipSelectedX;
			y = frameTip.tipSelectedY;

			g.translate(x *ViewConsts.tileSize, y *ViewConsts.tileSize);
			ViewConsts.vcBorder.width = 2;
			ViewConsts.vcBorder.paint(g, ViewConsts.colorSelected, game.assets, frame);
			g.translate(-x *ViewConsts.tileSize, -y *ViewConsts.tileSize);
		}

	}

}

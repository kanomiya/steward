package com.kanomiya.steward.common.controller;

import com.kanomiya.steward.common.model.assets.Assets;
import com.kanomiya.steward.common.model.overlay.logger.IngameLogger;

/**
 * @author Kanomiya
 *
 */
public class CIngameLogger extends IController<IngameLogger> {

	/**
	* @inheritDoc
	*/
	@Override
	public boolean click(int button, int x, int y, ControlListener controlListener, IngameLogger logger, Assets assets)
	{
		logger.autoCloseLock = true;

		if (530 <= x && x < 546)
		{
			if (13 <= y && y < 30)
			{
				if (0 < logger.beginIndex) logger.beginIndex --;
				logger.autoScrollLock(IngameLogger.millsWait);
				return true;
			}

			if (201 <= y && y < 218)
			{
				if (logger.beginIndex < logger.items.size() -1) logger.beginIndex ++;
				logger.autoScrollLock(IngameLogger.millsWait);
				return true;
			}

		}



		return false;
	}



}

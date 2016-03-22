package com.kanomiya.steward.model.script;

import javax.script.ScriptException;

import com.kanomiya.steward.model.assets.Assets;
import com.kanomiya.steward.model.event.Player;

/**
 * @author Kanomiya
 *
 */
public class ScriptFunctionBinder {

	protected Assets assets;
	protected Player player;

	public ScriptFunctionBinder(Assets assets, Player player)
	{
		this.assets = assets;
		this.player = player;
	}

	public void execute(String src)
	{
		try {
			assets.getScriptEngine().eval(assets.getScriptCode(src).code);
		} catch (ScriptException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void exit()
	{
		System.exit(0); // VELIF
	}



}
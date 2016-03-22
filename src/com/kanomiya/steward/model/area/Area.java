package com.kanomiya.steward.model.area;

import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import com.google.common.collect.Lists;
import com.kanomiya.steward.model.assets.Assets;
import com.kanomiya.steward.model.assets.resource.IResource;
import com.kanomiya.steward.model.event.Event;
import com.kanomiya.steward.model.script.Script;
import com.kanomiya.steward.model.script.ScriptEventType;
import com.kanomiya.steward.model.texture.Texture;




/**
 * @author Kanomiya
 *
 */
public class Area implements IResource {
	protected String id, name;

	protected int width, height;
	protected int chunkWidth, chunkHeight;
	protected Texture background;

	protected Tip[][] tips;
	protected List<Event> eventList;
	protected Chunk[][] chunks;

	protected Map<ScriptEventType, Script> scripts;

	protected Assets assets;

	public Area(String id, String name, int width, int height, Tip[][] tips, Assets assets)
	{
		this.id = id;
		this.name = name;
		this.width = width;
		this.height = height;
		this.tips = tips;
		this.assets = assets;

		eventList = Lists.newArrayList();

		chunkWidth = width /Chunk.chunkSize +1;
		chunkHeight = height /Chunk.chunkSize +1;
		chunks = new Chunk[chunkWidth][chunkHeight];

		for (int x=0; x<chunkWidth; x++)
		{
			for (int y=0; y<chunkHeight; y++)
			{
				chunks[x][y] = new Chunk(this);
			}
		}

	}


	/**
	 * @return id
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * @param id セットする id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width セットする width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height セットする height
	 */
	public void setHeight(int height) {
		this.height = height;
	}


	public boolean hasBackground()
	{
		return (background != null);
	}

	/**
	 * @return background
	 */
	public Texture getBackground() {
		return background;
	}

	/**
	 * @param background セットする background
	 */
	public void setBackground(Texture background) {
		this.background = background;
	}






	public Chunk getChunk(int x, int y)
	{
		int chunkX = x /Chunk.chunkSize;
		int chunkY = y /Chunk.chunkSize;
		if (width -width %Chunk.chunkSize < x) chunkX += 1;
		if (height -height %Chunk.chunkSize < y) chunkY += 1;

		return chunks[chunkX][chunkY];
	}

	public boolean inArea(int x, int y)
	{
		if (x < 0 || width <= x || y < 0 || height <= y) return false;
		return true;
	}

	public boolean tipExists(int x, int y)
	{
		if (! inArea(x, y)) return false;
		return (tips[x][y] != null);
	}

	public Tip getTip(int x, int y)
	{
		if (! tipExists(x, y)) return null;
		return tips[x][y];
	}

	/**
	 * @param tip
	 * @param x
	 * @param y
	 */
	public void setTip(Tip tip, int x, int y) {
		tips[x][y] = tip;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Event> getEvents(int x, int y)
	{
		List<Event> result = Lists.newArrayList();

		List<Event> ceventList = getChunk(x, y).eventList;
		for (Event cevent: ceventList)
		{
			if (x == cevent.x && y == cevent.y)
			{
				result.add(cevent);
			}
		}

		return result;
	}

	public boolean canEnter(Event event, int x, int y)
	{
		if (! tipExists(x, y)) return false;
		if (getTip(x, y).getAccessType() == AccessType.DENY) return false;

		List<Event> ceventList = getChunk(x, y).eventList;
		for (Event cevent: ceventList)
		{
			if (x == cevent.x && y == cevent.y && cevent.getAccessType() == AccessType.DENY) return false;
		}

		return true;
	}


	/**
	 *
	 * @param event
	 */
	public void setEvent(Event event)
	{
		Area area = event.area;

		if (event.area != null)
		{
			event.area.removeEvent(event);
		}
		if (event.chunk != null) event.chunk.eventList.remove(event);

		event.area = this;
		if (! eventList.contains(event)) eventList.add(event);

		event.chunk = getChunk(event.x, event.y);
		if (! event.chunk.eventList.contains(event)) event.chunk.eventList.add(event);

		if (assets.isInited() && area != this) launchEvent(event, event.x, event.y, ScriptEventType.ONENTERED); // VELIF futureTask
	}

	/**
	 * @param event
	 */
	public void removeEvent(Event event)
	{
		getChunk(event.x, event.y).eventList.remove(event);
		eventList.remove(event);
	}



	/**
	 * @return
	 */
	public List<Event> eventList() {
		return eventList;
	}


	public boolean launchEvent(Event launcher, int x, int y, ScriptEventType type)
	{
		boolean success = false;

		// System.out.println(launcher.id + ": " + launcher.x + "/" + launcher.y);

		if (scripts != null && scripts.containsKey(type))
		{
			Script script = scripts.get(type);
			String code = assets.getScriptCode(script.src).code;

			try {
				assets.getScriptEngine().eval(code);

			} catch (ScriptException e) {
				// TODO 自動生成された catch ブロック
				System.err.println("Excepion source: " + script.src);
				e.printStackTrace();

			} catch (Exception e)
			{
				// TODO
				System.err.println("Excepion source: " + script.src);
				e.printStackTrace();
			}
		}

		Chunk fchunk = getChunk(x, y);

		if (fchunk.hasEvent())
		{
			List<Event> feventList = fchunk.eventList();

			for (int i=0; i<feventList.size(); i++)
			{
				Event fevent = feventList.get(i);

				if ((type != ScriptEventType.ONCOLIDED || fevent != launcher) && fevent.x == x && fevent.y == y)
				{
					fevent.launchScript(assets, launcher, type);
					success = true;
				}
			}

		}

		return success;
	}


	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(getClass().getSimpleName());
		builder.append('[');

		builder.append("id: ");
		builder.append(id);
		builder.append(", name: ");
		builder.append(name);

		builder.append(']');

		return new String(builder);
	}









}
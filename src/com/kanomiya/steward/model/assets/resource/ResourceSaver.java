package com.kanomiya.steward.model.assets.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;

import com.google.gson.Gson;
import com.kanomiya.steward.model.assets.resource.type.ResourceType;

/**
 * @author Kanomiya
 *
 */
public class ResourceSaver<R extends IResource> {

	protected ResourceType<R> type;
	protected ResourceRegistry<R> registry;

	public ResourceSaver(ResourceType<R> type, ResourceRegistry defaulz)
	{
		this.type = type;
		if (defaulz == null) defaulz = new ResourceRegistry<R>();
		registry = defaulz;
	}

	public ResourceSaver<R> save(File saveBaseDir, Gson gson) throws IOException
	{
		if (! type.enableSave()) return this;

		File baseDir = new File(saveBaseDir, type.getDirName());
		if (baseDir.exists())
		{

			FileVisitor<Path> deleter = new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
				{
					file.toFile().delete();

					return FileVisitResult.CONTINUE;
				}
			};

			Files.walkFileTree(baseDir.toPath(), deleter);
		}

		baseDir.mkdirs();

		Iterator<R> resItr = registry.values().iterator();

		while (resItr.hasNext())
		{
			R resource = resItr.next();

			type.save(resource, baseDir, gson);
		}

		return this;
	}



}

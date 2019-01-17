/*
 * Copyright (C) 2017 GedMarc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jwebmp.logger.handlers;

import com.jwebmp.logger.LogFactory;
import com.jwebmp.logger.logging.LogColourFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * Logs to the standard output rather than the error output. Provides ansi colours if needed
 *
 * @author GedMarc
 * @version 1.0
 * @since 13 Dec 2016
 */
@SuppressWarnings("unused")
public class ConsoleSTDOutputHandler
		extends ConsoleHandler
{

	/**
	 * Field instance
	 */
	private static final ConsoleSTDOutputHandler instance = new ConsoleSTDOutputHandler();

	/**
	 * A list of ignored properties per level
	 */
	private static final Map<Level, String> levelIgnoredProperties = new HashMap<>();

	/**
	 * Field coloured
	 */
	private boolean coloured;
	/**
	 * Field level
	 */
	private Level level;

	/**
	 * Construct a new instance of the std output handler
	 */
	@SuppressWarnings("all")
	private ConsoleSTDOutputHandler()
	{
		this(false);
	}

	/**
	 * Construct a new instance of the std output handler
	 */
	@SuppressWarnings("all")
	private ConsoleSTDOutputHandler(boolean coloured)
	{
		setLevel(LogFactory.getDefaultLevel());
		setColoured(coloured);
		setFormatter(LogColourFormatter.getInstance());
		setOutputStream(System.out);
	}

	/**
	 * Gets the instance
	 *
	 * @return Returns the static instance for the log master
	 */
	public static ConsoleSTDOutputHandler getInstance()
	{
		return ConsoleSTDOutputHandler.instance;
	}

	/**
	 * Returns a non-null list of ignored properties rendered per display
	 *
	 * @return The mapped levels and properties that must be ignored when logging objectively
	 */
	public Map<Level, String> getLevelIgnoredProperties()
	{
		return ConsoleSTDOutputHandler.levelIgnoredProperties;
	}

	/**
	 * If the output handler is working in coloured mode
	 *
	 * @return if rendering coloured
	 */
	public boolean isColoured()
	{
		return coloured;
	}

	/**
	 * Sets if the output handler must output in coloured mode
	 *
	 * @param coloured
	 * 		If it must render coloured
	 *
	 * @return This handler
	 */
	public ConsoleSTDOutputHandler setColoured(boolean coloured)
	{
		this.coloured = coloured;
		return this;
	}

	/**
	 * Method hashCode ...
	 *
	 * @return int
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/**
	 * Method equals ...
	 *
	 * @param obj
	 * 		of type Object
	 *
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		return obj.getClass()
		          .isAssignableFrom(obj.getClass());
	}

	/**
	 * Returns the level assigned to this class and not the parent
	 *
	 * @return the JDK 8 Level object
	 */
	@Override
	public Level getLevel()
	{
		return level;
	}

	/**
	 * This this level and the parent level.
	 *
	 * @param level
	 * 		The level to apply
	 */
	@Override
	public void setLevel(Level level)
	{
		this.level = level;
		super.setLevel(level);
	}

}

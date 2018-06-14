/*
 * Copyright (C) 2017 Marc Magon
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
public class ConsoleSTDOutputHandler
		extends ConsoleHandler
		implements ConsoleOutput<ConsoleSTDOutputHandler>
{

	private static final ConsoleSTDOutputHandler instance = new ConsoleSTDOutputHandler();

	/**
	 * A list of ignored properties per level
	 */
	private static final Map<Level, String> levelIgnoredProperties = new HashMap<>();

	private boolean coloured;
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
		//super(System.out, LogColourFormatter.getInstance());
		setLevel(LogFactory.getDefaultLevel());
		setColoured(coloured);
		setFormatter(LogColourFormatter.getInstance());
		setOutputStream(System.out);
	}

	public static ConsoleSTDOutputHandler getInstance()
	{
		return instance;
	}

	/**
	 * Returns a non-null list of ignored properties rendered per display
	 *
	 * @return
	 */
	public Map<Level, String> getLevelIgnoredProperties()
	{
		return levelIgnoredProperties;
	}

	public boolean isColoured()
	{
		return coloured;
	}

	public ConsoleSTDOutputHandler setColoured(boolean coloured)
	{
		this.coloured = coloured;
		return this;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		return obj.getClass()
		          .getCanonicalName()
		          .equals(getClass().getCanonicalName());
	}

	@Override
	public Level getLevel()
	{
		return level;
	}

	@Override
	public void setLevel(Level level)
	{
		this.level = level;
		super.setLevel(level);
	}


}

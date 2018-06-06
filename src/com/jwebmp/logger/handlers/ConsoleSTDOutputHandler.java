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
import com.jwebmp.logger.logging.LogSingleLineFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

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

	/**
	 * A list of ignored properties per level
	 */
	private static final Map<Level, String> levelIgnoredProperties = new HashMap<>();

	/**
	 * Construct a new instance of the std output handler
	 */
	@SuppressWarnings("all")
	public ConsoleSTDOutputHandler()
	{
		setLevel(LogFactory.getDefaultLevel());
		setOutputStream(System.out);
		setFilter((LogRecord record) -> !(record.getMessage() == null || record.getMessage()
		                                                                       .isEmpty()));
		setFormatter(new LogSingleLineFormatter());
	}

	/**
	 * Construct a new instance of the std output handler
	 */
	@SuppressWarnings("all")
	public ConsoleSTDOutputHandler(boolean coloured)
	{
		setLevel(LogFactory.getDefaultLevel());
		setOutputStream(System.out);
		setFilter((LogRecord record) -> record != null && !(record.getMessage() == null || record.getMessage()
		                                                                                         .isEmpty()));
		setFormatter(new LogColourFormatter());
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
}
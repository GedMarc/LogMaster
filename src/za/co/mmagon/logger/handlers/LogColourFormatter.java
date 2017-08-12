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
package za.co.mmagon.logger.handlers;

import za.co.mmagon.logger.model.LogEntry;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * standard colour formatter for console output
 *
 * @author GedMarc
 * @since 14 Dec 2016
 */
public class LogColourFormatter extends java.util.logging.Formatter
{
	
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_RESET = "\u001b[0m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_BLACK = "\u001b[30m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_RED = "\u001b[31m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_GREEN = "\u001b[32m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_YELLOW = "\u001b[33m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_BLUE = "\u001b[34m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_PURPLE = "\u001b[35m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_CYAN = "\u001b[36m";
	/**
	 * Ansi Colour
	 */
	public static final String ANSI_WHITE = "\u001b[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	public static boolean INVERTED = true;

	/**
	 * The log colour formatter
	 */
	public LogColourFormatter()
	{
		//Nothing needing to be done
	}

	public static void main(String[] args)
	{
		Logger log = Logger.getLogger("Test Output");
		System.out.println("Standard Output");
		LogManager.getLogManager().getLogger("").addHandler(new ConsoleSTDOutputHandler());
		System.out.println("Coloured?");
		log.severe("Coloured?");
		
	}
	
	/**
	 * Formats according to level
	 *
	 * @param record
	 *
	 * @return
	 */
	@Override
	public String format(LogRecord record)
	{
		String output = "";
		if (record.getLevel() == Level.FINEST)
		{
			output = ANSI_CYAN
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "")
					+ LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		else if (record.getLevel() == Level.FINER)
		{
			output = ANSI_CYAN
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "") + LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		else if (record.getLevel() == Level.FINE)
		{
			output = ANSI_GREEN
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "") + LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		else if (record.getLevel() == Level.CONFIG)
		{
			output = ANSI_PURPLE
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "") + LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		else if (record.getLevel() == Level.INFO)
		{
			output = ANSI_BLUE
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "") + LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		else if (record.getLevel() == Level.WARNING)
		{
			output = ANSI_YELLOW
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "") + LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		else if (record.getLevel() == Level.SEVERE)
		{
			output = ANSI_RED
					+ (INVERTED ? ANSI_BLACK_BACKGROUND : "") + LogEntry.newEntry(record).toString() + ANSI_RESET;
		}
		return output + System.getProperty("line.separator");
	}
}

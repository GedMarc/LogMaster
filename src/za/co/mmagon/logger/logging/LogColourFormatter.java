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
package za.co.mmagon.logger.logging;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * standard colour formatter for console output
 *
 * @author GedMarc
 * @since 14 Dec 2016
 */
public class LogColourFormatter extends LogFormatter
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

	private static boolean INVERTED = false;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	/**
	 * The log colour formatter
	 */
	public LogColourFormatter()
	{
		//Nothing needing to be done
	}

	public static boolean isINVERTED()
	{
		return INVERTED;
	}

	public static void setINVERTED(boolean INVERTED)
	{
		LogColourFormatter.INVERTED = INVERTED;
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
		if (record.getMessage() == null || record.getMessage()
				                                   .trim()
				                                   .isEmpty())
		{
			return null;
		}

		String output = "";
		if (!INVERTED)
		{
			output += ANSI_WHITE;
			output += ANSI_BLACK_BACKGROUND;
		}
		output += "[" + sdf.format(record.getMillis()) + "]-";

		if (record.getLevel() == Level.FINEST)
		{
			output += ANSI_RED + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		else if (record.getLevel() == Level.FINER)
		{
			output += ANSI_CYAN + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		else if (record.getLevel() == Level.FINE)
		{
			output += ANSI_BLUE + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		else if (record.getLevel() == Level.CONFIG)
		{
			output += ANSI_PURPLE + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		else if (record.getLevel() == Level.INFO)
		{
			output += ANSI_GREEN + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		else if (record.getLevel() == Level.WARNING)
		{
			output += ANSI_YELLOW + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		else if (record.getLevel() == Level.SEVERE)
		{
			output += ANSI_RED + ANSI_BLACK_BACKGROUND + record.getMessage();
		}
		output += printException(record).toString();
		output = processParameters(output, record);
		output = processInverted(output, record);

		output += " - ";

		output += "[" + record.getLevel()
				                .getLocalizedName() + "]";

		return output + System.getProperty("line.separator");
	}

	private String processInverted(String output, LogRecord record)
	{
		if (!INVERTED)
		{
			if (record.getThrown() == null)
			{
				output += ANSI_RESET;
				output += ANSI_BLACK_BACKGROUND;
				output += ANSI_WHITE;

			}
			else
			{
				output += ANSI_BLACK;
			}
		}
		return output;
	}


	public SimpleDateFormat getSdf()
	{
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf)
	{
		this.sdf = sdf;
	}
}

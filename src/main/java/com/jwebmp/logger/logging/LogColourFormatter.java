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
package com.jwebmp.logger.logging;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * standard colour formatter for console output
 *
 * @author GedMarc
 * @since 14 Dec 2016
 */
public class LogColourFormatter
		extends LogFormatter
{
	private static final LogColourFormatter instance = new LogColourFormatter();

	private static String ANSI_RED_BACKGROUND = "\u001B[41m";
	private static String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	private static String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	private static String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	private static String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	private static String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	private static String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_RESET = "\u001b[0m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_BLACK = "\u001b[30m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_RED = "\u001b[31m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_GREEN = "\u001b[32m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_YELLOW = "\u001b[33m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_BLUE = "\u001b[34m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_PURPLE = "\u001b[35m";
	/**
	 * Ansi Colour
	 */
	private static String ANSI_CYAN = "\u001b[36m";
	/**
	 * Ansi Colour
	 */

	private static String ANSI_WHITE = "\u001b[37m";
	private static String ANSI_BLACK_BACKGROUND = "\u001B[40m";

	private static boolean INVERTED = false;
	private static boolean renderBlack = true;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	/**
	 * The log colour formatter
	 */
	private LogColourFormatter()
	{
		//Nothing needing to be done
	}

	public static boolean isInverted()
	{
		return INVERTED;
	}

	public static void setInverted(boolean INVERTED)
	{
		LogColourFormatter.INVERTED = INVERTED;
	}

	public static boolean isRenderBlack()
	{
		return renderBlack;
	}

	public static void setRenderBlack(boolean renderBlack)
	{
		LogColourFormatter.renderBlack = renderBlack;
	}

	public static String getAnsiRedBackground()
	{
		return ANSI_RED_BACKGROUND;
	}

	public static void setAnsiRedBackground(String ansiRedBackground)
	{
		ANSI_RED_BACKGROUND = ansiRedBackground;
	}

	public static String getAnsiGreenBackground()
	{
		return ANSI_GREEN_BACKGROUND;
	}

	public static void setAnsiGreenBackground(String ansiGreenBackground)
	{
		ANSI_GREEN_BACKGROUND = ansiGreenBackground;
	}

	public static String getAnsiYellowBackground()
	{
		return ANSI_YELLOW_BACKGROUND;
	}

	public static void setAnsiYellowBackground(String ansiYellowBackground)
	{
		ANSI_YELLOW_BACKGROUND = ansiYellowBackground;
	}

	public static String getAnsiBlueBackground()
	{
		return ANSI_BLUE_BACKGROUND;
	}

	public static void setAnsiBlueBackground(String ansiBlueBackground)
	{
		ANSI_BLUE_BACKGROUND = ansiBlueBackground;
	}

	public static String getAnsiPurpleBackground()
	{
		return ANSI_PURPLE_BACKGROUND;
	}

	public static void setAnsiPurpleBackground(String ansiPurpleBackground)
	{
		ANSI_PURPLE_BACKGROUND = ansiPurpleBackground;
	}

	public static String getAnsiCyanBackground()
	{
		return ANSI_CYAN_BACKGROUND;
	}

	public static void setAnsiCyanBackground(String ansiCyanBackground)
	{
		ANSI_CYAN_BACKGROUND = ansiCyanBackground;
	}

	public static String getAnsiWhiteBackground()
	{
		return ANSI_WHITE_BACKGROUND;
	}

	public static void setAnsiWhiteBackground(String ansiWhiteBackground)
	{
		ANSI_WHITE_BACKGROUND = ansiWhiteBackground;
	}

	public static String getAnsiReset()
	{
		return ANSI_RESET;
	}

	public static void setAnsiReset(String ansiReset)
	{
		ANSI_RESET = ansiReset;
	}

	public static String getAnsiBlack()
	{
		return ANSI_BLACK;
	}

	public static void setAnsiBlack(String ansiBlack)
	{
		ANSI_BLACK = ansiBlack;
	}

	public static String getAnsiRed()
	{
		return ANSI_RED;
	}

	public static void setAnsiRed(String ansiRed)
	{
		ANSI_RED = ansiRed;
	}

	public static String getAnsiGreen()
	{
		return ANSI_GREEN;
	}

	public static void setAnsiGreen(String ansiGreen)
	{
		ANSI_GREEN = ansiGreen;
	}

	public static String getAnsiYellow()
	{
		return ANSI_YELLOW;
	}

	public static void setAnsiYellow(String ansiYellow)
	{
		ANSI_YELLOW = ansiYellow;
	}

	public static String getAnsiBlue()
	{
		return ANSI_BLUE;
	}

	public static void setAnsiBlue(String ansiBlue)
	{
		ANSI_BLUE = ansiBlue;
	}

	public static String getAnsiPurple()
	{
		return ANSI_PURPLE;
	}

	public static void setAnsiPurple(String ansiPurple)
	{
		ANSI_PURPLE = ansiPurple;
	}

	public static String getAnsiCyan()
	{
		return ANSI_CYAN;
	}

	public static void setAnsiCyan(String ansiCyan)
	{
		ANSI_CYAN = ansiCyan;
	}

	public static String getAnsiWhite()
	{
		return ANSI_WHITE;
	}

	public static void setAnsiWhite(String ansiWhite)
	{
		ANSI_WHITE = ansiWhite;
	}

	public static String getAnsiBlackBackground()
	{
		return ANSI_BLACK_BACKGROUND;
	}

	public static void setAnsiBlackBackground(String ansiBlackBackground)
	{
		ANSI_BLACK_BACKGROUND = ansiBlackBackground;
	}

	public static LogColourFormatter getInstance()
	{
		return instance;
	}

	/**
	 * Formats according to level
	 *
	 * @param record
	 * 		The logging record to format into a string
	 *
	 * @return The message to log
	 */
	@Override
	public String format(LogRecord record)
	{
		if (record.getMessage() == null || record.getMessage()
		                                         .trim()
		                                         .isEmpty())
		{
			System.out.println("No Record Message");
			return null;
		}

		String output = "";
		if (!INVERTED)
		{
			output += ANSI_WHITE;
			if (renderBlack)
			{
				output += ANSI_BLACK_BACKGROUND;
			}
		}
		output += "[" + sdf.format(record.getMillis()) + "]-";

		record.setMessage(record.getMessage()
		                        .replace("\n", "")
		                        .replace("\t", " "));
		if (record.getLevel()
		          .equals(Level.FINEST))
		{
			output += ANSI_RED + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		else if (record.getLevel()
		               .equals(Level.FINER))
		{
			output += ANSI_CYAN + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		else if (record.getLevel()
		               .equals(Level.FINE))
		{
			output += ANSI_BLUE + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		else if (record.getLevel()
		               .equals(Level.CONFIG))
		{
			output += ANSI_PURPLE + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		else if (record.getLevel()
		               .equals(Level.INFO))
		{
			output += ANSI_GREEN + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		else if (record.getLevel()
		               .equals(Level.WARNING))
		{
			output += ANSI_YELLOW + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		else if (record.getLevel()
		               .equals(Level.SEVERE))
		{
			output += ANSI_RED + (renderBlack ? ANSI_BLACK_BACKGROUND : "") + record.getMessage();
		}
		output += printException(record).toString();
		output = processParameters(output, record);
		output = processInverted(output, record);

		output += " - ";
		if (output.equalsIgnoreCase(" - "))
		{
			output += "Empty Log?";
		}

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
				output += (renderBlack ? ANSI_BLACK_BACKGROUND : "");
				output += ANSI_WHITE;

			}
			else
			{
				output += (renderBlack ? ANSI_BLACK_BACKGROUND : "");
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

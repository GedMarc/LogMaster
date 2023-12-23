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
package com.guicedee.logger.logging;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * standard colour formatter for console output
 *
 * @author GedMarc
 * @since 14 Dec 2016
 */
@SuppressWarnings("unused")
public class LogSingleLineFormatter
		extends LogFormatter
{
	/**
	 * Ansi Colour
	 */

	private static final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	/**
	 * The log colour formatter
	 */
	@SuppressWarnings("unused")
	public LogSingleLineFormatter()
	{
		//Nothing needing to be done
	}

	/**
	 * Formats according to level
	 *
	 * @param record
	 * 		The log record coming in
	 *
	 * @return The string to render on the output
	 */
	@Override
	public String format(LogRecord record)
	{
		if (record.getMessage() == null || record.getMessage()
		                                         .trim()
		                                         .isEmpty())
		{
			return "";
		}
		if (record.getMessage()
		          .contains("visiting unvisited references"))
		{
			return "";
		}

		StringBuilder output = new StringBuilder();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()), ZoneId.systemDefault());
		output.append("[").append(sdf.format(localDateTime)).append("]-[");
		if(record.getThrown() != null)
			output.append(printException(record).toString());
		else
			output.append(record.getMessage());

		output = processParameters(output, record);

		output.append("]-");
		output.append("[").append(record.getLevel()
                .getLocalizedName()).append("]");
		output.append("-[").append(record.getLoggerName()).append("]");
		return output + System.getProperty("line.separator");
	}
}

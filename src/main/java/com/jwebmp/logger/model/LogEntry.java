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
package com.jwebmp.logger.model;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A default Log Entry
 *
 * @author GedMarc
 * @since Nov 22, 2016
 */
public class LogEntry

{


	/**
	 * The pattern for a property
	 */
	private static final Pattern PROPERTY_PATTERN = Pattern.compile("\\[(.*?)]-\\[(.*?)];");
	/**
	 * A list of the properties to display with each entry
	 */
	private static final Map<LogProperties, Boolean> displayedProperties;
	private static final List<String> exceptionHighlightedPackages = new ArrayList<>();

	static
	{
		displayedProperties = new EnumMap<>(LogProperties.class);
	}

	/**
	 * The date formatter of this log entry
	 */
	private final SimpleDateFormat dateFormatter;
	/**
	 * The date of this log entry
	 */
	private Date date;
	/**
	 * The level of this log entry
	 */
	private Level level;
	/**
	 * The message of this log entry
	 */
	private String message;
	/**
	 * The properties of this log entry
	 */
	private List<LogProperty> properties;
	/**
	 * The actual data of this log entry
	 */
	private Object data;
	/**
	 * The original system id of this log entry for identification
	 */
	private String originalSourceSystemID;

	/**
	 * Constructs a new log entry with the given entry and level
	 *
	 * @param entry
	 * 		A text entry
	 * @param level
	 * 		The level applied
	 */
	@SuppressWarnings("unused")
	private LogEntry(String entry, Level level)
	{
		this();
		setDate(new Date());
		setLevel(level);
		LogProperty props = LogProperty.newProperty("message", entry);
		getProperties().add(props);
	}

	/**
	 * Constructs a new log entry
	 */
	private LogEntry()
	{
		dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	}

	/**
	 * *
	 * Returns the properties of this log entry
	 *
	 * @return A list of Log Properties
	 */
	@NotNull
	public List<LogProperty> getProperties()
	{
		if (properties == null)
		{
			properties = new ArrayList<>();
		}
		return properties;
	}

	/**
	 * Sets the properties of this log entry
	 *
	 * @param properties
	 * 		A list of properties to set for this list
	 *
	 * @return LogEntry
	 */
	@NotNull
	public LogEntry setProperties(@NotNull List<LogProperty> properties)
	{
		this.properties = properties;
		return this;
	}

	/**
	 * Creates a new log entry with all global properties attached
	 *
	 * @param record
	 * 		A log record produced by java logging
	 *
	 * @return A more object based log entry constructed from the log record
	 */
	@SuppressWarnings("unused")
	public static LogEntry newEntry(LogRecord record)
	{
		LogEntry le = LogEntry.newEntry();
		le.setLevel(record.getLevel());

		Date d = new Date();
		d.setTime(record.getMillis());
		le.setDate(d);

		le.getProperties()
		  .add(LogProperty.newProperty(LogProperties.Level, padRight(record.getLevel()
		                                                                   .getName(), 7)));
		le.getProperties()
		  .add(LogProperty.newProperty(LogProperties.Date, le.dateFormatter.format(d)));
		le.getProperties()
		  .add(LogProperty.newProperty(LogProperties.Message, record.getMessage()));
		le.getProperties()
		  .add(LogProperty.newProperty(LogProperties.Name, record.getLoggerName()));
		le.getProperties()
		  .add(LogProperty.newProperty(LogProperties.Class, record.getSourceClassName()));
		le.getProperties()
		  .add(LogProperty.newProperty(LogProperties.Method, record.getSourceMethodName()));

		Throwable thrown = record.getThrown();
		if (thrown != null)
		{
			StringWriter sw = new StringWriter();
			try (PrintWriter pw = new PrintWriter(sw))
			{
				thrown.printStackTrace(pw);
				pw.flush();
				String exceptionString = sw.toString();
				le.getProperties()
				  .add(LogProperty.newProperty(LogProperties.Exception, exceptionString));
			}
			try
			{
				sw.close();
			}
			catch (IOException ex)
			{
				Logger.getLogger(LogEntry.class.getName())
				      .log(Level.SEVERE, null, ex);
			}
		}
		le.setMessage(record.getMessage());
		return le;
	}

	/**
	 * Creates a new log entry with all global properties attached
	 *
	 * @return A new entry
	 */
	public static LogEntry newEntry()
	{
		LogEntry entry = new LogEntry();
		entry.setDate(new Date());
		entry.setLevel(Level.INFO);
		return entry;
	}

	/**
	 * Pads a property field or value by so many digits
	 *
	 * @param s
	 * 		the string
	 * @param n
	 * 		number of digits
	 *
	 * @return The padded string right
	 */
	public static String padRight(String s, int n)
	{
		return String.format("%-" + n + "s", s);
	}

	/**
	 * Returns the properties map form this log entry
	 *
	 * @param message
	 * 		The message to break into key property pairs
	 *
	 * @return The rendered maps
	 */
	public Map<String, String> getPropertiesFromMessage(String message)
	{
		Map<String, String> props = new HashMap<>();
		if (!isParameterMessage())
		{
			return props;
		}
		Matcher m = PROPERTY_PATTERN.matcher(message);
		while (m.find())
		{
			props.put(m.group(1), m.group(2));
		}
		return props;
	}

	/**
	 * Is the parameter a message
	 *
	 * @return If the message is parameterized
	 */
	private boolean isParameterMessage()
	{
		Matcher m = PROPERTY_PATTERN.matcher(message);
		return m.groupCount() > 0;
	}

	/**
	 * Pads the property field or value by the number
	 *
	 * @param s
	 * 		the string
	 * @param n
	 * 		the number of chars to
	 *
	 * @return The string padded left
	 */
	@SuppressWarnings("unused")
	public static String padLeft(String s, int n)
	{
		return String.format("%1$" + n + "s", s);
	}

	/**
	 * Returns the actual pattern
	 *
	 * @return The property list
	 */
	@SuppressWarnings("unused")
	public static Pattern getPropertyPattern()
	{
		return PROPERTY_PATTERN;
	}

	/**
	 * Configuration for the log properties that are displayed
	 *
	 * @return The list of properties to render from the filter list
	 */
	@SuppressWarnings("unused")
	public static Map<LogProperties, Boolean> getDisplayedProperties()
	{
		return displayedProperties;
	}

	/**
	 * Exception packages that get highlighted
	 *
	 * @return The list of packages to highlight output on
	 */
	@SuppressWarnings("unused")
	public static List<String> getExceptionHighlightedPackages()
	{
		return exceptionHighlightedPackages;
	}

	/**
	 * Returns the current formatter for the date
	 *
	 * @return An instance of the date formatter
	 */
	public SimpleDateFormat getDateFormatter()
	{
		return dateFormatter;
	}

	/**
	 * Returns the log entry with the square brackets
	 *
	 * @return A string representation in brackets of the log message
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		if (getProperties().isEmpty() && !getMessage().isEmpty())
		{
			sb.append("[")
			  .append(padRight(getLevel().getName(), 7))
			  .append("];[")
			  .append(dateFormatter.format(getDate()))
			  .append("];");
			if (getData() != null)
			{
				sb.append(";[Data]-[")
				  .append(getData())
				  .append("];");
			}
			sb.append("[")
			  .append(getMessage())
			  .append("];");
		}
		else if (!getProperties().isEmpty())
		{
			getProperties().forEach(sb::append);
		}
		return sb.toString();
	}

	/**
	 * Returns the message for this log entry
	 *
	 * @return the Message
	 */
	public String getMessage()
	{
		if (message == null)
		{
			message = "";
		}
		return message;
	}

	/**
	 * Gets the level of this log entry
	 *
	 * @return The level applied
	 */
	public Level getLevel()
	{
		return level;
	}

	/**
	 * Sets the level of this log entry
	 *
	 * @param level
	 * 		The level
	 *
	 * @return This object
	 */
	public LogEntry setLevel(Level level)
	{
		this.level = level;
		return this;
	}

	/**
	 * Returns the date of this log entry
	 *
	 * @return The date associated with this entry
	 */
	public Date getDate()
	{
		return date;
	}

	/**
	 * Any attached object that you would want the data from
	 *
	 * @return Any data associated with this entry - customized
	 */
	public Object getData()
	{
		return data;
	}

	/**
	 * Any attached object you would want to fetch data from
	 *
	 * @param data
	 * 		Any data with this entry
	 *
	 * @return This entry
	 */
	public LogEntry setData(Object data)
	{
		this.data = data;
		return this;
	}

	/**
	 * Sets the date of this log entry
	 *
	 * @param date
	 * 		The date of this entry
	 *
	 * @return The log entry
	 */
	public LogEntry setDate(Date date)
	{
		this.date = date;
		return this;
	}

	/**
	 * Returns the message for this entry
	 *
	 * @param message
	 * 		Sets the message unmapped into a log entry from bracket form
	 *
	 * @return This log entry with mapped properties
	 */
	public LogEntry setMessage(String message)
	{
		this.message = message;
		getPropertiesFromMessage(this.message).forEach((key, value) -> getProperties().add(LogProperty.newProperty(key, value)));
		return this;
	}

	/**
	 * The original source system id
	 *
	 * @return an empty string
	 */
	@NotNull
	public String getOriginalSourceSystemID()
	{
		if (originalSourceSystemID == null)
		{
			originalSourceSystemID = "";
		}
		return originalSourceSystemID;
	}

	/**
	 * The original source system id
	 *
	 * @param originalSourceSystemID
	 * 		The original source system
	 *
	 * @return this;
	 */
	public LogEntry setOriginalSourceSystemID(String originalSourceSystemID)
	{
		this.originalSourceSystemID = originalSourceSystemID;
		return this;
	}

	/**
	 * Returns a specific log property
	 *
	 * @param name
	 * 		The name of the property
	 *
	 * @return The log property of the item
	 */
	public LogProperty getProperty(String name)
	{
		for (LogProperty next : properties)
		{
			if (next.getPropertyName()
			        .equalsIgnoreCase(name))
			{
				return next;
			}
		}
		return null;
	}
}

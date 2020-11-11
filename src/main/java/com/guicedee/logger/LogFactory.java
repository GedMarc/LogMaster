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
package com.guicedee.logger;

import com.guicedee.logger.handlers.ConsoleSTDOutputHandler;
import com.guicedee.logger.logging.LogColourFormatter;

import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.logging.Level.*;

/**
 * Default handler for java.util.logging framework.
 *
 * @author GedMarc
 * @since 13 Dec 2016
 */
public class LogFactory
{
	/**
	 * The handler for the console logger
	 */
	private static ConsoleSTDOutputHandler consoleLogger = ConsoleSTDOutputHandler.getInstance();

	/**
	 * The instance of the log factory
	 */
	private static LogFactory instance = new LogFactory();
	/**
	 * Whether or not to log to the console
	 */
	private static boolean LogToConsole;
	/**
	 * The default level for the logging
	 */
	private static Level DefaultLevel = FINE;
	/**
	 * If this factory is configured
	 */
	private static boolean configured;

	/**
	 * Hidden log factory constructor
	 */
	private LogFactory()
	{
		//Nothing required
	}


	public static ConsoleSTDOutputHandler configureConsoleColourOutput(Level outputLevel)
	{
		setLogToConsole(true);
		//System.setErr(System.out);
		LogFactory.setDefaultLevel(outputLevel);
		ConsoleSTDOutputHandler.getInstance()
		                       .setColoured(true)
		                       .setLevel(outputLevel);
		if (!configured)
		{
			LogManager.getLogManager()
			          .getLogger("")
			          .addHandler(ConsoleSTDOutputHandler.getInstance());
			configured = true;
		}
		return ConsoleSTDOutputHandler.getInstance();
	}

	/**
	 * Configures all the JWebMP Loggers to log to console with the given output using the SingleLineFormatter
	 *
	 * @param outputLevel
	 * 		The level to apply
	 *
	 * @return The Handler that was added
	 */
	public static ConsoleSTDOutputHandler configureConsoleSingleLineOutput(Level outputLevel)
	{
		setLogToConsole(true);
		ConsoleSTDOutputHandler.getInstance()
		                       .setColoured(false)
		                       .setLevel(outputLevel);
		LogManager.getLogManager()
		          .getLogger("")
		          .addHandler(ConsoleSTDOutputHandler.getInstance());

		LogFactory.setDefaultLevel(outputLevel);

		return ConsoleSTDOutputHandler.getInstance();
	}

	/**
	 * Configures all the JWebMP Loggers to log to console with the given output using the SingleLineFormatter
	 *
	 * @param outputLevel
	 * 		The level to apply
	 *
	 * @return The Handler that was added
	 */
	public static ConsoleSTDOutputHandler configureConsoleSingleLineOutput(Level outputLevel, boolean inverted)
	{
		//System.setErr(System.out);
		LogFactory.setDefaultLevel(outputLevel);
		setLogToConsole(true);
		LogColourFormatter.setInverted(inverted);
		ConsoleSTDOutputHandler.getInstance()
		                       .setColoured(false)
		                       .setLevel(outputLevel);
		LogManager.getLogManager()
		          .getLogger("")
		          .addHandler(ConsoleSTDOutputHandler.getInstance());
		return ConsoleSTDOutputHandler.getInstance();
	}

	/**
	 * Sets some of the configurations to a not so floody log
	 * <p>
	 * LogFactory.setGroupLevel("org.hibernate", Level.INFO);
	 * LogFactory.setGroupLevel("io.undertow.request", Level.INFO);
	 * LogFactory.setGroupLevel("jakarta.faces.component", Level.INFO);
	 * LogFactory.setGroupLevel("com.google.inject", Level.CONFIG);
	 * LogFactory.setGroupLevel("org.xnio", Level.INFO);
	 * LogFactory.setGroupLevel("btm", Level.INFO);
	 * LogFactory.setGroupLevel("com.microsoft.sqlserver.jdbc", Level.INFO);
	 * LogFactory.setGroupLevel("com.hazelcast", Level.INFO);
	 * LogFactory.setGroupLevel("jakarta.net", Level.INFO);
	 * LogFactory.setGroupLevel("org.apache.cxf", Level.CONFIG);
	 * LogFactory.setGroupLevel("jdk.event.security", Level.INFO);
	 * LogFactory.setGroupLevel("jakarta.xml.bind", Level.CONFIG);
	 * LogFactory.setGroupLevel("jakarta.enterprise.resource.webcontainer.jsf", Level.INFO);
	 */
	public static void configureDefaultLogHiding()
	{
		LogFactory.setGroupLevel("org.hibernate", Level.INFO);
		LogFactory.setGroupLevel("io.undertow.request", Level.INFO);
		LogFactory.setGroupLevel("jakarta.faces.component", Level.INFO);
		LogFactory.setGroupLevel("com.google.inject", Level.CONFIG);
		LogFactory.setGroupLevel("org.xnio", Level.INFO);
		LogFactory.setGroupLevel("btm", Level.INFO);
		LogFactory.setGroupLevel("com.microsoft.sqlserver.jdbc", Level.INFO);
		LogFactory.setGroupLevel("com.hazelcast", Level.INFO);
		LogFactory.setGroupLevel("javax.net", Level.INFO);
		LogFactory.setGroupLevel("org.apache.cxf", Level.CONFIG);
		LogFactory.setGroupLevel("jdk.event.security", Level.INFO);
		LogFactory.setGroupLevel("sun.rmi.loader", Level.INFO);
		LogFactory.setGroupLevel("sun.rmi.transport.tcp", Level.INFO);
		LogFactory.setGroupLevel("jakarta.xml.bind", Level.CONFIG);
		LogFactory.setGroupLevel("io.undertow.session", Level.INFO);
		LogFactory.setGroupLevel("io.undertow.websockets.core.request", Level.INFO);
		LogFactory.setGroupLevel("jakarta.enterprise.resource.webcontainer.jsf", Level.INFO);

	}


	/**
	 * Alias for get logger
	 *
	 * @param name
	 * 		Logger name to return
	 *
	 * @return A JDK 8 Logger
	 */
	public static Logger getLog(String name)
	{
		return LogFactory.getInstance()
		                 .getLogger(name);
	}

	/**
	 * Returns a logger in Async that may or may not log to the console according to configuration
	 *
	 * @param name
	 * 		Returns the logger
	 *
	 * @return The newly created logger
	 */
	public Logger getLogger(String name)
	{
		Logger newLog = Logger.getLogger("com.guicedee." + name);
		newLog.setUseParentHandlers(true);
		newLog.setLevel(LogFactory.DefaultLevel);
		return newLog;
	}

	/**
	 * Returns an instance of the log factory
	 *
	 * @return The static instance
	 */
	public static LogFactory getInstance()
	{
		return LogFactory.instance;
	}

	/**
	 * Returns the currently assigned default level
	 *
	 * @return The default level assigned to the
	 */
	public static Level getDefaultLevel()
	{
		return LogFactory.DefaultLevel == null ? FINE : LogFactory.DefaultLevel;
	}


	/**
	 * Sets the default level on all the loggers currently associated, as well as any future loggers
	 *
	 * @param newLevel
	 * 		The default level to apply on all loggers on all registered handlers
	 */
	public static void setGroupLevel(String category, Level newLevel)
	{
		ConsoleSTDOutputHandler.getLogPackagesLevels()
		                       .put(category, newLevel);
	}

	/**
	 * Sets the default level on all the loggers currently associated, as well as any future loggers
	 *
	 * @param level
	 * 		The default level to apply on all loggers on all registered handlers
	 */
	public static void setDefaultLevel(Level level)
	{
		LogFactory.DefaultLevel = level;

		Logger.getGlobal()
		      .setLevel(level);
		Logger.getAnonymousLogger()
		      .setLevel(level);
		Logger.getLogger("")
		      .setLevel(level);

		Enumeration<String> enums = LogManager.getLogManager()
		                                      .getLoggerNames();
		Set<String> newSet = new LinkedHashSet<>();
		newSet.add("");

		while (enums.hasMoreElements())
		{
			newSet.add(enums.nextElement());
		}
		for (String nextElement : newSet)
		{
			Logger logger = LogManager.getLogManager()
			                          .getLogger(nextElement);
			if (logger != null)
			{
				for (Handler handler : logger.getHandlers())
				{
					if (handler != null)
					{
						handler.setLevel(level);
					}
				}
			}
		}
	}

	/**
	 * Alias for get logger
	 *
	 * @param name
	 * 		Logger name to return
	 *
	 * @return A JDK 8 Logger
	 */
	public static Logger getLog(Class name)
	{
		return LogFactory.getInstance()
		                 .getLogger(name.toString());
	}

	/**
	 * If we should ever log to console
	 *
	 * @return If logging to console is enabled
	 */
	@SuppressWarnings("unused")
	public static boolean isLogToConsole()
	{
		return LogFactory.LogToConsole;
	}

	/**
	 * If we should ever log to console
	 *
	 * @param LogToConsole
	 * 		If must log to console
	 */
	@SuppressWarnings("unused")
	public static void setLogToConsole(boolean LogToConsole)
	{
		LogFactory.LogToConsole = LogToConsole;
	}

	/**
	 * Returns the console logger
	 *
	 * @return The console logger instance
	 */
	@SuppressWarnings("unused")
	public ConsoleSTDOutputHandler getConsoleLogger()
	{
		return LogFactory.consoleLogger;
	}
}

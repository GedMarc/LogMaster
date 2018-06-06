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
package com.jwebmp.logger;

import com.jwebmp.logger.handlers.ConsoleSTDOutputHandler;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.*;

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
	 * The instance of the log factory
	 */
	private static final LogFactory instance = new LogFactory();
	/**
	 * Whether or not to log to the console
	 */
	private static boolean LogToConsole = true;
	/**
	 * The default level for the logging
	 */
	private static Level DefaultLevel = FINE;
	/**
	 * The asynchronous holder
	 */
	private static boolean async = false;
	/**
	 * The handler for the console logger
	 */
	private final ConsoleSTDOutputHandler consoleLogger = new ConsoleSTDOutputHandler();
	/**
	 * The log handles that get operated on asynchronously
	 */
	private final List<Handler> logHandles = new ArrayList<>();
	/**
	 * The actual async logger
	 */
	private final AsyncLogger asyncLogger = new AsyncLogger();

	/**
	 * Hidden log factory constructor
	 */
	private LogFactory()
	{
		//No config
	}

	public static void configureConsoleColourOutput(Level outputLevel)
	{
		Handler[] handles = Logger.getLogger("")
		                          .getHandlers();
		for (Handler handle : handles)
		{
			if (handle != null)
			{
				handle.setLevel(outputLevel);
			}
		}
		LogFactory.setDefaultLevel(outputLevel);
		Logger.getLogger("")
		      .addHandler(new ConsoleSTDOutputHandler(true));
	}

	/**
	 * Returns a list of all the current log handlers the async logger triggers
	 *
	 * @return
	 */
	public List<Handler> getLogHandles()
	{
		return logHandles;
	}

	/**
	 * Returns if the log master is asynchronous
	 *
	 * @return
	 */
	public static boolean isAsync()
	{
		return async;
	}

	/**
	 * Sets if the log master is asynchronous
	 *
	 * @param async
	 */
	public static void setAsync(boolean async)
	{
		LogFactory.async = async;
	}

	/**
	 * Alias for get logger
	 *
	 * @param name
	 *
	 * @return
	 */
	public static Logger getLog(String name)
	{
		return getInstance().getLogger(name);
	}

	/**
	 * Returns a logger in Async that may or may not log to the console according to configuration
	 *
	 * @param name
	 *
	 * @return
	 */
	public Logger getLogger(String name)
	{
		Logger newLog = Logger.getLogger(name);
		newLog.setUseParentHandlers(true);
		newLog.setLevel(DefaultLevel);
		return newLog;
	}

	/**
	 * Returns an instance of the log factory
	 *
	 * @return
	 */
	public static LogFactory getInstance()
	{
		return instance;
	}

	public static void configureConsoleSingleLineOutput(Level outputLevel)
	{
		Handler[] handles = Logger.getLogger("")
		                          .getHandlers();
		for (Handler handle : handles)
		{
			if (handle != null)
			{
				handle.setLevel(outputLevel);
			}
		}
		LogFactory.setDefaultLevel(outputLevel);
		Logger.getLogger("")
		      .addHandler(new ConsoleSTDOutputHandler());
	}

	/**
	 * Returns the currently assigned default level
	 *
	 * @return
	 */
	public static Level getDefaultLevel()
	{
		return DefaultLevel == null ? Level.FINE : DefaultLevel;
	}

	/**
	 * Sets the default level on all the loggers currently associated, as well as any future loggers
	 *
	 * @param DefaultLevel
	 */
	public static void setDefaultLevel(Level DefaultLevel)
	{
		LogFactory.DefaultLevel = DefaultLevel;

		Enumeration<String> enums = LogManager.getLogManager()
		                                      .getLoggerNames();
		while (enums.hasMoreElements())
		{
			String nextElement = enums.nextElement();
			Logger logger = LogManager.getLogManager()
			                          .getLogger(nextElement);
			if (logger != null)
			{
				for (Handler handler : logger.getHandlers())
				{
					if (handler != null)
					{
						handler.setLevel(DefaultLevel);
					}
				}
			}
		}

		if (async)
		{
			instance.getLogHandles()
			        .forEach(logHandle -> logHandle.setLevel(DefaultLevel));
		}
	}

	/**
	 * If we should ever log to console
	 *
	 * @return
	 */
	public static boolean isLogToConsole()
	{
		return LogToConsole;
	}

	/**
	 * If we should ever log to console
	 *
	 * @param LogToConsole
	 */
	public static void setLogToConsole(boolean LogToConsole)
	{
		LogFactory.LogToConsole = LogToConsole;
	}

	/**
	 * Returns the console logger
	 *
	 * @return
	 */
	public ConsoleSTDOutputHandler getConsoleLogger()
	{
		return consoleLogger;
	}

	/**
	 * Adds a log handler to the collection
	 *
	 * @param handler
	 *
	 * @return
	 */
	public Handler addLogHandler(Handler handler)
	{
		logHandles.add(handler);
		return handler;
	}

	/**
	 * Returns a direct reference to the async logger
	 *
	 * @return
	 */
	public AsyncLogger getAsyncLogger()
	{
		return asyncLogger;
	}

	/**
	 * The physical thread the async logger runs through. Published through the log handles list
	 */
	public class LoggingThread
			extends Thread
	{

		/**
		 * The log record coming in
		 */
		private final LogRecord logEntry;

		/**
		 * A new log thread created from a log record
		 *
		 * @param logEntry
		 */
		public LoggingThread(LogRecord logEntry)
		{
			super("LoggingThread");
			this.logEntry = logEntry;
		}

		/**
		 * Publish to each log handler
		 */
		@Override
		public void run()
		{
			logHandles.forEach(next -> next.publish(logEntry));
		}
	}

	/**
	 * The Async Log Handler
	 */
	public class AsyncLogger
			extends java.util.logging.Handler
	{

		private LoggingThread thread;

		/**
		 * Default construction
		 */
		public AsyncLogger()
		{
			//Nothing needed to be done on load
		}

		/**
		 * Publish the record in a thread
		 *
		 * @param record
		 */
		@Override
		public synchronized void publish(LogRecord record)
		{
			thread = new LoggingThread(record);
			thread.start();
			flush();
		}

		@Override
		public void flush()
		{
			//Nothing Needed
		}

		@Override
		public void close()
		{
			//Nothing needed
		}
	}

}
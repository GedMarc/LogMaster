package za.co.mmagon.logger;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.*;
import za.co.mmagon.logger.handlers.ConsoleSTDOutputHandler;

/**
 *
 * @author GedMarc
 * @since 13 Dec 2016
 *
 */
public class LogFactory
{

    /**
     * Whether or not to log to the console
     */
    private static boolean LogToConsole = true;
    /**
     * The default level for the logging
     */
    private static Level DefaultLevel = Level.CONFIG;
    /**
     * The instance of the log factory
     */
    private static final LogFactory instance = new LogFactory();
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
        //Nothing needing to be done
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

    /**
     * Returns a logger in Async that may or may not log to the console according to configuration
     *
     * @param name
     *
     * @return
     */
    public Logger getLogger(String name)
    {
        if (getLogHandles().isEmpty())
        {
            getLogHandles().add(consoleLogger);
        }
        Logger newLog = Logger.getLogger(name);
        for (Handler handler : newLog.getHandlers())
        {
            newLog.removeHandler(handler);
        }
        newLog.addHandler(asyncLogger);
        newLog.setLevel(DefaultLevel);

        return newLog;
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
     * Returns the currently assigned default level
     *
     * @return
     */
    public static Level getDefaultLevel()
    {

        return DefaultLevel;
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
     * Returns the console logger
     *
     * @return
     */
    public ConsoleSTDOutputHandler getConsoleLogger()
    {
        return consoleLogger;
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
     * Sets the default level on all the loggers currently associated, as well as any future loggers
     *
     * @param DefaultLevel
     */
    public static void setDefaultLevel(Level DefaultLevel)
    {
        LogFactory.DefaultLevel = DefaultLevel;
        Enumeration<String> loggs = LogManager.getLogManager().getLoggerNames();
        while (loggs.hasMoreElements())
        {
            String logName = loggs.nextElement();
            LogManager.getLogManager().getLogger(logName).setLevel(DefaultLevel);
        }
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
     * The physical thread the async logger runs through.
     * Published through the log handles list
     */
    public class LoggingThread extends Thread
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
            logHandles.forEach(next ->
            {
                next.publish(logEntry);
            });
        }
    }

    /**
     * The Async Log Handler
     */
    public class AsyncLogger extends java.util.logging.Handler
    {

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
            LoggingThread thread = new LoggingThread(record);
            thread.start();
            flush();
        }

        @Override
        public void flush()
        {
            //Nothing Needed
        }

        @Override
        public void close() throws SecurityException
        {
            //Nothing Needed
        }
    }
}

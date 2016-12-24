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

    public static boolean LogToConsole = true;
    public static Level DefaultLevel = Level.CONFIG;
    private static final LogFactory instance = new LogFactory();

    private final ConsoleSTDOutputHandler consoleLogger = new ConsoleSTDOutputHandler();
    private final List<Handler> logHandles = new ArrayList<>();
    private final AsyncLogger asyncLogger = new AsyncLogger();

    ;

    public LogFactory()
    {
        /*Logger.getGlobal().setLevel(DefaultLevel);
        Logger newLogGlobal = Logger.getGlobal();
        for (int i = 0; i < newLogGlobal.getHandlers().length; i++)
        {
            Handler handler = newLogGlobal.getHandlers()[i];
            newLogGlobal.removeHandler(handler);
        }
        Logger newLog = Logger.getLogger("");
        for (int i = 0; i < newLog.getHandlers().length; i++)
        {
            Handler handler = newLog.getHandlers()[i];
            newLog.removeHandler(handler);
        }*/
        // newLog.setUseParentHandlers(false);
    }

    public static LogFactory getInstance()
    {
        return instance;
    }

    public Logger getLogger(String name)
    {
        if (getLogHandles().isEmpty())
        {
            getLogHandles().add(consoleLogger);
        }
        Logger newLog = Logger.getLogger(name);
        for (int i = 0; i < newLog.getHandlers().length; i++)
        {
            Handler handler = newLog.getHandlers()[i];
            newLog.removeHandler(handler);
        }
        newLog.addHandler(asyncLogger);
        newLog.setLevel(DefaultLevel);

        return newLog;
    }

    public static Logger getLog(String name)
    {
        return getInstance().getLogger(name);
    }

    public static Level getDefaultLevel()
    {

        return DefaultLevel;
    }

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

    public List<Handler> getLogHandles()
    {
        return logHandles;
    }

    public Handler addLogHandler(Handler handler)
    {
        logHandles.add(handler);
        return handler;
    }

    public AsyncLogger getAsyncLogger()
    {
        return asyncLogger;
    }

    public class LoggingThread extends Thread
    {

        private final LogRecord logEntry;

        public LoggingThread(LogRecord logEntry)
        {
            super("LoggingThread");
            this.logEntry = logEntry;
        }

        @Override
        public void run()
        {
            logHandles.forEach(next ->
            {
                next.publish(logEntry);
            });
        }
    }

    public class AsyncLogger extends java.util.logging.Handler
    {

        public AsyncLogger()
        {

        }

        @Override
        public boolean isLoggable(LogRecord record)
        {
            return super.isLoggable(record);
        }

        @Override
        public synchronized void publish(LogRecord record)
        {
            LoggingThread thread = new LoggingThread(record);
            thread.run();
            flush();
        }

        @Override
        public void flush()
        {

        }

        @Override
        public void close() throws SecurityException
        {

        }
    }
}

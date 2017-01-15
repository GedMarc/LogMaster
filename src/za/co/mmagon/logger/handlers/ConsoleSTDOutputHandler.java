package za.co.mmagon.logger.handlers;

import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import za.co.mmagon.logger.LogFactory;
import za.co.mmagon.logger.model.LogEntry;

/**
 * Logs to the standard output rather than the error output.
 * Provides ansi colours if needed
 *
 * @author GedMarc
 * @since 13 Dec 2016
 * @version 1.0
 *
 */
public class ConsoleSTDOutputHandler extends ConsoleHandler
{

    public static final String ANSI_RESET = "\u001b[0m";
    public static final String ANSI_BLACK = "\u001b[1;30m";
    public static final String ANSI_RED = "\u001b[1;31m";
    public static final String ANSI_GREEN = "\u001b[0;32m";
    public static final String ANSI_YELLOW = "\u001b[1;33m";
    public static final String ANSI_BLUE = "\u001b[1;34m";
    public static final String ANSI_PURPLE = "\u001b[0;35m";
    public static final String ANSI_CYAN = "\u001b[0;36m";
    public static final String ANSI_WHITE = "\u001b[0;37m";

    /**
     * Construct a new instance of the std output handler
     */
    public ConsoleSTDOutputHandler()
    {

        setLevel(LogFactory.DefaultLevel);
        setOutputStream(System.out);
        setFilter(new Filter()
        {
            @Override
            public boolean isLoggable(LogRecord record)
            {
                if (record.getMessage() == null || record.getMessage().isEmpty())
                {
                    return false;
                }
                return true;
            }
        });
        setFormatter(new Formatter()
        {
            @Override
            public String format(LogRecord record)
            {
                if (record.getLevel() == Level.FINEST)
                {
                    return ANSI_WHITE + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                else if (record.getLevel() == Level.FINER)
                {
                    return ANSI_YELLOW + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                else if (record.getLevel() == Level.FINE)
                {
                    return ANSI_GREEN + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                else if (record.getLevel() == Level.CONFIG)
                {
                    return ANSI_CYAN + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                else if (record.getLevel() == Level.INFO)
                {
                    return ANSI_BLUE + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                else if (record.getLevel() == Level.WARNING)
                {
                    return ANSI_PURPLE + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                else if (record.getLevel() == Level.SEVERE)
                {
                    return ANSI_RED + LogEntry.newEntry(record).toString() + ANSI_RESET;
                }
                return LogEntry.newEntry(record) + "\n\r";
            }
        });
    }

    @Override
    public void publish(LogRecord record)
    {
        super.publish(record);
    }
}

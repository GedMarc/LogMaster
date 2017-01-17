package za.co.mmagon.logger.handlers;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import za.co.mmagon.logger.LogFactory;

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

    /**
     * Construct a new instance of the std output handler
     */
    public ConsoleSTDOutputHandler()
    {

        setLevel(LogFactory.getDefaultLevel());
        setOutputStream(System.out);
        setFilter((LogRecord record) ->
        {
            return !(record.getMessage() == null || record.getMessage().isEmpty());
        });
        setFormatter(new LogColourFormatter());
    }
}

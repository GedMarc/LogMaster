package za.co.mmagon.logger;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.junit.Test;
import za.co.mmagon.logger.model.LogEntry;

/**
 *
 * @author GedMarc
 */
public class LogFactoryTest
{

    public LogFactoryTest()
    {
    }

    @Test
    public void testGetInstance()
    {
        LogFactory.DefaultLevel = Level.FINEST;
        Logger.getGlobal().setUseParentHandlers(false);
        Logger.getGlobal().setLevel(Level.SEVERE);
        Logger.getGlobal().setFilter(new Filter()
        {
            @Override
            public boolean isLoggable(LogRecord record)
            {
                return true;
            }
        });
        Logger log = LogFactory.getInstance().getLogger("Log Me");
        log.setFilter(new Filter()
        {
            @Override
            public boolean isLoggable(LogRecord record)
            {
                return true;
            }
        });
        log.setLevel(Level.FINEST);
        log.setFilter(null);
        log.severe("Severe Message");
        log.warning("This is a warning message");
        log.config("This is a config message"); //not prinint
        log.info("Did this log");
        log.fine("This is a fine message"); //not printing
        log.fine("Is it the fine?");
        LogEntry le = LogEntry.newEntry().setMessage("Check the formmating");
        log.finest(le.getMessage());

        try
        {
            throw new Exception("Testing to see if it splits");
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, "test exception property", e);
        }
    }
}

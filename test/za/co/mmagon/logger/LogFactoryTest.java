package za.co.mmagon.logger;

import org.junit.Test;
import za.co.mmagon.logger.model.LogEntry;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
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
		LogFactory.setDefaultLevel(Level.FINEST);
		Logger log = LogFactory.getLog("TestLog");
		log.setLevel(Level.FINEST);
		log.severe("Severe Message");
		log.config("========================================================================");
		log.warning("This is a warning message");
		log.config("========================================================================");
		log.config("This is a config message"); //not prinint
		log.info("Did this log");
		log.config("========================================================================");
		log.fine("This is a fine message"); //not printing
		log.config("========================================================================");
		log.fine("Is it the fine?");
		LogEntry le = LogEntry.newEntry().setMessage("Check the formmating");
		log.finest(le.getMessage());

		log.config("========================================================================");
		try
		{
			throw new Exception("Testing to see if it splits");
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "test exception property", e);
		}
	}

	@Test
	public void testColourOutput()
	{
		Logger log = LogFactory.getInstance().getLogger("Colour Test");
		//LogManager.getLogManager().getLogger("").addHandler(new ConsoleSTDOutputHandler());
		log.severe("Coloured Severe");
	}
}

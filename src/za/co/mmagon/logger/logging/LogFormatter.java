package za.co.mmagon.logger.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogRecord;

import static za.co.mmagon.logger.logging.LogColourFormatter.ANSI_RESET;

abstract class LogFormatter extends java.util.logging.Formatter
{

	StringBuilder printException(LogRecord record)
	{
		StringBuilder output = new StringBuilder();
		if (record.getThrown() != null)
		{
			Throwable t = record.getThrown();
			StringWriter sw = new StringWriter();
			try (PrintWriter pw = new PrintWriter(sw))
			{
				t.printStackTrace(pw);
			}
			output.append(ANSI_RESET);
			output.append(sw.toString());
		}
		return output;
	}

	String processParameters(String output, LogRecord record)
	{
		if (record.getParameters() != null && record.getParameters().length > 0)
		{
			for (int n = 0; n < record.getParameters().length; n++)
			{
				Object o = record.getParameters()[n];
				if (o == null)
				{
					continue;
				}
				String replace = "\\{" + n + "}";
				String replacable = o.toString();
				output = output.replaceAll(replace, replacable);
			}
		}
		return output;
	}
}

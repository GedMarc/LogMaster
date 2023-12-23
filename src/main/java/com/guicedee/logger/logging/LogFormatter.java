package com.guicedee.logger.logging;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class LogFormatter
		extends java.util.logging.Formatter
{

	StringBuilder printException(LogRecord record)
	{
		StringBuilder output = new StringBuilder();
		if (record.getThrown() != null)
		{
			Throwable t = record.getThrown();
			String stackTrace = ExceptionUtils.getStackTrace(t);
			output.append("\n").append(stackTrace);
		}
		return output;
	}

	StringBuilder processParameters(StringBuilder output, LogRecord record)
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
				try
				{
					Pattern p = Pattern.compile(replace);
					Matcher m = p.matcher(output);
					String s = m.replaceAll(replacable);
					output.delete(0, output.length());
					output.append(s);
				}catch (IllegalArgumentException iae)
				{
					//
				}
			}
		}
		return output;
	}
}

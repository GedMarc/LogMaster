/* 
 * The MIT License
 *
 * Copyright 2017 Marc Magon.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package za.co.mmagon.logger.handlers;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import za.co.mmagon.logger.model.LogEntry;

/**
 *
 * @author GedMarc
 * @since 14 Dec 2016
 *
 */
public class LogColourFormatter extends java.util.logging.Formatter
{

    /**
     * Ansi Colour
     */
    public static final String ANSI_RESET = "\u001b[0m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_BLACK = "\u001b[1;30m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_RED = "\u001b[1;31m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_GREEN = "\u001b[0;32m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_YELLOW = "\u001b[1;33m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_BLUE = "\u001b[1;34m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_PURPLE = "\u001b[0;35m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_CYAN = "\u001b[0;36m";
    /**
     * Ansi Colour
     */
    public static final String ANSI_WHITE = "\u001b[0;37m";

    /**
     * The log colour formatter
     */
    public LogColourFormatter()
    {
        //Nothing needing to be done
    }

    /**
     * Formats according to level
     *
     * @param record
     *
     * @return
     */
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
        return LogEntry.newEntry(record).toString();
    }
}

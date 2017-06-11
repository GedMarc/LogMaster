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

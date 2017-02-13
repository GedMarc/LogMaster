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

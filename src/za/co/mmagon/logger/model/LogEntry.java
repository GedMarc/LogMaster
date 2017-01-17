package za.co.mmagon.logger.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A default Log Entry
 *
 * @author GedMarc
 * @since Nov 22, 2016
 *
 */
public class LogEntry implements Serializable
{

    //private static final Logger log = LogFactory.getLog("LogEntry");
    private static final long serialVersionUID = 1L;
    /**
     * The pattern for a property
     */
    private static final Pattern propertyPatten = Pattern.compile("\\[(.*?)\\]-\\[(.*?)\\];");

    /**
     * The date of this log entry
     */
    private Date date;
    /**
     * The level of this log entry
     */
    private Level level;
    /**
     * The message of this log entry
     */
    private String message;
    /**
     * The properties of this log entry
     */
    private List<LogProperty> properties;
    /**
     * The actual data of this log entry
     */
    private Serializable data;
    /**
     * The date formatter of this log entry
     */
    private final SimpleDateFormat dateFormatter;
    /**
     * The original system id of this log entry for identification
     */
    private String originalSourceSystemID;

    /**
     * Creates a new log entry with all global properties attached
     *
     * @return
     */
    public static LogEntry newEntry()
    {
        LogEntry entry = new LogEntry();
        entry.setDate(new Date());
        entry.setLevel(Level.INFO);
        return entry;
    }

    /**
     * Creates a new log entry with all global properties attached
     *
     * @param record A log record produced by java logging
     *
     * @return
     */
    public static LogEntry newEntry(LogRecord record)
    {
        LogEntry le = LogEntry.newEntry();
        le.setLevel(record.getLevel());

        Date d = new Date();
        d.setTime(record.getMillis());
        le.setDate(d);

        le.getProperties().add(LogProperty.newProperty("Level", padRight(record.getLevel().getName(), 7)));
        le.getProperties().add(LogProperty.newProperty("Date", le.dateFormatter.format(d)));
        le.getProperties().add(LogProperty.newProperty("Message", record.getMessage()));
        le.getProperties().add(LogProperty.newProperty("Name", record.getLoggerName()));
        le.getProperties().add(LogProperty.newProperty("Class", record.getSourceClassName()));
        le.getProperties().add(LogProperty.newProperty("Method", record.getSourceMethodName()));
        Throwable thrown = record.getThrown();
        if (thrown != null)
        {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw))
            {
                thrown.printStackTrace(pw);
                pw.flush();
                String exceptionString = sw.toString();
                le.getProperties().add(LogProperty.newProperty("Exception", exceptionString));
            }
            try
            {
                sw.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(LogEntry.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        le.setMessage(record.getMessage());
        return le;
    }

    /**
     * Constructs a new log entry
     */
    private LogEntry()
    {
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    }

    /**
     * Constructs a new log entry with the given entry and level
     *
     * @param entry
     * @param level
     */
    @SuppressWarnings("")
    private LogEntry(String entry, Level level)
    {
        this.dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        setDate(new Date());
        setLevel(level);
        LogProperty props = LogProperty.newProperty("message", entry);
        getProperties().add(props);
    }

    /**
     * Returns the date of this log entry
     *
     * @return
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * Sets the date of this log entry
     *
     * @param date
     *
     * @return
     */
    public LogEntry setDate(Date date)
    {
        this.date = date;
        return this;
    }

    /**
     * Gets the level of this log entry
     *
     * @return
     */
    public Level getLevel()
    {
        return level;
    }

    /**
     * Sets the level of this log entry
     *
     * @param level
     *
     * @return
     */
    public LogEntry setLevel(Level level)
    {
        this.level = level;
        return this;
    }

    /**
     * *
     * Returns the properties of this log entry
     *
     * @return
     */
    public List<LogProperty> getProperties()
    {
        if (properties == null)
        {
            properties = new ArrayList<>();
        }
        return properties;
    }

    /**
     * Sets the properties of this log entry
     *
     * @param properties
     *
     * @return
     */
    public LogEntry setProperties(List<LogProperty> properties)
    {
        this.properties = properties;
        return this;
    }

    /**
     * Any attached object that you would want the data from
     *
     * @return
     */
    public Object getData()
    {
        return data;
    }

    /**
     * Any attached object you would want to fetch data from
     *
     * @param data
     *
     * @return
     */
    public LogEntry setData(Serializable data)
    {
        this.data = data;
        return this;
    }

    /**
     * Returns the message for this log entry
     *
     * @return
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Returns the message for this entry
     *
     * @param message
     *
     * @return
     */
    public LogEntry setMessage(String message)
    {
        this.message = message;
        getPropertiesFromMessage(this.message).entrySet().forEach(entry ->
        {
            String key = entry.getKey();
            String value = entry.getValue();
            getProperties().add(LogProperty.newProperty(key, value));
        });
        getProperties().add(LogProperty.newProperty("Level", padRight(getLevel().getName(), 7)));
        getProperties().add(LogProperty.newProperty("Date", dateFormatter.format(getDate())));

        getProperties().add(LogProperty.newProperty("Message", message));
        return this;
    }

    /**
     * Returns the log entry with the square brackets
     *
     * @return
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("");
        if (getProperties().isEmpty() && !getMessage().isEmpty())
        {
            sb.append("[").append(padRight(getLevel().getName(), 7)).append("];[")
                    .append(dateFormatter.format(getDate())).append("];");
            if (getData() != null)
            {
                sb.append(";[Data]-[").append(getData()).append("];");
            }
            sb.append("[").append(getMessage()).append("];");
        }
        else if (!getProperties().isEmpty())
        {
            getProperties().forEach(sb::append);
        }
        sb.append("");
        return sb.toString();
    }

    /**
     * The original source system id
     *
     * @return
     */
    public String getOriginalSourceSystemID()
    {
        if (originalSourceSystemID == null)
        {
            originalSourceSystemID = "-";
        }
        return originalSourceSystemID;
    }

    /**
     * The original source system id
     *
     * @param originalSourceSystemID
     */
    public void setOriginalSourceSystemID(String originalSourceSystemID)
    {
        this.originalSourceSystemID = originalSourceSystemID;
    }

    /**
     * Is the parameter a message
     *
     * @return
     */
    private boolean isParameterMessage()
    {
        Matcher m = propertyPatten.matcher(message);
        return m.groupCount() > 0;
    }

    /**
     * Returns the properties map form this log entry
     *
     * @param message
     *
     * @return
     */
    public Map<String, String> getPropertiesFromMessage(String message)
    {
        Map<String, String> props = new HashMap<>();
        if (!isParameterMessage())
        {
            return props;
        }
        Matcher m = propertyPatten.matcher(message);
        while (m.find())
        {
            props.put(m.group(1), m.group(2));
        }
        return props;
    }

    /**
     * Pads a property field or value by so many digits
     *
     * @param s the string
     * @param n number of digits
     *
     * @return
     */
    public static String padRight(String s, int n)
    {
        return String.format("%-" + n + "s", s);
    }

    /**
     * Pads the property field or value by the number
     *
     * @param s the string
     * @param n the number of chars to
     *
     * @return
     */
    public static String padLeft(String s, int n)
    {
        return String.format("%1$" + n + "s", s);
    }

    /**
     * Returns a specific log property
     *
     * @param name
     *
     * @return
     */
    public LogProperty getProperty(String name)
    {
        for (LogProperty next : properties)
        {
            if (next.getPropertyName().equalsIgnoreCase(name))
            {
                return next;
            }
        }
        return null;
    }
}

package za.co.mmagon.logger.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A default log property
 *
 * @author GedMarc
 * @since Nov 22, 2016
 *
 */
public class LogProperty implements Serializable
{

    private static final long serialVersionUID = 1L;

    /**
     * Any new name
     */
    private String propertyName;
    /**
     * Any value
     */
    private String propertyValue;

    /**
     * Default constructor for a log property
     */
    public LogProperty()
    {
        //No work needed here
    }

    /**
     * A new log property
     *
     * @param propertyName
     * @param propertyValue
     */
    public LogProperty(String propertyName, String propertyValue)
    {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    /**
     * A specific property name
     *
     * @return
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * Sets the property name
     *
     * @param propertyName
     */
    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

    /**
     * Gets the property value
     *
     * @return
     */
    public String getPropertyValue()
    {
        return propertyValue;
    }

    /**
     * Sets the property value
     *
     * @param propertyValue
     */
    public void setPropertyValue(String propertyValue)
    {
        this.propertyValue = propertyValue;
    }

    /**
     * Returns a new log entry property and all registered global properties in the log factory
     *
     * @param name The name of the property (usually message)
     * @param value The value of the property
     * @return The new log property
     */
    public static LogProperty newProperty(String name, String value)
    {
        LogProperty props = new LogProperty();
        props.setPropertyName(name);
        props.setPropertyValue(value);
        return props;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.propertyName);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final LogProperty other = (LogProperty) obj;
        return Objects.equals(this.propertyName, other.propertyName);
    }

    @Override
    public String toString()
    {
        return "[" + getPropertyName() + "]-[" + getPropertyValue() + "];";
    }
}

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
package za.co.mmagon.logger.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A default log property
 *
 * @author GedMarc
 * @since Nov 22, 2016
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
	 * Creates a sortable list of properties that can be customized per handler
	 *
	 * @param name
	 * @param value
	 *
	 * @return
	 */
	public static LogProperty newProperty(LogProperties name, String value)
	{
		return newProperty(name.toString(), value);
	}

	/**
	 * Returns a new log entry property and all registered global properties in the log factory
	 *
	 * @param name  The name of the property (usually message)
	 * @param value The value of the property
	 *
	 * @return The new log property
	 */
	public static LogProperty newProperty(String name, String value)
	{
		LogProperty props = new LogProperty();
		props.setPropertyName(name);
		props.setPropertyValue(value);
		return props;
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

	/**
	 * Returns a square bracket display of the properties
	 *
	 * @return
	 */
	@Override
	public String toString()
	{
		return "[" + getPropertyName() + "]-[" + getPropertyValue() + "];";
	}
}

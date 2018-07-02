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
package com.jwebmp.logger.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A default log property
 *
 * @author GedMarc
 * @since Nov 22, 2016
 */
public class LogProperty
		implements Serializable
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
	 * 		a specific property
	 * @param propertyValue
	 * 		the property value
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
	 * 		the generalized property name
	 * @param value
	 * 		the value
	 *
	 * @return A Log Property
	 */
	public static LogProperty newProperty(LogProperties name, String value)
	{
		return newProperty(name.toString(), value);
	}

	/**
	 * Returns a new log entry property and all registered global properties in the log factory
	 *
	 * @param name
	 * 		The name of the property (usually message)
	 * @param value
	 * 		The value of the property
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

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 59 * hash + Objects.hashCode(propertyName);
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
		LogProperty other = (LogProperty) obj;
		return Objects.equals(propertyName, other.propertyName);
	}

	/**
	 * Returns a square bracket display of the properties
	 *
	 * @return The string
	 */
	@Override
	public String toString()
	{
		return "[" + getPropertyName() + "]-[" + getPropertyValue() + "];";
	}

	/**
	 * A specific property name
	 *
	 * @return the property name or null
	 */
	public String getPropertyName()
	{
		return propertyName;
	}

	/**
	 * Sets the property name
	 *
	 * @param propertyName
	 * 		sets the property name
	 */
	@NotNull
	public LogProperty setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
		return this;
	}

	/**
	 * Gets the property value
	 *
	 * @return the property value
	 */
	public String getPropertyValue()
	{
		return propertyValue;
	}

	/**
	 * Sets the property value
	 *
	 * @param propertyValue
	 * 		the property value
	 */
	@NotNull
	public LogProperty setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
		return this;
	}
}

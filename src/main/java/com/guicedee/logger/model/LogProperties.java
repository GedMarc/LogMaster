/*
 * Copyright (C) 2017 Armin Software
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
package com.guicedee.logger.model;

/**
 * @author GedMarc
 * @since 16 Jul 2017
 */
@SuppressWarnings("unused")
public enum LogProperties
{
	Level("Level"),
	Date,
	Message,
	Name,
	Method,
	Class,
	Exception("\nException");
	/**
	 * Any sub data
	 */
	private String data;

	/**
	 * A new LogProperties
	 */
	LogProperties()
	{

	}

	/**
	 * A new LogProperties with data
	 */
	LogProperties(String data)
	{

	}

	/**
	 * Returns the name or the data contained within
	 *
	 * @return The data or the name
	 */
	@Override
	public String toString()
	{
		if (data != null && !data.isEmpty())
		{
			return data;
		}
		else
		{
			return name();
		}
	}
}

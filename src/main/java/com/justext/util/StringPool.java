/**
 * Copyright (c) 2016-present WizeNoze B.V. All rights reserved.
 * <p>
 * This file is part of justext-java.
 * <p>
 * justext-java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * justext-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with justext-java.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.justext.util;

/**
 * Pool of common strings.
 *
 * @author László Csontos
 */
public final class StringPool {

    /**
     * Represents the <code>alt</code> attribute of an <code>IMG</code> tag.
     */
    public static final String ATTRIBUTE_ALT = "alt";
    /**
     * Represents the <code>src</code> attribute of an <code>IMG</code> tag.
     */
    public static final String ATTRIBUTE_SRC = "src";
    /**
     * Carriage return.
     */
    public static final String CARRIAGE_RETURN = "\r";
    /**
     * Copyright character.
     */
    public static final char COPYRIGHT_CHAR = '\u00a9';
    /**
     * Copyright code.
     */
    public static final String COPYRIGHT_CODE = "&copy;";
    /**
     * Empty string.
     */
    public static final String EMPTY = "";
    /**
     * Closing bracket.
     */
    public static final String CLOSING_BRACKET = "]";
    /**
     * Opening bracket.
     */
    public static final String OPENING_BRACKET = "[";
    /**
     * New line.
     */
    public static final String NEW_LINE = "\n";
    /**
     * Period.
     */
    public static final String PERIOD = ".";
    /**
     * Single slash.
     */
    public static final String SLASH = "/";
    /**
     * Space.
     */
    public static final String SPACE = " ";
    /**
     * Represents an <code>A</code> tag.
     */
    public static final String TAG_A = "a";
    /**
     * Represents a <code>BR</code> tag.
     */
    public static final String TAG_BR = "br";
    /**
     * Represents a <code>IMG</code> tag.
     */
    public static final String TAG_IMG = "img";

    private StringPool() {
    }

}

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

package com.justext.exception;

/**
 * Created by lcsontos on 1/17/16.
 */
public class JusTextBeautifierException extends JusTextException {

    /**
     * @see RuntimeException#RuntimeException()
     */
    public JusTextBeautifierException() {
        super();
    }

    /**
     * @param cause Cause.
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public JusTextBeautifierException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message Message.
     * @see RuntimeException#RuntimeException(String)
     */
    public JusTextBeautifierException(String message) {
        super(message);
    }

    /**
     * @param message Message.
     * @param cause Cause.
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public JusTextBeautifierException(String message, Throwable cause) {
        super(message, cause);
    }

}

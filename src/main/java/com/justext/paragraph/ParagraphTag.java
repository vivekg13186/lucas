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

package com.justext.paragraph;

/**
 * The algorithm uses a simple way of segmentation. The contents of some HTML tags are (by default) visually formatted
 * as blocks by Web browsers. The idea is to form textual blocks by splitting the HTML page on these tags.
 *
 * @author László Csontos
 *
 */
public enum ParagraphTag {

    /**
     *
     */
    BODY,

    /**
     *
     */
    BLOCKQUOTE,

    /**
     *
     */
    CAPTION,

    /**
     *
     */
    CENTER,

    /**
     *
     */
    COL,

    /**
     *
     */
    COLGROUP,

    /**
     *
     */
    DD,

    /**
     *
     */
    DIV,

    /**
     *
     */
    DL,

    /**
     *
     */
    DT,

    /**
     *
     */
    FIELDSET,

    /**
     *
     */
    FORM,

    /**
     *
     */
    H1,

    /**
     *
     */
    H2,

    /**
     *
     */
    H3,

    /**
     *
     */
    H4,

    /**
     *
     */
    H5,

    /**
     *
     */
    H6,

    /**
     *
     */
    IMG,

    /**
     *
     */
    LEGEND,

    /**
     *
     */
    LI,

    /**
     *
     */
    OPTGROUP,

    /**
     *
     */
    OPTION,

    /**
     *
     */
    P,

    /**
     *
     */
    PRE,

    /**
     *
     */
    TABLE,

    /**
     *
     */
    TD,

    /**
     *
     */
    TEXTAREA,

    /**
     *
     */
    TFOOT,

    /**
     *
     */

    TH,

    /**
     *
     */
    THEAD,

    /**
     *
     */
    TR,

    /**
     *
     */
    UL;

}

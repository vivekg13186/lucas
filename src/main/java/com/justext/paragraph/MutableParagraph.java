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

import com.justext.Classification;

import java.util.List;

/**
 * Mutable instance of a paragraph. These are created by {@link ParagraphMaker} and processed by
 * {@link com.justext.Classifier}.
 *
 * @author László Csontos
 */
public interface MutableParagraph extends Paragraph {

    /**
     * Add the given text to the existing text nodes.
     * @param text text to be added.
     * @return normalized text of the input which has just been added to the paragraph.
     */
    String appendText(String text);

    /**
     * Decrements tags count.
     */
    void decrementTagsCount();

    /**
     * Creates an immutable copy of this paragraph.
     * @return an immutable copy of this paragraph.
     */
    Paragraph freeze();

    /**
     * Gets text nodes.
     * @return text nodes.
     */
    List<String> getTextNodes();

    /**
     * Increments character count in links.
     * @param delta delta
     */
    void incrementCharsInLinksCount(int delta);

    /**
     * Increments tags count.
     */
    void incrementTagsCount();

    /**
     * Sets classification.
     * @param classification classification.
     */
    void setClassification(Classification classification);

    /**
     * Sets the value of the <code>src</code> attribute of that <code>img</code> tag from which this paragraph was
     * extracted.
     *
     * @param url the value of the <code>src</code> attribute of the corresponding <code>img</code> tag.
     */
    void setUrl(String url);

}

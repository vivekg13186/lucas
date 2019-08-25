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
import java.util.Set;

/**
 * Represents peaces of text extracted from textual ({@link ParagraphTag}) HTML elements.
 *
 * @author László Csontos
 */
public interface Paragraph {

    /**
     * Gets count of characters in links.
     * @return count of characters in links.
     */
    int getCharsInLinksCount();

    /**
     * Gets that classification with which {@link MutableParagraph#setClassification(Classification)} was called for the
     * last time.
     *
     * @return classification.
     */
    Classification getClassification();

    /**
     * Gets DOM path.
     * @return DOM path.
     */
    String getDomPath();

    /**
     * Gets that classification with which {@link MutableParagraph#setClassification(Classification)} was called for the
     * first time.
     *
     * @return classification.
     */
    Classification getFirstClassification();

    /**
     * Gets link density.
     * @return link density.
     */
    float getLinkDensity();

    /**
     * Gets stop words count.
     * @param stopWords words.
     * @return stop words count.
     */
    int getStopWordsCount(Set<String> stopWords);

    /**
     * Gets stop words density.
     * @param stopWords words.
     * @return stop words density.
     */
    float getStopWordsDensity(Set<String> stopWords);

    /**
     * Gets tags count.
     * @return tags count.
     */
    int getTagsCount();

    /**
     * Concatenates text nodes into a single text.
     * @return Concatenated text of nodes.
     */
    String getText();

    /**
     * Returns the value of the <code>src</code> attribute of that <code>img</code> tag from which this paragraph was
     * extracted.
     *
     * @return the value of the <code>src</code> attribute of the corresponding <code>img</code> tag.
     */
    String getUrl();

    /**
     * Gets words.
     * @return words
     */
    List<String> getWords();

    /**
     * Gets word count.
     * @return word count.
     */
    int getWordsCount();

    /**
     * Gets XPath.
     * @return xpath.
     */
    String getXpath();

    /**
     * Returns if this paragraph contains text.
     *
     * @return true if this paragraph contains text.
     */
    boolean hasText();

    /**
     * Returns if this paragraph is boilerplate.
     * @return true if boilerplate, false otherwise.
     */
    boolean isBoilerplate();

    /**
     * Returns if this paragraph is heading.
     * @return true if heading, false otherwise.
     */
    boolean isHeading();

    /**
     * Returns if this paragraph is a H1 heading.
     * @return true if H1 heading, false otherwise.
     */
    boolean isHeadline();

    /**
     * Returns if this paragraph represents text extracted from the <code>alt</code> attribute of an <code>img</code>
     * tag.
     * @return true if image, false otherwise.
     */
    boolean isImage();

    /**
     * Returns if this paragraph is within a SELECT element.
     * @return true if it's within a SELECT, false otherwise.
     */
    boolean isSelect();

    /**
     * Returns the full length of this paragraph.
     * @return length of this paragraph.
     */
    int length();

}

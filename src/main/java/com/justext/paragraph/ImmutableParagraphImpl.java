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
 * Immutable copy of an already classified paragraph object. There are typically created by
 * {@link MutableParagraph#freeze()}.
 *
 * @author László Csontos
 */
public final class ImmutableParagraphImpl extends BaseParagraph {

    private final int charsInLinksCount;
    private final Classification classification;
    private final String domPath;
    private final Classification firstClassification;
    private final boolean hasText;
    private final boolean isBoilerplate;
    private final boolean isHeading;
    private final boolean isHeadline;
    private final boolean isImage;
    private final boolean isSelect;
    private final int length;
    private final float linkDensity;
    private final int tagsCount;
    private final String text;
    private final String url;
    private final List<String> words;
    private final int wordsCount;
    private final String xpath;

    /**
     * Creates an immutable copy of the given paragraph.
     * @param paragraph paragraph to make immutable
     */
    public ImmutableParagraphImpl(Paragraph paragraph) {
        charsInLinksCount = paragraph.getCharsInLinksCount();

        classification = paragraph.getClassification();
        firstClassification = paragraph.getFirstClassification();

        domPath = paragraph.getDomPath();
        isBoilerplate = paragraph.isBoilerplate();
        isHeading = paragraph.isHeading();
        isHeadline = paragraph.isHeadline();
        isImage = paragraph.isImage();
        isSelect = paragraph.isSelect();
        linkDensity = paragraph.getLinkDensity();
        tagsCount = paragraph.getTagsCount();
        xpath = paragraph.getXpath();

        text = paragraph.getText();
        hasText = hasText(text);
        length = text.length();

        url = paragraph.getUrl();
        words = paragraph.getWords();
        wordsCount = words.size();
    }

    @Override
    public int getCharsInLinksCount() {
        return charsInLinksCount;
    }

    @Override
    public Classification getClassification() {
        return classification;
    }

    @Override
    public String getDomPath() {
        return domPath;
    }

    @Override
    public Classification getFirstClassification() {
        return firstClassification;
    }

    @Override
    public float getLinkDensity() {
        return linkDensity;
    }

    @Override
    public int getStopWordsCount(Set<String> stopWords) {
        String[] wordArray = new String[getWordsCount()];
        return getStopWordsCount(getWords().toArray(wordArray), stopWords);
    }

    @Override
    public float getStopWordsDensity(Set<String> stopWords) {
        String[] wordArray = new String[getWordsCount()];
        return getStopWordsDensity(getWords().toArray(wordArray), stopWords);
    }

    @Override
    public int getTagsCount() {
        return tagsCount;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public List<String> getWords() {
        return words;
    }

    @Override
    public int getWordsCount() {
        return wordsCount;
    }

    @Override
    public String getXpath() {
        return xpath;
    }

    @Override
    public boolean hasText() {
        return hasText;
    }

    @Override
    public boolean isBoilerplate() {
        return isBoilerplate;
    }

    @Override
    public boolean isHeading() {
        return isHeading;
    }

    @Override
    public boolean isHeadline() {
        return isHeadline;
    }

    @Override
    public boolean isImage() {
        return isImage;
    }

    @Override
    public boolean isSelect() {
        return isSelect;
    }

    @Override
    public int length() {
        return length;
    }

}

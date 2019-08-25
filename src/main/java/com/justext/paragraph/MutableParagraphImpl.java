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
import com.justext.util.StringPool;
import com.justext.util.StringUtil;

import java.util.*;
import java.util.regex.Pattern;

import static com.justext.Classification.GOOD;

/**
 * Implementation of {@link MutableParagraphImpl}.
 *
 * @author László Csontos
 */
final class MutableParagraphImpl extends BaseParagraph implements MutableParagraph {

    private static final Pattern HEADER_PATTERN = Pattern.compile("\\bh\\d\\b");
    private static final Pattern HEADLINE_PATTERN = Pattern.compile("\\bh1\\b");
    private static final Pattern IMAGE_PATTERN = Pattern.compile("\\bimg\\b");
    private static final Pattern SELECT_PATTERN = Pattern.compile("^select|\\.select");

    private int charsInLinksCount = 0;
    private Classification classification;
    private Optional<String> domPath;
    private Classification firstClassification;
    private int tagsCount = 0;
    private String text;
    private List<String> textNodes;
    private String url;
    private String[] words;
    private Optional<String> xpath;

    /**
     * Creates an empty paragraph with the given path info.
     * @param pathInfo path info.
     */
    MutableParagraphImpl(PathInfo pathInfo) {
        this(pathInfo, null, 0, 0);
    }

    /**
     * Creates a paragraph with the given path info, text nodes, character count in links and tags count.
     * @param pathInfo path info
     * @param textNodes text nodes
     * @param charsInLinksCount character count in links
     * @param tagsCount tags count
     */
    MutableParagraphImpl(PathInfo pathInfo, List<String> textNodes, int charsInLinksCount, int tagsCount) {
        if (pathInfo != null) {
            setDomPath(pathInfo.dom());
            setXpath(pathInfo.xpath());
        } else {
            domPath = Optional.empty();
            xpath = Optional.empty();
        }

        this.charsInLinksCount = charsInLinksCount;
        this.tagsCount = tagsCount;

        if (textNodes == null) {
            textNodes = new ArrayList<>();
        }

        this.textNodes = textNodes;
    }

    /**
     * Creates a paragraph with the given text nodes.
     * @param textNodes text nodes.
     */
    MutableParagraphImpl(List<String> textNodes) {
        this(null, textNodes, 0, 0);
    }

    /**
     * Creates a paragraph with the given text nodes and character count in links.
     * @param textNodes text nodes.
     * @param charsInLinksCount count of characters in links.
     */
    MutableParagraphImpl(List<String> textNodes, int charsInLinksCount) {
        this(null, textNodes, charsInLinksCount, 0);
    }

    MutableParagraphImpl(Classification classification) {
        this(null, null, 0, 0);
        setClassification(classification);
    }

    @Override
    public String appendText(String text) {
        reset();

        text = StringUtil.normalizeWhiteSpaces(text);

        textNodes.add(text);

        return text;
    }

    @Override
    public void decrementTagsCount() {
        tagsCount--;
    }

    @Override
    public Paragraph freeze() {
        return new ImmutableParagraphImpl(this);
    }

    @Override
    public int getCharsInLinksCount() {
        return charsInLinksCount;
    }

    /**
     * Sets count of characters in links.
     * @param charsInLinksCount count of characters in links.
     */
    public void setCharsInLinksCount(int charsInLinksCount) {
        this.charsInLinksCount = charsInLinksCount;
    }

    @Override
    public Classification getClassification() {
        return classification;
    }

    @Override
    public void setClassification(Classification classification) {
        if (firstClassification != null) {
            firstClassification = classification;
        }

        this.classification = classification;
    }

    @Override
    public String getDomPath() {
        return domPath.orElse(StringPool.EMPTY);
    }

    /**
     * Sets DOM path.
     * @param domPath DOM path.
     */
    public void setDomPath(String domPath) {
        this.domPath = Optional.of(domPath);
    }

    @Override
    public Classification getFirstClassification() {
        return firstClassification;
    }

    @Override
    public float getLinkDensity() {
        int textLength = length();

        if (textLength == 0) {
            return 0;
        }

        return 1.0f * charsInLinksCount / textLength;
    }

    @Override
    public int getStopWordsCount(Set<String> stopWords) {
        return getStopWordsCount(words(), stopWords);
    }

    @Override
    public float getStopWordsDensity(Set<String> stopWords) {
        return getStopWordsDensity(words(), stopWords);
    }

    @Override
    public int getTagsCount() {
        return tagsCount;
    }

    /**
     * Sets tags count.
     * @param tagsCount tags count.
     */
    public void setTagsCount(int tagsCount) {
        this.tagsCount = tagsCount;
    }

    @Override
    public String getText() {
        if (text == null) {
            text = String.join(StringPool.EMPTY, textNodes);
            text = StringUtil.normalizeWhiteSpaces(text.trim());
        }

        return text;
    }

    @Override
    public List<String> getTextNodes() {
        return textNodes;
    }

    /**
     * Sets text nodes.
     * @param textNodes text nodes.
     */
    public void setTextNodes(List<String> textNodes) {
        reset();

        this.textNodes = textNodes;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public List<String> getWords() {
        return Collections.unmodifiableList(Arrays.asList(words()));
    }

    @Override
    public int getWordsCount() {
        return words().length;
    }

    @Override
    public String getXpath() {
        return xpath.orElse(StringPool.EMPTY);
    }

    /**
     * Sets XPath.
     * @param xpath xpath.
     */
    public void setXpath(String xpath) {
        this.xpath = Optional.of(xpath);
    }

    @Override
    public boolean hasText() {
        return StringUtil.isNotBlank(getText());
    }

    @Override
    public void incrementCharsInLinksCount(int delta) {
        charsInLinksCount += delta;
    }

    @Override
    public void incrementTagsCount() {
        tagsCount++;
    }

    @Override
    public boolean isBoilerplate() {
        return !GOOD.equals(classification);
    }

    @Override
    public boolean isHeading() {
        return HEADER_PATTERN.matcher(getDomPath()).find();
    }

    @Override
    public boolean isHeadline() {
        return HEADLINE_PATTERN.matcher(getDomPath()).find();
    }

    @Override
    public boolean isImage() {
        return IMAGE_PATTERN.matcher(getDomPath()).find();
    }

    @Override
    public boolean isSelect() {
        return SELECT_PATTERN.matcher(getDomPath()).find();
    }

    @Override
    public int length() {
        return getText().length();
    }

    private void reset() {
        text = null;
        words = null;
    }

    private String[] words() {
        if (words == null) {
            words = getText().split("\\s");
        }

        return words;
    }

}

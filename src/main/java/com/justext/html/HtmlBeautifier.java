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

package com.justext.html;

import com.justext.exception.JusTextBeautifierException;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.TagNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by lcsontos on 1/7/16.
 */
public final class HtmlBeautifier {

    private static final Logger LOG = LoggerFactory.getLogger(HtmlBeautificationLogger.class);

    private final CleanerProperties cleanerProperties;
    private final HtmlCleaner htmlCleaner;
    private final Serializer serializer;

    /**
     * Creates and initializes a cleaner instance.
     */
    public HtmlBeautifier() {
        cleanerProperties = createCleanerProperties();
        htmlCleaner = new HtmlCleaner(cleanerProperties);
        serializer = new CompactXmlSerializer(cleanerProperties);
    }

    /**
     * Cleans the given HTML document.
     *
     * @param html HTML document to clean.
     * @return Root node of the cleaned HTML tree.
     */
    public TagNode clean(String html) {
        return htmlCleaner.clean(html);

    }

    /**
     * Cleans the given HTML document.
     *
     * @param html HTML document to clean.
     * @return Cleaned HTML document as a string.
     */
    public String cleanHtml(String html) {
        TagNode tagNode = clean(html);

        StringWriter tagNodeWriter = new StringWriter();

        try {
            serializer.write(tagNode, tagNodeWriter, "utf-8");
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage(), ioe);
            throw new JusTextBeautifierException(ioe.getMessage(), ioe);
        }

        String cleanHtml = tagNodeWriter.toString();

        return cleanHtml;
    }

    private CleanerProperties createCleanerProperties() {
        CleanerProperties newCleanerProperties = new CleanerProperties();

        newCleanerProperties.addHtmlModificationListener(new HtmlBeautificationLogger());

        newCleanerProperties.setAllowHtmlInsideAttributes(false);
        newCleanerProperties.setAllowMultiWordAttributes(true);
        newCleanerProperties.setAddNewlineToHeadAndBody(false);
        newCleanerProperties.setKeepWhitespaceAndCommentsInHead(false);
        newCleanerProperties.setNamespacesAware(true);
        newCleanerProperties.setOmitComments(true);
        newCleanerProperties.setOmitDoctypeDeclaration(true);
        newCleanerProperties.setOmitXmlDeclaration(true);
        newCleanerProperties.setPruneTags("head,meta,title,script,style");
        newCleanerProperties.setRecognizeUnicodeChars(true);

        return newCleanerProperties;
    }

}

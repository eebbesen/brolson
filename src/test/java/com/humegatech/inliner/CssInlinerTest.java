package com.humegatech.inliner;

import junit.framework.TestCase;

import org.junit.Test;

public class CssInlinerTest extends TestCase {
    private static final String HTML_MESSAGE_CLASS_DECLARATION = "width: 740px; margin-bottom: 2em;";
    private static final String HTML_MESSAGE_CLASS = ".html_message_class";
    private static final String BANNER_CLASS_DECLARATION = "width: 700px;";
    private static final String BANNER_CLASS = ".banner_class";
    private static final String BANNER_ID = "#digest-banner-id";
    private static final String BANNER_ID_DECLARATION = "width: 640px;";
    private static final String CSS = HTML_MESSAGE_CLASS + " { " + HTML_MESSAGE_CLASS_DECLARATION + " }\n"
            + BANNER_CLASS + " { " + BANNER_CLASS_DECLARATION + " }" + BANNER_ID + " { " + BANNER_ID_DECLARATION
            + " } ";

    @Test
    public void testInlineCssSingleElementNoUserDefinedAttributes() {
        final String classSelector = HTML_MESSAGE_CLASS.substring(1);
        final String expectedHtml = stripWhitespace(HTML_MESSAGE_CLASS_DECLARATION);

        assertEquals(expectedHtml, CssInliner.inlineCssClass(CSS, classSelector));
    }

    @Test
    public void testInlineCssNoClassesInPreInlinedHtml() {
        final String classSelector = "rss_item_feed";

        assertEquals(null, CssInliner.inlineCssClass(CSS, classSelector));
    }

    @Test
    public void testGetDeclarationsForClassSelectorClassClassNotInCss() {
        assertEquals("", CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, "not_here", CSS));
    }

    @Test
    public void testGetDeclarationsForSelector() {
        // '#' as part of class selector
        assertEquals(stripWhitespace(BANNER_ID_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.ID_SELECTOR, BANNER_ID, CSS));

        // no '#' as part of class selector
        assertEquals(stripWhitespace(BANNER_ID_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.ID_SELECTOR, BANNER_ID.substring(1), CSS));
    }

    @Test
    public void testGetDeclarationsForSelectorId() {
        // '#' as part of class selector
        assertEquals(stripWhitespace(BANNER_ID_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.ID_SELECTOR, BANNER_ID, CSS));

        // no '#' as part of class selector
        assertEquals(stripWhitespace(BANNER_ID_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.ID_SELECTOR, BANNER_ID.substring(1), CSS));
    }

    @Test
    public void testGetDeclarationsForClassSelector() {
        // '.' as part of id selector
        assertEquals(stripWhitespace(HTML_MESSAGE_CLASS_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, HTML_MESSAGE_CLASS, CSS));
        assertEquals(stripWhitespace(BANNER_CLASS_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, BANNER_CLASS, CSS));

        // no '.' as part of id selector
        assertEquals(stripWhitespace(HTML_MESSAGE_CLASS_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, HTML_MESSAGE_CLASS.substring(1), CSS));
        assertEquals(stripWhitespace(BANNER_CLASS_DECLARATION),
                CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, BANNER_CLASS.substring(1), CSS));
    }

    @Test
    public void testGetDeclarationsForClassSelectorClassMalformedCss() {
        assertEquals(
                "",
                CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, HTML_MESSAGE_CLASS + " {"
                        + HTML_MESSAGE_CLASS_DECLARATION, CSS));
    }

    @Test
    public void testGetDeclarationsForClassSelectorClassEmptyCss() {
        assertEquals("", CssInliner.getDeclarationsForSelector(CssSelector.CLASS_SELECTOR, HTML_MESSAGE_CLASS, ""));
    }

    @Test
    public void testParseCssNullCss() {
        boolean exceptionThrown = false;

        try {
            CssInliner.parseCss(null);
        }
        catch (final NullPointerException npe) {
            exceptionThrown = true;
        }

        assertTrue("Should have thrown NPE for null CSS", exceptionThrown);
    }

    @Test
    public void testParseCssEmptyCss() {
        CssInliner.parseCss("");
    }

    /*
     * Not run as part of suite but helpful for figuring out what Phloc is doing
     */
    //    public void testPhlocCss()
    //    {
    //        final ICSSWriterSettings writerSettings = new CSSWriterSettings(ECSSVersion.CSS30, true);
    //        final CascadingStyleSheet aCss = CSSReader.readFromString(CSS, Charset.forName("utf-8"), ECSSVersion.CSS30);
    //        for (final CSSStyleRule styleRule : aCss.getAllStyleRules())
    //        {
    //            System.out.println(styleRule.getAsCSSString(writerSettings, 1));
    //            for (final CSSSelector selector : styleRule.getAllSelectors())
    //            {
    //                System.out.println(selector.getAsCSSString(writerSettings, 1));
    //            }
    //
    //            for (final CSSDeclaration declaration : styleRule.getAllDeclarations())
    //            {
    //                System.out.println(declaration.getAsCSSString(writerSettings, 1));
    //            }
    //        }
    //    }

    /**
     * Strip all whitespace (spaces, tabs, carriage returns, line feeds, etc.)
     * from a String
     * 
     * @param inputString
     * @return corresponding String with all whitespace stripped
     */
    private static String stripWhitespace(final String inputString) {
        return inputString.replaceAll("\\s", "");
    }
}
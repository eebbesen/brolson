package com.humegatech.inliner;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;

import com.phloc.css.ECSSVersion;
import com.phloc.css.ICSSWriterSettings;
import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CSSSelector;
import com.phloc.css.decl.CSSStyleRule;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;
import com.phloc.css.writer.CSSWriterSettings;

public class CssInliner {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final ECSSVersion CSS_VERSION = ECSSVersion.CSS30;
    // Using 0, but value doesn't matter for us since we're not indenting (or
    // supporting indented CSS yet)
    private static final int INDENT_LEVEL = 0;

    /*
     * Passing true for the second parameter causes the writer to strip all
     * whitespace from the output: perfect for inlining
     */
    private static final ICSSWriterSettings WRITER_SETTINGS = new CSSWriterSettings(ECSSVersion.CSS30, true);

    /**
     * Will take CSS and a class selector and will produce the css inlined
     * 
     * @param css
     *            CSS
     * @param cssClassSelector
     *            class for which to inline the css
     * @return String representing inlined css
     */
    public static String inlineCssClass(final String css, final String cssClassSelector) {
        final String classDeclarations = getDeclarationsForClassSelector(cssClassSelector, css);

        return (StringUtils.isNotBlank(classDeclarations) ? classDeclarations : null);
    }

    /**
     * Will take CSS and an id selector and will produce a tag the css inlined
     * 
     * @param css
     *            CSS
     * @param cssIdSelector
     *            id for which to inline the css
     * @return String representing inlined css
     */
    public static String inlineCssId(final String css, final String cssIdSelector) {
        final String idDeclarations = getDeclarationsForIdSelector(cssIdSelector, css);

        return (StringUtils.isNotBlank(idDeclarations) ? idDeclarations : "");
    }

    protected static CascadingStyleSheet parseCss(final String css) {
        final CascadingStyleSheet aCss = CSSReader.readFromString(css, CHARSET, CSS_VERSION);

        // TODO: null check and throw error if null?
        return aCss;
    }

    /**
     * Return the declarations for a CSS class selector as a String suitable for
     * inlining (newlines, extra spaces, etc. removed). Assumes '.' starts class
     * selector name so will append it if missing.
     * 
     * @param classSelectorName
     *            CSS selector element name
     * @param css
     *            CSS document
     * @return declarations for selector stripped of whitespace
     */
    protected static String getDeclarationsForClassSelector(final String classSelectorName, final String css) {
        final String localClassSelectorName = classSelectorName.startsWith(".") ? classSelectorName : "."
                + classSelectorName;

        return getDeclarations(localClassSelectorName, css);
    }

    /**
     * Return the declarations for a CSS id selector as a String suitable for
     * inlining (newlines, extra spaces, etc. removed). Assumes '#' starts id
     * selector name so will append it if missing.
     * 
     * @param idSelectorName
     *            CSS selector element name
     * @param css
     *            CSS document
     * @return declarations for selector stripped of whitespace
     */
    protected static String getDeclarationsForIdSelector(final String idSelectorName, final String css) {
        final String localIdSelectorName = idSelectorName.startsWith("#") ? idSelectorName : "#" + idSelectorName;

        return getDeclarations(localIdSelectorName, css);
    }

    /**
     * Return the declarations for a CSS inlining (newlines, extra spaces, etc. removed). 
     * 
     * @param selectorName
     *            CSS selector element name
     * @param css
     *            CSS document
     * @return declarations for selector stripped of whitespace
     */
    private static String getDeclarations(final String selectorName, final String css) {
        String declarationString = "";
        for (final CSSStyleRule styleRule : parseCss(css).getAllStyleRules()) {
            for (final CSSSelector selector : styleRule.getAllSelectors()) {
                if (selector.getAsCSSString(WRITER_SETTINGS, INDENT_LEVEL).equalsIgnoreCase(selectorName)) {
                    for (final CSSDeclaration declaration : styleRule.getAllDeclarations()) {
                        declarationString += declaration.getAsCSSString(WRITER_SETTINGS, INDENT_LEVEL) + ";";
                    }

                    break;
                }
            }
        }

        return declarationString;
    }
}
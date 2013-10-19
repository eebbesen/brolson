package com.humegatech.inliner.reader;

import java.nio.charset.Charset;

import com.phloc.css.ECSSVersion;
import com.phloc.css.decl.CascadingStyleSheet;
import com.phloc.css.reader.CSSReader;

/**
 * Interface around CSS reader libraries
 * 
 * @author eebbesen
 * 
 */
public class CssReaderImpl implements ICssReader {
    private static final Charset CHARSET = Charset.forName("utf-8");

    @Override
    public CascadingStyleSheet read(final String cssString) {
        return CSSReader.readFromString(cssString, CHARSET, ECSSVersion.CSS30);
    }
}
package com.humegatech.inliner.reader;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.phloc.css.decl.CSSDeclaration;
import com.phloc.css.decl.CascadingStyleSheet;

public class CssReaderImplTest extends TestCase {
    private static final String TESTDATA_DIR = "src/test/testdata/";

    private String cssString;

    /**
     * Get the file from the testdata directory
     * 
     * @param filename
     * @return
     * @throws IOException
     */
    private void loadCssAsString(final String filename) throws IOException {
        final File file = new File(TESTDATA_DIR + filename);
        cssString = FileUtils.readFileToString(file);

        // make sure the file has loaded
        assertEquals(0, cssString.indexOf("body {\n"));
    }

    @Test
    public void testRead() throws Exception {
        loadCssAsString("generic.css");

        final CssReaderImpl cssReader = new CssReaderImpl();
        final CascadingStyleSheet css = cssReader.read(cssString);

        assertEquals(13, css.getAllStyleRules().size());

        final CSSDeclaration cssDeclaration = css.getAllStyleRules().get(0).getDeclarationAtIndex(0);
        assertEquals("font-family", cssDeclaration.getProperty());
    }
}
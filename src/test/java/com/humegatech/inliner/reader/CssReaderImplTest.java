package com.humegatech.inliner.reader;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

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

    }

    @Test
    public void testRead() throws Exception {
	loadCssAsString("generic.css");
    }
}
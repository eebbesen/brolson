package com.humegatech.inliner.reader;

import com.phloc.css.decl.CascadingStyleSheet;

/**
 * Interface around CSS reader libraries
 * 
 * @author eebbesen
 * 
 */
public interface ICssReader {

    public CascadingStyleSheet read(String cssString);
}
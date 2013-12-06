package com.humegatech.inliner;

/**
 * Enum that associates selectors with identifying characters for parsing
 */
public enum CssSelector {
    CLASS_SELECTOR("."), ID_SELECTOR("#");

    private final String selector;

    CssSelector(final String selector) {
        this.selector = selector;
    }

    String selector() {
        return selector;
    }
}

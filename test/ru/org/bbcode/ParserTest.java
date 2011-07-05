package ru.org.bbcode;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 7/5/11
 * Time: 5:06 PM
 */
public class ParserTest {
    @Test
    public void firstTest() {
        Parser parser = new Parser(false);
        Assert.assertNotNull(parser);
    }
}

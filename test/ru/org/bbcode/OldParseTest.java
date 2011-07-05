package ru.org.bbcode;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 7/5/11
 * Time: 11:15 PM
 */
public class OldParseTest {
    @Test
    public void pTest(){
        Parser parser = new Parser(false);
        parser.parse("test\ntest1\n\ntest2");
        Assert.assertEquals(parser.renderXHtml(), "<div><p>test\ntest1</p>test2</div>");
    }

}

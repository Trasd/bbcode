package ru.org.bbcode;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 7/5/11
 * Time: 5:06 PM
 */
public class SimpleParserTest {
    @Test
    public void firstTest() {
        Parser parser = new Parser(false);
        Assert.assertNotNull(parser);
    }

    @Test
    public void brTest(){
        Parser parser = new Parser(false);
        parser.parse("[br]");
        Assert.assertEquals(parser.renderXHtml(), "<div><br/></div>");
    }

    @Test
    public void boldTest(){
        Parser parser = new Parser(false);
        parser.parse("[b]hello world[/b]");
        Assert.assertEquals(parser.renderXHtml(),"<div><b>hello world</b></div>");
    }

    @Test
    public void italicTest(){
        Parser parser = new Parser(false);
        parser.parse("[i]hello world[/i]");
        Assert.assertEquals(parser.renderXHtml(),"<div><i>hello world</i></div>");
    }

    @Test
    public void strikeoutTest(){
        Parser parser = new Parser(false);
        parser.parse("[s]hello world[/s]");
        Assert.assertEquals(parser.renderXHtml(),"<div><s>hello world</s></div>");
    }

    @Test
    public void emphasisTest(){
        Parser parser = new Parser(false);
        parser.parse("[strong]hello world[/strong]");
        Assert.assertEquals(parser.renderXHtml(),"<div><strong>hello world</strong></div>");
    }

    @Test
    public void quoteTest(){
        Parser parser = new Parser(false);
        parser.parse("[quote]hello world[/quote]");
        // TODO я нрипонял зачем <div> :-(
        Assert.assertEquals(parser.renderXHtml(),"<blockquote><div>hello world</div></blockquote>");
    }
    @Test
    public void quoteParamTest(){
        Parser parser = new Parser(false);
        parser.parse("[quote=maxcom]hello world[/quote]");
        // TODO я нрипонял зачем <div> :-(
        Assert.assertEquals(parser.renderXHtml(),"<p class=\"cite\"><cite>maxcom:</cite></p><blockquote><div>hello world</div></blockquote>");
    }
    @Test
    public void urlTest(){
        Parser parser = new Parser(false);
        parser.parse("[url]http://linux.org.ru[/url]");
        Assert.assertEquals(parser.renderXHtml(),"<div><a href=\"http://linux.org.ru\">http://linux.org.ru</a></div>");
    }
    @Test
    public void urlParamTest(){
        Parser parser = new Parser(false);
        parser.parse("[url=http://linux.org.ru]linux[/url]");
        Assert.assertEquals(parser.renderXHtml(),"<div><a href=\"http://linux.org.ru\">linux</a></div>");
    }
    @Test
    public void listTest(){
        Parser parser = new Parser(false);
        parser.parse("[list][*]one[*]two[*]three[/list]");
        Assert.assertEquals(parser.renderXHtml(),"<ul><li>one</li><li>two</li><li>three</li></ul>");
    }
    @Test
    public void codeTest(){
        Parser parser = new Parser(false);
        parser.parse("[code][list][*]one[*]two[*]three[/list][/code]");
        Assert.assertEquals(parser.renderXHtml(),"<pre><code>[list][*]one[*]two[*]three[/list]</code></pre>");
    }
    @Test
    public void userTest(){
        Parser parser = new Parser(false);
        parser.parse("[user]maxcom[/user]");
        Assert.assertEquals(parser.renderXHtml(),"<div><span style=\"white-space: nowrap\"><img src=\"/img/tuxlor.png\"><a style=\"text-decoration: none\" href='/people/maxcom/profile'>maxcom</a></span></div>");
    }


}

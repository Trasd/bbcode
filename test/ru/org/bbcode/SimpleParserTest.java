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
    private final Parser parser = new Parser();

    @Test
    public void brTest(){
        Assert.assertEquals(parser.parse("[br]").renderXHtml(), "<div><br/></div>");
    }

    @Test
    public void boldTest(){
        Assert.assertEquals(parser.parse("[b]hello world[/b]").renderXHtml(), "<div><b>hello world</b></div>");
    }

    @Test
    public void italicTest(){
        Assert.assertEquals(parser.parse("[i]hello world[/i]").renderXHtml(), "<div><i>hello world</i></div>");
    }

    @Test
    public void strikeoutTest(){
        Assert.assertEquals(parser.parse("[s]hello world[/s]").renderXHtml(), "<div><s>hello world</s></div>");
    }

    @Test
    public void emphasisTest(){
        Assert.assertEquals(parser.parse("[strong]hello world[/strong]").renderXHtml(), "<div><strong>hello world</strong></div>");
    }

    @Test
    public void quoteTest(){
        // TODO я нрипонял зачем <div> :-(
        Assert.assertEquals(parser.parse("[quote]hello world[/quote]").renderXHtml(), "<blockquote><div>hello world</div></blockquote>");
    }
    @Test
    public void quoteParamTest(){
        // TODO я нрипонял зачем <div> :-(
        Assert.assertEquals(parser.parse("[quote=maxcom]hello world[/quote]").renderXHtml(), "<p class=\"cite\"><cite>maxcom:</cite></p><blockquote><div>hello world</div></blockquote>");
    }
    @Test
    public void urlTest(){
        Assert.assertEquals(parser.parse("[url]http://linux.org.ru[/url]").renderXHtml(), "<div><a href=\"http://linux.org.ru\">http://linux.org.ru</a></div>");
    }
    @Test
    public void urlParamTest(){
        Assert.assertEquals(parser.parse("[url=http://linux.org.ru]linux[/url]").renderXHtml(), "<div><a href=\"http://linux.org.ru\">linux</a></div>");
    }
    @Test
    public void listTest(){
        Assert.assertEquals(parser.parse("[list][*]one[*]two[*]three[/list]").renderXHtml(), "<ul><li>one</li><li>two</li><li>three</li></ul>");
    }
    @Test
    public void codeTest(){
        Assert.assertEquals(parser.parse("[code][list][*]one[*]two[*]three[/list][/code]").renderXHtml(), "<pre><code>[list][*]one[*]two[*]three[/list]</code></pre>");
    }
    @Test
    public void userTest(){
        Assert.assertEquals(parser.parse("[user]maxcom[/user]").renderXHtml(), "<div><span style=\"white-space: nowrap\"><img src=\"/img/tuxlor.png\"><a style=\"text-decoration: none\" href='/people/maxcom/profile'>maxcom</a></span></div>");
    }
}

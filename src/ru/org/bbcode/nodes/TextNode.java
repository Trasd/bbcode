package ru.org.bbcode.nodes;

import ru.org.bbcode.Parser;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextNode extends Node {
    protected String text;

    public TextNode(Node parent, String text){
        super(parent);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String renderXHtml(){
        Matcher match = Parser.URL_REGEXP.matcher(text);
        StringBuilder ret = new StringBuilder();
        int pos = 0;
        while(match.find()){
            String url = match.group(2);
            String uri_type = Parser.escape(match.group(1));
            String url_encoded = "unsuported encode utf-8 :-(";
            try{
                url_encoded = URLEncoder.encode(url, "UTF-8");
            }catch(UnsupportedEncodingException ex){
                // TODO как это нет UTF-8 кодировки оО
            }

            ret.append(Parser.escape(text.substring(pos, match.start())));
            ret.append("<a href=\"")
                    .append(uri_type)
                    .append("://")
                    .append(url_encoded)
                    .append("\">")
                    .append(Parser.escape(url))
                    .append("</a>");
            pos = match.end();
        }
        ret.append(Parser.escape(text.substring(pos)));

        return ret.toString();
    }

    public String renderBBCode(){
        return text;
    }

    public boolean allows(String tagname){
        return false;
    }
}

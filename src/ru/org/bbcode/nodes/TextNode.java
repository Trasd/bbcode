package ru.org.bbcode.nodes;

import ru.org.bbcode.Parser;

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
        return Parser.escape(text);
    }

    public String renderBBCode(){
        return text;
    }

    public boolean allows(String tagname){
        return false;
    }
}

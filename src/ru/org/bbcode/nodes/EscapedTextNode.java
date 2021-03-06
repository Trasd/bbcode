package ru.org.bbcode.nodes;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class EscapedTextNode extends TextNode{
    public EscapedTextNode(Node node, String text){
        super(node, text);
    }

    public String renderBBCode(){
        StringBuilder ret = new StringBuilder();
        ret.append('[').append(text).append(']');
        return ret.toString();
    }
}

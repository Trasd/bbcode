package ru.org.bbcode.tags;

import ru.org.bbcode.Parser;
import ru.org.bbcode.nodes.Node;

import java.util.Set;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 12:34 PM
 */
public class QuoteTag extends Tag {
    public QuoteTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }
    public String renderNodeXhtml(Node node){
        StringBuilder ret = new StringBuilder();
        if(!node.isParameter()){
            node.setParameter("");
        }else{
            node.setParameter(node.getParameter().trim());
        }

        if(!node.getParameter().isEmpty()){
            ret.append("<p class=\"cite\"><cite>");
            ret.append(Parser.escape(node.getParameter()));
            ret.append(":</cite></p>");
            ret.append("<blockquote>");
            ret.append(node.renderChildrenXHtml());
            ret.append("</blockquote>");
        }else{
            ret.append("<blockquote>");
            ret.append(node.renderChildrenXHtml());
            ret.append("</blockquote>");
        }
        return ret.toString();
    }
}

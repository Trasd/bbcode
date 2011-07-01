package ru.org.bbcode.tags;

import ru.org.bbcode.Parser;
import ru.org.bbcode.nodes.Node;
import ru.org.bbcode.nodes.TextNode;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 12:20 PM
 */
public class UrlTag extends Tag {
    public UrlTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }
    public String renderNodeXhtml(Node node){
        StringBuilder ret = new StringBuilder();
        String url;
        if(node.lengthChildren() == 0){
            return "";
        }
        TextNode txtNode = (TextNode)node.getChildren().iterator().next();
        if(node.isParameter()){
            url = node.getParameter().trim();
        }else{
            url = txtNode.getText().trim();
        }
        String linkText = txtNode.getText().trim();
        if(url.length() != 0){
            ret.append("<a href=\"");
            ret.append(Parser.escape(url));
            ret.append("\">");
            ret.append(Parser.escape(linkText));
            ret.append("</a>");
        }
        return ret.toString();
    }

}

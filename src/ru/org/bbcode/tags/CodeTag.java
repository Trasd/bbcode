package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.Node;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 1:00 PM
 */
public class CodeTag extends Tag{
    public CodeTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }

    public String renderNodeXhtml(Node node){
        StringBuilder ret = new StringBuilder();
        ret.append("<pre><code>");
        ret.append(node.renderChildrenXHtml());
        ret.append("</code></pre>");
        return ret.toString();
    }

}

package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.Node;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 7/1/11
 * Time: 2:55 PM
 */
public class LiTag extends HtmlEquivTag{
    public LiTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
        setHtmlEquiv("li");
    }

    public String renderNodeBBCode(Node node){
        StringBuilder ret = new StringBuilder();
        return ret
                .append('[')
                .append(name)
                .append(']')
                .append(node.renderChildrenBBCode())
                .toString();
    }
}

package ru.org.bbcode.tags;

import ru.org.bbcode.Parser;
import ru.org.bbcode.nodes.Node;
import ru.org.bbcode.nodes.TextNode;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 7/6/11
 * Time: 12:27 AM
 */
public class MemberTag extends Tag{
    public MemberTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }

    public String renderNodeXhtml(Node node){
        if(node.lengthChildren() == 0){
            return "";
        }
        TextNode txtNode = (TextNode)node.getChildren().iterator().next();
        String memberName = Parser.escape(txtNode.getText());
        return Parser.getMemberLink(memberName);

    }

}

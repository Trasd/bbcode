package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.Node;
import ru.org.bbcode.nodes.TextNode;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImgTag extends Tag {
    public ImgTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }

    public String renderNodeXhtml(Node node){
        StringBuilder ret = new StringBuilder();
        if(node.lengthChildren() == 0){
            return "";
        }
        TextNode txtNode = (TextNode)node.getChildren().iterator().next();
        String imgurl = txtNode.getText();
        if(node.getParent().allows("img")){
            ret.append("<img src=\"");
            ret.append(imgurl); // TODO need escape
            ret.append("\"/>");
        }else{
            ret.append(imgurl); // TODO need escape
        }
        return ret.toString();
    }
    public String renderNodeBBCode(Node node){
        TextNode txtNode = (TextNode)node.getChildren().iterator().next();
        return txtNode.getText();
    }
}

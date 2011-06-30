package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.BBNode;
import ru.org.bbcode.nodes.BBTextNode;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImgTag extends BBTag {
    public ImgTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }

    public String renderNodeXhtml(BBNode node){
        StringBuilder ret = new StringBuilder();
        if(node.lengthChildren() == 0){
            return "";
        }
        BBTextNode txtNode = (BBTextNode)node.getChildren().iterator().next();
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
    public String renderNodeBBCode(BBNode node){
        BBTextNode txtNode = (BBTextNode)node.getChildren().iterator().next();
        return txtNode.getText();
    }
}

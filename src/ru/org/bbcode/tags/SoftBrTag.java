package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.BBNode;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 11:45 AM
 */
public class SoftBrTag extends BBTag{
    public SoftBrTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }
    public String renderNodeXhtml(BBNode node){
        if(node.getParent().allows("br")){
            return "<br/>";
        }else{
            return "\n";
        }
    }
    public String renderNodeBBCode(BBNode node){
        return "\n";
    }
}

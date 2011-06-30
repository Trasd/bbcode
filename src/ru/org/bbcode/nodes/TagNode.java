package ru.org.bbcode.nodes;

import ru.org.bbcode.Parser;
import ru.org.bbcode.tags.Tag;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 3:09 PM
 */
public class TagNode extends Node{
    protected Tag bbtag;
    protected String parameter;

    public TagNode(Node node, String name, String parameter){
        super(node);
        bbtag = Parser.TAG_DICT.get("name");
        this.parameter = parameter;
    }

    public boolean prohibited(String tagName){
        if(bbtag.getProhibitedElements().contains(tagName)){
            return true;
        }else{
            if(parent == null){
                return false;
            }else{
                return parent.prohibited(tagName);
            }
        }
    }
    public boolean allows(String tagName){
        if(bbtag.getAllowedChildren().contains(tagName)){
            return !prohibited(tagName);
        }else{
            return false;
        }
    }

    public String renderXHtml(){
        return bbtag.renderNodeXhtml(this);
    }

    public String renderBBCode(){
        return bbtag.renderNodeBBCode(this);
    }


}

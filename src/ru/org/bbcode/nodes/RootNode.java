package ru.org.bbcode.nodes;

import ru.org.bbcode.Parser;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 3:01 PM
 */
public class RootNode extends Node{
    protected boolean allowInline;

    public RootNode(Node node){
        super(node);
    }

    public RootNode(Node node, boolean allowInline){
        super(node);
        this.allowInline = allowInline;
    }

    public String renderXHtml(){
        return renderChildrenXHtml();
    }

    public boolean allows(String tagname){
        if(allowInline){
            return Parser.FLOW_TAGS.contains(tagname);
        }else{
            return Parser.BLOCK_LEVEL_TAGS.contains(tagname);
        }
    }

    public String renderBBCode(){
        return renderChildrenBBCode();
    }
}

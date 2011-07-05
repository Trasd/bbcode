package ru.org.bbcode.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/29/11
 * Time: 11:49 PM
*/
public class Node {
    protected Node parent=null;
    protected List<Node> children;
    protected String parameter;

    public Node(){
        children = new ArrayList<Node>();
    }

    public Node(Node parent){
        this.parent = parent;
        children = new ArrayList<Node>();
    }

    public Node getParent(){
        return parent;
    }

    public boolean allows(String tagname){
        assert false;
        return false;
    }

    public boolean prohibited(String tagname){
        return false;
    }

    public int lengthChildren(){
        return children.size();
    }

    public List<Node> getChildren(){
        return children;
    }

    public boolean isParameter(){
        return (parameter != null) && (parameter.length() > 0);
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String renderXHtml(){
        assert false;
        return "";
    }

    public String renderBBCode(){
        assert false;
        return "";
    }

    public String renderChildrenXHtml(){
        StringBuilder stringBuilder = new StringBuilder();
        for( Node child : children){
            stringBuilder.append(child.renderXHtml());
        }
        return stringBuilder.toString();
    }

    public String renderChildrenBBCode(){
        StringBuilder stringBuilder = new StringBuilder();
        for( Node child : children){
            stringBuilder.append(child.renderBBCode());
        }
        return stringBuilder.toString();

    }
}

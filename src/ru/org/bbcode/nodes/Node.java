package ru.org.bbcode.nodes;

import ru.org.bbcode.tags.CodeTag;
import ru.org.bbcode.tags.QuoteTag;

import java.lang.reflect.Field;
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
        return parameter.isEmpty();
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
        List<Node> stripChildren = stripOutsideBrs(children);
        StringBuilder stringBuilder = new StringBuilder();
        for( Node child : stripChildren){
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

    public List<Node> stripOutsideBrs(List<Node> nodes){

/*        List<Node> ret = new ArrayList<Node>();
        List<Node> blockNodes = new ArrayList<Node>();
        List<Node> nonBlockNodes = new ArrayList<Node>();
        for(Node node : nodes){
            Class nodeClass = node.getClass();
            Field bbtagField;
            try{
                bbtagField = nodeClass.getField("bbtag");
                if(CodeTag.class == bbtagField.getType() || QuoteTag.class == bbtagField.getType()){
                    blockNodes.add(node);
                }
            }catch (Exception ex){
                Node stripNode = stripBr(node);
                if(stripNode != null){
                    nonBlockNodes.add(node);
                }
            }
        } */

        return nodes;
    }
}

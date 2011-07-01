package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/29/11
 * Time: 11:20 PM
 */
public class Tag {
    protected String name;
    protected Set<String> allowedChildren;
    protected String implicitTag;
    protected boolean selfClosing=false;
    protected Set<String> prohibitedElements;
    protected boolean discardable=false;

    public Tag(String name, Set<String> allowedChildren, String implicitTag){
        this.name = name;
        this.implicitTag = implicitTag;
        this.allowedChildren = allowedChildren;
    }

    public void setProhibitedElements(Set<String> prohibitedElements) {
        this.prohibitedElements = prohibitedElements;
    }

    public void setSelfClosing(boolean selfClosing) {
        this.selfClosing = selfClosing;
    }

    public void setDiscardable(boolean discardable) {
        this.discardable = discardable;
    }

    public String renderNodeXhtml(Node node){
        throw new NotImplementedException();
    }

    public String renderNodeBBCode(Node node){
        StringBuilder opening = new StringBuilder(name);
        StringBuilder render = new StringBuilder();
        if(node.isParameter()){
            opening.append('=');
            opening.append(node.getParameter());
        }
        if(selfClosing){
            render.append('[')
                    .append(opening)
                    .append("/]");
        }else{
            render.append('[')
                    .append(opening).append(']')
                    .append(node.renderChildrenBBCode())
                    .append("[/")
                    .append(name)
                    .append(']');
        }
        return render.toString();
    }

    public Set<String> getAllowedChildren() {
        return allowedChildren;
    }

    public Set<String> getProhibitedElements() {
        return prohibitedElements;
    }

    public String getName() {
        return name;
    }

    public String getImplicitTag() {
        return implicitTag;
    }

    public boolean isSelfClosing() {
        return selfClosing;
    }

    public boolean isDiscardable() {
        return discardable;
    }
}

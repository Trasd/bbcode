package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.Node;

import java.util.Set;
import java.util.regex.Pattern;

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

    public static final Pattern MEMBER_REGEXP = Pattern.compile("^['\"]([0-9A-Za-z_]{1,30})['\"]$");
    public static final Pattern BBTAG_REGEXP = Pattern.compile("\\[\\[?/?([A-Za-z\\*]+)(:[a-f0-9]+)?(=[^\\]]+)?\\]?\\]");

    public static class Builder{

        protected boolean selfClosing=false;
        protected Set<String> prohibitedElements;
        protected boolean discardable=false;

        public Tag build(String name, Set<String> allowedChildren, String implicitTag){
            return new Tag(name, allowedChildren, implicitTag);
        }

        public Builder setSelfClosing(boolean selfClosing) {
            this.selfClosing = selfClosing;
            return this;
        }

        public Builder setProhibitedElements(Set<String> prohibitedElements) {
            this.prohibitedElements = prohibitedElements;
            return this;
        }

        public Builder setDiscardable(boolean discardable) {
            this.discardable = discardable;
            return this;
        }
    }

    public Tag(String name, Set<String> allowedChildren, String implicitTag){
        this.name = name;
        this.implicitTag = implicitTag;
        this.allowedChildren = allowedChildren;
    }

    public static Builder builder(){
        return new Builder();
    }

    protected Tag(Builder builder){
        selfClosing = builder.selfClosing;
        prohibitedElements = builder.prohibitedElements;
        discardable = builder.discardable;
    }


    public String renderNodeXhtml(Node node){
        assert false;
        return "exception!11";
    }

    public String renderNodeBBCode(Node node){
        StringBuilder opening = new StringBuilder(name);
        StringBuilder render = new StringBuilder();
        if(node.isParameter()){
            opening.append('=');
            opening.append(node.getParameter());
        }
        if(selfClosing){
            render.append('[');
            render.append(opening);
            render.append("/]");
        }else{
            render.append('[');
            render.append(opening);
            render.append(']');
            render.append(node.renderChildrenBBCode());
            render.append("[/");
            render.append(name);
            render.append(']');
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
}

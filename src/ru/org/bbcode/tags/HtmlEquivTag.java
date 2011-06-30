package ru.org.bbcode.tags;

import ru.org.bbcode.nodes.Node;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 10:40 AM
 */
public class HtmlEquivTag extends Tag {
    protected String htmlEquiv;
    protected Map<String, String> attributes;

    public static class Builder extends Tag.Builder{
        protected String htmlEquiv;
        protected Map<String,String> attributes;

        public Builder setHtmlEquiv(String htmlEquiv) {
            this.htmlEquiv = htmlEquiv;
            return this;
        }

        public Builder setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
            return this;
        }
        public HtmlEquivTag build(String name, Set<String> allowedChildren, String implicitTag){
            return new HtmlEquivTag(name, allowedChildren, implicitTag);
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public HtmlEquivTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }

    protected HtmlEquivTag(Builder builder){
        super(builder);
        htmlEquiv = builder.htmlEquiv;
        attributes = builder.attributes;
    }

    public String renderNodeXhtml(Node node){
        StringBuilder opening = new StringBuilder(htmlEquiv);
        StringBuilder ret = new StringBuilder();
        if(attributes != null){
            Iterator i = attributes.keySet().iterator();
            opening.append(' ');
            while(i.hasNext()){
                Map.Entry<String,String> entry = (Map.Entry<String,String>)i.next();
                String key = entry.getKey();
                String value = entry.getValue();
                opening.append(key);
                opening.append('=');
                opening.append(value); // TODO need escape value
                opening.append(' ');
            }
        }

        if(selfClosing){
            ret.append('<').append(opening).append("/>");
        }else{
            if(node.lengthChildren() > 0){
                ret.append('<').append(opening).append('>');
                ret.append(node.renderChildrenXHtml());
                ret.append('<').append(htmlEquiv).append('>');
            } // TODO else ret null ?
        }
        return ret.toString();
    }

}

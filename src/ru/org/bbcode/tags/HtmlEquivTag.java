package ru.org.bbcode.tags;

import ru.org.bbcode.Parser;
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

    public HtmlEquivTag(String name, Set<String> allowedChildren, String implicitTag){
        super(name, allowedChildren, implicitTag);
    }

    public void setHtmlEquiv(String htmlEquiv) {
        this.htmlEquiv = htmlEquiv;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
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
                opening.append(Parser.escape(value));
                opening.append(' ');
            }
        }

        if(selfClosing){
            ret.append('<').append(opening).append("/>");
        }else{
            if(node.lengthChildren() > 0){
                ret.append('<').append(opening).append('>');
                ret.append(node.renderChildrenXHtml());
                ret.append("</").append(htmlEquiv).append('>');
            }
        }
        return ret.toString();
    }

    public String renderNodeBBCode(Node node){
        if("div".equals(name)){
            return node.renderChildrenBBCode();
        }else{
            return super.renderNodeBBCode(node);
        }
    }

}

package ru.org.bbcode.nodes;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class BBTextNode extends BBNode {
    private String text;

    public BBTextNode(BBNode parent, String text){
        super(parent);
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String renderXHtml(){
        return text; // TODO need escape
    }

    public String renderBBCode(){
        return text;
    }

    public boolean allows(String tagname){
        return false;
    }
}

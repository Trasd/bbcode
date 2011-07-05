package ru.org.bbcode;

import junit.framework.Test;
import ru.org.bbcode.nodes.*;
import ru.org.bbcode.tags.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 1:18 PM
 */
public class Parser {

    public static Set<String> INLINE_TAGS;
    public static Set<String> BLOCK_LEVEL_TAGS;
    public static Set<String> FLOW_TAGS;
    public static Set<String> OTHER_TAGS;
    public static Set<String> ANCHOR_TAGS;

    public static List<Tag> TAGS;
    public static Map<String,Tag> TAG_DICT;
    public static Set<String> TAG_NAMES;

    public static final Pattern BBTAG_REGEXP = Pattern.compile("\\[\\[?/?([A-Za-z\\*]+)(:[a-f0-9]+)?(=[^\\]]+)?\\]?\\]");
    public static final Pattern URL_REGEXP = Pattern.compile("(\\w+)://([^\\s]+)");
    public static final Pattern P_REGEXP = Pattern.compile("(\r?\n){2,}");


    static{
        INLINE_TAGS = new HashSet<String>();
        INLINE_TAGS.add("b");
        INLINE_TAGS.add("i");
        INLINE_TAGS.add("u");
        INLINE_TAGS.add("s");
        INLINE_TAGS.add("em");
        INLINE_TAGS.add("strong");
        INLINE_TAGS.add("url");
        INLINE_TAGS.add("user");
        INLINE_TAGS.add("br");
        INLINE_TAGS.add("text");
        INLINE_TAGS.add("img");
        INLINE_TAGS.add("softbr");

        BLOCK_LEVEL_TAGS = new HashSet<String>();
        BLOCK_LEVEL_TAGS.add("p");
        BLOCK_LEVEL_TAGS.add("quote");
        BLOCK_LEVEL_TAGS.add("list");
        BLOCK_LEVEL_TAGS.add("pre");
        BLOCK_LEVEL_TAGS.add("code");
        BLOCK_LEVEL_TAGS.add("div");
        BLOCK_LEVEL_TAGS.add("cut");

        FLOW_TAGS = new HashSet<String>();
        FLOW_TAGS.addAll(INLINE_TAGS);
        FLOW_TAGS.addAll(BLOCK_LEVEL_TAGS);

        OTHER_TAGS = new HashSet<String>();
        OTHER_TAGS.add("*");

        ANCHOR_TAGS = new HashSet<String>();
        ANCHOR_TAGS.add("url");

        TAGS = new ArrayList<Tag>();
        { // <br/>
            HtmlEquivTag tag = new HtmlEquivTag("br", new HashSet<String>(), "div");
            tag.setSelfClosing(true);
            //tag.setDiscardable(true);
            tag.setHtmlEquiv("br");
            TAGS.add(tag);
        }
        { // <br/>, but can adapt during render ?
            Set<String> children = new HashSet<String>();
            SoftBrTag tag = new SoftBrTag("softbr", children, "div");
            tag.setSelfClosing(true);
            tag.setDiscardable(true);
            TAGS.add(tag);
        }
        { // <b>
            HtmlEquivTag tag = new HtmlEquivTag("b", INLINE_TAGS, "div");
            tag.setHtmlEquiv("b");
            TAGS.add(tag);
        }
        { // <i>
            HtmlEquivTag tag = new HtmlEquivTag("i", INLINE_TAGS, "div");
            tag.setHtmlEquiv("i");
            TAGS.add(tag);
        }
        { // <u> TODO Allert: The U tag has been deprecated in favor of the text-decoration style property.
            HtmlEquivTag tag = new HtmlEquivTag("u", INLINE_TAGS, "div");
            tag.setHtmlEquiv("u");
            TAGS.add(tag);
        }
        { // <s> TODO Allert: The S tag has been deprecated in favor of the text-decoration style property.
            HtmlEquivTag tag = new HtmlEquivTag("s", INLINE_TAGS, "div");
            tag.setHtmlEquiv("s");
            TAGS.add(tag);
        }
        { // <em>
            HtmlEquivTag tag = new HtmlEquivTag("em", INLINE_TAGS, "div");
            tag.setHtmlEquiv("em");
            TAGS.add(tag);
        }
        { // <strong>
            HtmlEquivTag tag = new HtmlEquivTag("strong", INLINE_TAGS, "div");
            tag.setHtmlEquiv("strong");
            TAGS.add(tag);
        }
        { // <a>
            Set<String> el = new HashSet<String>();
            el.add("text");
            UrlTag tag = new UrlTag("url", el, "div");
            TAGS.add(tag);
        }
        { // <a> member
            Set<String> el = new HashSet<String>();
            el.add("text");
            MemberTag tag = new MemberTag("user", el, "div");
            TAGS.add(tag);
        }
        { // <p>
            HtmlEquivTag tag = new HtmlEquivTag("p", INLINE_TAGS, null);
            tag.setHtmlEquiv("p");
            TAGS.add(tag);
        }
        { // <div>
            HtmlEquivTag tag = new HtmlEquivTag("div", FLOW_TAGS, null);
            tag.setHtmlEquiv("div");
            TAGS.add(tag);
        }
        { // <blockquote>
            Set<String> el = new HashSet<String>();
            el.addAll(BLOCK_LEVEL_TAGS);
            el.add("softbr");
            QuoteTag tag = new QuoteTag("quote", el, "div");
            TAGS.add(tag);
        }
        { // <ul>
            Set<String> el = new HashSet<String>();
            el.add("*");
            el.add("softbr");
            HtmlEquivTag tag = new HtmlEquivTag("list", el, null);
            tag.setHtmlEquiv("ul");
            TAGS.add(tag);
        }
        { // <pre> (only img currently needed out of the prohibited elements)
            Set<String> elements = new HashSet<String>();
            elements.add("img");
            elements.add("big");
            elements.add("small");
            elements.add("sub");
            elements.add("sup");
            HtmlEquivTag tag = new HtmlEquivTag("pre", INLINE_TAGS, null);
            tag.setHtmlEquiv("pre");
            tag.setProhibitedElements(elements);
            TAGS.add(tag);
        }
        { // <pre class="code">
            Set<String> elements = new HashSet<String>();
            elements.add("img");
            elements.add("big");
            elements.add("small");
            elements.add("sub");
            elements.add("sup");

            CodeTag tag = new CodeTag("code", INLINE_TAGS, null);
            tag.setProhibitedElements(elements);
            TAGS.add(tag);
        }
        {   // [cut]
            CutTag tag = new CutTag("cut", FLOW_TAGS, null);
            tag.setHtmlEquiv("div");
            TAGS.add(tag);
        }
        { //  <li>
            LiTag tag = new LiTag("*", FLOW_TAGS, "list");
            TAGS.add(tag);
        }

        TAG_DICT = new HashMap<String, Tag>();
        for(Tag tag : TAGS){
            if(!"text".equals(tag.getName())){
                TAG_DICT.put(tag.getName(), tag);
            }
        }
        TAG_NAMES = new HashSet<String>();
        for(Tag tag : TAGS){
            TAG_NAMES.add(tag.getName());
        }
    }

    public static String escape(String html){
        return html
                .replace("&", "&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"", "&quot;");
    }

    public static String getMemberLink(String name){
        String pattern = "<span style=\"white-space: nowrap\"><img src=\"/img/tuxlor.png\"><a style=\"text-decoration: none\" href='/people/%s/profile'>%s</a></span>";
        return String.format(pattern, name, name); // TODO
    }



    protected boolean rootAllowsInline;
    protected boolean renderCut;
    protected String cutUrl;
    protected Node currentNode;
    protected Node rootNode;

    public Parser(boolean renderCut, String cutUrl, boolean rootAllowsInline){
        this.renderCut = renderCut;
        this.cutUrl = cutUrl;
        this.rootAllowsInline = rootAllowsInline;
    }

    public Parser(boolean rootAllowsInline){
        this.rootAllowsInline = rootAllowsInline;
    }

    public Parser(){
        this.rootAllowsInline = false;
    }

    void pushTextNode(String text, boolean escaped){

        if(!currentNode.allows("text")){
            if(text.trim().length() == 0){
                if(escaped){
                    currentNode.getChildren().add(new EscapedTextNode(currentNode, text));
                }else{
                    currentNode.getChildren().add(new TextNode(currentNode, text));
                }
            }else{
                if(currentNode.allows("div")){
                    currentNode.getChildren().add(new TagNode(currentNode,"div", ""));
                    descend();
                }else{
                    ascend();
                }
                pushTextNode(text, false);
            }
        }else{
            Matcher matcher = P_REGEXP.matcher(text);

            if(matcher.find()){
                currentNode.getChildren().add(new TagNode(currentNode, "p", " "));
                descend();
                pushTextNode(text.substring(0, matcher.start()), false);
                ascend();
                pushTextNode(text.substring(matcher.end()), false);
            }else{
                if(escaped){
                    currentNode.getChildren().add(new EscapedTextNode(currentNode,text));
                }else{
                    currentNode.getChildren().add(new TextNode(currentNode,text));
                }
            }
        }
    }

    void descend(){
        currentNode = currentNode.getChildren().get(currentNode.getChildren().size()-1);
    }

    void ascend(){
        currentNode = currentNode.getParent();

    }

    void pushTagNode(String name, String parameter){
        if(!currentNode.allows(name)){
            Tag newTag = TAG_DICT.get(name);

            if(newTag.isDiscardable()){
                return;
            }else if(currentNode == rootNode || BLOCK_LEVEL_TAGS.contains(((TagNode)currentNode).getBbtag().getName()) && newTag.getImplicitTag() != null){
                pushTagNode(newTag.getImplicitTag(), "");
                pushTagNode(name, parameter);
            }else{
                currentNode = currentNode.getParent();
                pushTagNode(name, parameter);
            }
        }else{
            TagNode node = new TagNode(currentNode, name, parameter);
            if("cut".equals(name)){
                ((CutTag)(node.getBbtag())).setRenderOptions(renderCut, cutUrl);
            }
            currentNode.getChildren().add(node);
            if(!node.getBbtag().isSelfClosing()){
                descend();
            }
        }
    }

    void closeTagNode(String name){
        Node tempNode = currentNode;
        while (true){
            if(tempNode == rootNode){
                break;
            }
            if(TagNode.class.isInstance(tempNode)){
                TagNode node = (TagNode)tempNode;
                if(node.getBbtag().getName().equals(name)){
                    currentNode = tempNode;
                    ascend();
                    break;
                }
            }
            tempNode = tempNode.getParent();
        }
    }

    protected String prepare(String bbcode){
        return bbcode.replaceAll("\r\n", "\n").replaceAll("\n", "");
    }

    public void parse(String rawbbcode){
        rootNode = new RootNode(rootAllowsInline);
        currentNode = rootNode;
        String bbcode = rawbbcode;
        int pos = 0;
        boolean isCode = false;
        while(pos<bbcode.length()){
            Matcher match = BBTAG_REGEXP.matcher(bbcode).region(pos,bbcode.length());
            if(match.find()){
                pushTextNode(bbcode.substring(pos,match.start()), false);
                String tagname = match.group(1);
                String parameter = match.group(3);
                String wholematch = match.group(0);

                if(wholematch.startsWith("[[") && wholematch.endsWith("]]")){
                    pushTextNode(wholematch.substring(1,wholematch.length()-1), true);
                }else{
                    if(parameter != null && parameter.length() > 0){
                        parameter = parameter.substring(1);
                    }
                    if(TAG_NAMES.contains(tagname)){
                        if(wholematch.startsWith("[[")){
                            pushTextNode("[", false);
                        }


                        if(wholematch.startsWith("[/")){
                            if(!isCode || "code".equals(tagname)){
                                closeTagNode(tagname);
                            }else{
                                pushTextNode(wholematch,false);
                            }
                            if("code".equals(tagname)){
                                isCode = false;
                            }
                        }else{
                            if(isCode && !"code".equals(tagname)){
                                pushTextNode(wholematch,false);
                            }else if("code".equals(tagname)){
                                isCode = true;
                                pushTagNode(tagname, parameter);
                            }else{
                                pushTagNode(tagname, parameter);
                            }
                        }

                        if(wholematch.endsWith("]]")){
                            pushTextNode("]", false);
                        }
                    }else{
                        pushTextNode(wholematch, false);
                    }
                }
                pos = match.end();
            }else{
                pushTextNode(bbcode.substring(pos),false);
                pos = bbcode.length();
            }
        }

    }

    public String renderXHtml(){
        return rootNode.renderXHtml();
    }

    public String renderBBCode(){
        return rootNode.renderBBCode();
    }


}

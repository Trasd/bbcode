package ru.org.bbcode;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import com.sun.org.apache.xpath.internal.operations.And;
import ru.org.bbcode.nodes.*;
import ru.org.bbcode.tags.*;

import java.util.*;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 1:18 PM
 */
public class Parser {

    public static Set<String> INLINE_TAGS = initInlineTags();
    public static Set<String> BLOCK_LEVEL_TAGS = initBlockLevelTags();
    public static Set<String> FLOW_TAGS = initFlowTags();
    public static Set<String> OTHER_TAGS = initOtherTags();
    public static Set<String> ANCHOR_TAGS = initAnchorTags();

    public static List<Tag> TAGS = initTags();
    public static Map<String,Tag> TAG_DICT = initTagDict();
    public static Set<String> TAG_NAMES = initTagNames();

    private static List<Tag> initTags(){
        List<Tag> tags = new ArrayList<Tag>();
        // <br/>
        {
            Set<String> children = new HashSet<String>();
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("<br>")
                    .setSelfClosing(true)
                    .setDiscardable(true)
                    .build("br", children, "div");

            tags.add(tag);
        }
        // <br/>, but can adapt during render ?
        {
            Set<String> children = new HashSet<String>();
            Tag tag = SoftBrTag.builder()
                    .setSelfClosing(true)
                    .setDiscardable(true)
                    .build("softbr", children, "div");
            tags.add(tag);
        }
        // <b>
        {
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("b")
                    .build("b", INLINE_TAGS, "div");
            tags.add(tag);
        }
        // <img/>
        {
            Tag tag = ImgTag.builder()
                    .build("img", INLINE_TAGS, "div");
            tags.add(tag);
        }
        // <i>
        {
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("i")
                    .build("i", INLINE_TAGS, "div");
            tags.add(tag);
        }
        // <a>
        {
            Set<String> children = new HashSet<String>();
            children.add("text");
            Tag tag = UrlTag.builder()
                    .build("url", children , "div");
            tags.add(tag);
        }
        // <p>
        {
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("p")
                    .build("p", INLINE_TAGS, null);
            tags.add(tag);
        }
        // <div>
        {
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("div")
                    .build("div", FLOW_TAGS, null);
            tags.add(tag);
        }
        // <blockquote>
        {
            Set<String> children = new HashSet<String>();
            children.add("softbr");
            children.addAll(BLOCK_LEVEL_TAGS);
            Tag tag = QuoteTag.builder()
                    .build("quote", children, "div");
            tags.add(tag);
        }
        // <ul>
        {
            Set<String> children = new HashSet<String>();
            children.add("softbr");
            children.add("*");
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("ul")
                    .build("list", children, null);
            tags.add(tag);
        }
        // <pre> (only img currently needed out of the prohibited elements)
        {
            Set<String> elements = new HashSet<String>();
            elements.add("img");
            elements.add("big");
            elements.add("small");
            elements.add("sub");
            elements.add("sup");
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("pre")
                    .setProhibitedElements(elements)
                    .build("pre", INLINE_TAGS, null);
            tags.add(tag);
        }
        // <pre class="code">
        {
            Set<String> elements = new HashSet<String>();
            elements.add("img");
            elements.add("big");
            elements.add("small");
            elements.add("sub");
            elements.add("sup");
            Tag tag = CodeTag.builder()
                    .setProhibitedElements(elements)
                    .build("code", INLINE_TAGS, null);
            tags.add(tag);
        }
        // <li>
        {
            Tag tag = HtmlEquivTag.builder()
                    .setHtmlEquiv("li")
                    .build("*", FLOW_TAGS, "list");
            tags.add(tag);
        }
        return tags;
    }

    public static Map<String,Tag> initTagDict(){
        Map<String,Tag> tagDict = new HashMap<String, Tag>();
        for(Tag tag : initTags()){
            if(!"text".equals(tag.getName())){
                tagDict.put(tag.getName(), tag);
            }
        }
        return tagDict;
    }

    public static Set<String> initTagNames(){
        Set<String> tagNames = new HashSet<String>();
        for(Tag tag : initTags()){
            tagNames.add(tag.getName());
        }
        return tagNames;
    }

    private static Set<String> initInlineTags(){
        Set<String> tags = new HashSet<String>();
        tags.add("b");
        tags.add("i");
        tags.add("url");
        tags.add("br");
        tags.add("text");
        tags.add("img");
        tags.add("softbr");
        return tags;
    }

    private static Set<String> initBlockLevelTags(){
        Set<String> tags = new HashSet<String>();
        tags.add("p");
        tags.add("quote");
        tags.add("list");
        tags.add("pre");
        tags.add("code");
        tags.add("div");
        return tags;
    }

    private static Set<String> initFlowTags(){
        Set<String> tags = new HashSet<String>();
        tags.addAll(initInlineTags());
        tags.addAll(initBlockLevelTags());
        return tags;
    }

    private static Set<String> initOtherTags(){
        Set<String> tags = new HashSet<String>();
        tags.add("*");
        return tags;
    }

    private static Set<String> initAnchorTags(){
        Set<String> tags = new HashSet<String>();
        tags.add("url");
        return tags;
    }

    protected boolean rootAllowsInline;
    protected Node currentNode;
    protected Node rootNode;

    public Parser(boolean rootAllowsInline){
        this.rootAllowsInline = rootAllowsInline;
    }

    public Parser(){
        this.rootAllowsInline = false;
    }

    void pushTextNode(String text, boolean escaped){
        String textClass;
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
            if(escaped){
                currentNode.getChildren().add(new EscapedTextNode(currentNode,text));
            }else{
                currentNode.getChildren().add(new TextNode(currentNode,text));
            }
        }
    }

    void descend(){
        currentNode = currentNode.getChildren().remove(currentNode.getChildren().size()-1);
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
        return bbcode.replaceAll("\r\n", "\n").replaceAll("\n", "[softbr]");
    }

    public void parse(String rawbbcode){
        rootNode = new RootNode(rootAllowsInline);
        currentNode = rootNode;
        String bbcode = prepare(rawbbcode);
        int pos = 0;
        while(pos<bbcode.length()){
            Matcher match = Tag.BBTAG_REGEXP.matcher(bbcode).region(pos,bbcode.length());
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
                            closeTagNode(tagname);
                        }else{
                            pushTagNode(tagname, parameter);
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

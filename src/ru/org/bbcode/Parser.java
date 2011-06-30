package ru.org.bbcode;

import ru.org.bbcode.tags.*;

import java.util.*;

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


    public Parser(){

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

}

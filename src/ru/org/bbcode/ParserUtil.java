package ru.org.bbcode;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 9:51 PM
 */
public class ParserUtil {

    public static String bb2xhtml(String bbcode, boolean rootAllowsInline){
        Parser parser = new Parser(rootAllowsInline);
        parser.parse(bbcode);
        return parser.renderXHtml();
    }

    public static String correct(String bbcode, boolean rootAllowsInline){
        Parser parser = new Parser(rootAllowsInline);
        parser.parse(bbcode);
        return parser.renderBBCode();
    }

    public static String to_html(String text){
        return bb2xhtml(text, false);
    }

    public static void main(String[] args){
        String p = to_html("[b]ikiki[/b]");
        System.out.print(p.length());
        System.out.print(p);
/*        Console console = System.console();

        if (console == null) {
            System.err.println("No console.");
            System.exit(1);
        }
        while (true){
            String text = console.readLine("%nEnter your regex: ");
            console.format("result:%s.%n", to_html(text));
        }*/

    }



}

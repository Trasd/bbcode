package ru.org.bbcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

    public static void main(String[] args) throws Exception{
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print("> ");
        while(null != (line=console.readLine())){
            if("".equals(line.trim())){
                break;
            }
            String html = bb2xhtml(line, false);
            String bb = correct(line, false);

            System.out.println("html: "+html);
            System.out.println("bbcode: "+bb);

            System.out.print("> ");
        }
    }
}

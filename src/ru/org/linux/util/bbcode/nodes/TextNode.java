/*
 * Copyright (c) 2005-2006, Luke Plant
 * All rights reserved.
 * E-mail: <L.Plant.98@cantab.net>
 * Web: http://lukeplant.me.uk/
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *
 *      * Redistributions in binary form must reproduce the above
 *        copyright notice, this list of conditions and the following
 *        disclaimer in the documentation and/or other materials provided
 *        with the distribution.
 *
 *      * The name of Luke Plant may not be used to endorse or promote
 *        products derived from this software without specific prior
 *        written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Rewrite with Java language and modified for lorsource by Ildar Hizbulin 2011
 * E-mail: <hizel@vyborg.ru>
 */

package ru.org.linux.util.bbcode.nodes;

import ru.org.linux.util.bbcode.Parser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class TextNode extends Node {
    protected String text;

    public TextNode(Node parent, String text){
        super(parent);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String renderXHtml(){
        Matcher match = Parser.URL_REGEXP.matcher(text);
        StringBuilder ret = new StringBuilder();
        int pos = 0;
        while(match.find()){
            String url = match.group(2);
            String uri_type = Parser.escape(match.group(1));
            String url_encoded = "unsuported encode utf-8 :-(";
            try{
                url_encoded = URLEncoder.encode(url, "UTF-8");
            }catch(UnsupportedEncodingException ex){
                // TODO как это нет UTF-8 кодировки оО
            }

            ret.append(Parser.escape(text.substring(pos, match.start())));
            ret.append("<a href=\"")
                    .append(uri_type)
                    .append("://")
                    .append(url_encoded)
                    .append("\">")
                    .append(Parser.escape(url))
                    .append("</a>");
            pos = match.end();
        }
        ret.append(Parser.escape(text.substring(pos)));

        return ret.toString();
    }

    public String renderBBCode(){
        return text;
    }

    public boolean allows(String tagname){
        return false;
    }
}

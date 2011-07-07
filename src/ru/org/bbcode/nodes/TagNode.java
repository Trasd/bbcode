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

package ru.org.bbcode.nodes;

import ru.org.bbcode.Parser;
import ru.org.bbcode.tags.Tag;

/**
 * Created by IntelliJ IDEA.
 * User: hizel
 * Date: 6/30/11
 * Time: 3:09 PM
 */
public class TagNode extends Node{
    protected Tag bbtag;

    public TagNode(Node node, String name, String parameter){
        super(node);
        bbtag = Parser.TAG_DICT.get(name);
        this.parameter = parameter;
    }

    public boolean prohibited(String tagName){
        if(bbtag.getProhibitedElements() != null && bbtag.getProhibitedElements().contains(tagName)){
            return true;
        }else{
            if(parent == null){
                return false;
            }else{
                return parent.prohibited(tagName);
            }
        }
    }
    public boolean allows(String tagName){
        if(bbtag.getAllowedChildren().contains(tagName)){
            return !prohibited(tagName);
        }else{
            return false;
        }
    }

    public Tag getBbtag() {
        return bbtag;
    }

    public String renderXHtml(){
        return bbtag.renderNodeXhtml(this);
    }

    public String renderBBCode(){
        return bbtag.renderNodeBBCode(this);
    }


}

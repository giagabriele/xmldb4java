/*
 * Copyright 2011 Giacomo Stefano Gabriele
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xmldb.core.trasformers;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.tree.DefaultElement;
import org.xmldb.core.type.Sequence;

/**
 *
 * @author Giacomo Stefano Gabriele
 */
public class SequenceTrasformers implements Trasformers<Sequence>{

    public Element trasformElement(Sequence t) {
        DefaultElement result = new DefaultElement("Sequence");

        DefaultAttribute id = new DefaultAttribute("id",t.getId());
        DefaultAttribute sequence = new DefaultAttribute("sequence",String.valueOf(t.getSequence()));

        result.add(id);
        result.add(sequence);

        return result;
    }

    public Sequence trasformModel(Element element) {
        Sequence s = new Sequence();
        s.setId(element.attributeValue("id"));
        s.setSequence(Integer.parseInt(element.attributeValue("sequence")));

        return s;
    }

}

package com.forest.config.spring.schema;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author Forest Dong
 * @date 2022年04月25日 20:56
 */
public class ForestNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("", new BeanDefinitionParser() {
            @Override
            public BeanDefinition parse(Element element, ParserContext parserContext) {
                return null;
            }
        });
    }
}

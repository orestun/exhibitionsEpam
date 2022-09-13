package com.epam.exhibitions;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebListener
public class AttributeListener implements HttpSessionAttributeListener {

    public static final String TEXT_YELLOW = "\u001B[33m";
    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        System.out.println(TEXT_YELLOW+"Attribute "+sbe.getName()+ " is created");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        System.out.println(TEXT_YELLOW+"Attribute "+sbe.getName()+ " is removed");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        System.out.println(TEXT_YELLOW+"Attribute "+sbe.getName()+ " is replaced");
    }
}

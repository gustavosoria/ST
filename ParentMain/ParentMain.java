package test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.InputElementFactory;
import net.sourceforge.jwebunit.api.IElement;
import net.sourceforge.jwebunit.htmlunit.HtmlUnitElementImpl;
import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Gustavo German Soria
 * ID: 163076
 * 
 * File: ParentMain.php 
 * Variable: ‘selectclass’, 'student'
 * Vulnerability: variable is passed within a hidden field.
 */

public class ParentMain {
    
    private WebTester tester;
    
    public ParentMain() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    public void testVulnerability194a() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "parent2");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("Students of orazio orazio");
        tester.setWorkingForm("student");
        tester.setHiddenField("selectclass","'><a href=http://www.ibm.com>Malicious link</a> <br />");
        addSubmitButton("//form[@name='student']");
        tester.submit();
        tester.assertLinkNotPresentWithText("Malicious link");
    }
     @Test
    public void testVulnerability194b() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "parent2");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("Students of orazio orazio");
        tester.setWorkingForm("student");
        tester.setHiddenField("student","'><a href=http://www.ibm.com>Malicious link</a> <br />");
        addSubmitButton("//form[@name='student']");
        tester.submit();
        tester.assertLinkNotPresentWithText("Malicious link");
    }
     
     private void addSubmitButton(String expression) {
        IElement element = tester.getElementByXPath(expression);
        DomElement form = ((HtmlUnitElementImpl) element).getHtmlElement();
        InputElementFactory factory = InputElementFactory.instance;
        AttributesImpl attributes = new AttributesImpl();
        attributes.addAttribute("", "", "type", "", "submit");
        HtmlElement submit = factory.createElement(form.getPage(), "input", attributes);
        form.appendChild(submit);
    }
}

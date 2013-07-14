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
 * File: ParentViewCourses.php 
 * Variable: ‘student’
 * Vulnerability: variable is passed within a hidden field.
 */

public class ParentViewCourses {
    
    private WebTester tester;
    
    public ParentViewCourses() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }
    
     @Test
    public void testVulnerability142() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "parent2");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("Students of orazio orazio");
        tester.clickLinkWithText("Student name Student surname");
        tester.assertTextPresent("Student name Student surname's Classes");
        tester.setWorkingForm("student");
        tester.setHiddenField("student","2'> <a href=\"http://www.ibm.com\">Malicious link</a> <br / '");
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

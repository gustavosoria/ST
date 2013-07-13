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
 * 
 * File: AddAssignments.php 
 * Variable: ‘selectclass’
 * Vulnerability: the variable is passed within a hidden field, but the value can be modified closing  
 * the tag and refining the attack.
 */
public class AddAttendance {
    
    private WebTester tester;
    
    public AddAttendance() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @Test
    public void testVulnerability11() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();

        tester.assertMatch("topolino topolino's Classes");
        tester.setWorkingForm("teacher");
        tester.clickLinkWithText("class1");


        tester.assertMatch("Class Settings");
        tester.clickLinkWithText("Assignments");
        tester.assertMatch("Manage Assignments");
        tester.clickButtonWithText("Add");

        tester.setWorkingForm("addassignment");
        tester.setTextField("selectclass", "4 '> <a href=www.ibm.com>malicious link</a> <br / '");
        addSubmitButton("//form[@name='addassignment']");
        tester.submit();

        tester.assertLinkNotPresentWithText("malicious link");
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

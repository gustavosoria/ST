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
 * File: AddAttendance.php 
 * Variables: ‘semester’, ‘student’.
 * Vulnerability: the variables are passed within a hidden field, but the value 
 * can be modified closing  the tag and refining the attack.
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
    //variable semester
    public void testVulnerability13a() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");

        tester.clickLinkWithText("Attendance");
        tester.assertMatch("Attendance");
        tester.clickButtonWithText("Add");
        tester.assertTextPresent("Add New Attendance Record");

        tester.setWorkingForm("addattendance");
        tester.setTextField("semester", "4 '> <a href=www.ibm.com>malicious link</a> <br / '");
        addSubmitButton("//form[@name='addattendance']");
        tester.submit();

        tester.assertLinkNotPresentWithText("malicious link");
    }

    @Test
    //variable student
    public void testVulnerability13b() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");

        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");

        tester.clickLinkWithText("Attendance");
        tester.assertMatch("Attendance");
        tester.clickButtonWithText("Add");
        tester.assertTextPresent("Add New Attendance Record");

        tester.setWorkingForm("addattendance");
        tester.setTextField("student", "4 '> <a href=www.ibm.com>malicious link</a> <br / '");
        addSubmitButton("//form[@name='addattendance']");
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

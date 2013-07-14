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
 * File: ViewClassSettings.php 
 * Variable: ‘selectclass’ 
 * Vulnerability: variable is passed within a hidden field.
 */
public class ViewClassSettings {

    private WebTester tester;

    public ViewClassSettings() {
    }

    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @Test
    public void testVulnerability87_88() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "student");
        tester.setTextField("password", "student");
        tester.submit();
        tester.assertMatch("student_n student_n's Classes");
        tester.clickLinkWithText("class1");
        tester.assertMatch("Class Settings");
        tester.setWorkingForm("classes");
        tester.setTextField("selectclass", "6'> <a href=\"http://www.ibm.com\">malicious link</a> <br / '");
        addSubmitButton("//form[@name='classes']");
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

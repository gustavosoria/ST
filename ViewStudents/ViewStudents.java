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
 * File: ViewStudents.php 
 * Variable: ‘selectclass’ 
 * Vulnerability: variable is passed within a hidden field.
 */
public class ViewStudents {

    private WebTester tester;

    public ViewStudents() {
    }

    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @Test
    public void testVulnerability181() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "teacher1");
        tester.setTextField("password", "nonlaso");
        tester.submit();
        tester.assertMatch("topolino topolino's Classes");
        tester.clickLinkWithText("class1");
        tester.assertMatch("Class Settings");
        tester.clickLinkWithText("Students");
        tester.assertTextPresent("Students");
        tester.setWorkingForm("classes");
        tester.setHiddenField("selectclass", "6 '><a href=http://www.ibm.com>Malicious link</a> <br />");
        addSubmitButton("//form[@name='classes']");
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

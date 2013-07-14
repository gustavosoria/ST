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
 * File: EditAnnouncement.php 
 * Variable: ‘delete’
 * Vulnerability: variable is passed within a hidden field, but the value can be modified closing  the tag and refining the attack.
 */

public class EditAnnouncement {
    
    private WebTester tester;
    
    public EditAnnouncement() {
    }
    
    @Before
    public void setUp() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8888/schoolmate/");
    }

    @Test
    public void testVulnerability41() {
        tester.beginAt("index.php");
        tester.assertMatch("Today's Message");
        tester.setTextField("username", "test");
        tester.setTextField("password", "test");
        tester.submit();
        tester.assertMatch("Manage Classes");
        tester.clickLinkWithText("Announcements");
        tester.assertMatch("Manage Announcements");
        tester.setWorkingForm("announcements");
        tester.checkCheckbox("delete[]", "1");
        tester.setTextField("delete[]", "1'> <a href=http://www.ibm.com>Malicious link</a><br/'");
        addSubmitButton("//form[@name='announcements']");
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

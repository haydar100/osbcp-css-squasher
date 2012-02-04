package com.osbcp.squicss;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.parser.CSSOMParser;

public class Squicss {

	protected static Squicss oParser;

	public static void main(final String[] args) throws IOException {

		Opti2 logic = new Opti2();

		// cssfile accessed as a resource, so must be in the pkg (in src dir).
		InputStream stream = Squicss.class.getResourceAsStream("loopia.css");
		InputSource source = new InputSource(new InputStreamReader(stream));
		CSSOMParser parser = new CSSOMParser();
		CSSStyleSheet stylesheet = parser.parseStyleSheet(source, null, null);

		CSSRuleList ruleList = stylesheet.getCssRules();

		for (int i = 0; i < ruleList.getLength(); i++) {
			CSSRule rule = ruleList.item(i);
			if (rule instanceof CSSStyleRule) {
				CSSStyleRule styleRule = (CSSStyleRule) rule;

				String selectorString = styleRule.getSelectorText();

				for (String select2 : selectorString.split(",")) {

					Selector selector = new Selector(select2.trim());
					//				System.out.println("selector:" + i + ": " + selector);
					CSSStyleDeclaration styleDeclaration = styleRule.getStyle();

					Set<PropertyValue> values = new HashSet<PropertyValue>();

					for (int j = 0; j < styleDeclaration.getLength(); j++) {

						String property = styleDeclaration.item(j);
						String value = styleDeclaration.getPropertyCSSValue(property).getCssText();
						//					System.out.println("property: " + property);
						//					System.out.println("value: " + value);
						//					System.out.println("priority: " + styleDeclaration.getPropertyPriority(property));

						PropertyValue propertyValue = new PropertyValue(property, value);

						values.add(propertyValue);

					}

					logic.register(selector, values);

				}

			}// end of StyleRule instance test
		} // end of ruleList loop

		logic.print2();

	}
}

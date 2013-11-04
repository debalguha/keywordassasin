package org.fipecafi.tools.filter.test;

import java.io.InputStream;

import org.fipecafi.tools.filter.IFilter;
import org.fipecafi.tools.filter.Filter;
import org.fipecafi.tools.filter.FilterXMLParser;
import org.fipecafi.tools.filter.Filters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilterXMLParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMarshallXmlConfig() throws Exception {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("filter.xml");
		Filters filters = FilterXMLParser.marshallXmlConfig(is);
		
		for (IFilter filter : filters.getFilters())
			System.out.println(((Filter)filter).getFilterName());
	}

}

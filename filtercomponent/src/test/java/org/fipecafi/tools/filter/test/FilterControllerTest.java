package org.fipecafi.tools.filter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.Collection;

import org.fipecafi.tools.filter.IFilter;
import org.fipecafi.tools.filter.Option;
import org.fipecafi.tools.filter.OptionFilter;
import org.fipecafi.tools.filter.component.controller.FilterController;
import org.fipecafi.tools.filter.demo.servlet.DerbyUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FilterControllerTest {
	InputStream is = null;
	static{
		DerbyUtil.initDerby();
	}
	@Before
	public void setUp() throws Exception {
		System.setProperty("junit", "true");
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream("filter.xml");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetMenuCount() {
		FilterController controller = new FilterController();
		controller.init(is);
		assertEquals(3, controller.getMenuCount());
		assertEquals(2, controller.getOptionFilters().size());
		assertEquals(1, controller.getDateFilters().size());
	}

	@Test
	public void testGetFilterNames() {
		FilterController controller = new FilterController();
		controller.init(is);
		assertEquals(3,controller.getFilterNames().size());
	}
	
	@Test
	public void testCheckFilterOptions(){
		FilterController controller = new FilterController();
		controller.init(is);
		Collection<IFilter> filters = controller.getFilterMap().values();
		for(IFilter filter : filters){
			if(filter instanceof OptionFilter){
				for(Option option : ((OptionFilter) filter).getOptions())
					System.out.println(option);
			}
		}
		
	}
}

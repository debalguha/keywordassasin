package org.google.api.ui.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.google.api.model.ModelObject;
import org.google.api.ui.listener.ModelObserver;
import org.google.api.util.Strength;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name="chartController")
@SessionScoped
public class ChartController implements ModelObserver {
	private CartesianChartModel categoryModel;
	private ChartSeries ratingCategories;
	private Strength[] strengths = new Strength[]{Strength.UNKNOWN, Strength.TERRIBLE, Strength.BAD, Strength.GOOD, Strength.GREAT, Strength.AMAZING};
	private static final Log logger = LogFactory.getLog(ChartController.class);

	public ChartController() {
		categoryModel = new CartesianChartModel();
		ratingCategories = new ChartSeries();
		ratingCategories.setLabel("Ratings");
		/*
		 * categoryModel = new CartesianChartModel(); ratingCategories = new
		 * ChartSeries(); ratingCategories.set(Strength.TERRIBLE.name(), new
		 * Integer(0)); ratingCategories.set(Strength.BAD.name(), new
		 * Integer(0)); ratingCategories.set(Strength.GOOD.name(), new
		 * Integer(0)); ratingCategories.set(Strength.GREAT.name(), new
		 * Integer(0)); ratingCategories.set(Strength.AMAZING.name(), new
		 * Integer(0)); categoryModel.addSeries(ratingCategories);
		 */
		reCreateChartModel();
	}

	@Override
	public void observe(ModelObject model) {
		if (model.getKeywordRating() == null)
			return;
		int existingCount = ratingCategories.getData()
				.get(model.getKeywordRating().name()).intValue();
		ratingCategories.set(model.getKeywordRating().name(),
				(existingCount + 1));
		/*
		 * int existingCount = 0; if(livePieModel.getData()!=null &&
		 * !livePieModel.getData().isEmpty())
		 * livePieModel.getData().get(model.getKeywordRating
		 * ().name()).intValue();
		 */
	}

	public CartesianChartModel getCategoryModel() {
		System.out.println(FacesContext.getCurrentInstance().getExternalContext().getClass());
		System.out.println(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getClass());
		//System.out.println(((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getUserPrincipal().getClass());
		return categoryModel;
	}

	private void reCreateChartModel() {
		for(Strength strength : strengths)
			ratingCategories.set(strength.name(), new Integer(0));
		
		categoryModel.addSeries(ratingCategories);
	}

	public void clear() {
		categoryModel.clear();
		reCreateChartModel();
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Item selected", "Item Index: " + event.getItemIndex()
						+ ", Series Index:" + event.getSeriesIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public boolean isRender() {
		return ratingCategories.getData() != null ? ratingCategories.getData()
				.isEmpty() : false;
	}

	public boolean getRender() {
		return ratingCategories.getData() != null ? ratingCategories.getData()
				.isEmpty() : false;
	}

}

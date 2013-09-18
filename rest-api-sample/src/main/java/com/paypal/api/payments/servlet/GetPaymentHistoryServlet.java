// #GetPaymentList Sample
// This sample code demonstrate how you can
// retrieve a list of all Payment resources
// you've created using the Payments API.
// Note various query parameters that you can
// use to filter, and paginate through the
// payments list.
// API used: GET /v1/payments/payments
package com.paypal.api.payments.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentHistory;
import com.paypal.api.payments.util.GenerateAccessToken;
import com.paypal.core.rest.PayPalRESTException;
import com.paypal.core.rest.PayPalResource;
import com.paypal.core.rest.QueryParameters;

/**
 * @author lvairamani
 * 
 */
public class GetPaymentHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger
			.getLogger(GetPaymentHistoryServlet.class);

	public void init(ServletConfig servletConfig) throws ServletException {
		// ##Load Configuration
		// Load SDK configuration for
		// the resource. This intialization code can be
		// done as Init Servlet.
		InputStream is = GetPaymentHistoryServlet.class
				.getResourceAsStream("/sdk_config.properties");
		try {
			PayPalResource.initConfig(is);
		} catch (PayPalRESTException e) {
			LOGGER.fatal(e.getMessage());
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		QueryParameters queryParameters = new QueryParameters();
		queryParameters.setCount("10");
		try {
			
			// ###AccessToken
			// Retrieve the access token from
			// OAuthTokenCredential by passing in
			// It is not mandatory to generate Access Token on a per call basis.
			// Typically the access token can be generated once and
			// reused within the expiry window
			String accessToken = GenerateAccessToken.getAccessToken();

			// ###Retrieve
			// Retrieve the PaymentHistory object by calling the
			// static `get` method
			// on the Payment class, and pass the
			// AccessToken and a QueryParameters object that contains
			// query parameters for paginations and filtering.
			// Refer the API documentation
			// for valid values for keys
			PaymentHistory paymentHistory = Payment.get(accessToken,
					queryParameters);
			LOGGER.info("Payment History = " + paymentHistory.toString());
			req.setAttribute("response", Payment.getLastResponse());
		} catch (PayPalRESTException e) {
			req.setAttribute("error", e.getMessage());
		}
		req.getRequestDispatcher("response.jsp").forward(req, resp);

	}

}

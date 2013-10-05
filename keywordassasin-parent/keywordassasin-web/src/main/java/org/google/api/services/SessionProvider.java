package org.google.api.services;

import java.util.HashMap;
import java.util.Map;

import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.common.lib.auth.ClientLoginTokens;

public final class SessionProvider {
	private static Map<String, AdWordsSession> sessionMap = new HashMap<String, AdWordsSession>();

	public static AdWordsSession createOrFindAdWordSession(String email)
			throws Exception {
		if (!sessionMap.containsKey(email)) {
			String clientLoginToken = new ClientLoginTokens.Builder()
					.forApi(ClientLoginTokens.Api.ADWORDS).fromFile().build()
					.requestToken();
			AdWordsSession session = new AdWordsSession.Builder().fromFile()
					.withClientLoginToken(clientLoginToken).build();
			sessionMap.put(email, session);
		}
		return sessionMap.get(email);
	}
}

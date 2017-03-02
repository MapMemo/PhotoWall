/* AccountFormatter.java created on Feb 21, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.ws.auth;

import java.util.Map;

import tw.funymph.photowall.core.Account;
import tw.funymph.photowall.utils.MapUtils;

/**
 * @author spirit
 * @version 
 * @since 
 */
public interface AccountFormatter {

	public static Map<String, Object> publicInfo(Account account) {
		Map<String, Object> info = MapUtils.asMap("identity", account.getIdentity());
		info.put("nickname", account.getNickname());
		return info;
	}
}

/* SqlAuthenticationRepository.java created on Mar 1, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.sql2o;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import tw.funymph.photowall.core.Authentication;
import tw.funymph.photowall.core.repository.AuthenticationRepository;
import tw.funymph.photowall.core.repository.RepositoryException;

/**
 * This class provides the implementation of {@link AuthenticationRepository}
 * with the Sql2o technology.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class SqlAuthenticationRepository implements AuthenticationRepository {

	private Sql2o sql2o;

	/**
	 * Construct a <code>SqlAuthenticationRepository</code> instance with
	 * the Sql2o instance.
	 * 
	 * @param sql2o the Sql2o instance
	 */
	public SqlAuthenticationRepository(Sql2o sql2o) {
		this.sql2o = sql2o;
	}

	@Override
	public Authentication get(String token) {
		Authentication authentication = null;
		try (Connection connection = sql2o.open()) {
			authentication = connection.createQuery("select * from AUTHENTICATION where TOKEN=:token")
				.addColumnMapping("ID", "identity")
				.addColumnMapping("TOKEN", "token")
				.addParameter("token", token)
				.executeAndFetchFirst(Authentication.class);
		}
		return authentication;
	}

	@Override
	public void save(Authentication authentication) throws RepositoryException {
		try (Connection connection = sql2o.beginTransaction()) {
			connection.createQuery("insert into AUTHENTICATION(ID, TOKEN) values (:identity, :token)")
				.bind(authentication)
				.executeUpdate();
			connection.commit();
		}
		catch (Sql2oException e) {
			throw new RepositoryException(e.getMessage(), e);
		}
	}
}

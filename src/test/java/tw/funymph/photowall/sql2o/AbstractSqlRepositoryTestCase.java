/* AbstractSqlRepositoryTestCase.java created on Mar 2, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.sql2o;

import static java.sql.DriverManager.getConnection;

import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;

/**
 * This class provides some common-used set up and tear down methods
 * for testing repository.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractSqlRepositoryTestCase {

	private Sql2o sql2o;

	/**
	 * Use the flyway to set up the tables in the in-memory database
	 * and prepare the data source.
	 *  
	 * @return the initialized Sql2o
	 */
	public Sql2o initSql2o() {
		Flyway flyway = new Flyway();
		flyway.setDataSource("jdbc:derby:memory:photowall;create=true", null, null);
		flyway.migrate();
		sql2o = new Sql2o(flyway.getDataSource());
		return sql2o;
	}

	/**
	 * Drop all database.
	 */
	public void dropDatabase() {
		try {
			getConnection("jdbc:derby:memory:photowall;drop=true");
		}
		catch (Exception e) {
			
		}
	}
}

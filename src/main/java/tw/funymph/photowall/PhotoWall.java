/* PhotoWall.java created on Feb 18, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static spark.Spark.*;
import static tw.funymph.photowall.ws.HttpStatusCodes.NotFound;
import static tw.funymph.photowall.ws.SparkWebService.enableCORS;
import static tw.funymph.photowall.ws.SparkWebService.wrapException;
import static tw.funymph.photowall.wss.WebSocketEventHandler.EventPath;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;

import tw.funymph.photowall.core.AccountManager;
import tw.funymph.photowall.core.DefaultAccountManager;
import tw.funymph.photowall.sql2o.SqlAccountRepository;
import tw.funymph.photowall.sql2o.SqlAuthenticationRepository;
import tw.funymph.photowall.ws.WebServiceException;
import tw.funymph.photowall.ws.auth.AccountWebService;
import tw.funymph.photowall.wss.WebSocketEventHandler;

/**
 * The main entry of the PhotoWall Web service.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
public class PhotoWall {

	private static final String DerbyDatabaseUrlKey = "database.derby.url";
	private static final String ApplicationPropertiesPath = "/application.properties";

	private static PhotoWall instance;
	private static Sql2o sql2o;

	private AccountManager accountManager;

	/**
	 * Get the shared instance (singleton) of {@link PhotoWall}.
	 * 
	 * @return the shared instance
	 */
	public static synchronized PhotoWall sharedInstance() {
		if (instance == null) {
			instance = new PhotoWall();
		}
		return instance;
	}

	/**
	 * The main entry.
	 * 
	 * @param args the command line arguments
	 * @throws Exception if any error occurred
	 */
	public static void main(String[] args) throws Exception {
		Properties properties = loadProperties();
		DataSource dataSource = setUpDatabase(properties);
		sql2o = new Sql2o(dataSource);
		webSocket(EventPath, WebSocketEventHandler.class);
		staticFiles.externalLocation(new File("files").getAbsolutePath());
		notFound((request, response) -> {
			return wrapException(response, currentTimeMillis(), new WebServiceException(NotFound, -1, format("no action for %s %s", request.requestMethod(), request.pathInfo())));
		});
		init();
		enableCORS();
		path("/ws", () -> {
			new AccountWebService(sharedInstance().getAccountManager()).routes();
		});
	}

	/**
	 * Construct a <code>PhotoWall</code> instance. The constructor is
	 * private, please call {@link #sharedInstance()} to get the shared
	 * instance.
	 */
	private PhotoWall() {
		accountManager = new DefaultAccountManager(new SqlAccountRepository(sql2o), new SqlAuthenticationRepository(sql2o));
	}

	/**
	 * Get the account manager.
	 * 
	 * @return the account manager
	 */
	public AccountManager getAccountManager() {
		return accountManager;
	}

	/**
	 * Load the application properties from the file.
	 * 
	 * @return the application properties
	 * @throws Exception if any error occurred
	 */
	private static Properties loadProperties() throws Exception {
		Properties properties = new Properties();
		try (InputStream stream = PhotoWall.class.getResourceAsStream(ApplicationPropertiesPath)) {
			properties.load(stream);
		}
		return properties;
	}

	/**
	 * Set up the database including the necessary migration.
	 * 
	 * @param properties the application properties
	 * @return the ready-to-use data source
	 */
	private static DataSource setUpDatabase(Properties properties) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(properties.getProperty(DerbyDatabaseUrlKey), null, null);
		flyway.migrate();
		return flyway.getDataSource();
	}
}

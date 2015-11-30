package br.com.jsbse.integracao.hibernate;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jsbse.arquitetura.excecao.ExcecaoInfraestrutura;
import br.com.jsbse.arquitetura.util.FabricaArquivo;
import br.com.jsbse.arquitetura.util.UtilString;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
	private static final String JDBC_USERNAME = "jdbc.username";

	private static final String ACQUIRE_INCREMENT = "pool.acquireIncrement";

	private static final String JDBC_DRIVER = "jdbc.driver";

	private static final String JDBC_URL = "jdbc.url";

	private static final String JDBC_PASSWORD = "jdbc.password";

	private static final String MIN_POOL_SIZE = "pool.minPoolSize";

	private static final String MAX_POOL_SIZE = "pool.maxPoolSize";

	private static final long serialVersionUID = 8074002161278796379L;

	private static final String MAX_IDLE_TIME = "pool.maxIdleTime";

	private static Logger log = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);

	private String fileLocation;

	private ComboPooledDataSource cpds;

	private Properties properties;

	/**
	 * 
	 * Constructor. Initializes the ComboPooledDataSource based on the config.properties.
	 * 
	 * @throws PropertyVetoException
	 */
	public MultiTenantConnectionProviderImpl(String fileLocation) throws PropertyVetoException {
		log.info("Initializing Connection Pool!");
		

		if (UtilString.isVazio(fileLocation))
			throw new ExcecaoInfraestrutura(
					"A propriedade fileLocation deve ser preenchida com o nome do arquivo properties que contém os dados de conexão com o banco de dados.");

		this.fileLocation = fileLocation;
		properties = FabricaArquivo.getInstance().criaProperties(this.fileLocation);
		cpds = new ComboPooledDataSource();
		cpds.setDriverClass(properties.getProperty(JDBC_DRIVER));
		cpds.setJdbcUrl(properties.getProperty(JDBC_URL));
		cpds.setUser(properties.getProperty(JDBC_USERNAME));
		cpds.setPassword(properties.getProperty(JDBC_PASSWORD));
		cpds.setMinPoolSize(!StringUtils.isEmpty(properties.getProperty(MIN_POOL_SIZE)) ? Integer.parseInt(properties.getProperty(MIN_POOL_SIZE)) : 3);
		cpds.setMaxPoolSize(!StringUtils.isEmpty(properties.getProperty(MAX_POOL_SIZE)) ? Integer.parseInt(properties.getProperty(MAX_POOL_SIZE)) : 12);
		cpds.setAcquireIncrement(!StringUtils.isEmpty(properties.getProperty(ACQUIRE_INCREMENT)) ? Integer.parseInt(properties
				.getProperty(ACQUIRE_INCREMENT)) : 3);
		cpds.setMaxIdleTime(!StringUtils.isEmpty(properties.getProperty(MAX_IDLE_TIME)) ? Integer.parseInt(properties.getProperty(MAX_IDLE_TIME)) : 300);
		cpds.setPreferredTestQuery("SELECT 1");
		log.info("Connection Pool initialised!");
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		log.debug("Get Default Connection:::Number of connections (max: busy - idle): {} : {} - {}",
				new int[] { cpds.getMaxPoolSize(), cpds.getNumBusyConnectionsAllUsers(), cpds.getNumIdleConnectionsAllUsers() });
		if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()) {
			log.warn("Maximum number of connections opened");
		}
		if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers() == 0) {
			log.error("Connection pool empty!");
		}
		return cpds.getConnection();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		log.debug("Get {} Connection:::Number of connections (max: busy - idle): {} : {} - {}", new Object[] { tenantIdentifier, cpds.getMaxPoolSize(),
				cpds.getNumBusyConnectionsAllUsers(), cpds.getNumIdleConnectionsAllUsers() });
		if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize()) {
			log.warn("Maximum number of connections opened");
		}
		if (cpds.getNumConnectionsAllUsers() == cpds.getMaxPoolSize() && cpds.getNumIdleConnectionsAllUsers() == 0) {
			log.error("Connection pool empty!");
		}

		// Connection connection = cpds.getConnection(tenantIdentifier, properties.getProperty(tenantIdentifier));
		Connection connection = cpds.getConnection();
		connection.createStatement().execute("USE " + tenantIdentifier);
		return connection;
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) {
		try {
			this.releaseAnyConnection(connection);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return ConnectionProvider.class.equals(unwrapType) || MultiTenantConnectionProvider.class.equals(unwrapType)
				|| MultiTenantConnectionProviderImpl.class.isAssignableFrom(unwrapType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		if (isUnwrappableAs(unwrapType)) {
			return (T) this;
		} else {
			throw new UnknownUnwrapTypeException(unwrapType);
		}
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
}

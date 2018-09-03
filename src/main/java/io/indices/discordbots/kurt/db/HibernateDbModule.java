package io.indices.discordbots.kurt.db;

import io.indices.discordbots.kurt.config.Config;
import io.indices.discordbots.kurt.module.IModule;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Log
@Singleton
public class HibernateDbModule implements IModule, DbProvider {

    @Inject
    private Config config;

    private SessionFactory sessionFactory;

    @Override
    public void enable() {
        boolean shouldCreateTable = config.db().initialTableCreation;
        if (shouldCreateTable) {
            config.db().initialTableCreation = false;
        }

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
          // credentials:
          .applySetting("hibernate.connection.username", config.db().username)
          .applySetting("hibernate.connection.password", config.db().password)
          .applySetting("hibernate.connection.driver_class", config.db().driver)
          .applySetting("hibernate.connection.url", config.db().url + "?useSSL=false")
          .applySetting("hibernate.dialect", config.db().dialect)

          // misc:
          .applySetting("hibernate.hbm2ddl.auto", shouldCreateTable ? "create" : "update")
          .applySetting("hibernate.connection.pool_size", config.db().poolSize + "")

          // [0]: https://vladmihalcea.com/2016/09/05/the-hibernate-enable_lazy_load_no_trans-anti-pattern/
          .applySetting("hibernate.enable_lazy_load_no_trans", true)
          .applySetting("hibernate.connection.autocommit", true)

          .build();

        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.buildMetadata();
        sessionFactory = metadata.buildSessionFactory();
    }

    @Override
    public void disable() {

    }
}

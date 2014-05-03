/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamer8.yosimce.server.hibernate.dao;

import java.net.SocketException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 * 
 * @author jorge
 */
public class HibernateUtil {

	private static SessionFactory sessionFactory;
//	private static SessionFactory sessionFactorySlave;

	static {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			// config file.
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);

			// Create the SessionFactory from standard (hibernate_slave.cfg.xml)
			// config file.
//			configuration = new Configuration();
//			configuration.configure("hibernate_slave.cfg.xml");
//			serviceRegistry = new ServiceRegistryBuilder().applySettings(
//					configuration.getProperties()).buildServiceRegistry();
//			sessionFactorySlave = configuration
//					.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static SessionFactory getSessionFactorySlave() {
//		return sessionFactorySlave;
		return sessionFactory;
	}

	public static void resetSessionFactory() {
		if (sessionFactory != null) {
			if (!sessionFactory.isClosed()) {
				sessionFactory.close();
			}
		}
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml)
			// config file.
			Configuration configuration = new Configuration();
			configuration.configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void rollback(Session s) {
		try {
			if (s.isOpen()) {
				s.getTransaction().rollback();
			}
		} catch (HibernateException ex) {
			// if (ex.indexOfThrowable(SocketException.class) != -1) {
			resetSessionFactory();
			// }
		}
	}

	public static void rollbackActiveOnly(Session s) {
		try {
			if (s.isOpen()) {
				if (s.getTransaction().isActive()) {
					s.getTransaction().rollback();
				}
			}
		} catch (HibernateException ex) {
			// if (ex.indexOfThrowable(SocketException.class) != -1) {
			resetSessionFactory();
			// }
		}
	}
}

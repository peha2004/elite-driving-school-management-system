package org.example.elite_driving_school_management_system.config;

import org.example.elite_driving_school_management_system.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;


    private FactoryConfiguration()  {
        Properties prop = new Properties();

        try {
            prop.load(
                    FactoryConfiguration.class.getClassLoader().getResourceAsStream("hibernate.properties")
            );

            Configuration configuration = new Configuration();
            configuration.addProperties(prop);

            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Student.class);
            configuration.addAnnotatedClass(Course.class);
            configuration.addAnnotatedClass(Instructor.class);
            configuration.addAnnotatedClass(Lesson.class);
            configuration.addAnnotatedClass(Payment.class);


            sessionFactory = configuration.buildSessionFactory();

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error in hibernate properties",e);
        }
    }

    public static FactoryConfiguration getInstance()  {
        return factoryConfiguration == null ?
                factoryConfiguration = new FactoryConfiguration()
                :
                factoryConfiguration;
    }

    public Session getSession(){
        Session session = sessionFactory.openSession();
        return session;
    }

    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}

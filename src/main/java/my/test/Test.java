package my.test;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;

public class Test {

    public static void main(String[] args) {
        //builder.setFile(new File("config.xml"));
        try {
            DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder("config-definition.xml");
            Configuration config = builder.getConfiguration(true);
            System.out.println(config.getString("dspace.foo"));
            System.out.println(config.getString("dspace.name"));
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

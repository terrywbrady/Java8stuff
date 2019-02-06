package my.test;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.EnvironmentConfiguration;

public class Test {

    public static void main(String[] args) {
        //builder.setFile(new File("config.xml"));
        try {
            DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder("config-definition.xml");
            Configuration config = builder.getConfiguration(true);
            for(Iterator<String> keys = config.getKeys(); keys.hasNext(); ) {
                System.out.println("TBTB1 "+keys.next());
            }
            
            System.out.println("");
            
            for(Iterator<String> keys=new EnvironmentConfiguration().getKeys(); keys.hasNext();) {
                System.out.println("TBTB2 "+keys.next());
            }
            
            System.out.println("");
            
            for(String s: System.getenv().keySet()) {
                System.out.println("TBTB3"+s);
            }
            
            System.out.println(config.getString("dspace.foo"));
            System.out.println(config.getString("dspace.name"));
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

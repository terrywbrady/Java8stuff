package my.test;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.EnvironmentConfiguration;

public class Test {

    public static void main(String[] args) {
        Configuration config = new EnvironmentConfiguration();
        System.out.println(config.getString("dspace.foo"));
    }

}

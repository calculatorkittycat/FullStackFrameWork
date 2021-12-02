package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();


    static{
        try {
            FileInputStream file = new FileInputStream("config.properties");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("Properties File not found");
        }
    }


    public static String read(String keyWord){
        return properties.getProperty(keyWord);
    }


}

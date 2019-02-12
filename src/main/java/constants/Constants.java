package constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Constants {
    final Properties prop = new Properties();
    public int massTransitcorePoolSize;
    public int massTransitMaxPoolSize;
    public int massTransitKeepAlive;
    public int maxLinePerFile;
    public int flushThreshold;
    public String inventoryStore;
    public String levelStore;
    public String RTLocation;
    public String bitFilePathLocation;
    public String runLOrB;
    public String sourceDBOrFile;
    public int numIterations;
    public String RT;

    public Constants() {
        try {
            try {
                prop.load(new FileInputStream("/tmp/config.properties"));
            }catch (Exception e){
                System.out.println("========== ERROR LOADING EXTERNAL CONFIG. LOADING DEFAULT ===========");
                prop.load(this.getClass().getResourceAsStream("config.properties"));
            }

            this.massTransitcorePoolSize = Integer.valueOf(prop.getProperty("massTransitcorePoolSize"));
            this.massTransitMaxPoolSize = Integer.valueOf(prop.getProperty("massTransitMaxPoolSize"));;
            this.massTransitKeepAlive = Integer.valueOf(prop.getProperty("massTransitKeepAlive"));;
            this.maxLinePerFile = Integer.valueOf(prop.getProperty("maxLinePerFile"));;
            this.flushThreshold = Integer.valueOf(prop.getProperty("flushThreshold"));;
            this.inventoryStore = prop.getProperty("inventoryStore");
            this.levelStore = prop.getProperty("levelStore");
            this.RTLocation= prop.getProperty("RTLocation");
            this.bitFilePathLocation = prop.getProperty("bitFilePathLocation");
            this.runLOrB = prop.getProperty("runLOrB");
            this.sourceDBOrFile = prop.getProperty("sourceDBOrFile");
            this.numIterations = Integer.valueOf(prop.getProperty("numIterations"));
            this.RT = prop.getProperty("RT");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public String levelStore(){
        return this.levelStore;
    }
    public String inventoryStore(){
        return this.inventoryStore + "-" +System.nanoTime();
    }
    public static void main(String[] args){
        System.out.println(new Constants().flushThreshold);
    }

}

package driver;

import constants.Constants;
import flush.paths.BitFilePath;
import flush.paths.LevelPath;
import pool.MassTransitThreadPoolConfig;
import pool.MasstransitThreadPool;
import pool.MassTransitThreadPoolProvider;
import tasks.DiscoveryTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/* In real code poller should be implemented to poll db for unfinished work and pick by configured limit.
   Failures on startup just have to mark all undergoing processes as unfinished.at a time pre-configured levels should be processed.


***************************************************** Improvements *****************************************************************

   - Have different pools for different tasks - (done)
   - check if stream list files keeps data in memory after processing as well?(FileVisitor also uses DirectoryStream probably best!)
   - create files at l-level and don't use in memory queue
   - create two MasstransitThreadPool one for l level and other for bitfilepath level (has producer consumer approach on crash tasks should be picked from DB)(done)
   - state system for recover

   - (status codes - Green, Yellow, Red)

*/

public class Driver {
    public static void main(String[] args) throws Exception {
        Constants constants = new Constants();
        MassTransitThreadPoolConfig l_config = new MassTransitThreadPoolConfig.Builder(constants.massTransitcorePoolSize, MassTransitThreadPoolProvider.POOL_TYPE.L_POOL)
                .maxPoolSize(constants.massTransitMaxPoolSize).keepAlive(constants.massTransitKeepAlive).build();
        MasstransitThreadPool l_pool = MassTransitThreadPoolProvider.getPool(l_config);

        MassTransitThreadPoolConfig b_config = new MassTransitThreadPoolConfig.Builder(constants.massTransitcorePoolSize, MassTransitThreadPoolProvider.POOL_TYPE.B_POOL)
                .maxPoolSize(constants.massTransitMaxPoolSize).keepAlive(constants.massTransitKeepAlive).build();
        MasstransitThreadPool b_pool = MassTransitThreadPoolProvider.getPool(b_config);

        try {

            if(constants.runLOrB.contains("L")) {
                Path levelPath = FileSystems.getDefault().getPath(constants.RTLocation);
                l_pool.execute(new DiscoveryTask(new LevelPath(levelPath, constants.RT)));
            }
            List<String> paths = getLevelPaths(constants.levelStore()+"-1");
            for (int counter = 0 ; counter < constants.numIterations; counter++){
                paths.forEach(path -> {
                            System.out.println("processing path - " + path);
                            Path bitFilePath = FileSystems.getDefault().getPath(path);
                            b_pool.execute(new DiscoveryTask(new BitFilePath(bitFilePath, constants.RT)));
                        }
                );
            }

        }finally {
            b_pool.shutdown();
            l_pool.shutdown();
        }
    }

    public static List<String> getLevelPaths(String path){
        List<String> paths = new ArrayList<>();
        try {

            File f = new File(path);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                paths.add(readLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return paths;
    }
}

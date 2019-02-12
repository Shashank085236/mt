package flush;

import constants.Constants;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Objects;


public class OnDemandInventoryFilesWriterImpl extends FlushStore {

    private PrintWriter mCurrentFileWriter = null;
    private Integer mCurrentFileCount = Integer.valueOf(1);
    private Long mCurrentLineCount = Long.valueOf(1);
    private String name ="";
    private boolean isRollOver;
    Constants constants = new Constants();
    
    public OnDemandInventoryFilesWriterImpl(String fqfn, boolean isRollOver){
        Objects.requireNonNull(fqfn);
    	this.name = fqfn;
    	this.isRollOver = isRollOver;
    }
    
    /**
     * Check for the no of line and if exceeds the max allowed, create a new file
     */
    protected void checkAndCreateNewFile() throws IOException{
    	if (mCurrentFileWriter==null || mCurrentLineCount > constants.maxLinePerFile){
             //close the current file store
             this.close();
             String mCurrentFileName = name;
             if(isRollOver){
            	 mCurrentFileName = mCurrentFileName+"-"+mCurrentFileCount;
            	 //Create a new file
            	 mCurrentFileCount++;
             }

             try{
                 debug("Creating store instance...");
                 mCurrentFileWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                         new FileOutputStream(mCurrentFileName), "UTF-8")));
                 mCurrentLineCount = Long.valueOf(1);
               
             }catch (IOException ioe){
                 System.out.println("Error occured while creating the file - "+mCurrentFileName);
                 if (mCurrentFileWriter != null)
                    mCurrentFileWriter.close();
                 mCurrentFileWriter = null;
                 throw ioe;
             }
        }
    }

    /**
     * store the data to the current file. If the file exceeds the max line allowed
     * create a new file and store it.
     */
    public void store(String data) throws IOException{
        checkAndCreateNewFile();
        mCurrentFileWriter.println(data);
        if(mCurrentLineCount%constants.flushThreshold == 0){
        	mCurrentFileWriter.flush();
        }
        mCurrentLineCount++;
    }

    /**
     * Close the result writing process
     */
    
    public void close() {

        if (mCurrentFileWriter != null) {
            mCurrentFileWriter.flush();
            mCurrentFileWriter.close();
        }
         mCurrentFileWriter = null;
    }

    private void debug(String message){
/*        if(logger.isDebugEnabled()){
            logger.debug(message);
        }*/
    }

}

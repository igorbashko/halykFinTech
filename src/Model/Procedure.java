/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author igorbashka
 */
public class Procedure {
    private String name;
    private long startTime;
    private long finishTime;
    private long expectedTime;
    private long realTime;
    private long error;
    
    public Procedure(String name,
            long expectedTime){
        this.name = name;
        this.startTime = System.currentTimeMillis();
        this.expectedTime = expectedTime;
    }
    
    private void setRealTime(){
        long realTime = finishTime - startTime;
        this.realTime = realTime;
    }
    public void setError(){
        this.error = realTime - expectedTime;
    }
    public String getName(){
        return name;
    }
    public void setFinishTime(){
        this.finishTime = System.currentTimeMillis();
        setRealTime();
    }
    public long getRealTime(){
        return realTime;
    }
    public long getError(){
        return error;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author igorbashka
 */
public class Queue {
    private List<Procedure> deposit;
    private List <Procedure> credit;
    private List <Procedure> Other;
    private List <Procedure> depositFinished;
    private List <Procedure> creditFinished;
    private List <Procedure> otherFinished;
    private List<Procedure>[] bigStack;
    
    public Queue(){
    this.deposit = new ArrayList<>();
    this.credit = new ArrayList<>();
    this.Other = new ArrayList<>();
    this.depositFinished = new ArrayList<>();
    this.creditFinished = new ArrayList<>();
    this.otherFinished = new ArrayList<>();
    this.bigStack = new ArrayList[6];
    this.bigStack[0] = this.deposit;
    this.bigStack[1] = this.credit;
    this.bigStack[2] = this.Other;
    this.bigStack[3] = this.depositFinished;
    this.bigStack[4] = this.creditFinished;
    this.bigStack[5] = this.otherFinished;
    }
    private List <Procedure> getCorrectList(String name){
        int number;
        if(name.equals("Deposit"))
            number = 0;
        else if(name.equals("Credit"))
            number = 1;
        else if(name.equals("Other"))
            number = 2;
        else if(name.equals("depositFinished"))
            number = 3;
        else if (name.equals("creditFinished"))
            number = 4;
        else if(name.equals("otherFinished"))
            number = 5;
        else
            number = 0;
        return this.bigStack[number];
    }
    private String getNameFinArray(String name){
        if(name.equals("Deposit"))
            return "depositFinished";
        else if(name.equals("Credit"))
            return "creditFinished";
        else if(name.equals("Other"))
            return "otherFinished";
        else
            return "";
    }
    public void addToQueue(Procedure procedure){
        String name = procedure.getName();
        getCorrectList(name).add(procedure);
    }
    
    public void removeFromQueue(String name){
       Procedure finished = getCorrectList(name).get(0);
       finished.setFinishTime();
       getCorrectList(name).remove(0);
       getCorrectList(getNameFinArray(name)).add(finished);
    }
    public long calculateExpectedTime(List <Procedure> procedureList){
        long estimateTime = 0;
        long estimateError = 0;
        for(Procedure proc : procedureList){
            estimateTime +=proc.getRealTime();
            proc.setError();
            estimateError +=proc.getError();
        }
        long expectedTime = 8000;
        if(!procedureList.isEmpty()){
        estimateTime = estimateTime/procedureList.size();
        estimateError = estimateError/procedureList.size();
        expectedTime = estimateTime + estimateError;
        }
        return expectedTime;
    }
    
   public long getExpectedTime(String name){
       long expectedTime = 4000;
       if(getCorrectList(name).size() !=0){
       int numberOfCustomers = getCorrectList(name).size();
        String nameFinished = getNameFinArray(name);
        expectedTime = calculateExpectedTime(getCorrectList(nameFinished))*
                numberOfCustomers;
       }
        return expectedTime;
    }
   
   public int getNumOfCustomers(String name){
       return getCorrectList(name).size();
   }
}
    

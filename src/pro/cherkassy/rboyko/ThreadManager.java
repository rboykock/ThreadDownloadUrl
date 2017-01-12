package pro.cherkassy.rboyko;

import pro.cherkassy.rboyko.interfaces.IDataPorvaider;
import pro.cherkassy.rboyko.interfaces.IStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by rboyko on 11.01.17.
 */
public class ThreadManager {
    protected IDataPorvaider dataPorvaider;
    protected IStorage storage;
    protected int count=0;
    public ThreadManager(IDataPorvaider dataPorvaider,IStorage storage,int count){
        this.dataPorvaider=dataPorvaider;
        this.storage=storage;
        this.count=count;
    }

    public void start(){
        if(count==0)
            return;
        Thread[] pool=new Thread[count];
        for (int i=0;i<count;i++) {
            pool[i] = new Thread(() -> {
                InputStream inputStream=null;
                BufferedReader bufferedReader;
                StringBuilder text;
                String line;
                String urlString;
                while (true) {
                    synchronized (dataPorvaider) {
                       urlString = dataPorvaider.next();
                    }
                    if (urlString == "")
                        return;
                    text= new StringBuilder();
                    try {
                        inputStream = new URL(urlString).openStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        while ((line = bufferedReader.readLine()) != null) {
                            text.append(line);
                        }
                        synchronized (storage) {
                            storage.add(urlString + " " + Main.Utils.md5(text.toString())+"\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (inputStream != null)
                                inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            pool[i].start();
        }
        for(Thread thread:pool)
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}

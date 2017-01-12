package pro.cherkassy.rboyko;

import pro.cherkassy.rboyko.interfaces.IStorage;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by rboyko on 11.01.17.
 */
public class Storage implements IStorage {

    protected FileWriter fileWriter;

    public Storage(String filename){
        try {
            fileWriter=new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(String string) {
        try {
            fileWriter.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

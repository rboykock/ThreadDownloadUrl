package pro.cherkassy.rboyko;

import pro.cherkassy.rboyko.interfaces.IDataPorvaider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/**
 * Created by rboyko on 11.01.17.
 */
public class DataProvaider implements IDataPorvaider {

    protected List<String> lines;
    private int counter=0;

    public DataProvaider(String filename){
        try {
            lines=Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String next() {
        if(counter<lines.size()){
            return lines.get(counter++);
        }
        return "";
    }
}

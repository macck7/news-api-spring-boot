package com.navi.mynewsservice.model;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    public List<String> getAllData(String file){
        List<String> data = new ArrayList<>();
        String path = "src/main/resources/static/"+file;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    public void addData(String file, String value) {
        String path = "src/main/resources/static/"+file;
        try (FileWriter fileWriter = new FileWriter(path, true)) {
            fileWriter.append(value).append("\n"); // Append the value and a new line
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

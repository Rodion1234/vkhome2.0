package ru.markov.vkproject.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.markov.vkproject.entity.UserF;

public class FileReaderDB {

    public List<UserF> getUsers(File file) {
        FileReader fr = null;
        List<UserF> users = new ArrayList<>();
        try {

            fr = new FileReader(file);
            Scanner scan = new Scanner(fr);

            while (scan.hasNextLine()) {
                UserF user = new UserF(scan.nextLine().split("\t")[0]);
                users.add(user);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReaderDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(FileReaderDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return users;
    }
}

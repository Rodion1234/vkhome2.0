package ru.markov.vkproject.database;

import java.io.BufferedWriter;
import java.io.File;
import java.util.List;
import ru.markov.vkproject.entity.UserF;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileWriterDB {


    public void writeUsers(List<UserF> users, String group, File file) {
        FileWriter nFile = null;
        BufferedWriter bufferWriter = null;
        try {
            nFile = new FileWriter(file,true);
            bufferWriter = new BufferedWriter(nFile);
            for (UserF user : users) {
                bufferWriter.write(user.getId() + "\t" + group + "\n");
            }

        } catch (IOException ex) {
            Logger.getLogger(FileWriterDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bufferWriter != null) {
                    bufferWriter.close();
                }
                if (nFile != null) {
                    nFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileWriterDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeFriends(List<UserF> friends, File file) {
        FileWriter nFile = null;
        BufferedWriter bufferWriter = null;
        try {
            nFile = new FileWriter(file,true);
            bufferWriter = new BufferedWriter(nFile);
            for (UserF user : friends) {
                if (user.getUsers() != null) {
                    for (Integer user1 : user.getUsers()) {
                        {
                            if (!user.getUsers().isEmpty()) {
                                bufferWriter.write(user.getId() + "\t" + user1 + "\n");
                            }
                        }
                    }

                }
            }

        } catch (IOException ex) {
            Logger.getLogger(FileWriterDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (bufferWriter != null) {
                    bufferWriter.close();
                }
                if (nFile != null) {
                    nFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileWriterDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

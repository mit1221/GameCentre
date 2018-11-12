package fall2018.csc2017.slidingtiles;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializeDemo {


    public static void main(String[] args) {

        {
            ArrayList<User> al = new ArrayList<>();
            al.add(new User("user1", 123456));
            System.out.println("...");

            try {
                FileOutputStream fos = new FileOutputStream("/Users/victor/Desktop/group_0536/Phase1/slidingtiles/app/src/main/java/fall2018/csc2017/slidingtiles/user1.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(al);
                oos.close();
                fos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.out.println(ioe);
            }
        }

        try {
            FileInputStream fis = new FileInputStream("/Users/victor/Desktop/group_0536/Phase1/slidingtiles/app/src/main/java/fall2018/csc2017/slidingtiles/user1.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<User> users = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
            User a = users.get(0);
            System.out.println(a.getPassword());


        } catch (IOException ioe) {
            ioe.printStackTrace();

        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();

        }


    }
}
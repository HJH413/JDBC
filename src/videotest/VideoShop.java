package videotest;

import javax.swing.*;
import java.awt.*;

public class VideoShop extends JFrame {

    CustomerView cv;
    VideoView vv;
    RentView rv;

    public VideoShop() {

        cv = new CustomerView();
        vv = new VideoView();
        rv = new RentView();

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("고객관리",cv);
        pane.addTab("비디오관리",vv);
        pane.addTab("대여관리",rv);

        pane.setSelectedIndex(2);

        add(pane, BorderLayout.CENTER);


        setBounds(300,300,600,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new VideoShop();

    } // end of main
} // end of VideoShop class

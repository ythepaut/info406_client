package fr.groupe4.clientprojet;

import fr.groupe4.clientprojet.utils.Location;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
    public int getX(double lon, int zoom) {
        double n = Math.floor(Math.pow(2, zoom));

        double xTile = n * (lon + 180.0) / 360.0;

        return (int) xTile;
    }

    public int getY(double lat, int zoom) {
        double n = Math.floor(Math.pow(2, zoom));

        double yTile = n * (1 - (Math.log(Math.tan(Math.toRadians(lat)) + 1.0 / Math.cos(Math.toRadians(lat))) / Math.PI)) / 2;

        return (int) yTile;
    }

    public Test() throws Exception {
        // Formula to get x,y is: n = 2 ^ zoom; xtile = n * ((lon_deg + 180) / 360); ytile = n * (1 - (log(tan(lat_rad) + sec(lat_rad)) / π)) / 2;

        int zoom = 8;

        double lon = 5.87762;
        double lat = 45.5722632;

        JFrame test = new JFrame("Test Google Maps");

        String imageUrl = "http://mt0.google.com/vt/lyrs=m&x=" + getX(lon, zoom) + "&y=" + getY(lat, zoom) + "&z=" + zoom;

        String destinationFile = Location.getPath() + "/tmp/image.jpg";

        try {
            InputStream in = new URL(imageUrl).openStream();
            Files.copy(in, Paths.get(destinationFile));
        }
        catch (IOException e) {
            System.out.println("Erreur ! L'image exite déjà ?");
        }

        test.add(new JLabel(new ImageIcon((new ImageIcon(Location.getPath() + "/tmp/image.jpg")).getImage().getScaledInstance(500, 500,
                java.awt.Image.SCALE_SMOOTH))));

        test.setVisible(true);
        test.pack();

        test.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                test.dispose();
            }
        });
    }
}

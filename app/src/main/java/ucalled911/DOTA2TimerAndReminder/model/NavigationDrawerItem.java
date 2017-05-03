package ucalled911.DOTA2TimerAndReminder.model;

import java.util.ArrayList;

import ucalled911.DOTA2TimerAndReminder.R;

public class NavigationDrawerItem {

    private String title;
    private int imageID;


    public static ArrayList<NavigationDrawerItem> getData() {
        ArrayList<NavigationDrawerItem> dataList = new ArrayList<>();
        int[] imageIds = getImages();
        String[] titles = getTitles();
        for (int i = 0; i < titles.length; i++) {
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setTitle(titles[i]);
            navItem.setImageID(imageIds[i]);
            dataList.add(navItem);
        }
        return dataList;
    }

    private static int[] getImages() {
        return new int[]{
                //R.drawable.ic_home,
                R.drawable.ic_info_black,
                R.drawable.ic_settings_black};
    }

    private static String[] getTitles() {
        return new String[]{
                //"Home",
                "Information",
                "Settings"
        };
    }

    // getters and setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public int getImageID() {
        return imageID;
    }

}

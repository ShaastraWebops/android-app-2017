package shaastra.com.android_app_2017;

/**
 * Created by gokulan on 27/11/16.
 */

public class EventListitem {
    String itemid, itemName, imageid, imagename;
    public EventListitem(String r, String s, String t, String u)
    {
        itemid = r;
        itemName = s;
        imageid = t;
        imagename = u;
    }
    public String getItemid() {return itemid;}
    public String getItemName()
    {
        return itemName;
    }
    public String getImageid()
    {
        return imageid;
    }
    public String getImagename()
    {
        return imagename;
    }
}
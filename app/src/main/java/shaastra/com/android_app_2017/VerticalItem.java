package shaastra.com.android_app_2017;

/**
 * Created by gokulan on 27/11/16.
 */

public class VerticalItem {
    String itemid, itemName;
    public VerticalItem(String r, String s)
    {
        itemid = r;
        itemName = s;
    }
    public String getItemid() {return itemid;}
    public String getItemName()
    {
        return itemName;
    }
}

package soccer.play;

/**
 *
 * @author mont_
 */
public class DisplayString implements IDisplayDataItem{
    
    String displayDetail;
    int id = 0;
    @Override
    public boolean isDetailAvailable() {
        return false;
    }

    @Override
    public String getDisplayDetail() {
        return this.displayDetail;
    }

    @Override
    public int getID() {
       return this.id;
    }

    @Override
    public String getDetailType() {
       return "String";
    }

    public void setDisplayDetail(String displayDetail) {
        this.displayDetail = displayDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public DisplayString(String displayDetail) {
        this.displayDetail = displayDetail;
    }
    
    
    
}

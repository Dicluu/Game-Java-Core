package Main.Utils.Timers;

import Main.Maps.Cell;
import Main.Maps.Map;
import Main.Objects.Materials.Material;
import Main.Utils.Messenger;
import Main.Utils.Annotations.NeedImprovement;

import java.util.Date;

@NeedImprovement(comment = "serialization for objectTimelines")
public class ObjectTimeline {

    private Long start, end;
    private Long interval;
    private Material material;
    private Map map;


    public ObjectTimeline(Material material, Map map, Cell cell) {
        this.start = new Date().getTime();
        this.interval = material.getMaterial().getRespawnTime();
        this.end = start+interval;
        this.map = map;
        this.material = material;
        cell.removeObject(material);
    }

    /**
     * verifies relevance of object timer
     * @return result of verifying
     */
    public boolean touch() {
        Date date = new Date();
        if (date.getTime() > end) {
            returnObject();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * returning object on map
     */
    private void returnObject() {
        Messenger.systemMessage("Object " + material + " returned on x = " + material.getX() + " y = " + material.getY() );
        map.setObject(material);
    }

}

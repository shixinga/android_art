package one.com.serializable;

import java.io.Serializable;

/**
 * Created by shixing on 2017/4/24.
 */

public class User implements Serializable {
    public String name;
    public int id;
    public boolean isMale;

    public User( int id,String name,boolean isMale) {
        this.name = name;
        this.id = id;
        this.isMale = isMale;
    }


}

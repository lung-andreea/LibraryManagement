package Domain;

import java.util.ArrayList;

public abstract class BaseEntity<ID> {
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getId().toString();
    }

    public abstract BaseEntity<ID> constructor(ArrayList<String> items);
}
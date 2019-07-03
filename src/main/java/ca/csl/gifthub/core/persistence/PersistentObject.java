package ca.csl.gifthub.core.persistence;

import java.io.Serializable;

public abstract class PersistentObject<K extends Serializable> implements Serializable {

    private static final long serialVersionUID = 4265380127014869397L;

    public abstract K getId();

    public abstract void setId(K id);

}

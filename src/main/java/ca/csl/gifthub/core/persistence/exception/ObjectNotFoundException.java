package ca.csl.gifthub.core.persistence.exception;

import java.io.Serializable;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1404345318954813116L;

    public ObjectNotFoundException(Serializable id, @SuppressWarnings("rawtypes") Class clazz) {
        super("id '" + id.toString() + "' for object of type " + clazz.getName() + " was not found");
    }
}

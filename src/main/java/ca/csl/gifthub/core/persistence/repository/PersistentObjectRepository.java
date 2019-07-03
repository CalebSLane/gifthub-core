package ca.csl.gifthub.core.persistence.repository;

import java.io.Serializable;

import ca.csl.gifthub.core.persistence.PersistentObject;

/**
 * @author caleb
 *
 * @param <T> the type of PersistentObject this is for
 * @param <K> The type of the PersistentObject's id field
 */
public interface PersistentObjectRepository<T extends PersistentObject<K>, K extends Serializable> {

}

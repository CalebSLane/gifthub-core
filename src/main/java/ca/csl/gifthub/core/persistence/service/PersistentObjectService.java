package ca.csl.gifthub.core.persistence.service;

import java.io.Serializable;
import java.util.List;

import ca.csl.gifthub.core.persistence.PersistentObject;

/**
 * @author caleb
 *
 * @param <T> the type of PersistentObject this is for
 * @param <K> The type of the PersistentObject's id field
 */
public interface PersistentObjectService<T extends PersistentObject<K>, K extends Serializable> {

    /**
     * @param id
     * @return the baseObject corresponding with the id or a new object s
     */
    T getById(K id);

    /**
     * @return all data type for the baseObject type
     */
    List<T> getAll();

    /**
     * @param baseObject the data to insert
     * @return the id of the inserted baseObject
     */
    K insert(T baseObject);

    /**
     * @param baseObjects the data to insert
     * @return the ids of the inserted baseObjects
     */
    List<K> insertAll(List<T> baseObjects);

    /**
     * @param baseObject the new data to update the database with. Will insert if it
     *                   doesn't already exist
     * @return the baseObject as it was saved to the database
     */
    T save(T baseObject);

    /**
     * @param baseObjects the new data to update the database with. Will insert if
     *                    it doesn't already exist
     * @return the baseObjects as they were saved to the database
     */
    List<T> saveAll(List<T> baseObjects);

    /**
     * @param baseObject the new data to update the database with. Must have an id
     *                   parameter
     * @return the baseObject as it was saved to the database
     */
    T update(T baseObject);

    /**
     * @param baseObjects the new data to update the database with. Must have an id
     *                    parameter
     * @return the baseObjects as they were saved to the database
     */
    List<T> updateAll(List<T> baseObjects);

    /**
     * @param baseObject the data to delete from the database. Must have primary key
     *                   fields filled in
     */
    void delete(T baseObject);

    void deleteById(K id);

    /**
     * @param baseObjects List of all baseObjects to delete from the database. Must
     *                    have primary key fields filled in
     */
    void deleteAll(List<T> baseObjects);

    void deleteAllById(List<K> ids);

    List<K> deleteAllById(List<K> ids, boolean batchDeleteOnly);

    /**
     * @return the number of rows
     */
    long getCount();

}

package ca.csl.gifthub.core.persistence.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ca.csl.gifthub.core.persistence.PersistentObject;
import ca.csl.gifthub.core.persistence.exception.ObjectNotFoundException;
import ca.csl.gifthub.core.persistence.repository.PersistentObjectRepository;

@Transactional
public abstract class PersistentObjectServiceImp<T extends PersistentObject<K>, K extends Serializable, R extends PersistentObjectRepository<T, K> & JpaRepository<T, K>>
        implements PersistentObjectService<T, K> {

    private static final Logger LOG = LogManager.getLogger(PersistentObjectServiceImp.class);

    private final Class<T> classType;
    private final R baseRepository;

    public PersistentObjectServiceImp(Class<T> clazz, R baseRepository) {
        this.classType = clazz;
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(K id) {
        return this.baseRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, this.classType));
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return this.baseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCount() {
        return this.baseRepository.count();
    }

    @Override
    public K insert(T baseObject) {
        this.baseRepository.save(baseObject);
        return baseObject.getId();
    }

    @Override
    public List<K> insertAll(List<T> baseObjects) {
        List<K> ids = new ArrayList<>();
        for (T baseObject : baseObjects) {
            ids.add(this.insert(baseObject));
        }
        return ids;
    }

    @Override
    public T save(T baseObject) {
        return this.baseRepository.save(baseObject);
    }

    @Override
    public List<T> saveAll(List<T> baseObjects) {
        List<T> resultObjects = new ArrayList<>();
        for (T baseObject : baseObjects) {
            resultObjects.add(this.save(baseObject));
        }
        return resultObjects;
    }

    @Override
    public T update(T baseObject) {
        this.getById(baseObject.getId());
        return this.baseRepository.save(baseObject);
    }

    @Override
    public List<T> updateAll(List<T> baseObjects) {
        List<T> resultObjects = new ArrayList<>();
        for (T baseObject : baseObjects) {
            resultObjects.add(this.update(baseObject));
        }
        return resultObjects;
    }

    @Override
    public void delete(T baseObject) {
        this.getById(baseObject.getId());
        this.baseRepository.delete(baseObject);
    }

    @Override
    public void deleteById(K id) {
        T oldObject = this.getById(id);
        this.baseRepository.delete(oldObject);
    }

    @Override
    public void deleteAll(List<T> baseObjects) {
        for (T baseObject : baseObjects) {
            this.delete(baseObject);
        }
    }

    @Override
    public void deleteAllById(List<K> ids) {
        for (K id : ids) {
            this.deleteById(id);
        }
    }

    @Override
    public List<K> deleteAllById(List<K> ids, boolean batchDeleteOnly) {
        List<K> successfullyDeleted = new ArrayList<>();
        for (K id : ids) {
            try {
                this.deleteById(id);
                successfullyDeleted.add(id);
            } catch (RuntimeException e) {
                LOG.error("could not delete user with id: " + id, e);
                if (batchDeleteOnly) {
                    throw e;
                }
            }
        }
        return successfullyDeleted;
    }

}

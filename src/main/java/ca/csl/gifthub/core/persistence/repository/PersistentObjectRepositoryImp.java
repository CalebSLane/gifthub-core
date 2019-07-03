package ca.csl.gifthub.core.persistence.repository;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import ca.csl.gifthub.core.persistence.PersistentObject;

@Repository
public class PersistentObjectRepositoryImp<T extends PersistentObject<K>, K extends Serializable>
        implements PersistentObjectRepository<T, K> {

}

package com.dooc.health.infrastructure;

import com.dooc.health.models.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByPersonId(int personId);

    Address findAddressByPersonCpf(String cpf);

    Address findAddressByPersonEmail(String email);

    Address getAddressByZipCodeLike(String zipCode);

    Address getAddressByIdPerson(int idPerson);
    Address getAddressByID(int idAddress);
}

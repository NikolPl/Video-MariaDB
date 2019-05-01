package cz.czechitas.webapp.repository;

import java.sql.*;
import java.util.*;
import org.mariadb.jdbc.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;
import cz.czechitas.webapp.domain.*;

public class CustomerRepository {

    private MariaDbDataSource konfiguraceDb;
    JdbcTemplate dotazovac;
    RowMapper<Customer> mapovacZakazniku;

    public CustomerRepository() {
        try {
            konfiguraceDb = new MariaDbDataSource();
            konfiguraceDb.setUserName("student");
            konfiguraceDb.setPassword("password");
            konfiguraceDb.setUrl("jdbc:mysql://localhost:3306/VideoBoss");

            dotazovac = new JdbcTemplate(konfiguraceDb);

            mapovacZakazniku = BeanPropertyRowMapper.newInstance(Customer.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> findAll() {
        return dotazovac.query(
                "select * from Customers where Deleted = false order by ID, Lastname, Firstname", mapovacZakazniku);
    }

    public Customer findOne(Long id) {
        return dotazovac.queryForObject(
                "select * from Customers where ID = ? and Deleted = false", mapovacZakazniku, id);
    }

    public Customer save(Customer novyZakaznik) {

          if (novyZakaznik.getId() == 0L)
          {
              return ulozNovy(novyZakaznik);
          }
          else {
              return zmen(novyZakaznik);
          }
    }

    private Customer ulozNovy(Customer zaznamKUlozeni) {
        Customer zakaznik = clone(zaznamKUlozeni);
        GeneratedKeyHolder drzakNaVygenerovanyKlic = new GeneratedKeyHolder();
        String sql = "INSERT INTO Customers (Firstname, Lastname, Address, Version) " +
                "VALUES (?, ?, ?, ?)";
        dotazovac.update((Connection con) -> {
                    PreparedStatement prikaz = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    prikaz.setString(1, zakaznik.getFirstName());
                    prikaz.setString(2, zakaznik.getLastName());
                    prikaz.setString(3, zakaznik.getAddress());
                    prikaz.setLong(4, zakaznik.getVersion());
                    return prikaz;
                },
                drzakNaVygenerovanyKlic);
        zakaznik.setId(drzakNaVygenerovanyKlic.getKey().longValue());
        return zakaznik;
        
    }

    private Customer zmen(Customer zaznamKeZmene){
        Customer zakaznik = clone(zaznamKeZmene);
        dotazovac.update(
                "UPDATE Customers SET Firstname = ?, Lastname = ?, Address = ?, Version = Version + 1 WHERE ID = ? AND Version = ?",
                zakaznik.getFirstName(),
                zakaznik.getLastName(),
                zakaznik.getAddress(),
                zakaznik.getId(),
                zakaznik.getVersion());
        zakaznik.setVersion(zakaznik.getVersion() + 1);
        return zakaznik;
    }

    public void delete(Long id, Long version) {
        dotazovac.update(
                "UPDATE Customers SET Deleted = TRUE, Version = Version + 1 WHERE ID = ? AND Version = ?",
                id,
                version);
    }

    private Customer clone(Customer puvodni){
        return new Customer(puvodni.getId(),puvodni.getFirstName(),puvodni.getLastName(),puvodni.getAddress(),puvodni.isDeleted(),puvodni.getVersion());

    }

}

package com.bigbasket.data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component

public interface BigBasketRepository extends CrudRepository<BigBasketEntity, Integer>
{
    long deleteAllByDateEquals(String date);
}

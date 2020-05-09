package com.bigbasket.data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component

public interface BigBasketRepository extends CrudRepository<BigBasketEntity, Integer>
{

}

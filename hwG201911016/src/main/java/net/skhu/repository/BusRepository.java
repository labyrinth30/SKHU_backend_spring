package net.skhu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer>  {

    Bus findByBusNo(String busNo);

    Page<Bus> findByBusNoOrFirstStopStartsWithOrCategoryCategoryNameStartsWith(
            String busNo, String firstStop, String categoryName, Pageable pageable);
}


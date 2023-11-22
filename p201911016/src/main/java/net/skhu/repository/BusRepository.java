package net.skhu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer>  {
	// 버스 번호로 검색
	List<Bus> findByBusNo(String busNo);
	// 출발역으로 검색
	List<Bus> findByFirstStop(String firstStop);
	// 카테고리id로 검색
	List<Bus> findByCategoryId(int categoryId);
	// 카테고리 이름으로 검색
	List<Bus> findByCategoryCategoryName(String categoryName);
	// 첫차 시간을 기준으로 오름차순으로 전체 목록 조회
    List<Bus> findAllByOrderByFirstBusAsc();
}

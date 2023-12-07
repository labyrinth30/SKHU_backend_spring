package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.skhu.entity.Bus;
import net.skhu.model.BusEdit;
import net.skhu.model.Pagination;
import net.skhu.repository.BusRepository;

@Service
public class BusService {

    @Data @AllArgsConstructor
    public class Order {
        int value;
        String label;
    }

    Order[] orders = new Order[] {
        new Order(0, "정렬 순서"),
        new Order(1, "버스번호 오름차순"),
        new Order(2, "버스번호 내림차순"),
        new Order(3, "첫차 오름차순"),
        new Order(4, "버스종류 오름차순")
    };

    Sort[] sorts = new Sort[] {
        Sort.by(Sort.Direction.ASC, "id"),
        Sort.by(Sort.Direction.ASC, "busNo"),
        Sort.by(Sort.Direction.DESC, "busNo"),
        Sort.by(Sort.Direction.ASC, "firstBus"),
        Sort.by(Sort.Direction.ASC, "category.categoryName"),
    };

    @Autowired BusRepository busRepository;
    ModelMapper modelMapper = new ModelMapper();

    public Order[] getOrders() {
        return orders;
    }

    public BusEdit findOne(int id) {
    	Bus busEntity = busRepository.findById(id).get();
        return toEditModel(busEntity);
    }

    public Bus findByBusNo(String busNo) {
        return busRepository.findByBusNo(busNo);
    }

    public List<Bus> findAll(Pagination pagination) {
        int orderIndex = pagination.getOd();
        PageRequest pageRequest = PageRequest.of(pagination.getPg() - 1,
                pagination.getSz(), sorts[orderIndex]);
        Page<Bus> page;
        if (pagination.getSt().length() == 0)
            page = busRepository.findAll(pageRequest);
        else
            page = busRepository.findByBusNoOrFirstStopStartsWithOrCategoryCategoryNameStartsWith(
                    pagination.getSt(), pagination.getSt(), pagination.getSt(), pageRequest);
        pagination.setRecordCount((int)page.getTotalElements());
        return page.getContent();
    }

    public void insert(BusEdit busEdit, BindingResult bindingResult,
            Pagination pagination) throws Exception {
        if (hasErrors(busEdit, bindingResult))
            throw new Exception("입력 데이터 오류");
        Bus bus = toEntity(busEdit);
        busRepository.save(bus);
          pagination.setSt("");
          pagination.setOd(0);
        int lastPage = (int)Math.ceil((double)busRepository.count() / pagination.getSz());
        pagination.setPg(lastPage);
    }

    public void update(BusEdit busEdit,
            BindingResult bindingResult) throws Exception {
        if (hasErrors(busEdit, bindingResult))
            throw new Exception("입력 데이터 오류");
        Bus bus = toEntity(busEdit);
        busRepository.save(bus);
    }

    public void delete(int id) {
        busRepository.deleteById(id);
    }

    public Bus toEntity(BusEdit busEdit) {
        return modelMapper.map(busEdit, Bus.class);
    }

    public BusEdit toEditModel(Bus busDto) {
        return modelMapper.map(busDto, BusEdit.class);
    }

    public boolean hasErrors(BusEdit busEdit, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return true;
        Bus bus2 = findByBusNo(busEdit.getBusNo());
        if (bus2 != null && bus2.getId() != busEdit.getId()) {
            bindingResult.rejectValue("busNo", null, "버스번호가 중복됩니다.");
            return true;
        }
        return false;
    }
}


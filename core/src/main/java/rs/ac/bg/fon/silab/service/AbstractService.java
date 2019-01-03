package rs.ac.bg.fon.silab.service;

import java.util.List;

public interface AbstractService<SaveDto, GetDto, DetailsDto> {

    void insert(SaveDto saveDto);

    void update(SaveDto saveDto, Integer id);

    void delete(Integer id);

    DetailsDto getById(Integer id);

    List<GetDto> getAll();

}

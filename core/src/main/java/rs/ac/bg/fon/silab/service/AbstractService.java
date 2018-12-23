package rs.ac.bg.fon.silab.service;

import java.util.List;

public interface AbstractService<SaveDTO, GetDTO> {

    void insert(SaveDTO saveDTO);

    void update(SaveDTO saveDTO, Integer id);

    void delete(Integer id);

    GetDTO getById(Integer id);

    List<GetDTO> getAll();

}

package com.example.socks.service;

import com.example.socks.model.Socks;
import com.example.socks.repository.SocksRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SocsService {

    private final SocksRepository socksRepository;

    public static final List<String> OPERATIONS = Arrays.asList("moreThan", "lessThan", "equal");

    public SocsService(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    /**
     * Добавление носков на склад
     * @param request - объект, содержащий цвет, состав и количество носков
     */
    public void addSocks(Socks request) {
        Socks socks = socksRepository.getSocksByColorAndCottonPart(request.getColor(), request.getCottonPart());
        if(socks == null) {
            socks = request;
        } else {
            socks.setQuantity(socks.getQuantity() + request.getQuantity());
        }
        socksRepository.save(socks);
    }

    /**
     * Изъятие носков со склада
     * @param request - объект, содержащий цвет, состав и количество носков
     * @return - возвращает true в случае успеха или false в случае, если носков с запрашиваемыми характеристиками
     * нет на складе или запрошено количество более, чем есть на складе.
     */
    public boolean takeSocks(Socks request) {
        Socks socks = socksRepository.getSocksByColorAndCottonPart(request.getColor(), request.getCottonPart());
        if(socks == null || request.getQuantity() > socks.getQuantity()) {
            return false;
        }
        socks.setQuantity(socks.getQuantity() - request.getQuantity());
        socksRepository.save(socks);
        return true;
    }

    /**
     * Получение инормации об остатках носков на складе
     * @param color - цвет носков
     * @param operation - операция сравнения (moreThan, lessThan, equal)
     * @param cottonPart - значение процента хлопка в составе носков из сравнения
     * @return - возвращает количество носков на складе, соответствущих заданным параметрам или 0
     */
    public Integer getSocks(String color, String operation, Integer cottonPart) {
        if(operation.equals(OPERATIONS.get(2))) { // equal
            Socks socks = socksRepository.getSocksByColorAndCottonPart(color, cottonPart);
            if (socks != null) {
                return socks.getQuantity();
            }
        }
        if(operation.equals(OPERATIONS.get(0))) { // moreThan
            List<Socks> socks = socksRepository.getSocksByColorAndMoreThenCottonPart(color, cottonPart);
            if (socks.size() > 0) {
                int count = 0;
                for (Socks sock : socks) {
                    count = count + sock.getQuantity();
                }
                return count;
            }
        }
        if(operation.equals(OPERATIONS.get(1))) { // lessThan
            List<Socks> socks = socksRepository.getSocksByColorAndLessThenCottonPart(color, cottonPart);
            if (socks.size() > 0) {
                int count = 0;
                for (Socks sock : socks) {
                    count = count + sock.getQuantity();
                }
                return count;
            }
        }
        return 0;
    }
}

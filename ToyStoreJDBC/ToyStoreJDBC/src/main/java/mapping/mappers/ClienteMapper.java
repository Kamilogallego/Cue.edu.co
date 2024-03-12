package mapping.mappers;

import mapping.dtos.ClienteDTO;
import model.cliente;

public class ClienteMapper {
    public static ClienteDTO mapFromModel(cliente cliente){
        return new ClienteDTO( cliente.getName(), cliente.getNumber_ID(), cliente.getDateBirth());

    }
    public static cliente mapFromDTO(ClienteDTO clienteDTO){
        return cliente.builder()
                .name(clienteDTO.name())
                .number_ID(clienteDTO.number_ID())
                .dateBirth(clienteDTO.dateBirth())
                .build();
    }
}

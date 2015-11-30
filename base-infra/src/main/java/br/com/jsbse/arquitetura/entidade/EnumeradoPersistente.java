package br.com.jsbse.arquitetura.entidade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.jsbse.arquitetura.servico.dto.EnumeradoDto;

public interface EnumeradoPersistente {

	public String getId();

	public String getDescricao();

	public static EnumeradoDto toEnumeradoDto(EnumeradoPersistente enumerado) {
		if (enumerado == null)
			return null;

		EnumeradoDto dto = new EnumeradoDto();
		dto.setId(enumerado.getId());
		dto.setDescricao(enumerado.getDescricao());
		return dto;
	}

	public static Collection<EnumeradoDto> toEnumeradoDto(EnumeradoPersistente[] values) {
		List<EnumeradoDto> lista = new ArrayList<>();
		for (EnumeradoPersistente enumerado : values) {
			lista.add(toEnumeradoDto(enumerado));
		}
		return lista;
	}
}

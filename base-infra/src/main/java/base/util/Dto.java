package base.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Dto extends HashMap<String, Object> {

	public Dto() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object get(Object key) {
		if (contemSubObjeto(key)) {
			String[] valores = ((String) key).split("[.]");

			// busca o valor solicitado dentro da hierarquia
			Object valor = null;
			for (String chave : valores) {
				if (valor == null) {
					valor = super.get(chave);
				} else {
					if (valor instanceof Map) {
						valor = ((Map) valor).get(chave);
					}
				}
			}

			return valor;

		}
		return super.get(key);
	}

	/**
	 * Verifica se foi solicitado o valor de um subelemento do dto. Exemplo: contaContabil: {id: 1, nid: 'Ativo'} dto.get("contaContabil:id") retorna 1
	 * (id da conta contÃ¡bil) dto.get("contaContabil:nid") retorna Ativo (nid da conta contÃ¡bil)
	 * 
	 * @param key
	 * @return true se foi solicitado um sub elemento.
	 */
	private boolean contemSubObjeto(Object key) {
		return key instanceof String && ((String) key).indexOf(".") != -1;
	}

	public Dto(int initialCapacity) {
		super(initialCapacity);
	}

	public void set(String key, Object value) {
		if (contemSubObjeto(key)) {
			String[] valores = ((String) key).split("[.]");

			// cria a hierarquia de objetos

			String chave = valores[0];

			Dto dtoFilho = (Dto) (containsKey(chave) ? get(chave) : new Dto());

			dtoFilho.put(valores[1], value);
			put(valores[0], dtoFilho);

		} else {
			put(key, value);
		}
	}

	public Boolean getBoolean(String key) {
		Object value = get(key);
		if (value instanceof Double) {
			return (Double) value == 1.0 ? true : (Double) value == 0.0 ? false : null;
		}
		return (Boolean) get(key);
	}

	// Esta funÃƒÂ§ÃƒÂ£o foi substituida por 'UtilService.getUnifarDate(Object
	// date)'.
	// Isto foi feito pois agora o Json aceita data como uma string no formato:
	// "dd-MM-yyyy HH:mm:ss"

	public Date getDate(String key) {
		return null;

		// Date result = null;
		// try {
		// DateTimeFormat dateTimeFormat =
		// DateTimeFormat.getFormat("dd-MM-yyyy HH:mm:ss");
		// result = dateTimeFormat.parse(key);
		// } catch (Exception e)
		// {
		// throw new RuntimeException(Msg.dtoDataInvalida);
		// }
		// return result;

		// Object value = get(key);
		// SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		// String strDate = (String) value;
		// try {
		// return sdf.parse(strDate);
		// } catch (ParseException e) {
		// throw new RuntimeException(Msg.dtoDataInvalida);
		// }
	}

	public Integer getInteger(String key) {
		Object value = get(key);
		if (value instanceof String) {
			return new Integer((String) value);
		} else if (value instanceof Double) {
			return ((Double) value).intValue();
		} else if (value instanceof BigInteger) {
			return new Integer(((BigInteger) value).intValue());
		} else {
			return (Integer) get(key);
		}
	}

	public Double getDouble(String key) {
		Object value = get(key);
		if (value instanceof String) {
			return new Double((String) value);
		} else if (value instanceof Integer) {
			return ((Integer) value).doubleValue();
		} else {
			return (Double) get(key);
		}
	}

	public BigDecimal getBigDecimal(String key) {
		Object value = get(key);
		if (value instanceof String) {
			return new BigDecimal((String) value);
		} else if (value instanceof Integer) {
			return new BigDecimal((Integer) value);
		} else {
			return (BigDecimal) value;
		}
	}

	public Long getLong(String key) {
		Object value = get(key);
		if (value instanceof String) {
			return new Double((String) value).longValue();
		} else if (value instanceof Integer) {
			return ((Integer) value).longValue();
		} else if (value instanceof Double) {
			return ((Double) value).longValue();
		} else {
			return (Long) value;
		}
	}

	public String getString(String key) {
		return (String) get(key);
	}

	public void set(Dto dto) {
		putAll(dto);
	}
}

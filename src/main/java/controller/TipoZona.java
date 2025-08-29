package controller;

public enum TipoZona {

	INGRESO(1),
	EGRESO(2),
	TRANSFORMACION(3);
	
	private Integer id;
	
	TipoZona(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	
	static public TipoZona getById(Integer id) {
		for (TipoZona tipo : values()) {
			if (tipo.getId() == id) {
				return tipo;
			}
		}
		return null;
	}
}

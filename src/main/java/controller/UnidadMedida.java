package controller;

public enum UnidadMedida {

	KG(1, "Kg", 1d),
	G(2, "g", 1000d),
	LB(3, "lb", 1/2.205),
	OZ(4, "oz", 1/35.274);
	
	private Integer id;
	private String tipoUnidad;
	private Double factorConversion;
	
	UnidadMedida(Integer id, String tipo, Double factor) {
		this.id = id;
		this.tipoUnidad = tipo;
		this.factorConversion = factor;
	}
	
	public Integer getId() {
		return id;
	}
	public Double getFactorConversion() {
		return this.factorConversion;
	}
	public String getTipoUnidad() {
		return this.tipoUnidad;
	}
	
	static public UnidadMedida getById(Integer id) {
		for (UnidadMedida unidad : values()) {
			if (unidad.getId() == id) {
				return unidad;
			}
		}
		return null;
	}
}

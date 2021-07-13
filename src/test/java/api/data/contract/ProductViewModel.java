package api.data.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductViewModel {
	@JsonProperty("productId")
  public int ProductId ;
	@JsonProperty("productName")
  public String ProductName ;

}

package labs.java.qt;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

/**
 * 
 * @author
 * @since 2018-05-14
 */
public class DwConf extends Configuration {

	@NotEmpty
	private String appName = "appName";
	
	@JsonProperty
	public String getAppName() {
		return appName;
	}
    @JsonProperty
	public void setAppName(String appName) {
		this.appName = appName;
	}
}

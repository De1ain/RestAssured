package POJO;

import java.util.List;

public class CoursesPOJO {

	private List<WebAutomationPOJO> webAutomation;
	private List<ApiPOJO> api;
	private List<MobilePOJO> mobile;

	public List<WebAutomationPOJO> getWebAutomation() {
		return webAutomation;
	}

	public void setWebAutomation(List<WebAutomationPOJO> webAutomation) {
		this.webAutomation = webAutomation;
	}

	public List<ApiPOJO> getApi() {
		return api;
	}

	public void setApi(List<ApiPOJO> api) {
		this.api = api;
	}

	public List<MobilePOJO> getMobile() {
		return mobile;
	}

	public void setMobile(List<MobilePOJO> mobile) {
		this.mobile = mobile;
	}

}

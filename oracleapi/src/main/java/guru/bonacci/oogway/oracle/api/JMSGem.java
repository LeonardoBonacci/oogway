package guru.bonacci.oogway.oracle.api;

import static java.lang.String.format;

import java.io.Serializable;

/**
 * Until jigsaw allows us to hide a class from those who use this library this does the work...
 */
public class JMSGem implements IGem, Serializable {

	private static final long serialVersionUID = -295422703255886286L;

	private String essence;

	public JMSGem() {
	}

	public JMSGem(String essence) {
		this.essence = essence;
	}

	@Override
	public String getEssence() {
		return essence;
	}

	@Override
	public void setEssence(String essence) {
		this.essence = essence;
	}
	
	@Override
    public String toString() {
        return format("JMSGem[essence='%s']", essence);
    }
}

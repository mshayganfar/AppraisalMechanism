
import java.awt.TextArea;

import jess.JessException;
import jess.Rete;
import jess.awt.TextAreaWriter;

public class Mechanisms {

	protected static Rete JessEngine = new Rete();
	
	protected static final String strAppraisalModuleTemplates = "templates/mental-states/mental-states-templates.clp";
	
	public static void initializeMentalStates() {
		try {
			JessEngine.batch("modules/module-definitions.clp");
			JessEngine.reset();
			JessEngine.batch(strAppraisalModuleTemplates);
			
		} catch (JessException e) {
			e.printStackTrace();
		}
	}
}

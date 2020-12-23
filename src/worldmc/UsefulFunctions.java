package worldmc;

import java.util.List;

public class UsefulFunctions {

	public static boolean matchesStringList(String toMatch, String configListLocation) {
		List<String> matchlist = WMC.plugin.getConfig().getStringList(configListLocation);

		if (matchlist.contains(toMatch)) {
			return true;
		}
		return false;
	}
}

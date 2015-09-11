import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.MontageCmd;

public class ConvertCmdSingleton {
	private static ConvertCmd cmd;
	private static CompositeCmd compositeCmd;
	private static MontageCmd montageCmd;
	private static String impath;

	public static String getIMPath() {
		if (impath == null) {
			Properties prop = new Properties();
			String fileName = "app.config";
			InputStream is;
			try {
				is = new FileInputStream(fileName);
				prop.load(is);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			impath = prop.getProperty("app.impath");
		}
		return impath;
	}

	public static ConvertCmd getConvertCmdInstance() {
		if (cmd == null) {
			cmd = new ConvertCmd();
			cmd.setSearchPath(getIMPath());
		}
		return cmd;
	}

	public static CompositeCmd getCompositeCmdInstance() {
		if (compositeCmd == null) {
			compositeCmd = new CompositeCmd();
			compositeCmd.setSearchPath(getIMPath());
		}
		return compositeCmd;
	}

	public static MontageCmd getMontageCmdInstance() {
		if (montageCmd == null) {
			montageCmd = new MontageCmd();
			montageCmd.setSearchPath(getIMPath());
		}
		return montageCmd;
	}

}

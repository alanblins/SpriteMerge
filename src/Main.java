import java.io.IOException;

import org.im4java.core.IM4JavaException;

public class Main {

	public static void main(String[] args) {
		try {
			ProcessSheet processSheet = new ProcessSheet();
			processSheet.readSpriteFolders(args[0]);
			processSheet.process();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IM4JavaException e) {
			e.printStackTrace();
		} 
	}

}
